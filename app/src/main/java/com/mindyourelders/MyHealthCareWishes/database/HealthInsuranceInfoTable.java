package com.mindyourelders.MyHealthCareWishes.database;


public class HealthInsuranceInfoTable {
    private String KEY_ID = "_id";
    private String KEY_HIC_ID = "healthinsuranceid";
    private String KEY_HIC_NAME = "healthinsurancecompanyname";
    private String KEY_HIC_OPHNO = "healthinsurancecompanyphonenumber";
    private String KEY_HIC_IDNO = "healthinsuranceidnumber";
    private String KEY_HIC_GRNO = "healthinsurancecompanygroupnumber";

    public HealthInsuranceInfoTable(String hicName, String hicOfficeNo, String hicIdNo, String hicgrNo, String hicId2, String globalUserId2) {
        KEY_HIC_NAME = hicName;
        KEY_HIC_OPHNO = hicOfficeNo;
        KEY_HIC_IDNO = hicIdNo;
        KEY_HIC_GRNO = hicgrNo;
        KEY_HIC_ID = hicId2;
        KEY_ID = globalUserId2;
    }


    public HealthInsuranceInfoTable() {
        // TODO Auto-generated constructor stub
    }


    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String kEY_ID) {
        KEY_ID = kEY_ID;
    }

    public String getKEY_HIC_ID() {
        return KEY_HIC_ID;
    }

    public void setKEY_HIC_ID(String kEY_HIC_ID) {
        KEY_HIC_ID = kEY_HIC_ID;
    }

    public String getKEY_HIC_NAME() {
        return KEY_HIC_NAME;
    }

    public void setKEY_HIC_NAME(String kEY_HIC_NAME) {
        KEY_HIC_NAME = kEY_HIC_NAME;
    }

    public String getKEY_HIC_OPHNO() {
        return KEY_HIC_OPHNO;
    }

    public void setKEY_HIC_OPHNO(String kEY_HIC_OPHNO) {
        KEY_HIC_OPHNO = kEY_HIC_OPHNO;
    }

    public String getKEY_HIC_IDNO() {
        return KEY_HIC_IDNO;
    }

    public void setKEY_HIC_IDNO(String kEY_HIC_IDNO) {
        KEY_HIC_IDNO = kEY_HIC_IDNO;
    }

    public String getKEY_HIC_GRNO() {
        return KEY_HIC_GRNO;
    }

    public void setKEY_HIC_GRNO(String kEY_HIC_GRNO) {
        KEY_HIC_GRNO = kEY_HIC_GRNO;
    }

}
