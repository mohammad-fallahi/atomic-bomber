package com.example.model.ingame;

import javafx.animation.Timeline;

public interface Attacker {
    
    public double getLayoutX();

    public double getLayoutY();

    public double getWidth();

    public double getHeight();

    public void setInRange(boolean val);

    public boolean isInRange();

    public double getAttackRadius();

    public Timeline getAttackerTimeline();

}
