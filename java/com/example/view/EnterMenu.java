package com.example.view;

import com.example.controller.EnterMenuController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EnterMenu {

    private static Stage mainStage;
    private static Scene scene;
    private static Media themeSong;
    private static MediaPlayer songPlayer;
    static {
        themeSong = new Media(EnterMenu.class.getResource("/sounds/Significance.mp3").toString());
        songPlayer = new MediaPlayer(themeSong);
        songPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                songPlayer.seek(Duration.ZERO);
            }
        });
    }
    

    public static void initialize(Stage stage) throws Exception {
        mainStage = stage;
        scene = new Scene(FXMLLoader.load(EnterMenu.class.getResource("/FXML/enter-menu.fxml")));
        mainStage.setScene(scene);
    }
    
    public static void run(Stage stage) throws Exception {
        
        initialize(stage);
        songPlayer.play();
        mainStage.show();
        mainStage.centerOnScreen();

    }

    public static MediaPlayer getPlayer() {
        return songPlayer;
    }

    // ===================================================================================================

    @FXML
    private Button enterButton,
                   registerButton,
                   enterAsGuest;

    @FXML
    private TextField usernameForEnter,
                      usernameForRegister;

    @FXML
    private PasswordField passwordForEnter,
                          passwordForRegister,
                          passwordConfirmation;

    @FXML
    private Label enterErrorLabel,
                  registerErrorLabel;


    @FXML
    private void enter(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        String username = usernameForEnter.getText();
        String password = passwordForEnter.getText();
        
        String result = EnterMenuController.loginUser(username, password);
        enterErrorLabel.setText(result);

        if(result.length() == 0) {
            EnterMenuController.gotoMainMenu(mainStage);
        }
    }
    
    @FXML
    private void register(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        String username = usernameForRegister.getText();
        String password = passwordForRegister.getText();
        String passwordAgain = passwordConfirmation.getText();
        
        String result = EnterMenuController.registerUser(username, password, passwordAgain);
        registerErrorLabel.setTextFill(Color.rgb(255, 0, 0));
        registerErrorLabel.setText(result);

        if(result.length() == 0) {
            registerErrorLabel.setTextFill(Color.rgb(21, 107, 0));
            registerErrorLabel.setText("register successful");
            usernameForRegister.setText("");
            passwordForRegister.setText("");
            passwordConfirmation.setText("");
        }

    }
    
    @FXML
    private void enterAsGuest(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        EnterMenuController.enterAsGuest();
        EnterMenuController.gotoMainMenu(mainStage);
    }

}
