package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Rocket extends Rectangle {
    
    private GameController controller;

    private RocketAnimation animation;

    public Rocket(GameController controller, double initX, double initY) {
        super(30, 10);
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/bomb.png").toExternalForm())));
        this.controller = controller;

        Fighter fighter = this.controller.getFighter();
        double fighterX = fighter.getLayoutX() + fighter.getWidth() / 2.0;
        double fighterY = fighter.getLayoutY() + fighter.getHeight() / 2.0;

        double vSpeed = fighterY - initY;
        double hSpeed = fighterX - initX;
        double magnitude = magnitudeOfVector(hSpeed, vSpeed);
        if(hSpeed < 0) this.setScaleX(-1);
        this.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)));
        this.animation = new RocketAnimation(controller, this, 10 * vSpeed / magnitude, 10 * hSpeed / magnitude);
    }

    public GameController getController() {
        return controller;
    }

    public RocketAnimation getAnimation() {
        return animation;
    }

    private double magnitudeOfVector(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }
}
