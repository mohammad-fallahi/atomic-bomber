package com.example.model.ingame;

import com.example.controller.GameController;
import com.example.model.User;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class AttackingTank extends Tank implements Attacker {

    private double attackRadius;

    private boolean inRange = false;

    private InRangeChecker checker;

    private Timeline attacker;

    public AttackingTank(GameController controller, double initX, boolean initDirection, double speed) {
        super(controller, initX, initDirection, speed);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/attacking-tank.png").toExternalForm())));
        attackRadius = User.getLoggedInUser().getSetting().tankAttackRadius * 250;
        checker = new InRangeChecker(controller, this);
        checker.play();
        attacker = new Timeline(new KeyFrame(Duration.millis(1000), event -> controller.attackFighter(this)));
        attacker.setCycleCount(-1);
        controller.getAnimations().add(attacker);
        attacker.play();
    }
    
    public double getAttackRadius() {
        return attackRadius;
    }

    public Timeline getAttackerTimeline() {
        return attacker;
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean val) {
        inRange = val;
    }
}
