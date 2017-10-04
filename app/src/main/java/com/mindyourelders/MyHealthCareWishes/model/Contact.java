package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by varsha on 8/28/2017.
 */

public class Contact {
    String name="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id="";

    public Contact(String name, String email, String phone, byte[] image) {
       this.name=name;
        this.image=image;
        this.phone=phone;
        this.email=email;
    }

    public Contact() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email="";
    byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone="";
}
