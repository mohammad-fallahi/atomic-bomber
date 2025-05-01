package com.example.controller;

import com.example.model.User;

public class PrfoileMenuController {
    
    public static String updateInfo(String username, String oldPassword, String newPassword, String passwordConfirm) {

        String result = null;

        if(username.length() != 0) {
            result = changeUsername(username);
        }
        if(result != null && result.length() != 0) {
            return result;
        }

        if(newPassword.length() != 0) {
            result = changePassword(oldPassword, newPassword, passwordConfirm);
        }
        if(result != null && result.length() != 0) {
            return result;
        }

        if(result != null) {
            return "data updated successfully";
        }

        return "";
    }
    
    private static String changeUsername(String username) {

        if(User.getUserByUsername(username) != null) {
            return "username already exists";
        }

        User.getLoggedInUser().setUsername(username);
        return "";
    }

    private static String changePassword(String oldPassword, String newPassword, String passwordConfirm) {

        if(!User.getLoggedInUser().getPassword().equals(oldPassword)) {
            return "incorrect password";
        }

        if(!newPassword.equals(passwordConfirm)) {
            return "please confirm your password";
        }

        User.getLoggedInUser().setPassword(passwordConfirm);
        return "";
    }

    public static void logOut() {
        if(User.getLoggedInUser().isGuest()) {
            deleteAccount();
        } else {
            User.setLoggedInUser(null);
        }
    }

    public static void deleteAccount() {
        User.removeUser(User.getLoggedInUser());
        User.setLoggedInUser(null);
    }
}
