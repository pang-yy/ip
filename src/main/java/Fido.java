import java.util.Scanner;

public class Fido {
    private final static String CHATBOT_NAME = "Fido";
    private final static String DIVIDER = "_".repeat(60);
    private final static int OUTPUT_INDENT_LEVEL = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputHandler iHandler = new InputHandler();

        printMessage(DIVIDER);
        printMessage(hello());
        printMessage(DIVIDER);
        
        sc.useDelimiter("\n")
            .tokens()
            .map(line -> line.trim())
            .takeWhile(line -> !line.equalsIgnoreCase("bye"))
            .forEach(line -> {
                printMessage(DIVIDER);
                try {
                    printMessage(iHandler.action(line.split(" ")));
                } catch (FidoException e) {
                    printMessage(e.getMessage());
                }
                printMessage(DIVIDER);
            });
        sc.close();

        printMessage(DIVIDER);
        printMessage(bye());
        printMessage(DIVIDER);
    }

    private static void printMessage(String out) {
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) +
            out.trim().replaceAll("\n", "\n" + " ".repeat(OUTPUT_INDENT_LEVEL)));
    }

    private static String hello() {
        //return String.format("Beep...Hello! I am %s. What can I do for you", CHATBOT_NAME);
        return "Hi, I am\n" +
            " ________ ___  ________  ________\n" + 
            "|\\  _____\\\\  \\|\\   ___ \\|\\   __  \\\n" +
            "\\ \\  \\__/\\ \\  \\ \\  \\_|\\ \\ \\  \\|\\  \\\n" +
            " \\ \\   __\\\\ \\  \\ \\  \\ \\\\ \\ \\  \\\\\\  \\\n" +
            "  \\ \\  \\_| \\ \\  \\ \\  \\_\\\\ \\ \\  \\\\\\  \\\n" +
            "   \\ \\__\\   \\ \\__\\ \\_______\\ \\_______\\\n" +
            "    \\|__|    \\|__|\\|_______|\\|_______|\n" +
            "What can I do for you?";
    }

    private static String bye() {
        return "Have a nice day!";
    }
}
