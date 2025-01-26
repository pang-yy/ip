import java.io.IOException;
import java.util.Scanner;

public class Fido {

    private final UI ui;
    private final Storage storage;

    public static void main(String[] args) {
        try {
            new Fido("data/task.txt").run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    Fido(String dirName) throws IOException {
        this.ui = new UI();
        this.storage = new Storage(dirName);
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        this.ui.hello();
        try {
            TaskList tasklist = new TaskList(this.storage);
            sc.useDelimiter("\n")
                .tokens()
                .takeWhile(line -> !line.trim().equalsIgnoreCase("bye"))
                .forEach(line -> {
                    try {
                        this.ui.printMessage(tasklist.action(line.trim().split(" ")));
                    } catch (FidoException e) {
                        this.ui.printMessage(e.getMessage());
                    }
                });
            sc.close();
        } catch (FidoException e) {
            this.ui.printMessage(e.getMessage());
        }
        this.ui.bye();
    }
}
