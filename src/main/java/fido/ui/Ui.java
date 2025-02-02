package fido.ui;

/**
 * The {@code Ui} class handles all user interface operations.
 */
public class Ui {
    private static final String BYE_MESSAGE = "Have a nice day!";
    private static final String GREET_MESSAGE = "Hello, I am\n"
            + " ________  ___   ________   ________     \n"
            + "|\\  _____\\|\\  \\ |\\   ___ \\ |\\   __  \\    \n"
            + "\\ \\  \\__/ \\ \\  \\\\ \\  \\_|\\ \\\\ \\  \\|\\  \\   \n"
            + " \\ \\   __\\ \\ \\  \\\\ \\  \\ \\\\ \\\\ \\  \\\\\\  \\  \n"
            + "  \\ \\  \\_|  \\ \\  \\\\ \\  \\_\\\\ \\\\ \\  \\\\\\  \\ \n"
            + "   \\ \\__\\    \\ \\__\\\\ \\_______\\\\ \\_______\\\n"
            + "    \\|__|     \\|__| \\|_______| \\|_______|\n"
            + "What can I do for you?";

    private final String divider;
    private final int outputIndentLevel;

    /**
     * Constructs a default {@code Ui} instance.
     * This constructor construct {@code Ui} instance with predifined divider,
     * divider repeat count, and output indent level.
     * Internally calls {@link #Ui(String, int, int)} with default values:
     * {@code divider = "_"}, {@code dividerRepeat = 60}, and
     * {@outputIndentLevel = 10}
     */
    public Ui() {
        this("_", 60, 4);
    }

    /**
     * Constructs a new {@code Ui} instance.
     *
     * @param divider The string used as a divider.
     * @param dividerRepeat The number of times the divider string is repeated.
     * @param outputIndentLevel The number of spaces to indent output by.
     */
    public Ui(String divider, int dividerRepeat, int outputIndentLevel) {
        this.divider = divider.repeat(dividerRepeat);
        this.outputIndentLevel = outputIndentLevel;
    }

    /**
     * Prints a welcome message to the user.
     */
    public void hello() {
        this.printMessage(Ui.GREET_MESSAGE);
    }

    /**
     * Returns welcome message.
     */
    public String getHello() {
        return Ui.GREET_MESSAGE;
    }

    /**
     * Prints a bye message to the user.
     */
    public void bye() {
        this.printMessage(Ui.BYE_MESSAGE);
    }

    /**
     * Returns bye message.
     */
    public String getBye() {
        return Ui.BYE_MESSAGE;
    }

    /**
     * Formats the given message according to the {@code Ui}'s setting and
     * returns the formatted string.
     *
     * @param out The raw message that needs to be formatted.
     * @return A formatted version of the specified {@code out}.
     */
    public String formatMessage(String out) {
        return " ".repeat(this.outputIndentLevel) + divider + "\n"
                + " ".repeat(outputIndentLevel)
                + out.trim().replaceAll("\n", "\n" + " ".repeat(outputIndentLevel)) + "\n"
                + " ".repeat(outputIndentLevel) + divider;
    }

    /**
     * Prints the specified message to the console, first formatting it by
     * calling {@link #formatMessage(String)}.
     *
     * @param out The raw message that needs to be formatted.
     */
    public void printMessage(String out) {
        System.out.println(this.formatMessage(out));
    }
}
