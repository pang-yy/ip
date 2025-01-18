import java.util.Scanner;

public class Fido {
    private final static String CHATBOT_NAME = "Fido";
    private final static String DIVIDER = "_".repeat(60);
    private final static int OUTPUT_INDENT_LEVEL = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        printMessage(DIVIDER);
        printMessage(hello());
        printMessage(DIVIDER);
        
        sc.useDelimiter("\n")
            .tokens()
            .map(line -> line.trim())
            .takeWhile(line -> !line.equalsIgnoreCase("bye"))
            .map(line -> echo(line))
            .forEach(out -> {
                printMessage(DIVIDER);
                printMessage(out);
                printMessage(DIVIDER);
            });
        sc.close();

        printMessage(DIVIDER);
        printMessage(bye());
        printMessage(DIVIDER);
    }

    public static void printMessage(String out) {
        System.out.println(" ".repeat(OUTPUT_INDENT_LEVEL) + out);
    }

    public static String hello() {
        return String.format("Beep...Hello! I am %s. What can I do for you", CHATBOT_NAME);
    }

    public static String bye() {
        return "Have a nice day!";
    }

    public static String echo(String cmd) {
        return cmd;
    }
}
