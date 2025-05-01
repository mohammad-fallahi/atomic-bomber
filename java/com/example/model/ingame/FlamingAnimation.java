package com.example.model.ingame;

import com.example.controller.GameController;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FlamingAnimation extends Transition {

    private GameController controller;

    private Target target;

    private Rectangle flame;

    public FlamingAnimation(GameController controller, Target target) {
        this.controller = controller;
        this.target = target;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(1);
        this.setOnFinished(event -> {
            removeTarget();
        });
        this.controller.getAnimations().add(this);

        flame = new Rectangle(50, 50);
        flame.setFill(new ImagePattern(new Image(getClass().getResource("/images/flame.png").toExternalForm())));
        double x = target.getLayoutX() + target.getWidth() / 2.0;
        double y = target.getLayoutY() + target.getHeight() / 2.0;
        flame.setLayoutX(x - 25);
        flame.setLayoutY(y - 15);
        int index = controller.getGamePane().getChildren().indexOf(target);
        controller.getGamePane().getChildren().add(index+1, flame);
    }

    @Override
    protected void interpolate(double progress) {

        flame.setOpacity(1 - progress);
        target.setOpacity(1 - progress);

    }
    
    private void removeTarget() {
        this.stop();
        controller.getGamePane().getChildren().remove(target);
        controller.getAnimations().remove(this);
    }
}
