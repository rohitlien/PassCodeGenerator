package com.rohit.passcodegenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oust on 2/2/19.
 */

public class UserDataModel extends RealmObject{
    @PrimaryKey
    private int id;
    private String username,passcode,imagePath;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
