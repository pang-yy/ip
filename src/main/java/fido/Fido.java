package fido;

import java.io.IOException;
import java.util.Scanner;

import fido.exception.FidoException;
import fido.storage.Storage;
import fido.task.TaskList;
import fido.ui.Ui;

/**
 * The main class for {@code Fido} chatbot.
 */
public class Fido {
    private final Ui ui;
    private final Storage storage;
    private TaskList taskList;

    /**
     * Constructs a new {@code Fido} instance using the specified directory.
     * This constructor may throw an {@link IOException} if it fails
     * to access or initialise necessary path.
     *
     * @param dirName The directory name or path where the task file are located.
     * @throws IOException If an error occurs while initialising the specified path.
     */
    public Fido(String dirName) throws IOException, FidoException {
        this.ui = new Ui();
        this.storage = new Storage(dirName);
        this.taskList = new TaskList(this.storage);
    }

    /** Overloads constructor */
    public Fido() throws IOException, FidoException {
        this("data/task.txt");
    }

    /**
     * Runs the chatbot.
     * This method contains the primary chatbot loop.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        this.ui.hello();
        
        sc.useDelimiter("\n")
                .tokens()
                .takeWhile(line -> !line.trim().equalsIgnoreCase("bye"))
                .forEach(line -> {
                    try {
                        this.ui.printMessage(
                            this.taskList.action(line.trim().split(" "))
                        );
                    } catch (FidoException e) {
                        this.ui.printMessage(e.getMessage());
                    }
                });
        sc.close();
        
        this.ui.bye();
    }

    public String getResponse(String inputs) {
        if (inputs.trim().equals("bye")) {
            return this.ui.getBye();
        } else if (inputs.trim().equals("hello")) {
            return this.ui.getHello();
        }
        try {
            return this.taskList.action(inputs.trim().split(" "));
        } catch (FidoException e) {
            return e.getMessage();
        }
    }

    /**
     * Represents entry point of the chatbot.
     * This method initialises a new {@code Fido} instance with a path to data file,
     * and then calls {@link #run()} to start the cahtbot.
     * If an {@link IOException} occurs, it is caught here, and an error message
     * is printed and the program will terminates.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            new Fido("data/task.txt").run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (FidoException e) {
            System.out.println(e.getMessage());
        }
    }
}
