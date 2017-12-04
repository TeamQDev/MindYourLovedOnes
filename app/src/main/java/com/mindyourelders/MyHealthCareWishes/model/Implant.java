package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/22/2017.
 */

public class Implant implements Serializable{
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String name="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date="";

}
