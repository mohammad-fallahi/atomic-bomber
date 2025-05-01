package com.example.model.ingame;

import java.util.ArrayList;

import com.example.controller.GameController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AtomicAnimation extends Transition {

    private Atomic atomic;
    private GameController controller;

    private double vSpeed, hSpeed;

    public AtomicAnimation(GameController controller, Atomic atomic, double vSpeed, double hSpeed) {
        this.controller = controller;
        this.atomic = atomic;
        this.vSpeed = vSpeed;
        this.hSpeed = hSpeed;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {

        double x = atomic.getLayoutX();
        double y = atomic.getLayoutY();

        x += hSpeed;
        y += vSpeed;
        vSpeed += Atomic.ACCELERATION;

        if(x > 1200) x = 0;
        if(x < 0) x = 1200;
        
        if(y > 702) {
            BOOOOOM();
        }
        
        atomic.setLayoutX(x);
        atomic.setLayoutY(y);
        atomic.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)));

    }

    private void BOOOOOM() {
        ArrayList<Target> targets = controller.getTargets();
        ArrayList<Target> explodedTargets = new ArrayList<Target>();
        for (Target target : targets) {
            if(distance(atomic, target) <= 150) {
                explodedTargets.add(target);
            }
        }
        for (Target target : explodedTargets) {
            controller.explode(target);
        }
        drawExplosionCircle();
        removeAtomic();
    }

    private void drawExplosionCircle() {
        Circle circle = new Circle();
        circle.setCenterX(atomic.getLayoutX() + atomic.getWidth() / 2.0);
        circle.setCenterY(atomic.getLayoutY() + atomic.getHeight() / 2.0);
        circle.setRadius(150);
        circle.setFill(Color.rgb(255, 255, 255, 1));
        controller.getGamePane().getChildren().add(0, circle);
        Timeline fade = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            circle.setOpacity(circle.getOpacity() - 0.01);
        }));
        fade.setCycleCount(100);
        fade.play();
        fade.setOnFinished(event -> {
            controller.getGamePane().getChildren().remove(circle);
        });
    }

    private void removeAtomic() {
        this.stop();
        controller.getGamePane().getChildren().remove(atomic);
        controller.getAnimations().remove(this);
    }

    private double distance(Rectangle lhs, Rectangle rhs) {
        double x1 = lhs.getLayoutX() + lhs.getWidth() / 2.0; 
        double y1 = lhs.getLayoutY() + lhs.getHeight() / 2.0;
        double x2 = rhs.getLayoutX() + rhs.getWidth() / 2.0;
        double y2 = rhs.getLayoutY() + rhs.getHeight() / 2.0;
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
}
