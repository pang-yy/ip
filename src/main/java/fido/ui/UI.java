package fido.ui;

public class Ui {
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

    public Ui() {
        this("_", 60, 4);
    }

    public Ui(String divider, int dividerRepeat, int outputIndentLevel) {
        this.divider = divider.repeat(dividerRepeat);
        this.outputIndentLevel = outputIndentLevel;
    }

    public void hello() {
        this.printMessage(Ui.GREET_MESSAGE);
    }

    public void bye() {
        this.printMessage(Ui.BYE_MESSAGE);
    }

    public String formatMessage(String out) {
        return " ".repeat(this.outputIndentLevel) + divider + "\n" +
            " ".repeat(outputIndentLevel) +
                out.trim().replaceAll("\n", "\n" + " ".repeat(outputIndentLevel)) + "\n" +
            " ".repeat(outputIndentLevel) + divider;
    }

    public void printMessage(String out) {
        System.out.println(this.formatMessage(out));
    }
}
