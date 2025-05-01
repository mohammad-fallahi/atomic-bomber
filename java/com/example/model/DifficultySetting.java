package com.example.model;

public enum DifficultySetting {
    
    EASY(1, 1, 1, 1),
    MEDIUM(2, 2, 2, 0.75),
    HARD(3, 3, 3, 0.5)
    ;
    public double tankSpeed;
    public double migAttackRadius;
    public double tankAttackRadius;
    public double migDelay;

    DifficultySetting(double tankSpeed, double migAttackRadius, double tankAttackRadius, double migDelay) {
        this.tankSpeed = tankSpeed;
        this.migAttackRadius = migAttackRadius;
        this.tankAttackRadius = tankAttackRadius;
        this.migDelay = migDelay;
    }
}
