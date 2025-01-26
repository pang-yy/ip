public class UI {
    private final String divider;
    private final int outputIndentLevel;

    private static final String GREET_MESSAGE = "Hello, I am\n" +
        " ________  ___   ________   ________     \n" +
        "|\\  _____\\|\\  \\ |\\   ___ \\ |\\   __  \\    \n" +
        "\\ \\  \\__/ \\ \\  \\\\ \\  \\_|\\ \\\\ \\  \\|\\  \\   \n" +
        " \\ \\   __\\ \\ \\  \\\\ \\  \\ \\\\ \\\\ \\  \\\\\\  \\  \n" +
        "  \\ \\  \\_|  \\ \\  \\\\ \\  \\_\\\\ \\\\ \\  \\\\\\  \\ \n" +
        "   \\ \\__\\    \\ \\__\\\\ \\_______\\\\ \\_______\\\n" +
        "    \\|__|     \\|__| \\|_______| \\|_______|\n" +
        "What can I do for you?";
    
    private static final String BYE_MESSAGE = "Have a nice day!";

    UI() {
        this("_".repeat(60), 4);
    }

    UI(String divider, int outputIndentLevel) {
        this.divider = divider;
        this.outputIndentLevel = outputIndentLevel;
    }

    void hello() {
        this.printMessage(UI.GREET_MESSAGE);
    }

    void bye() {
        this.printMessage(UI.BYE_MESSAGE);
    }

    void printMessage(String out) {
        System.out.println(" ".repeat(outputIndentLevel) + divider);
        System.out.println(" ".repeat(outputIndentLevel) +
            out.trim().replaceAll("\n", "\n" + " ".repeat(outputIndentLevel)));
        System.out.println(" ".repeat(outputIndentLevel) + divider);
    }
}
