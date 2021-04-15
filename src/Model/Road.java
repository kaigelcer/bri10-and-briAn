package Model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Road {

    Button button;
    Image image;
    ImageView imageView;
    String url;
    Boolean luminanceMethod;
    Boolean sufficientlyLit;


    public Road(String url){
        this.url = url;
        image = new Image(url);
        imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        button = new Button();
        button.setPrefSize(150, 150);
        button.setMinSize(150, 150);
        button.setGraphic(imageView);
        luminanceMethod = false;
        sufficientlyLit = false;
    }

    public void setLuminanceMethod(){
        luminanceMethod = true;
    }

    public void setButton(Button button){
        this.button = button;
    }

    public Button getButton(){
        return button;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public String getUrl(){
        return url;
    }

    public void setSufficientlyLit(Boolean bool){
        sufficientlyLit = bool;
    }

    public Boolean isSufficientlyLit(){
        return sufficientlyLit;
    }

}
