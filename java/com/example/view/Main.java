package com.example.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        mainStage = stage;
        stage.setTitle("Atomic Bomber");
        stage.getIcons().add(new Image(getClass().getResource("/images/radio-active.png").toString()));
        stage.setResizable(false);
        
        EnterMenu.run(stage);
    }

    public static Stage getStage() {
        return mainStage;
    }
}