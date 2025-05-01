package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class RocketAnimation extends Transition {

    private GameController controller;
    private Rocket rocket;

    private double vSpeed, hSpeed;

    public RocketAnimation(GameController controller, Rocket rocket, double vSpeed, double hSpeed) {
        this.controller = controller;
        this.rocket = rocket;
        this.vSpeed = vSpeed;
        this.hSpeed = hSpeed;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {

        double x = rocket.getLayoutX();
        double y = rocket.getLayoutY();

        if(rocket.getBoundsInParent().intersects(controller.getFighter().getBoundsInParent())) {
            removeRocket();
            controller.hitFighter();
        }

        if(y < 0 || y > 800 || x < 0 || x > 1200) {
            removeRocket();
        }

        x += hSpeed;
        y += vSpeed;
        rocket.setLayoutX(x);
        rocket.setLayoutY(y);
    }

    private void removeRocket() {
        this.stop();
        controller.getGamePane().getChildren().remove(rocket);
        controller.getAnimations().remove(this);
    }
}
