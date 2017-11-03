package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by welcome on 9/24/2017.
 */

public class MedInfo {
    int id;
    int userId;
    String feet="";
    String blind="No";
    String speech="No";
    String feed="No";
    String toilet="No";

    String aideNote="";

    public String getAideNote() {
        return aideNote;
    }

    public void setAideNote(String aideNote) {
        this.aideNote = aideNote;
    }

    public String getVisionNote() {
        return visionNote;
    }

    public void setVisionNote(String visionNote) {
        this.visionNote = visionNote;
    }

    public String getFunctionnote() {
        return functionnote;
    }

    public void setFunctionnote(String functionnote) {
        this.functionnote = functionnote;
    }

    public String getDietNote() {
        return dietNote;
    }

    public void setDietNote(String dietNote) {
        this.dietNote = dietNote;
    }

    String visionNote="";
    String functionnote="";
    String dietNote="";

    public String getBlind() {
        return blind;
    }

    public void setBlind(String blind) {
        this.blind = blind;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getMedicate() {
        return medicate;
    }

    public void setMedicate(String medicate) {
        this.medicate = medicate;
    }

    String medicate="No";


    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    String mouth="";

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String note="";

    public String getMouthnote() {
        return mouthnote;
    }

    public void setMouthnote(String mouthnote) {
        this.mouthnote = mouthnote;
    }

    String mouthnote="";
    String inch="";
    String weight="";
    String color="";
    String lang1="";
    String lang2="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public String getInch() {
        return inch;
    }

    public void setInch(String inch) {
        this.inch = inch;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLang1() {
        return lang1;
    }

    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getLense() {
        return lense;
    }

    public void setLense(String lense) {
        this.lense = lense;
    }

    public String getFalses() {
        return falses;
    }

    public void setFalses(String falses) {
        this.falses = falses;
    }

    public String getImplants() {
        return implants;
    }

    public void setImplants(String implants) {
        this.implants = implants;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    String glass="";
    String lense="";
    String falses="";
    String implants="";
    String aid="";
    String bloodType="";
    String donor="";
    String pet="";
}
