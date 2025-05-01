package com.example.controller;

import java.util.ArrayList;
import java.util.Random;

import com.example.model.User;
import com.example.model.ingame.Atomic;
import com.example.model.ingame.AtomicBonus;
import com.example.model.ingame.Attacker;
import com.example.model.ingame.AttackingTank;
import com.example.model.ingame.Bomb;
import com.example.model.ingame.Building;
import com.example.model.ingame.Cluster;
import com.example.model.ingame.ClusterBonus;
import com.example.model.ingame.Fighter;
import com.example.model.ingame.FlamingAnimation;
import com.example.model.ingame.MPBarAnimation;
import com.example.model.ingame.Mig;
import com.example.model.ingame.MigAnimation;
import com.example.model.ingame.MigBuilder;
import com.example.model.ingame.Tank;
import com.example.model.ingame.Target;
import com.example.model.ingame.Tree;
import com.example.model.ingame.Truck;
import com.example.view.GameInterface;
import com.example.view.Main;
import com.example.view.MainMenu;
import com.example.model.ingame.MovingTarget;
import com.example.model.ingame.MovingTargetAnimation;
import com.example.model.ingame.Rocket;
import com.example.model.ingame.Shelter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GameController {

    // Game soft objects:
    private GameInterface gameInterface;
    private Media themeSong;
    private MediaPlayer songPlayer;
    private Random randomNumberGenerator;
    private Pane gamePane;

    // Game hard objects:
    private Fighter fighter;
    private ArrayList<Target> targets;

    // Game info:
    private int killCount, atomicCount, 
                clusterCount, 
                bombsDropped, 
                bombs,
                waveNumber;

    private double hp, mp;

    // Animations:
    private ArrayList<Animation> allAnimations;
    private boolean isPaused = false, isFreezed = false;
    
    private boolean isWaveFinished = false, isGameFinished = false;

    // Constructor:
    public GameController(GameInterface gameInterface, Pane pane) {
        this.gameInterface = gameInterface;
        gamePane = pane;

        allAnimations = new ArrayList<Animation>();
        themeSong = new Media(getClass().getResource("/sounds/End-of-the-Unknown.mp3").toString());
        songPlayer = new MediaPlayer(themeSong);
        songPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                songPlayer.seek(Duration.ZERO);
            }
        });
        randomNumberGenerator = new Random();
        songPlayer.play();
    }
    

    // Getter methods:
    public Pane getGamePane() {
        return gamePane;
    }
    
    public Fighter getFighter() {
        return fighter;
    }
    
    public ArrayList<Target> getTargets() {
        return targets;
    }
    
    public int getAtomicCount() {
        return atomicCount;
    }

    public int getClusterCount() {
        return clusterCount;
    }

    public double getMP() {
        return mp;
    }

    public double getHP() {
        return hp;
    }

    public ArrayList<Animation> getAnimations() {
        return allAnimations;
    }
    
    public int getRandomInt(int leftBound, int rightBound) {
        return randomNumberGenerator.nextInt(rightBound - leftBound) + leftBound;
    }
    
    public double getRandomDouble(double leftBound, double rightBound) {
        return randomNumberGenerator.nextDouble() * (rightBound - leftBound) + leftBound;
    }
    

    // Setter Methods:
    public void setMP(double val) {
        mp = val;
        gameInterface.setMP(val / 100.0);
    } 

    public void setHP(double val) {
        hp = val;
        gameInterface.setHP(val / 100.0);
        if(val <= 0) {
            gameOver();
        }
    }
    
    public void setAtomicCount(int val) {
        atomicCount = val;
        gameInterface.setAtomicCount(val);
    }

    public void setClusterCount(int val) {
        clusterCount = val;
        gameInterface.setClusterCount(val);
    }


    // Wave methods:
    public void startFirstWave() {
        waveNumber = 1;
        targets = new ArrayList<Target>();
        bombsDropped = killCount = atomicCount = clusterCount = 0;
        bombs = 2;
        hp = 100; mp = 0;
        
        placeFighter();
        
        placeFixedTargets();
        placeMovingTargets();
        isWaveFinished = false;
    }
    
    public void startSecondWave() {
        waveNumber = 2;
        bombs = 3;
        gameInterface.setWaveNumber(2);
        if(isPaused) togglePause();

        placeFighter();
        
        placeFixedTargets();
        placeMovingTargets();
        int cnt = getRandomInt(1, 3);
        for (int i = 0; i < cnt; i++) {
            placeAttackingTank();
        }
        isWaveFinished = false;
    }

    public void startThirdWave() {
        waveNumber = 3;
        bombs = 4;
        gameInterface.setWaveNumber(3);
        if(isPaused) togglePause();

        placeFighter();
        
        placeMigBuilder();

        placeFixedTargets();
        placeMovingTargets();
        int cnt = getRandomInt(1, 3);
        for (int i = 0; i < cnt; i++) {
            placeAttackingTank();
        }
        isWaveFinished = false;
    }

    private void finishWave() {
        if(waveNumber == 3) {
            finishGame();
            return;
        }

        togglePause();
        isWaveFinished = true;
        Pane finishWavePane = null;
        try {
            finishWavePane = FXMLLoader.load(getClass().getResource("/FXML/finish-wave.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        double accuracy = (double) killCount / (double) bombsDropped;
        accuracy = Math.round(accuracy * 100);
        Label accuracyIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(1);
        accuracyIndicator.setText("ACCURACY: " + (int) accuracy + "%");

        getGamePane().getChildren().add(finishWavePane);
    }
    
    private void goToNextWave(boolean isCheat) {
        if(waveNumber == 3) {
            finishWave(); return;
        }
        if(!isCheat) {
            gamePane.getChildren().remove(gamePane.getChildren().size() - 1);
        }
        
        removeEveryThing();
        
        if(waveNumber == 1) {
            startSecondWave(); return;
        }
        if(waveNumber == 2) {
            startThirdWave(); return;
        }
    }
    
    private void removeEveryThing() {
        ArrayList<Node> toBeRemoved = new ArrayList<Node>();
        for (int i = 0; i < gamePane.getChildren().size() - 8; i++) {
            toBeRemoved.add(gamePane.getChildren().get(i));
        }
        for (Node node : toBeRemoved) {
            gamePane.getChildren().remove(node);
        }
        targets.clear();
        allAnimations.clear();
    }
    
    private boolean isWaveFinished() {
        for (Target target : targets) {
            if(!(target instanceof Tree)) return false;
        }
        isWaveFinished = true;
        return true;
    }
    
    private void finishGame() {
        
        togglePause();
        isWaveFinished = true;
        isGameFinished = true;
        Pane finishWavePane = null;
        try {
            finishWavePane = FXMLLoader.load(getClass().getResource("/FXML/finish-game.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        double accuracy = (double) killCount / (double) bombsDropped;
        accuracy = Math.round(accuracy * 100);
        Label accuracyIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(1);
        accuracyIndicator.setText("ACCURACY: " + (int) accuracy + "%");
        
        Label killIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(2);
        killIndicator.setText(killCount + " Kills");

        getGamePane().getChildren().add(finishWavePane);
        
    }
    
    private void gameOver() {
        
        togglePause();
        isWaveFinished = true;
        isGameFinished = true;
        Pane finishWavePane = null;
        try {
            finishWavePane = FXMLLoader.load(getClass().getResource("/FXML/lose-popup.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        double accuracy = (double) killCount / (double) bombsDropped;
        accuracy = Math.round(accuracy * 100);
        Label accuracyIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(1);
        accuracyIndicator.setText("ACCURACY: " + (int) accuracy + "%");
        
        Label killIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(2);
        killIndicator.setText(killCount + " Kills");
        
        Label waveIndicator = (Label) ((Pane) finishWavePane.getChildren().get(0)).getChildren().get(3);
        waveIndicator.setText("Wave " + waveNumber);

        getGamePane().getChildren().add(finishWavePane);

    }


    // Initialization methods:
    private void placeFixedTargets() {
        int treeCount = getRandomInt(3, 5);
        for (int i = 0; i < treeCount; i++) {
            placeTree();
        }
        int buildingCount = getRandomInt(1, 3);
        for (int i = 0; i < buildingCount; i++) {
            placeBuilding();
        }
        int shelterCount = getRandomInt(1, 3);
        for (int i = 0; i < shelterCount; i++) {
            placeShelter();
        }
    }
    
    private void placeMovingTargets() {
        int tankCount = getRandomInt(3, 5);
        for (int i = 0; i < tankCount; i++) {
            placeTank();
        }
        int truckCount = getRandomInt(2, 4);
        for (int i = 0; i < truckCount; i++) {
            placeTruck();
        }
    }

    private void placeTree() {
        Tree tree = new Tree(this, getRandomDouble(20, 1150));
        // fixedTargets.add(tree);
        targets.add(tree);
        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), tree);
    }
    
    private void placeBuilding() {
        Building building = new Building(this, getRandomDouble(40, 1140));
        // fixedTargets.add(building);
        targets.add(building);
        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), building);
    }
    
    private void placeShelter() {
        Shelter shelter = new Shelter(this, getRandomDouble(40, 1140));
        // fixedTargets.add(shelter);
        targets.add(shelter);
        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), shelter);
    }
    
    private void placeTank() {
        double speedCoef = User.getLoggedInUser().getSetting().tankSpeed;
        Tank tank = new Tank(this, getRandomDouble(80, 1100), 
        getRandomInt(0, 2) % 2 == 0, 
        speedCoef * getRandomDouble(Tank.MIN_SPEED, Tank.MAX_SPEED));
        
        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), tank);
        targets.add(tank);
        tank.getAnimation().play();
    }
    
    private void placeTruck() {
        Truck truck = new Truck(this, getRandomDouble(80, 1100), 
        getRandomInt(0, 2) % 2 == 0, 
        getRandomDouble(Truck.MIN_SPEED, Truck.MAX_SPEED));
        
        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), truck);
        // vehicles.add(truck);
        targets.add(truck);
        truck.getAnimation().play();
    }
    
    private void placeAttackingTank() {
        double speedCoef = User.getLoggedInUser().getSetting().tankSpeed;
        AttackingTank tank = new AttackingTank(this, getRandomDouble(80, 1100), 
        getRandomInt(0, 2) % 2 == 0, 
        speedCoef * getRandomDouble(Tank.MIN_SPEED, Tank.MAX_SPEED));

        int size = gamePane.getChildren().size();
        gamePane.getChildren().add(getRandomInt(1, size-7), tank);
        targets.add(tank);
        tank.getAnimation().play();
    }

    private void placeMigBuilder() {
        MigBuilder migBuilder = new MigBuilder(this);
        migBuilder.play();
    }

    private void placeFighter() {
        fighter = new Fighter(0, 100, this);
        fighter.setOnKeyPressed(event -> {
            handleKeyPress(event);
        });
        fighter.setOnKeyReleased(event -> {
            handleKeyRelease(event);
        });
        gamePane.getChildren().add(0, fighter);
        fighter.getAnimation().play();
        fighter.requestFocus();
    }
    

    // Handler methods:
    private void handleKeyPress(KeyEvent event) {
        if(isPaused || isWaveFinished) return;
        if(event.getCode() == KeyCode.W) {
            fighter.getAnimation().setDecreaseRotate(true);
        }
        if(event.getCode() == KeyCode.S) {
            fighter.getAnimation().setIncreaseRotate(true);
        }
        if(event.getCode() == KeyCode.A) {
            fighter.setSpeedR(Fighter.MIN_SPEED);
        }
        if(event.getCode() == KeyCode.D) {
            fighter.setSpeedR(Fighter.MAX_SPEED);
        }
    }
    
    private void handleKeyRelease(KeyEvent event) {
        if(isGameFinished) {
            if(event.getCode() == KeyCode.ENTER) {
                songPlayer.stop();
                try {
                    MainMenu.run(Main.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if(isWaveFinished) {
            if(event.getCode() == KeyCode.ENTER) {
                goToNextWave(false);
            }
            return;
        }
        if(isPaused) {
            if(event.getCode() == KeyCode.ESCAPE) {
                togglePause();
            }
            return;
        }
        if(event.getCode() == KeyCode.W) {
            fighter.getAnimation().setDecreaseRotate(false);
        }
        if(event.getCode() == KeyCode.S) {
            fighter.getAnimation().setIncreaseRotate(false);
        }
        if(event.getCode() == KeyCode.A) {
            fighter.setSpeedR(Fighter.DEFAULT_SPEED);
        }
        if(event.getCode() == KeyCode.D) {
            fighter.setSpeedR(Fighter.DEFAULT_SPEED);
        }
        if(event.getCode() == KeyCode.SPACE) {
            shootBomb();
        }
        if(event.getCode() == KeyCode.R) {
            shootAtomic();
        }
        if(event.getCode() == KeyCode.C) {
            shootCluster();
        }
        if(event.getCode() == KeyCode.TAB) {
            useMana();
        }
        if(event.getCode() == KeyCode.T) {
            placeTank();
        }
        if(event.getCode() == KeyCode.G) {
            setAtomicCount(atomicCount + 1);
        }
        if(event.getCode() == KeyCode.CONTROL) {
            setClusterCount(clusterCount + 1);
        }
        if(event.getCode() == KeyCode.H) {
            setHP(100);
        }
        if(event.getCode() == KeyCode.P) {
            goToNextWave(true);
        }
        if(event.getCode() == KeyCode.ESCAPE) {
            togglePause();
        }
    }
    

    // Freeze and pause:
    private boolean isFreezable(Animation animation) {
        return (animation instanceof MigAnimation || animation instanceof MigBuilder
                                                  || animation instanceof MovingTargetAnimation
                                                  || animation instanceof Timeline);
    }

    private void togglePause() {
        for (Animation animation : allAnimations) {
            if(!isPaused) animation.pause();
            else {
                if(!isFreezed) animation.play();
                else if(!isFreezable(animation)) animation.play();
            }
        }
        isPaused = !isPaused;
    }

    public void freeze() {
        isFreezed = true;
        for (Animation animation : allAnimations) {
            if(isFreezable(animation)) {
                animation.pause();
            }
        }
    }
    
    public void unfreeze() {
        isFreezed = false;
        for (Animation animation : allAnimations) {
            if(isFreezable(animation)) {
                animation.play();
            }
        }
    }

    private void useMana() {
        if(mp < 100) return;
        MPBarAnimation animation = new MPBarAnimation(this);
        animation.play();
    }

    public void resetMPBar() {
        gameInterface.resetMPBar();
    }


    // Shoot methods:
    private void shootBomb() {
        if(bombs == 0) return;
        bombs--;
        bombsDropped++;
        double initX = fighter.getLayoutX() + fighter.getWidth() / 2;
        double initY = fighter.getLayoutY() + fighter.getHeight() / 2;
        double vSpeed = fighter.getSpeedR() * Math.sin(fighter.getSpeedTheta());
        double hSpeed = fighter.getSpeedR() * Math.cos(fighter.getSpeedTheta());
        Bomb bomb = new Bomb(initX, initY, vSpeed, hSpeed * 0.75, this);
        gamePane.getChildren().add(gamePane.getChildren().indexOf(fighter), bomb);
        bomb.getAnimation().play();
        Timeline reloadTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> bombs++));
        reloadTimeline.setCycleCount(1);
        reloadTimeline.play();
    }
    
    private void shootAtomic() {
        if(atomicCount == 0) return;
        bombsDropped++;
        setAtomicCount(atomicCount - 1);
        double initX = fighter.getLayoutX() + fighter.getWidth() / 2;
        double initY = fighter.getLayoutY() + fighter.getHeight() / 2;
        Atomic atomic = new Atomic(this, initX, initY);
        gamePane.getChildren().add(gamePane.getChildren().indexOf(fighter), atomic);  
        atomic.getAnimation().play();  
    }
    
    private void shootCluster() {
        if(clusterCount == 0) return;
        bombsDropped++;
        setClusterCount(clusterCount - 1);
        double initX = fighter.getLayoutX() + fighter.getWidth() / 2;
        double initY = fighter.getLayoutY() + fighter.getHeight() / 2;
        Cluster cluster = new Cluster(this, initX, initY);
        gamePane.getChildren().add(gamePane.getChildren().indexOf(fighter), cluster);
        cluster.getAnimation().play();
    }

    private void giveAtomic(Building building) {
        double initX = building.getLayoutX() + building.getWidth() / 2.0;
        double initY = building.getLayoutY() + building.getHeight() / 2.0;
        AtomicBonus bonus = new AtomicBonus(this, initX, initY);
        gamePane.getChildren().add(0, bonus);
        bonus.getAnimation().play();
    }

    private void giveCluster(Shelter shelter) {
        double initX = shelter.getLayoutX() + shelter.getWidth() / 2.0;
        double initY = shelter.getLayoutY() + shelter.getHeight() / 2.0;
        ClusterBonus bonus = new ClusterBonus(this, initX, initY);
        gamePane.getChildren().add(0, bonus);
        bonus.getAnimation().play();
    }


    // BOOOOM!!
    public void explode(Target target) {
        
        if(target instanceof MovingTarget) {
            ((MovingTarget) target).getAnimation().stop();
            allAnimations.remove(((MovingTarget) target).getAnimation());
        }
        
        if(target instanceof Attacker) {
            ((Attacker) target).getAttackerTimeline().stop();
            allAnimations.remove(((Attacker) target).getAttackerTimeline());
        }
        
        if(target instanceof Building) {
            giveAtomic((Building) target);
        }
        
        if(target instanceof Shelter) {
            giveCluster((Shelter) target);
        }
        
        targets.remove(target);
        FlamingAnimation animation = new FlamingAnimation(this, target);
        animation.play();

        killCount += target.getKillValue();
        gameInterface.setKillCount(killCount);
        setMP(Math.min(100, mp + target.getKillValue() * 10));

        if(isWaveFinished()) {
            finishWave();
        }
    }


    // We're under attack!!!
    public void migAttack() {
        Mig mig = new Mig(this, getRandomDouble(100, 600), getRandomDouble(5, 6.5));
        gamePane.getChildren().add(mig);
        mig.getAnimation().play();
    }

    public void attackFighter(Attacker attacker) {
        if(!attacker.isInRange()) return;
        double initX = attacker.getLayoutX() + attacker.getWidth() / 2.0;
        double initY = attacker.getLayoutY() + attacker.getHeight() / 2.0;
        Rocket rocket = new Rocket(this, initX, initY);
        gamePane.getChildren().add(rocket);
        rocket.getAnimation().play();
    }

    public void hitFighter() {
        setHP(Math.max(0, hp - 34));
    }
}
