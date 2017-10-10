package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/20/2017.
 */

public class Card implements Serializable {

int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    int userid;
    public  byte[] getImgFront() {
        return imgFront;
    }

    public void setImgFront( byte[] imgFront) {
        this.imgFront = imgFront;
    }

    byte[] imgFront;

    public  byte[] getImgBack() {
        return imgBack;
    }

    public void setImgBack( byte[] imgBack) {
        this.imgBack = imgBack;
    }

    byte[] imgBack;
    String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;
}
