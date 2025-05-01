package com.example.model.ingame;

import com.example.controller.GameController;
import com.example.model.User;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Mig extends Rectangle implements Attacker {
    
    private GameController controller;
    
    private double attackRadius;

    private boolean inRange = false;

    private InRangeChecker checker;

    private Timeline attacker;

    private MigAnimation animation;

    public Mig(GameController controller, double initY, double speed) {
        super(80, 32);
        this.setLayoutX(1200);
        this.setLayoutY(initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/mig.png").toExternalForm())));
        attackRadius = User.getLoggedInUser().getSetting().migAttackRadius * 250;
        
        checker = new InRangeChecker(controller, this);
        checker.play();
        attacker = new Timeline(new KeyFrame(Duration.millis(750), event -> controller.attackFighter(this)));
        attacker.setCycleCount(-1);
        controller.getAnimations().add(attacker);
        attacker.play();
        
        this.controller = controller;

        animation = new MigAnimation(controller, this, speed);
    }

    @Override
    public void setInRange(boolean val) {
        inRange = val;
    }

    @Override
    public boolean isInRange() {
        return inRange;
    }

    @Override
    public double getAttackRadius() {
        return attackRadius;
    }

    public Timeline getAttackerTimeline() {
        return attacker;
    }

    public GameController getController() {
        return controller;
    }

    public MigAnimation getAnimation() {
        return animation;
    }

}
