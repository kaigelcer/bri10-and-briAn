package Model;


import javafx.scene.control.TextField;

public class Hour {

    int hour;
    TextField carsField;

    public Hour(int hour, TextField carsField){
        this.hour = hour;
        this.carsField = carsField;
    }

    // Return the number of cars passing in this particular hour
    public float getCars(){
        return Float.parseFloat(carsField.getText());
    }



}
