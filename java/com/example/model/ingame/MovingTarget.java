package com.example.model.ingame;

import com.example.controller.GameController;


public abstract class MovingTarget extends Target {
    
    private GameController controller;
    private MovingTargetAnimation animation;

    public MovingTarget(GameController controller, double width, 
                                                   double height, 
                                                   double initX, 
                                                   int killValue, 
                                                   boolean initDirection) {
        super(controller, width, height, initX, killValue);
        if(!initDirection) this.setScaleX(-1);
        this.controller = controller;

        animation = new MovingTargetAnimation(this, controller, initDirection);
    }

    public GameController getController() {
        return controller;
    }

    public MovingTargetAnimation getAnimation() {
        return animation;
    }

    public abstract double getSpeed();
}
