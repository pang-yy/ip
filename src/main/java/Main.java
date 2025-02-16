import java.io.IOException;

import fido.Fido;
import fido.exception.FidoException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Fido fido;

    private final Image applicationIcon = new Image(this.getClass().getResourceAsStream("/images/leonardo.png"));

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            this.fido = new Fido();
            
            stage.setScene(scene);
            stage.getIcons().add(this.applicationIcon);
            stage.setTitle("Fido chatbot");
            fxmlLoader.<MainWindow>getController().setFido(this.fido);
            stage.show();
            fxmlLoader.<MainWindow>getController().sendStartUp();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FidoException e) {
            e.printStackTrace();
        }
    }
}
