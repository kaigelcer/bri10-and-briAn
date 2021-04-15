package UI;

import Model.Road;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.util.LinkedList;

public class RoadSelection extends UI {

    LinkedList<Road> roads;
    private TilePane tilePane;

    public RoadSelection(UI parent) {
        super(parent);
    }

    @Override
    void setView() {
        tilePane = new TilePane();
        tilePane.setPadding(new Insets(20,20,20,20));
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        roads = new LinkedList<>();
        roads.add(new Road("UI/Images/Curve90.png"));
        roads.add(new Road("UI/Images/CurveDouble.png"));
        roads.add(new Road("UI/Images/CurveRightAngle.png"));
        roads.add(new Road("UI/Images/DoubleCrosswalk.png"));
        roads.add(new Road("UI/Images/Intersection.png"));
        roads.add(new Road("UI/Images/Intersection2.png"));
        roads.add(new Road("UI/Images/NarrowCurve90.png"));
        roads.add(new Road("UI/Images/SingleCrosswalk.png"));
        roads.add(new Road("UI/Images/Straight.png"));
        roads.add(new Road("UI/Images/TeeSection.png"));
        roads.add(new Road("UI/Images/UCurve.png"));
        roads.add(new Road("UI/Images/WideCurve90.png"));
        scene = new Scene(tilePane, 550, 700);

    }

    @Override
    void setController() {
        for(Road road : roads){
            tilePane.getChildren().add(road.getButton());
            road.getButton().setOnAction(event -> {
                ((DesignGUI) parent).displayNewNode((ImageView) road.getButton().getGraphic(), road.getUrl());
                window.close();
            });
        }

    }
}
