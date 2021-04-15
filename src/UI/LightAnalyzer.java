package UI;

import Model.Light;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class LightAnalyzer extends UI {

    LinkedList<Light> lights;
    LinkedList<DraggableNode> nodes;
    VBox layout;
    Boolean sufficientlyLit = true;
    private Button analyzeLightingButton;
    private TextField poleHeightInput;
    private TextField lumenInput;
    private TextField reflectanceInput;

    public LightAnalyzer(DesignGUI parent){
        super(parent);
    }

    @Override
    void setView() {
        lights = ((DesignGUI) parent).getLights();
        nodes = ((DesignGUI) parent).getNodes();
        layout = new VBox();

        Label poleHeightLabel = new Label("What is your pole height (metres)?");
        poleHeightInput = new TextField();
        poleHeightInput.setMinWidth(200);
        poleHeightInput.setText("10");
        Label reflectanceLabel = new Label("What is the reflectance of road surface?");
        reflectanceInput = new TextField();
        reflectanceInput.setMinWidth(200);
        reflectanceInput.setText("0.33");
        Label luminousFluxLabel = new Label("What is the lumen output of one of your lamps?");
        lumenInput = new TextField();
        lumenInput.setText("6000");
        lumenInput.setMinWidth(200);
        Label info = new Label("From the given information, we can give you a rough estimate as to whether or " +
                "not your lighting configuration provides sufficient illumination for every road segment in your " +
                "design");
        info.setWrapText(true);
        analyzeLightingButton = new Button("Analyze Lighting");
        layout.setSpacing(50);
        layout.setStyle("-fx-background-color: #FCF0CC");
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setFillWidth(true);
        layout.setPadding(new Insets(50, 50, 25, 50));
        layout.getChildren().addAll(poleHeightLabel, poleHeightInput, reflectanceLabel, reflectanceInput, luminousFluxLabel,
                lumenInput, info, analyzeLightingButton);
        scene = new Scene(layout, 600, 800);

    }

    @Override
    void setController(){

        analyzeLightingButton.setOnAction(event -> {
            float poleHeight = Float.parseFloat(poleHeightInput.getText());
            float lumenOutput = Float.parseFloat(lumenInput.getText());
            float reflectance = Float.parseFloat(reflectanceInput.getText());
            for(DraggableNode node : nodes){
                float totalIlluminance = 0;
                if(! node.getUrl().equals("UI/Images/light.png")){
                    for(Light light : lights){
                        float distance = computeDistanceBetweenPoints((float) light.getX(), (float) light.getY(),
                                poleHeight, (float) node.getX(), (float) node.getY());
                        float illuminance = computeIlluminanceAtPoint(distance, lumenOutput);
                        totalIlluminance += illuminance;
                    }

                    System.out.println(totalIlluminance);
                    if(totalIlluminance < 0.4){
                        node.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5)");
                    } else {
                        node.setStyle("-fx-background-color: transparent");
                    }
                }
            }
            window.close();
        });


    }


    // Assume the road surface is a diffuse reflector
    public float computeLuminanceAtPoint(float illuminance, float reflectance){
        return (float) (illuminance * reflectance / PI);
    }

    // Assume Light Loss Factor (LLF) and Coefficient of Utilization (CU) are included in lumFlux (Lumen output of lamp)
    public float computeIlluminanceAtPoint(float distance, float lumFlux){
        return (float) (lumFlux / (4 * PI * Math.pow(distance, 2)));
    }

    // Note we assume that the Z value of the incident point is 0 because it is a road
    public float computeDistanceBetweenPoints(float sourceX, float sourceY, float sourceZ, float pointX, float pointY){
        return (float) Math.sqrt(pow(sourceX-pointX, 2) + pow(sourceY-pointY, 2) + pow(sourceZ, 2));
    }
}
