package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bomb extends Rectangle {

    public static final double ACCELERATION = 0.1;

    private GameController controller;

    private BombAnimation animation;
    
    public Bomb(double initX, double initY, double vSpeed, double hSpeed, GameController controller) {
        super(30, 10);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/bomb.png").toExternalForm())));
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.controller = controller;
        if(hSpeed < 0) this.setScaleX(-1);
        animation = new BombAnimation(controller, this, vSpeed, hSpeed);
    }

    public GameController getController() {
        return controller;
    }

    public BombAnimation getAnimation() {
        return animation;
    }
}
