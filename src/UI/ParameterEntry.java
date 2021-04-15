package UI;

import Model.Hour;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class ParameterEntry extends UI {

    // maximum # of cars per hour for which dimming will be engaged
    private static final float THRESHOLD = 70;

    // approximate amount of time for which dimmed lights will brighten when a car approaches
    private static final float brightenedTime = 15;

    VBox layout;
    private Button button;
    private int numLights;
    private LinkedList<Hour> hours;
    private TextField dimmingPercentageInput;
    private TextField powerInput;
    private Label energySavings;

    public ParameterEntry(DesignGUI parent) {
        super(parent);
    }


    @Override
    void setView() {
        numLights = ((DesignGUI) parent).getNumLights();
        layout = new VBox();
        hours = new LinkedList<>();

        //Table
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        for(int i = 0; i < 24; i++){
            Label label1 = new Label(String.valueOf(i) + ":00 - " + String.valueOf(i+1) + ":00");
            gridPane.add(label1, 1, i);
            TextField carsField = new TextField();
            carsField.setText("1");
            gridPane.add(carsField, 2, i);
            Label label2 = new Label("vehicles");
            gridPane.add(label2, 3, i);
            hours.add(new Hour(i, carsField));
        }
        scrollPane.setContent(gridPane);
        scrollPane.setMinViewportHeight(100);


        // Inputs
        Label trafficFrequency = new Label("Hourly Traffic Frequency");
        Label power = new Label("How much power do your lights draw?");
        powerInput = new TextField();
        powerInput.setPromptText("Average power draw per light [W]");
        powerInput.setMinWidth(200);
        Label dimming = new Label("How much do you want to dim your lights?");
        dimmingPercentageInput = new TextField();
        dimmingPercentageInput.setPromptText("Dimming percentage [%]");
        dimmingPercentageInput.setMinWidth(200);
        button = new Button("Estimate Savings");
        energySavings = new Label("Approximately: ______ Watts");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(button, energySavings);
        layout.setSpacing(50);
        layout.setStyle("-fx-background-color: #FCF0CC");
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setFillWidth(true);
        layout.setPadding(new Insets(50, 50, 25, 50));
        layout.getChildren().addAll(trafficFrequency, scrollPane, power, powerInput, dimming, dimmingPercentageInput, hBox);
        ScrollPane masterScrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #FCF0CC");
        scrollPane.setPrefViewportHeight(100);
        masterScrollPane.setContent(layout);
        masterScrollPane.setStyle("-fx-background-color: #FCF0CC");
        masterScrollPane.setFitToWidth(true);
        scrollPane.setFitToWidth(true);
        scene = new Scene(masterScrollPane, 600, 800);

    }


    @Override
    void setController() {
        button.setOnAction(event -> {
            float energySavedPerDay = 0;
            float powerUsedNoBri = numLights * (Float.parseFloat(powerInput.getText()));
            float dimmingPercentage = (Float.parseFloat(dimmingPercentageInput.getText())) / 100;
            for(Hour hour : hours){
                float carsPerHour = hour.getCars();
                if(carsPerHour <= THRESHOLD){
                    float energyUsedNoBri = powerUsedNoBri * 3600;
                    float energyUsedWBri = (3600 - carsPerHour * brightenedTime) * powerUsedNoBri * dimmingPercentage +
                            carsPerHour * brightenedTime * powerUsedNoBri;
                    energySavedPerDay += (energyUsedNoBri - energyUsedWBri);
                }
            }
            float powerSavings = energySavedPerDay / 86400;
            energySavings.setText("Approximately: " + String.valueOf(powerSavings) + " Watts");
        });

    }
}
