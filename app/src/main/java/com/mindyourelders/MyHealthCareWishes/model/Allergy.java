package com.mindyourelders.MyHealthCareWishes.model;

import java.io.Serializable;

/**
 * Created by welcome on 9/22/2017.
 */

public class Allergy implements Serializable{

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String allergy="";

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    String treatment="";
    String reaction="";

    public String getOtherReaction() {
        return otherReaction;
    }

    public void setOtherReaction(String otherReaction) {
        this.otherReaction = otherReaction;
    }

    String otherReaction="";
}
