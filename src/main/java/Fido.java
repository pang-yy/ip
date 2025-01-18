import java.util.Scanner;

public class Fido {
    private final static String CHATBOT_NAME = "Fido";
    private final static String DIVIDER = "_".repeat(60);
    public final static int OUTPUT_INDENT_LEVEL = 4;

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
                printMessage(iHandler.action(line).map(x -> x).orElse(""));
                printMessage(DIVIDER);
            });
        sc.close();

        printMessage(DIVIDER);
        printMessage(bye());
        printMessage(DIVIDER);
    }

    private static void printMessage(String out) {
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) + out);
    }

    private static String hello() {
        return String.format("Beep...Hello! I am %s. What can I do for you", CHATBOT_NAME);
    }

    private static String bye() {
        return "Have a nice day!";
    }
}
