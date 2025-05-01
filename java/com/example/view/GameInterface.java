package com.example.view;


import com.example.controller.GameController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameInterface {
    
    private Stage mainStage;
    private GameController controller;

    @FXML
    private Pane gamePane;

    @FXML
    private Label waveIndicator,
                  atomicIndicator,
                  clusterIndicator,
                  killCountIndicator;

    @FXML
    private ProgressBar hpBar,
                        mpBar;


    @FXML
    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.getFighter().requestFocus();
            }            
        });
        mpBar.getStyleClass().add("mana-bar");
        controller = new GameController(this, gamePane);
        controller.startFirstWave();
    }


    public void run(Stage stage) throws Exception {
        
        mainStage = stage;
        gamePane = FXMLLoader.load(getClass().getResource("/FXML/game-view.fxml"));
        Scene scene = new Scene(gamePane);
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.centerOnScreen();
    }

    public void setKillCount(int value) {
        killCountIndicator.setText("" + value);
    }

    public void setAtomicCount(int value) {
        atomicIndicator.setText("" + value);
    }

    public void setClusterCount(int value) {
        clusterIndicator.setText("" + value);
    }

    public void setMP(double value) {
        mpBar.setProgress(value);
        if(mpBar.getProgress() == 1.0) {
            mpBar.setId("full-mana-bar");
        }
    }

    public void setHP(double value) {
        hpBar.setProgress(value);
    }

    public void resetMPBar() {
        mpBar.setId("");
    }


    public void setWaveNumber(int wave) {
        waveIndicator.setText("Wave " + wave);
    }


    public void goToMainMenu() throws Exception {
        MainMenu.run(mainStage);
    }
}