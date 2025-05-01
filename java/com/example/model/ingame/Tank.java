package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Tank extends MovingTarget {

    public static final double MIN_SPEED = 1.5, MAX_SPEED = 2.5;

    private double speed;

    public Tank(GameController controller, double initX, boolean initDirection, double speed) {
        super(controller, 60, 40, initX, 2, initDirection);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/tank.png").toExternalForm())));
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}
