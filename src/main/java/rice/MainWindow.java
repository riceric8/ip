package rice;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

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

    private Rice rice;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Student.png"));
    private Image riceImage = new Image(this.getClass().getResourceAsStream("/images/RiceCooker.jpeg"));

    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener(observable -> Platform.runLater(() -> scrollPane.setVvalue(1.0)));
    }

    /** Injects the Rice instance */
    public void setRice(Rice r) {
        rice = r;
        dialogContainer.getChildren().add(DialogBox.getRiceDialog(rice.displayList(), riceImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = rice.getResponse(input);
        String suggestion = rice.getSuggestion(response);
        if (suggestion == null) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getRiceDialog(response, riceImage));
        } else {
            dialogContainer.getChildren().addAll(
                    DialogBox.getErrorDialog(response, input, riceImage),
                    DialogBox.getSuggestionDialog(suggestion, riceImage));
        }
        userInput.clear();
    }
}
