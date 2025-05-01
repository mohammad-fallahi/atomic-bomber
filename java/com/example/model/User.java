package com.example.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class User {
    
    private String username, password;
    private boolean guest;
    private Image avatarImage;
    private DifficultySetting setting;

    // Static fields:
    private static ArrayList<User> allUsers = new ArrayList<User>();
    private static User loggedInUser = null;


    // Constructors: (as a guest and as a non-guest)
    public User() {
        username = "guest-user";
        password = "12345678";
        guest = true;
        avatarImage = AvatarPicker.randomAvatar();
        setting = DifficultySetting.EASY;
        
        allUsers.add(this);
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        guest = false;
        avatarImage = AvatarPicker.randomAvatar();
        setting = DifficultySetting.EASY;

        allUsers.add(this);
    }


    // Getter methods:
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGuest() {
        return guest;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }

    public DifficultySetting getSetting() {
        return setting;
    }

    
    // Setter methods:
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatarImage(Image image) {
        avatarImage = image;
    }

    public void setDifficultySettings(DifficultySetting setting) {
        this.setting = setting;
    }
    
    
    // Static methods:
    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if(user.getUsername().equals(username)) return user;
        }
        return null;
    }
    
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    
    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void removeUser(User user) {
        allUsers.remove(user);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + (guest ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (guest != other.guest)
            return false;
        return true;
    }

    
    
}
