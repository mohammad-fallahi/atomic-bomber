package com.example.view;

import com.example.model.DifficultySetting;
import com.example.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingMenu {
    
    private static Stage mainStage;
    private static Scene scene;

    public static void run(Stage stage) throws Exception {
        mainStage = stage;
        scene = new Scene(FXMLLoader.load(AvatarMenu.class.getResource("/FXML/settings-menu.fxml")));
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.centerOnScreen();
    }

    @FXML
    public void initialize() {
        resetColors();
        if(User.getLoggedInUser().getSetting() == DifficultySetting.EASY) {
            easy.setTextFill(Color.rgb(140, 0, 0));
        }
        if(User.getLoggedInUser().getSetting() == DifficultySetting.MEDIUM) {
            medium.setTextFill(Color.rgb(140, 0, 0));
        }
        if(User.getLoggedInUser().getSetting() == DifficultySetting.HARD) {
            hard.setTextFill(Color.rgb(140, 0, 0));
        }
    }

    @FXML
    private Label easy, medium, hard;

    @FXML
    private void goBack(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        MainMenu.run(mainStage);
    }

    private void resetColors() {
        easy.setTextFill(Color.WHITE);
        medium.setTextFill(Color.WHITE);
        hard.setTextFill(Color.WHITE);
    }

    @FXML
    private void setEasy(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;

        resetColors();
        easy.setTextFill(Color.rgb(140, 0, 0));
        User.getLoggedInUser().setDifficultySettings(DifficultySetting.EASY);
    }
    
    @FXML
    private void setMedium(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        resetColors();
        medium.setTextFill(Color.rgb(140, 0, 0));
        User.getLoggedInUser().setDifficultySettings(DifficultySetting.MEDIUM);
    }
    
    @FXML
    private void setHard(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        resetColors();
        hard.setTextFill(Color.rgb(140, 0, 0));
        User.getLoggedInUser().setDifficultySettings(DifficultySetting.HARD);
    }

}
