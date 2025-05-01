package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Building extends FixedTarget {
    
    public Building(GameController controller, double initX) {
        super(controller, 60, 80, initX, 1);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/building.png").toExternalForm())));
    }

}
