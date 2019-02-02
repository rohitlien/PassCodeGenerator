package com.rohit.passcodegenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oust on 2/2/19.
 */

public class PassCode extends RealmObject{
    @PrimaryKey
    private int passC;

    public int getPassC() {
        return passC;
    }

    public void setPassC(int passC) {
        this.passC = passC;
    }
}
