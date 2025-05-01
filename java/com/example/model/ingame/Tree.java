package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Tree extends FixedTarget {

    public Tree(GameController controller, double initX) {
        super(controller, 40, 70, initX, 0);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/cactus.png").toExternalForm())));
    }
}
