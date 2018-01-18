package com.mindyourlovedones.healthcare.model;

import com.mindyourlovedones.healthcare.DashBoard.DateClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by welcome on 10/12/2017.
 */

public class Appoint implements Serializable {
    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    int unique;
    public String getPre() {
        return Pre;
    }

    public void setPre(String pre) {
        Pre = pre;
    }
    String Pre="";
    int id;
    int userid;
    String type="";

    public ArrayList<DateClass> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<DateClass> dateList) {
        this.dateList = dateList;
    }

    ArrayList<DateClass> dateList=new ArrayList<>();

    public String getOtherDoctor() {
        return otherDoctor;
    }

    public void setOtherDoctor(String otherDoctor) {
        this.otherDoctor = otherDoctor;
    }

    public String getOtherFrequency() {
        return OtherFrequency;
    }

    public void setOtherFrequency(String otherFrequency) {
        OtherFrequency = otherFrequency;
    }

    String otherDoctor="";
    String OtherFrequency="";

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    String Doctor="";
    String Date="";
    String frequency="";
}
