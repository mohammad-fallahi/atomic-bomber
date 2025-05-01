package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class MigAnimation extends Transition {

    private GameController controller;
    private Mig mig;

    private double speed;

    public MigAnimation(GameController controller, Mig mig, double speed) {
        this.controller = controller;
        this.mig = mig;
        this.speed = speed;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {

        double x = mig.getLayoutX();

        x -= speed;
        
        if(x < -mig.getWidth()) {
            removeMig();
        }

        mig.setLayoutX(x);
    }

    private void removeMig() {
        this.stop();
        mig.getAttackerTimeline().stop();
        controller.getGamePane().getChildren().remove(mig);
        controller.getAnimations().remove(this);
    }
    
}
