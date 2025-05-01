package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Truck extends MovingTarget {

    public static final double MIN_SPEED = 2, MAX_SPEED = 3.5;

    private double speed;

    public Truck(GameController controller, double initX, boolean initDirection, double speed) {
        super(controller, 80, 40, initX, 2, initDirection);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/truck.png").toExternalForm())));
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
    
}
