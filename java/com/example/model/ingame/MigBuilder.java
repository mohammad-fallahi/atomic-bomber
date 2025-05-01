package com.example.model.ingame;

import com.example.controller.GameController;
import com.example.model.User;

import javafx.animation.Transition;
import javafx.util.Duration;

public class MigBuilder extends Transition {

    private GameController controller;

    public MigBuilder(GameController controller) {
        this.controller = controller;
        this.setCycleDuration(Duration.millis(User.getLoggedInUser().getSetting().migDelay * 5000));
        this.setCycleCount(-1);
        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double progress) {

        if(progress == 1) {
            controller.migAttack();
        }

    }
    
}
