import fido.Fido;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Fido fido;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/michaelangelo.png"));
    private final Image fidoImage = new Image(this.getClass().getResourceAsStream("/images/leonardo.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Fido instance */
    public void setFido(Fido fido) {
        this.fido = fido;
    }

    /** Send hello message on start up */
    public void sendStartUp() {
        dialogContainer.getChildren().addAll(
            DialogBox.getBotDialog("Hello from GUI,\nI am Fido,\ntype `help` for list of commands!", fidoImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (!input.isBlank()) {
            String response = fido.getResponse(input);
            dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, fidoImage)
            );
            userInput.clear();
        }
    }
}
