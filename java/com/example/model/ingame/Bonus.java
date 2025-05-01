package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.shape.Circle;

public class Bonus extends Circle {

    private GameController controller;

    private BonusAnimation animation;

    public Bonus(GameController controller, double initX, double initY) {
        super(20);
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.controller = controller;
        animation = new BonusAnimation(controller, this);
    }

    public GameController getController() {
        return controller;
    }

    public BonusAnimation getAnimation() {
        return animation;
    }
}
