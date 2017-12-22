package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by varsha on 8/24/2017.
 */

public class Document implements Serializable {
    public String getOtherDoc() {
        return OtherDoc;
    }

    public void setOtherDoc(String otherDoc) {
        OtherDoc = otherDoc;
    }

    String OtherDoc="";
    public String getOtherCategory() {
        return OtherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        OtherCategory = otherCategory;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    String OtherCategory="";
    String Hospital="";
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    String from="";
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    String Category="";
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

    int id;
    int userid;
    String type="";
    String date="";
    String person="";

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    String principle="";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String holder="";
    String location="";

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    int image;
    String name="";
    String desc="";

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    String document="";
}
