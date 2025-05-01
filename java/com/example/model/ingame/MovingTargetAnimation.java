package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class MovingTargetAnimation extends Transition {

    private MovingTarget vehicle;
    private GameController controller;
    private boolean direction;
    private boolean isFreezed = false;

    public MovingTargetAnimation(MovingTarget vehicle, GameController controller, boolean direction) {
        this.vehicle = vehicle;
        this.controller = controller;
        this.direction = direction;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {
        double x = vehicle.getLayoutX();
        if(x > 1200 - vehicle.getWidth() || x < 0) {
            direction = !direction;
        }

        if(direction) {
            x += vehicle.getSpeed();
            vehicle.setScaleX(1);
        }
        else {
            x -= vehicle.getSpeed();
            vehicle.setScaleX(-1);
        }
        vehicle.setLayoutX(x);
    }

    public void freeze() {
        isFreezed = true;
    }

    public void unfreeze() {
        isFreezed = false;
    }

    public boolean isFreezed() {
        return isFreezed;
    }
}
