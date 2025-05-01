package com.example.model;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AvatarPicker {
    
    private enum Avatars {
        AVATAR_1(new Image(AvatarPicker.class.getResource("/images/avatar-pic-1.png").toString())),
        AVATAR_2(new Image(AvatarPicker.class.getResource("/images/avatar-pic-2.png").toString())),
        AVATAR_3(new Image(AvatarPicker.class.getResource("/images/avatar-pic-3.png").toString())),
        AVATAR_4(new Image(AvatarPicker.class.getResource("/images/avatar-pic-4.png").toString())),
        AVATAR_5(new Image(AvatarPicker.class.getResource("/images/avatar-pic-5.png").toString()))
        ;
        
        private Image image;
        
        Avatars(Image image) {
            this.image = image;
        }
        
        public Image getImage() {
            return image;
        }
    }

    private static final Avatars[] VALUES = Avatars.values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();
    

    public static ArrayList<Image> getAllImages() {
        ArrayList<Image> result = new ArrayList<Image>();
        for (Avatars avatar : VALUES) {
            result.add(avatar.getImage());
        }
        return result;
    }
    
    
    public static Image randomAvatar() {
        return VALUES[RANDOM.nextInt(SIZE)].getImage();
    }

    public static Image customAvatar() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        fileChooser.setTitle("Select an image file");

        File file = fileChooser.showOpenDialog(null);
        if(file == null) return null;

        return new Image(file.toURI().toURL().toString());
    }
}