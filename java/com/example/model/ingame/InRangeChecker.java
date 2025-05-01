package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class InRangeChecker extends Transition {

    private GameController controller;

    private Attacker attacker;

    public InRangeChecker(GameController controller, Attacker attacker) {
        this.controller = controller;
        this.attacker = attacker;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double arg0) {

        Fighter fighter = controller.getFighter();
        double targetX = fighter.getLayoutX() + fighter.getWidth() / 2.0;
        double targetY = fighter.getLayoutY() + fighter.getHeight() / 2.0;

        double tankX = attacker.getLayoutX() + attacker.getWidth() / 2.0;
        double tankY = attacker.getLayoutY() + attacker.getHeight() / 2.0;

        if(distance(targetX, targetY, tankX, tankY) <= attacker.getAttackRadius()) {
            attacker.setInRange(true);
        } else {
            attacker.setInRange(false);
        }
    }
    
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
