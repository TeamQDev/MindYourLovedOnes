package com.mindyourlovedones.healthcare.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by welcome on 11/28/2017.
 */

public class Living extends ArrayList<Living> implements Serializable {
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
    String functionOther="";

    public String getFunctionOther() {
        return functionOther;
    }

    public void setFunctionOther(String functionOther) {
        this.functionOther = functionOther;
    }

    public String getFunctionNote() {
        return functionNote;
    }

    public void setFunctionNote(String functionNote) {
        this.functionNote = functionNote;
    }

    public String getInstOther() {
        return InstOther;
    }

    public void setInstOther(String instOther) {
        InstOther = instOther;
    }

    public String getInstNote() {
        return InstNote;
    }

    public void setInstNote(String instNote) {
        InstNote = instNote;
    }

    String functionNote="";
    String InstOther="";
    String InstNote="";
    String finance = "", prepare = "", shop = "", use = "", bath = "", continence = "",dress="",feed="",toileting="",transfer="",transport="",pets="",drive="",keep="",medication="";

    String remote = "";
    String alert="";

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    String computer="";

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getContinence() {
        return continence;
    }

    public void setContinence(String continence) {
        this.continence = continence;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getToileting() {
        return toileting;
    }

    public void setToileting(String toileting) {
        this.toileting = toileting;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }
}
