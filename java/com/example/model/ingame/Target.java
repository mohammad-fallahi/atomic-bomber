package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.shape.Rectangle;

public class Target extends Rectangle {
    
    private GameController controller;
    private int killValue;

    public Target(GameController controller, double width, double height, double initX, int killValue) {
        super();
        this.setWidth(width);
        this.setHeight(height);
        this.setLayoutX(initX);
        this.setLayoutY(722 - height);
        this.killValue = killValue;
    }

    public GameController getController() {
        return controller;
    }

    public int getKillValue() {
        return killValue;
    }

}
