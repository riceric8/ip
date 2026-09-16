package rice;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Starts the JavaFX graphical interface for Rice.
 */
public class Main extends Application {

    private Rice rice = new Rice();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap, 400, 600);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(300.0);
            stage.setMinHeight(400.0);
            fxmlLoader.<MainWindow>getController().setRice(rice); // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
