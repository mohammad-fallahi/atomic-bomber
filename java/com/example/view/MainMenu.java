package com.example.view;

import com.example.model.User;

import javafx.scene.media.MediaPlayer.Status;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu {

    private static Stage mainStage;
    private static Scene scene;
    private static Media themeSong;
    private static MediaPlayer songPlayer;
    static {
        themeSong = new Media(MainMenu.class.getResource("/sounds/Mourning.mp3").toString());
        songPlayer = new MediaPlayer(themeSong);
        songPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                songPlayer.seek(Duration.ZERO);
            }
        });
    }
    
    public static void run(Stage stage) throws Exception {
        
        mainStage = stage;
        scene = new Scene(FXMLLoader.load(MainMenu.class.getResource("/FXML/main-menu.fxml")));
        mainStage.setScene(scene);
        if(!songPlayer.getStatus().equals(Status.PLAYING)) songPlayer.play();
        mainStage.show();
        mainStage.centerOnScreen();
        
    }

    
    @FXML
    private Label username,
                  resumeButton,
                  newGameButton,
                  settingsButton,
                  standingsButton,
                  quitButton;
    
    @FXML
    private Circle avatar;
    

    @FXML
    public void initialize() {

        initializeUserInfo();

        Tooltip tooltip = new Tooltip("Avatar menu");
        tooltip.setFont(new Font(20));
        Tooltip.install(avatar, tooltip);
    }


    private void initializeUserInfo() {
        username.setText(User.getLoggedInUser().getUsername());
        avatar.setFill(new ImagePattern(User.getLoggedInUser().getAvatarImage()));
    }


    @FXML
    private void goToProfileMenu(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        ProfileMenu.run(mainStage, songPlayer);
    }

    @FXML
    private void goToAvatarMenu(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        AvatarMenu.run(mainStage);
    }

    @FXML
    private void resumeGame(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;

    }

    @FXML
    private void startNewGame(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        songPlayer.stop();
        (new GameInterface()).run(mainStage);
    }

    @FXML
    private void goToSettings(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        SettingMenu.run(mainStage);
    }

    @FXML
    private void goToStandings(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;

    }

    @FXML
    private void quitGame(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;

        Platform.exit();
    }

}
