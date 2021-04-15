package UI;

import Model.Light;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;

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
    int GRIDSIZE;
    MenuItem zoomIn;
    MenuItem zoomOut;
    CheckMenuItem showLeftMenu;
    CheckMenuItem showRightMenu;
    LinkedList<Light> lights;
    LinkedList<DraggableNode> nodes;


    Button lightAnalysisButton;
    Button energySavingsButton;

    Button newLightButton;

    Button newStreetButton;
    Button horizontalFlipButton;
    Button verticalFlipButton;
    Button rotateButton;

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
    private ScrollPane designPane;
    private TextField xCoordinate;
    private TextField yCoordinate;

    public DesignGUI(Stage window) {
        super(window);
    }

    public void setView() {
        lights = new LinkedList<>();
        nodes = new LinkedList<>();
        GRIDSIZE = 7;

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
        energySavingsButton = new Button("Estimate Energy Savings...");
        lightAnalysisButton = new Button("Analyze Lighting...");
        leftMenu.getChildren().addAll(energySavingsButton, lightAnalysisButton);
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
        Image lightImage = new Image("UI/Images/light.png");
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
        rotateButton = new Button();
        rotateButton.setPrefSize(30,30);
        ImageView rotateIcon = new ImageView(new Image("UI/Images/rotate.png"));
        rotateIcon.setFitHeight(30);
        rotateIcon.setPreserveRatio(true);
        rotateButton.setGraphic(rotateIcon);
        horizontalFlipButton = new Button();
        horizontalFlipButton.setPrefSize(30,30);
        ImageView horizontalFlipIcon = new ImageView(new Image("UI/Images/ReflectHorizontal.png"));
        horizontalFlipIcon.setFitHeight(30);
        horizontalFlipIcon.setPreserveRatio(true);
        horizontalFlipButton.setGraphic(horizontalFlipIcon);
        verticalFlipButton = new Button();
        verticalFlipButton.setPrefSize(30,30);
        ImageView verticalFlipIcon = new ImageView(new Image("UI/Images/ReflectVertical.png"));
        verticalFlipIcon.setFitHeight(30);
        verticalFlipIcon.setPreserveRatio(true);
        verticalFlipButton.setGraphic(verticalFlipIcon);
        HBox nodeManipulationButtons = new HBox();
        nodeManipulationButtons.setAlignment(Pos.CENTER);
        nodeManipulationButtons.setSpacing(5);
        nodeManipulationButtons.getChildren().addAll(rotateButton, horizontalFlipButton, verticalFlipButton);
        xCoordinate = new TextField();
        xCoordinate.setPromptText("x:");
        xCoordinate.setMaxWidth(50);
        yCoordinate = new TextField();
        yCoordinate.setPromptText("y:");
        yCoordinate.setMaxWidth(50);
        Label adjustNodeCoordinates = new Label("Adjust Node Coordinates:");
        VBox nodeCoordinateEntry = new VBox();
        nodeCoordinateEntry.setAlignment(Pos.CENTER);
        nodeCoordinateEntry.getChildren().addAll(adjustNodeCoordinates, xCoordinate, yCoordinate);
        Rectangle gridLegend = new Rectangle(GRIDSIZE, GRIDSIZE);
        gridLegend.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.9));
        gridLegend.setStroke(Color.BLACK);
        Label legendLabel = new Label("1 square metre");
        HBox legend = new HBox();
        legend.setAlignment(Pos.CENTER);
        legend.setSpacing(5);
        legend.getChildren().addAll(gridLegend, legendLabel);

        rightMenu.getChildren().add(nodeManipulationButtons);
        rightMenu.getChildren().add(legend);
        rightMenu.getChildren().add(nodeCoordinateEntry);
        rightMenu.setStyle("-fx-background-color: #FCF0CC");


        //Create Design Pane
        designPane = new ScrollPane();
        pane = new Pane();
        ImagePattern gridPattern = createGridPattern();
        designPane.pannableProperty().set(true);
        designPane.setPrefSize(595, 200);
        ImageView greenBackground = new ImageView();
        Rectangle grid = new Rectangle();
        grid.setWidth(2000);
        grid.setHeight(2000);
        grid.setFill(gridPattern);
        /*greenBackground.setImage(new Image("greenBackground.jpg"));
        greenBackground.setFitHeight(2000);
        greenBackground.setFitWidth(2000);*/
        pane.getChildren().add(grid);

        designPane.setContent(pane);


        layout = new VBox();
        layout.getChildren().add(menuBar);
        SplitPane splitPane = new SplitPane();
        layout.getChildren().add(splitPane);
        layout.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().addAll(leftMenu, designPane, rightMenu);

        scene = new Scene(layout, 1200, 700);
        scene.getStylesheets().add("/UI/Style/DesignGui.css");
    }

    private ImagePattern createGridPattern() {
        double w = GRIDSIZE;
        double h = GRIDSIZE;

        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.9));
        gc.fillRect(0, 0, w, h);
        gc.strokeRect(0, 0, w, h);

        Image image = canvas.snapshot(new SnapshotParameters(), null);
        ImagePattern pattern = new ImagePattern(image, 0, 0, w, h, false);
        return pattern;
    }

    public void setController() {

        xCoordinate.setOnAction(event -> {
            for(DraggableNode node : nodes){
                if(node.isSelected() && xCoordinate.getText() != null){
                    node.setX(Double.parseDouble(xCoordinate.getText()));
                    node.setLayoutX(Double.parseDouble(xCoordinate.getText()));
                }
            }
        });

        yCoordinate.setOnAction(event -> {
            for(DraggableNode node : nodes){
                if(node.isSelected() && yCoordinate.getText() != null){
                    node.setY(Double.parseDouble(yCoordinate.getText()));
                    node.setLayoutY(Double.parseDouble(xCoordinate.getText()));
                }
            }
        });


        pane.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(ZoomEvent event) {


            }
        });

        pane.setOnZoomStarted(new EventHandler<ZoomEvent>() {
            @Override public void handle(ZoomEvent event) {
                event.consume();
            }
        });

        pane.setOnZoomFinished(new EventHandler<ZoomEvent>() {
            @Override public void handle(ZoomEvent event) {
                event.consume();
            }
        });

        newFile.setOnAction(event -> {
            System.out.println("Creating new file");
        });

        newLightButton.setOnAction(event -> {
            Light light = new Light();
            Image lightImage = new Image("UI/Images/light.png");
            ImageView lightView = new ImageView(lightImage);
            lightView.setFitWidth(98);
            lightView.setFitHeight(80);
            light.getChildren().add(lightView);
            light.setPrefSize(98, 80);
            light.setUrl("UI/Images/light.png");
            pane.getChildren().add(light);
            lights.add(light);
            addNode(light);
        });

        newStreetButton.setOnAction(event -> {
            new RoadSelection(this);
        });

        energySavingsButton.setOnAction(event -> new ParameterEntry(this));

        lightAnalysisButton.setOnAction(event -> new LightAnalyzer(this));

        rotateButton.setOnAction(event -> {
            for(DraggableNode node : nodes){
                if(node.isSelected())
                    node.rotate90();
            }
        });

        verticalFlipButton.setOnAction(event -> {
            for(DraggableNode node : nodes){
                if(node.isSelected())
                    node.flipVertical();
            }
        });

        horizontalFlipButton.setOnAction(event -> {
            for(DraggableNode node : nodes){
                if(node.isSelected())
                    node.flipHorizontal();
            }
        });

        saveFile.setOnAction(event -> {

            FileChooser saveDialogue = new FileChooser();
            saveDialogue.setTitle("Save Dialog");
            saveDialogue.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            File file = saveDialogue.showSaveDialog(new Stage());
            if (file != null){
                try {
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);
                    for (DraggableNode node : nodes){
                        pw.println(node.getX() +","+node.getY()+","+node.getUrl());
                    }
                    pw.flush();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    // Display a draggable node with the given image and url
    public void displayNewNode(ImageView view, String url){
        DraggableNode node = new DraggableNode();
        node.setUrl(url);
        view.setFitHeight(165);
        node.getChildren().add(view);
        node.setAlignment(Pos.CENTER);
        node.setPrefSize(165, 165);
        pane.getChildren().add(node);
        addNode(node);
    }

    public int getNumLights(){
        return lights.size();
    }

    public LinkedList<Light> getLights() {
        return lights;
    }

    public LinkedList<DraggableNode> getNodes() {
        return nodes;
    }

    public void addNode(DraggableNode node){
        node.setAllDraggableNodes(nodes);
    }

    public void loadNodes(LinkedList<DraggableNode> nodesToLoad){

        for(DraggableNode node : nodesToLoad){
            addNode(node);
            pane.getChildren().add(node);
            node.setLayoutX(node.getX());
            node.setLayoutY(node.getY());
            if(node.getUrl().equals("UI/Images/light.png")){
                node.setPrefSize(98, 80);
                lights.add((Light) node);
            } else{
                node.setPrefSize(200, 200);
            }

        }
    }


}
