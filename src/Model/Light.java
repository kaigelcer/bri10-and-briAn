package Model;

import UI.DraggableNode;

public class Light extends DraggableNode {

    String name;
    float efficacy = 100;
    float luminance = 10;
    float powerInput = 10;

    public Light(){

    }

    public Light(String name, float efficacy, float luminance, float powerInput){
        this.name = name;
        this.efficacy = efficacy;
        this.luminance = luminance;
        this.powerInput = powerInput;
    }

}
