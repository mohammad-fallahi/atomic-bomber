package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MPBarAnimation extends Transition {

    private GameController controller;
    private Rectangle effect;

    public MPBarAnimation(GameController controller) {
        this.setCycleDuration(Duration.millis(5000));
        this.setCycleCount(1);
        this.setOnFinished(event -> {
            controller.unfreeze();
            controller.resetMPBar();
            controller.getGamePane().getChildren().remove(effect);
            controller.getAnimations().remove(this);
        });
        this.controller = controller;
        this.controller.getAnimations().add(this);
        controller.freeze();

        effect = new Rectangle(1200, 800);
        effect.setFill(new ImagePattern(new Image(getClass().getResource("/images/frozen.png").toExternalForm())));
        controller.getGamePane().getChildren().add(effect);
    }

    @Override
    protected void interpolate(double progress) {

        effect.setOpacity(1 - progress);
        controller.setMP(100 * (1 - progress));

    }
    
}
