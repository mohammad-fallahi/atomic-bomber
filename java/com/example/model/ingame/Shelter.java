package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Shelter extends FixedTarget {
    
    public Shelter(GameController controller, double initX) {
        super(controller, 80, 80, initX, 1);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/shelter.png").toExternalForm())));
    }

}
