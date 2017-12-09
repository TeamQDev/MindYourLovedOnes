package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by welcome on 12/9/2017.
 */

public class PhotoImage {
    int id;
    String photo="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoCard() {
        return photoCard;
    }

    public void setPhotoCard(String photoCard) {
        this.photoCard = photoCard;
    }

    String photoCard="";
}
