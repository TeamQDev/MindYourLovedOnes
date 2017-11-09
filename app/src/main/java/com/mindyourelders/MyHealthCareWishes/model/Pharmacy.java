package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/22/2017.
 */

public class Pharmacy implements Serializable {
    public byte[] getPhoto() {
        return photo;
    }
    byte[] photoCard;
    public byte[] getPhotoCard() {
        return photoCard;
    }

    public void setPhotoCard(byte[] photoCard) {
        this.photoCard = photoCard;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    byte[] photo;
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    String phone="";
    String fax="";
    String website="";
    String name="";

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String address="";
    String note="";
}
