package com.example.model.ingame;

import com.example.controller.GameController;


public class FixedTarget extends Target {
    
    private GameController controller;

    public FixedTarget(GameController controller, double width, double height, double initX, int killValue) {
        super(controller, width, height, initX, killValue);
    }

    public GameController getController() {
        return controller;
    }
}
