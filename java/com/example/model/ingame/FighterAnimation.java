package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.util.Duration;

public class FighterAnimation extends Transition {

    private Fighter fighter;
    private GameController controller;

    private boolean increaseRotate, decreaseRotate;
    
    public FighterAnimation(GameController controller, Fighter fighter) {
        this.controller = controller;
        this.fighter = fighter;
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
        increaseRotate = false;
        decreaseRotate = false;
        
        this.controller.getAnimations().add(this);
    }

    public void setIncreaseRotate(boolean b) {
        increaseRotate = b;
    }
    
    public void setDecreaseRotate(boolean b) {
        decreaseRotate = b;
    }


    @Override
    protected void interpolate(double arg0) {
        double x = fighter.getLayoutX(), y = fighter.getLayoutY();
        if(increaseRotate) fighter.setSpeedTheta(fighter.getSpeedTheta() + Fighter.DELTA_THETA);
        if(decreaseRotate) fighter.setSpeedTheta(fighter.getSpeedTheta() - Fighter.DELTA_THETA);

        double deltaX = fighter.getSpeedR() * Math.cos(fighter.getSpeedTheta());
        double deltaY = fighter.getSpeedR() * Math.sin(fighter.getSpeedTheta());
        x += deltaX; y += deltaY;

        if(x > 1200) x = 0;
        if(x < -fighter.getWidth()) x = 1200;

        y = Math.max(y, 0);

        if(y >= 690) {
            controller.setHP(0);
        }
        y = Math.min(y, 690);

        fighter.setLayoutX(x);
        fighter.setLayoutY(y);
        fighter.setRotate(Math.toDegrees(fighter.getSpeedTheta()));
        if(deltaX < 0) fighter.setScaleY(-1);
        if(deltaX > 0) fighter.setScaleY(1);

    }
    
}
