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

public class ClusterAnimation extends Transition {

    private GameController controller;
    private Cluster cluster;

    private double hSpeed, vSpeed;
    private double minY;

    public ClusterAnimation(GameController controller, Cluster cluster, double vSpeed, double hSpeed) {
        this.controller = controller;
        this.cluster = cluster;
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
        minY = 800;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);

        this.controller.getAnimations().add(this);
    }

    @Override
    protected void interpolate(double arg0) {

        double x = cluster.getLayoutX();
        double y = cluster.getLayoutY();

        minY = Math.min(y, minY);

        x += hSpeed;
        y += vSpeed;
        vSpeed += Cluster.ACCELERATION;

        if(x > 1200) x = 0;
        if(x < 0) x = 1200;

        if(minY < 400 && y > 400) {
            divideIntoBombs();
        }

        if(y > 702) {
            BOOM();
        }
        
        cluster.setLayoutX(x);
        cluster.setLayoutY(y);
        cluster.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)));

    }

    void divideIntoBombs() {
        int count = controller.getRandomInt(4, 7);
        for(int i = 0; i < count; i++) {
            double initX = cluster.getLayoutX() + cluster.getWidth() / 2.0;
            double initY = cluster.getLayoutY() + cluster.getHeight() / 2.0;
            double vx = 1.5 * hSpeed / (count-1) * i + 0.2;
            double vy = 0.75 * vSpeed / (count-1) * (count-i-1);
            Bomb bomb = new Bomb(initX, initY, vy, vx, controller);
            int index = controller.getGamePane().getChildren().indexOf(cluster);
            controller.getGamePane().getChildren().add(index, bomb);
            bomb.getAnimation().play();
        }
        drawExplosionCircle();
        removeCluster();
    }

    private void BOOM() {
        ArrayList<Target> targets = controller.getTargets();
        ArrayList<Target> explodedTargets = new ArrayList<Target>();
        for (Target target : targets) {
            if(distance(cluster, target) <= 100) {
                explodedTargets.add(target);
            }
        }
        for (Target target : explodedTargets) {
            controller.explode(target);
        }
        drawExplosionCircle();
        removeCluster();
    }

    private void drawExplosionCircle() {
        Circle circle = new Circle();
        circle.setCenterX(cluster.getLayoutX() + cluster.getWidth() / 2.0);
        circle.setCenterY(cluster.getLayoutY() + cluster.getHeight() / 2.0);
        circle.setRadius(100);
        circle.setFill(Color.rgb(255, 255, 255, 1));
        controller.getGamePane().getChildren().add(0, circle);
        Timeline fade = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            circle.setOpacity(circle.getOpacity() - 0.01);
        }));
        fade.setCycleCount(100);
        fade.play();
        fade.setOnFinished(event -> {
            controller.getGamePane().getChildren().remove(circle);
        });
    }

    private void removeCluster() {
        this.stop();
        controller.getGamePane().getChildren().remove(cluster);
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
