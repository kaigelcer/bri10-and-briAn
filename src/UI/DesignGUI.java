package UI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DesignGUI extends UI {

    VBox layout;
    MenuItem newFile;
    MenuItem loadFile;
    MenuItem saveFile;
    MenuItem exit;
    MenuItem undo;
    MenuItem redo;
    MenuItem cut;
    MenuItem copy;
    MenuItem paste;
    CheckMenuItem fullScreen;
    MenuItem zoomIn;
    MenuItem zoomOut;
    CheckMenuItem showLeftMenu;
    CheckMenuItem showRightMenu;


    Button energyButton;
    Button costButton;
    Button lightAnalysisButton;


    Button newLightButton;

    Button newStreetButton;

    private VBox leftMenu;
    public static final String CSS_STYLE =
            "  -fx-glass-color: rgba(85, 132, 160, 0.9);\n"
                    + "  -fx-alignment: center;\n"
                    + "  -fx-font-size: 20;\n"
                    + "  -fx-background-color: linear-gradient(to bottom, derive(-fx-glass-color, 50%), -fx-glass-color);\n"
                    + "  -fx-border-color: derive(-fx-glass-color, -60%);\n"
                    + "  -fx-border-width: 2;\n"
                    + "  -fx-background-insets: 1;\n"
                    + "  -fx-border-radius: 3;\n"
                    + "  -fx-background-radius: 3;\n";
    private Pane pane;

    public DesignGUI(Stage window) {
        super(window);
    }

    public void setView() {

        // Create file menu
        Menu fileMenu = new Menu("File");
        newFile = new MenuItem("New...");
        loadFile = new MenuItem("Load...");
        saveFile = new MenuItem("Save");
        exit = new MenuItem("Exit");
        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(loadFile);
        fileMenu.getItems().add(saveFile);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exit);

        // Create edit menu
        Menu editMenu = new Menu("Edit");
        undo = new MenuItem("Undo");
        redo = new MenuItem("Redo");
        cut = new MenuItem("Cut");
        copy = new MenuItem("Copy");
        paste = new MenuItem("Paste");
        editMenu.getItems().add(undo);
        editMenu.getItems().add(redo);
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(cut);
        editMenu.getItems().add(copy);
        editMenu.getItems().add(paste);

        //Create View Menu
        Menu viewMenu = new Menu("View");
        showLeftMenu = new CheckMenuItem("Show Left Menu");
        showRightMenu = new CheckMenuItem("Show Right Menu");
        fullScreen = new CheckMenuItem("Full Screen");
        zoomIn = new MenuItem("Zoom In");
        zoomOut = new MenuItem("Zoom Out");
        viewMenu.getItems().add(showLeftMenu);
        viewMenu.getItems().add(showRightMenu);
        viewMenu.getItems().add(new SeparatorMenuItem());
        viewMenu.getItems().add(fullScreen);
        viewMenu.getItems().add(zoomIn);
        viewMenu.getItems().add(zoomOut);


        // Add menus to bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu);

        //Create Left Menu
        leftMenu = new VBox();
        leftMenu.setPrefWidth(200);
        leftMenu.setMaxWidth(200);
        //leftMenu.setFillWidth(false);
        leftMenu.setSpacing(50);
        leftMenu.setAlignment(Pos.CENTER);
        energyButton = new Button("Estimate Energy Savings");
        energyButton.setWrapText(true);
        energyButton.setTextAlignment(TextAlignment.CENTER);
        costButton = new Button("Estimate Cost Savings");
        lightAnalysisButton = new Button("Analyze Lighting");
        leftMenu.getChildren().addAll(energyButton, costButton, lightAnalysisButton);
        leftMenu.setStyle("-fx-background-color: #2D2D2A");
        for(Node node : leftMenu.getChildren()){
            Button button = (Button) node;
            button.setMinWidth(150);
        }

        //Create Right Menu
        VBox rightMenu = new VBox();
        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setPrefWidth(200);
        rightMenu.setMaxWidth(200);
        rightMenu.setSpacing(50);
        newLightButton = new Button("+");
        newStreetButton = new Button("+");
        Image lightImage = new Image("light.png");
        ImageView lightView = new ImageView(lightImage);
        lightView.setFitHeight(80);
        lightView.setPreserveRatio(true);
        Image roadImage = new Image("roadSegment.png");
        ImageView roadView = new ImageView(roadImage);
        roadView.setFitHeight(80);
        roadView.setPreserveRatio(true);
        rightMenu.getChildren().addAll(newLightButton, newStreetButton);
        newLightButton.setPrefSize(80, 80);
        newLightButton.setGraphic(lightView);
        newStreetButton.setPrefSize(80, 80);
        newStreetButton.setGraphic(roadView);
        rightMenu.setStyle("-fx-background-color: #FCF0CC");


        //Create Design Pane
        ScrollPane designPane = new ScrollPane();
        pane = new Pane();
        designPane.pannableProperty().set(true);
        designPane.setPrefSize(595, 200);
        ImageView greenBackground = new ImageView();
        greenBackground.setImage(new Image("greenBackground.jpg"));
        greenBackground.setFitHeight(2000);
        greenBackground.setFitWidth(2000);
        pane.getChildren().add(greenBackground);
        designPane.setContent(pane);


        layout = new VBox();
        layout.getChildren().add(menuBar);
        SplitPane splitPane = new SplitPane();
        layout.getChildren().add(splitPane);
        layout.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().addAll(leftMenu, designPane, rightMenu);


        scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add("/UI/Style/DesignGui.css");

    }

    public void setController() {
        newFile.setOnAction(event -> {
            System.out.println("Creating new file");
        });

        newLightButton.setOnAction(event -> {
            DraggableNode node = new DraggableNode();
            Image lightImage = new Image("light.png");
            ImageView lightView = new ImageView(lightImage);
            lightView.setFitWidth(98);
            lightView.setFitHeight(80);
            node.getChildren().add(lightView);
            node.setPrefSize(98, 80);
            pane.getChildren().add(node);
        });

        newStreetButton.setOnAction(event -> {
            DraggableNode node = new DraggableNode();
            Image roadImage = new Image("roadSegment.png");
            ImageView roadView = new ImageView(roadImage);
            roadView.setFitWidth(98);
            roadView.setFitHeight(80);
            node.getChildren().add(roadView);
            node.setPrefSize(98, 80);
            pane.getChildren().add(node);
        });



    }


}
