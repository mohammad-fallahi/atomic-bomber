package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class AtomicBonus extends Bonus {

    public AtomicBonus(GameController controller, double initX, double initY) {
        super(controller, initX, initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/radio-active.png").toExternalForm())));
    }

}
