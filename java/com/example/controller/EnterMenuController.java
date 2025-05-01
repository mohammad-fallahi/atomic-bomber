package com.example.controller;

import com.example.model.User;
import com.example.view.EnterMenu;
import com.example.view.MainMenu;

import javafx.stage.Stage;

public class EnterMenuController {
    
    public static String registerUser(String username, String password, String passwordConfirmation) {
        
        if(username.length() == 0 || password.length() == 0) {
            return "username and password must be non empty";
        }
        
        if(User.getUserByUsername(username) != null) {
            return "username already exists";
        }
        
        if(!password.equals(passwordConfirmation)) {
            return "please confirm your password";
        }
        
        new User(username, passwordConfirmation);
        
        return "";
    }
    
    public static String loginUser(String username, String password) {
        
        if(username.length() == 0 || password.length() == 0) {
            return "username and password must be non empty";
        }
        
        User user = User.getUserByUsername(username);
        if(user == null) {
            return "invalid username";
        }

        if(!user.getPassword().equals(password)) {
            return "invalid password";
        }

        User.setLoggedInUser(user);

        return "";
    }

    public static void enterAsGuest() {
        User user = new User();
        User.setLoggedInUser(user);
    }

    public static void gotoMainMenu(Stage stage) throws Exception {
        EnterMenu.getPlayer().stop();
        MainMenu.run(stage);        
    }
}
