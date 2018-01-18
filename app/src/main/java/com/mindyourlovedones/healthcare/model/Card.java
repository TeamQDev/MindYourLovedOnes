package com.mindyourlovedones.healthcare.model;

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
    public  String getImgFront() {
        return imgFront;
    }

    public void setImgFront( String imgFront) {
        this.imgFront = imgFront;
    }

    String imgFront="";

    public  String getImgBack() {
        return imgBack;
    }

    public void setImgBack( String imgBack) {
        this.imgBack = imgBack;
    }

    String imgBack="";
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

    String type="";
}
