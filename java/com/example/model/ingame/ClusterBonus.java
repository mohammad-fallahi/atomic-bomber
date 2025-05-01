package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ClusterBonus extends Bonus {
    
    public ClusterBonus(GameController controller, double initX, double initY) {
        super(controller, initX, initY);
        this.setFill(new ImagePattern(new Image(getClass().getResource("/images/cluster-bomb-bonus.png").toExternalForm())));
    }

}
