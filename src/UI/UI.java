package UI;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class UI {

    Scene scene;
    Stage window;

    public UI(Stage window){
        setView();
        setController();
        this.window = window;
        window.setScene(scene);
    }

    void setSceneOnStage(Stage window){
        window.setScene(scene);
    }

    abstract void setView();

    abstract void setController();

}
