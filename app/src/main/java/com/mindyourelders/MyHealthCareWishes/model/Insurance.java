package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by varsha on 8/28/2017.
 */

public class Insurance {

    String name="";
    String phone="";
    int image;
    String id="";

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
