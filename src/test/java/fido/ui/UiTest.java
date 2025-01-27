package fido.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {

    @Test
    public void printMessage_singleLineMessage_success() {
        String message = "Hello World";
        String divider = "=".repeat(40);
        String indentation = " ".repeat(5);
        assertEquals(indentation + divider + "\n" + 
                indentation + message + "\n" +
                indentation + divider,
            new Ui("=", 40, 5).formatMessage((message)));
    }

    @Test
    public void printMessage_multiLineMessage_success() {
        String message = "This \nis a\n multiline message.";
        String divider = "=".repeat(60);
        String indentation = " ".repeat(4);
        assertEquals(indentation + divider + "\n" + 
                indentation + "This " + "\n" +
                indentation + "is a" + "\n" +
                indentation + " multiline message." + "\n" +
                indentation + divider,
            new Ui("=", 60, 4).formatMessage((message)));
    }
}
