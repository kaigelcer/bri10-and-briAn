package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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
        newDesign.setOnAction(e -> new DesignGUI(window));

        loadDesign.setOnAction(e -> {
            FileChooser loadDialogue = new FileChooser();
            loadDialogue.setTitle("Save Dialog");
            loadDialogue.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            File file = loadDialogue.showOpenDialog(new Stage());
            LinkedList<DraggableNode> nodes = new LinkedList<>();

            try {
                Scanner scanner = new Scanner(file);
                scanner.useDelimiter("[,\n]");

                while(scanner.hasNext()){
                    DraggableNode node = new DraggableNode();
                    String x = scanner.next();
                    String y = scanner.next();
                    String url = scanner.next();
                    //scanner.next();
                    System.out.println("X: " + x + "! " + "Y: " + y +  "! " + "U: " + url);
                    node.setX(Double.parseDouble(x));
                    node.setY(Double.parseDouble(y));
                    node.setUrl(url);
                    ImageView view = new ImageView(new Image(url));
                    if(node.getUrl().equals("UI/Images/light.png")){
                        view.setFitWidth(98);
                        view.setFitHeight(80);
                    } else {
                        view.setPreserveRatio(true);
                        view.setFitHeight(165);
                    }
                    node.getChildren().add(view);
                    nodes.add(node);
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            DesignGUI designGUI = new DesignGUI(window);
            designGUI.loadNodes(nodes);

        } );

    }

}
