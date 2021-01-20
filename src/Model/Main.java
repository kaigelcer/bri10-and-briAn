package Model;

import UI.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) throws Exception {
        mainWindow.setTitle("briAn by bri10");
        new MainMenu(mainWindow);
        mainWindow.show();
    }

}
