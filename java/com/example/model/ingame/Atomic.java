package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Atomic extends Rectangle {
    
    public static final double ACCELERATION = 0.06;

    private GameController controller;

    private AtomicAnimation animation;

    public Atomic(GameController controller, double initX, double initY) {
        super(45, 25);
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/atomic-bomb.png").toExternalForm())));

        this.controller = controller;
        Fighter fighter = controller.getFighter();
        double vSpeed = fighter.getSpeedR() * Math.sin(fighter.getSpeedTheta());
        double hSpeed = fighter.getSpeedR() * Math.cos(fighter.getSpeedTheta());
        if(hSpeed < 0) this.setScaleX(-1);
        animation = new AtomicAnimation(controller, this, vSpeed, hSpeed * 0.6);
    }

    public GameController getController() {
        return controller;
    }

    public AtomicAnimation getAnimation() {
        return animation;
    }
}
