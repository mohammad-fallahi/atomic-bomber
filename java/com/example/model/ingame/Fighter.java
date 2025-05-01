package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Fighter extends Rectangle {

    public static final double MIN_SPEED = 3,
                               MAX_SPEED = 8, 
                               DEFAULT_SPEED = 5,
                               DELTA_THETA = 0.05;


    private GameController controller;

    private FighterAnimation animation;
    private double speedR, speedTheta = 0;
    
    public Fighter(double initX, double initY, GameController controller) {
        super(80, 32);   // Height: 80, width: 32  --> ratio: 5:2
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/fighter.png").toExternalForm())));
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.controller = controller;
        speedR = DEFAULT_SPEED;

        animation = new FighterAnimation(this.controller, this);
    }

    public GameController getController() {
        return controller;
    }

    public FighterAnimation getAnimation() {
        return animation;
    }

    public double getSpeedR() {
        return speedR;
    }

    public double getSpeedTheta() {
        return speedTheta;
    }

    public void setSpeedR(double r) {
        speedR = r;
    }

    public void setSpeedTheta(double theta) {
        speedTheta = theta;
    }

}
