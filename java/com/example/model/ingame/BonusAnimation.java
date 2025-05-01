package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class BonusAnimation extends Transition {

    private GameController controller;
    private Bonus bonus;
    private double initialY;

    public BonusAnimation(GameController controller, Bonus bonus) {
        this.controller = controller;
        this.bonus = bonus;
        bonus.getLayoutX();
        initialY = bonus.getLayoutY();
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {

        double x = bonus.getLayoutX();
        double y = bonus.getLayoutY();

        handleIntersectionWithFighter();

        if(y < 0) {
            removeAnimation();
        }

        x += controller.getRandomDouble(7, 10) * Math.cos(0.05 * (y - initialY));
        y -= 2.5;

        bonus.setLayoutX(x);
        bonus.setLayoutY(y);
    }

    private void removeAnimation() {
        this.stop();
        controller.getGamePane().getChildren().remove(bonus);
        controller.getAnimations().remove(this);
    }
    
    private void handleIntersectionWithFighter() {
        if(bonus.getBoundsInParent().intersects(controller.getFighter().getBoundsInParent())) {
            if(bonus instanceof AtomicBonus) {
                controller.setAtomicCount(controller.getAtomicCount() + 1);
            } else {
                controller.setClusterCount(controller.getClusterCount() + 1);
            }
            removeAnimation();
        }
    }

}
