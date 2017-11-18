package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 11/14/2017.
 */

public class Links implements Serializable{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String name="";
    String url="";

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    int image;
}
