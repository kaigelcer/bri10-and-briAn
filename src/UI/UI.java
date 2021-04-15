package UI;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class UI {

    UI parent;
    Scene scene;
    Stage window;

    public UI(Stage window){
        this.window = window;
        setView();
        setController();
        window.setScene(scene);
        window.centerOnScreen();
    }

    public UI(UI parent){
        this.parent = parent;
        setView();
        setController();
        window = new Stage();
        window.setScene(scene);
        window.show();
    }

    public UI(){
        setView();
        setController();
        window = new Stage();
        window.setScene(scene);
        window.show();
    }

    void setSceneOnStage(Stage window){
        window.setScene(scene);
    }

    abstract void setView();

    abstract void setController();

}
