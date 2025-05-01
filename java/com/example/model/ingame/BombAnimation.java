package com.example.model.ingame;

import java.util.ArrayList;

import com.example.controller.GameController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BombAnimation extends Transition {

    private Bomb bomb;
    private GameController controller;
    private boolean hitSomething = false;

    private double vSpeed, hSpeed;

    public BombAnimation(GameController controller, Bomb bomb, double vSpeed, double hSpeed) {
        this.controller = controller;
        this.bomb = bomb;
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
        this.vSpeed = vSpeed;
        this.hSpeed = hSpeed;
        
        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {
        
        double x = bomb.getLayoutX();
        double y = bomb.getLayoutY();

        handleIntersectionWithTargets();
        if(hitSomething) {
            removeBomb(true);
        }
        
        x += hSpeed;
        y += vSpeed;
        vSpeed += Bomb.ACCELERATION;
        
        if(x > 1200) x = 0;
        if(x < 0) x = 1200;
        
        if(y > 702) {
            removeBomb(false);
        }
        
        bomb.setLayoutX(x);
        bomb.setLayoutY(y);
        bomb.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)));
    }

    private void removeBomb(boolean hit) {
        this.stop();
        if(!hit) showExplosion();
        controller.getGamePane().getChildren().remove(bomb);
        controller.getAnimations().remove(this);
    }
    
    private void handleIntersectionWithTargets() {
        ArrayList<Target> targets = controller.getTargets();
        ArrayList<Target> explodedTargets = new ArrayList<Target>();
        for (Target target : targets) {
            if(bomb.getBoundsInParent().intersects(target.getBoundsInParent())) {
                explodedTargets.add(target);
                hitSomething = true;
            }
        }

        for (Target target : explodedTargets) {
            controller.explode(target);
        }
    }

    private void showExplosion() {
        double x = bomb.getLayoutX();
        double y = bomb.getLayoutY();
        Rectangle explosion = new Rectangle(20, 20);
        explosion.setLayoutX(x + 10);
        explosion.setLayoutY(y + 10);
        explosion.setFill(new ImagePattern(new Image(getClass().getResource("/images/explosion.png").toExternalForm()))); 
        int index = controller.getGamePane().getChildren().indexOf(bomb);
        controller.getGamePane().getChildren().add(index+1, explosion);
        Timeline fade = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            explosion.setOpacity(explosion.getOpacity() - 0.02);
        }));
        fade.setCycleCount(50);
        fade.setOnFinished(event -> {
            controller.getGamePane().getChildren().remove(explosion);
        });
        fade.play();
    }
    
}
