import java.io.IOException;
import java.util.Scanner;

public class Fido {
    private final static String DIVIDER = "_".repeat(60);
    private final static int OUTPUT_INDENT_LEVEL = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            InputHandler iHandler = new InputHandler();
            printMessage(hello());
            
            sc.useDelimiter("\n")
                .tokens()
                .takeWhile(line -> !line.trim().equalsIgnoreCase("bye"))
                .forEach(line -> {
                    try {
                        printMessage(iHandler.action(line.trim().split(" ")));
                    } catch (FidoException e) {
                        printMessage(e.getMessage());
                    }
                });
            sc.close();

            printMessage(bye());
        } catch (IOException e) {
            printMessage(e.getMessage());
        } catch (FidoException e) {
            printMessage(e.getMessage());
        }
    }

    private static void printMessage(String out) {
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) + DIVIDER);
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) +
            out.trim().replaceAll("\n", "\n" + " ".repeat(OUTPUT_INDENT_LEVEL)));
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) + DIVIDER);
    }

    private static String hello() {
        //return String.format("Beep...Hello! I am %s. What can I do for you", CHATBOT_NAME);
        return "Hello, I am\n" +
            " ________  ___   ________   ________     \n" +
            "|\\  _____\\|\\  \\ |\\   ___ \\ |\\   __  \\    \n" +
            "\\ \\  \\__/ \\ \\  \\\\ \\  \\_|\\ \\\\ \\  \\|\\  \\   \n" +
            " \\ \\   __\\ \\ \\  \\\\ \\  \\ \\\\ \\\\ \\  \\\\\\  \\  \n" +
            "  \\ \\  \\_|  \\ \\  \\\\ \\  \\_\\\\ \\\\ \\  \\\\\\  \\ \n" +
            "   \\ \\__\\    \\ \\__\\\\ \\_______\\\\ \\_______\\\n" +
            "    \\|__|     \\|__| \\|_______| \\|_______|\n" +
            "What can I do for you?";
    }

    private static String bye() {
        return "Have a nice day!";
    }
}
