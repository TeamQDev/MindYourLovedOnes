package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by varsha on 9/8/2017.
 */

public class Finance implements Serializable {

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    String ContactName="";
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location="";
    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    String Fax;
    String practiceName;
    String photoCard;
    public String getPhotoCard() {
        return photoCard;
    }

    public void setPhotoCard(String photoCard) {
        this.photoCard = photoCard;
    }

    public String getOtherCategory() {
        return OtherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        OtherCategory = otherCategory;
    }

    String OtherCategory="";

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String photo;
    int id;
    String officePhone="";

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getHourPhone() {
        return hourPhone;
    }

    public void setHourPhone(String hourPhone) {
        this.hourPhone = hourPhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String hourPhone="";
    String otherPhone="";
    String lastseen="";
    String website="";
    String note="";
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    String Category="";
    String firm="";
    String phone="";
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address="";

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    String mobile="";
    String email="";
    String name="";
    String schedule="";
    String other="";
}
