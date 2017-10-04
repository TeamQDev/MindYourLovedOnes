package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/20/2017.
 */

public class Card implements Serializable {


    public int getImgFront() {
        return imgFront;
    }

    public void setImgFront(int imgFront) {
        this.imgFront = imgFront;
    }

    int imgFront;

    public int getImgBack() {
        return imgBack;
    }

    public void setImgBack(int imgBack) {
        this.imgBack = imgBack;
    }

    int imgBack;
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
