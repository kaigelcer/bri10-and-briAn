package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends UI {

    Label welcomeLabel;
    Hyperlink aboutLink;
    Button newDesign;
    Button loadDesign;
    Button tutorial;
    Button exit;

    public MainMenu(Stage window) {
        super(window);
    }

    public void setView(){
        VBox layout = new VBox(20);
        layout.setPrefWidth(100);

        welcomeLabel = new Label("Welcome to briAn by bri10");
        //welcomeLabel.setStyle("-fx-text-fill: aliceblue");
        aboutLink = new Hyperlink("About");
        newDesign = new Button("New Design");
        newDesign.setId("big-bold");
        newDesign.setPrefWidth(layout.getPrefWidth());
        loadDesign = new Button("Load Design");
        loadDesign.getStyleClass().add("button-one");
        loadDesign.setPrefWidth(layout.getPrefWidth());
        tutorial = new Button("Tutorial");
        tutorial.setPrefWidth(layout.getPrefWidth());
        exit = new Button("Exit");
        exit.setPrefWidth(layout.getPrefWidth());


        layout.getChildren().addAll(welcomeLabel, aboutLink, newDesign, loadDesign, tutorial, exit);
        layout.setAlignment(Pos.CENTER);
        layout.prefWidth(50);
        layout.setFillWidth(false);


        scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add("/UI/Style/Viper.css");
    }

    public void setController() {
        newDesign.setOnAction(e -> {
            new DesignGUI(window);
        });

    }

}
