package com.example.view;

import java.io.File;
import java.util.ArrayList;

import com.example.controller.AvatarMenuController;
import com.example.model.AvatarPicker;
import com.example.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AvatarMenu {
    
    private static Stage mainStage;
    private static Scene scene;

    public static void run(Stage stage) throws Exception {
        mainStage = stage;
        scene = new Scene(FXMLLoader.load(AvatarMenu.class.getResource("/FXML/avatar-menu.fxml")));
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.centerOnScreen();
    }

    @FXML
    private Label username,
                  dropBoxLabel;

    @FXML
    private Circle userAvatar,
                   avatarPicture1,
                   avatarPicture2,
                   avatarPicture3,
                   avatarPicture4;

    @FXML
    private BorderPane dragAndDropPane;


    @FXML
    public void initialize() {
        initializeUserInfo();
        AvatarMenuController.init();
        initializeAvatarPics();

        Tooltip tooltip = new Tooltip("your image dimensions must be small enough :)");
        tooltip.setFont(new Font(15));
        Tooltip.install(dragAndDropPane, tooltip);
    }

    private void initializeUserInfo() {
        username.setText(User.getLoggedInUser().getUsername());
        userAvatar.setFill(new ImagePattern(User.getLoggedInUser().getAvatarImage()));
    }
    
    private void initializeAvatarPics() {
        ArrayList<Image> initialAvatarPics = AvatarMenuController.getInitialAvatarPics();
        fillDefaultAvatars(initialAvatarPics);
    }

    private void fillDefaultAvatars(ArrayList<Image> images) {
        avatarPicture1.setFill(new ImagePattern(images.get(0)));
        avatarPicture2.setFill(new ImagePattern(images.get(1)));
        avatarPicture3.setFill(new ImagePattern(images.get(2)));
        avatarPicture4.setFill(new ImagePattern(images.get(3)));
    }

    
    @FXML
    private void goBack(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        MainMenu.run(mainStage);
    }
    
    @FXML
    private void changeAvatar(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY) return;
        
        Circle target = (Circle) event.getSource();
        Image selectedImage = ((ImagePattern) target.getFill()).getImage();
        
        setAvatar(selectedImage);
    }

    @FXML
    private void chooseFileFromPC(MouseEvent event) throws Exception {
        if(event.getButton() != MouseButton.PRIMARY) return;

        Image customAvatar = AvatarPicker.customAvatar();
        if(customAvatar == null) return;

        setAvatar(customAvatar);
    }

    @FXML
    private void changeDragAndDropLabelText(DragEvent event) {
        if(!event.getDragboard().hasFiles()) return;
        File file = event.getDragboard().getFiles().get(0);
        
        if(!AvatarMenuController.isImage(file)) return;
        
        dropBoxLabel.setText("drop it here");
    }

    @FXML
    private void resetDragAndDropLabelText(DragEvent event) {
        dropBoxLabel.setText("or drag and drop here");
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            File file = event.getDragboard().getFiles().get(0);
            if(AvatarMenuController.isImage(file)) event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    @FXML
    private void setDragDropAvatar(DragEvent event) throws Exception {
        boolean success = false;
        if(event.getDragboard().hasFiles()) {
            File file = event.getDragboard().getFiles().get(0);
            Image image = new Image(file.toURI().toURL().toString());
            setAvatar(image);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void setAvatar(Image image) {
        ArrayList<Image> avatarsArrangement = AvatarMenuController.setAvatarPicture(image);
        fillDefaultAvatars(avatarsArrangement);
        userAvatar.setFill(new ImagePattern(User.getLoggedInUser().getAvatarImage()));
    }
}
