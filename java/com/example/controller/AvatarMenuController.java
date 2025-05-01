package com.example.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import com.example.model.AvatarPicker;
import com.example.model.User;

import javafx.scene.image.Image;

public class AvatarMenuController {

    private static ArrayList<Image> avatarsArrangement;
    
    public static void init() {
        avatarsArrangement = AvatarPicker.getAllImages();
        avatarsArrangement.remove(User.getLoggedInUser().getAvatarImage());
    }
    
    public static ArrayList<Image> getInitialAvatarPics() {
        return avatarsArrangement;
    }

    private static void selectAvatarFromDefaults(Image targetImage) {
        ArrayList<Image> allDefaultAvatars = AvatarPicker.getAllImages();
        for (Image image : allDefaultAvatars) {
            if(!avatarsArrangement.contains(image)) {
                avatarsArrangement.set(avatarsArrangement.indexOf(targetImage), image);
                break;
            }
        }
        User.getLoggedInUser().setAvatarImage(targetImage);
    }
    
    public static ArrayList<Image> setAvatarPicture(Image image) {

        if(avatarsArrangement.contains(image)) {
            selectAvatarFromDefaults(image);
        } else {
            User.getLoggedInUser().setAvatarImage(image);
        }

        return avatarsArrangement;
    }

    public static boolean isImage(File file) {
        try {
            String mimetype = Files.probeContentType(file.toPath());
            if(mimetype == null || !mimetype.split("/")[0].equals("image")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
