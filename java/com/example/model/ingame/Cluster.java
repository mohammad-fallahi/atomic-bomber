package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Cluster extends Rectangle {

    public static final double ACCELERATION = 0.06;
    
    private GameController controller;
    private ClusterAnimation animation;

    public Cluster(GameController controller, double initX, double initY) {
        super(50, 30);
        this.setLayoutX(initX);
        this.setLayoutY(initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/cluster-bomb.png").toExternalForm())));

        this.controller = controller;
        Fighter fighter = controller.getFighter();
        double vSpeed = fighter.getSpeedR() * Math.sin(fighter.getSpeedTheta());
        double hSpeed = fighter.getSpeedR() * Math.cos(fighter.getSpeedTheta());
        if(hSpeed < 0) this.setScaleX(-1);
        animation = new ClusterAnimation(controller, this, vSpeed, hSpeed * 0.6);
    }

    public GameController getController() {
        return controller;
    }

    public ClusterAnimation getAnimation() {
        return animation;
    }
}
