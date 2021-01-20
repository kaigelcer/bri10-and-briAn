package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationWindow {

    static boolean ans;

    public static boolean display(String title, String message, String positiveText, String negativeText) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        //Create positive and negative buttons
        Button positiveButton = new Button(positiveText);
        Button negativeButton = new Button(negativeText);
        positiveButton.setOnAction(e -> window.close());

        positiveButton.setOnAction(e -> {
            ans = true;
            window.close();
        });

        negativeButton.setOnAction(e -> {
            ans = false;
            window.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, positiveButton, negativeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> {
            ans = false;
        });

        return ans;
    }
}
