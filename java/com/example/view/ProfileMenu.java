package com.example.view;

import java.util.Optional;

import com.example.controller.PrfoileMenuController;
import com.example.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ProfileMenu {
    
    private static Stage mainStage;
    private static Scene scene;
    private static MediaPlayer songPlayer;

    public static void run(Stage stage, MediaPlayer player) throws Exception {

        songPlayer = player;

        mainStage = stage;
        scene = new Scene(FXMLLoader.load(ProfileMenu.class.getResource("/FXML/profile-menu.fxml")));
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.centerOnScreen();

    }


    @FXML
    private Label username,
                  logoutButton,
                  deleteAccButton,
                  updateButton,
                  errorField;

    @FXML
    private Circle avatar;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField oldPass,
                          newPass,
                          newPassConfirm;


    @FXML
    public void initialize() {
        initializeUserInfo();

        if(User.getLoggedInUser().isGuest()) {
            usernameField.setDisable(true);
            oldPass.setDisable(true);
            newPass.setDisable(true);
            newPassConfirm.setDisable(true);
            updateButton.setDisable(true);
        }
    }

    private void initializeUserInfo() {
        username.setText(User.getLoggedInUser().getUsername());
        avatar.setFill(new ImagePattern(User.getLoggedInUser().getAvatarImage()));
    }


    @FXML
    private void goBack(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        MainMenu.run(mainStage);
    }

    @FXML
    private void logOut() throws Exception {
        PrfoileMenuController.logOut();

        songPlayer.stop();
        EnterMenu.run(mainStage);
    }

    @FXML
    private void deleteAccount() throws Exception {
        if(!deletionConfirmed()) return;

        PrfoileMenuController.deleteAccount();

        songPlayer.stop();
        EnterMenu.run(mainStage);
    }

    private boolean deletionConfirmed() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("delete account confirmation");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("all your data will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) return true;
        return false;
    }

    @FXML
    private void updateInfo(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;

        errorField.setTextFill(Color.rgb(255, 0, 0));
        String newUsername = usernameField.getText(),
               oldPassword = oldPass.getText(),
               newPassword = newPass.getText(),
               passwordConfirm = newPassConfirm.getText();
        String result = PrfoileMenuController.updateInfo(newUsername, oldPassword, newPassword, passwordConfirm);
        errorField.setText(result);
        if(result.equals("data updated successfully")) {
            errorField.setTextFill(Color.rgb(178, 255, 0));
            usernameField.setText("");
            oldPass.setText("");
            newPass.setText("");
            newPassConfirm.setText("");
        }
        username.setText(User.getLoggedInUser().getUsername());
        if(User.getLoggedInUser().getUsername().equals(usernameField.getText())) {
            usernameField.setText("");
        }
    }

}
