package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by varsha on 8/28/2017.
 */

public class Insurance {

    String name="";
    String phone="";
    int image;
    int id;
    int userid;
    String fax="";

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    String website="";
    String subscriber="";
    byte[] photo;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    String Type="";

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    String member="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    String group="";
}
