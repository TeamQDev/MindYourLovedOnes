package com.mindyourelders.MyHealthCareWishes.database;

public class EmergencyContactDetailTable {
    private String KEY_ID = "_id";
    private String KEY_MEC_ID = "personalemergencycontactid";
    private String KEY_MEC_USERNAME = "userName";
    private byte[] KEY_MEC_IMAGEPATH = null;
    private String RELATIONSHIP = "relationship";

    private String KEY_MEC_ADDRESS = "address";
    private String KEY_MEC_CITYSTATEZIPCODE = "citystatezipcode";
    private String KEY_MEC_HPHNO = "homephonenumber";
    private String KEY_MEC_OPHNO = "officephonenumber";
    private String KEY_MEC_PPHONO = "personalphonenumber";
    private String KEY_MEC_EMAIL = "email";

    public EmergencyContactDetailTable(String emerContName,
                                       String relationship, String emerContAddress, String emerContCity,
                                       String emerContHPhone, String emerOfficePhone,
                                       String emerContCellPhone, String emerContEmail,
                                       byte[] profileImgByte, String emergencyContactId2,
                                       String globalUserId2) {
        KEY_MEC_USERNAME = emerContName;
        KEY_MEC_ADDRESS = emerContAddress;
        KEY_MEC_CITYSTATEZIPCODE = emerContCity;
        KEY_MEC_HPHNO = emerContHPhone;
        KEY_MEC_OPHNO = emerOfficePhone;
        KEY_MEC_PPHONO = emerContCellPhone;
        KEY_MEC_EMAIL = emerContEmail;
        KEY_MEC_IMAGEPATH = profileImgByte;
        RELATIONSHIP = relationship;
        KEY_MEC_ID = emergencyContactId2;
        KEY_ID = globalUserId2;

    }

    public EmergencyContactDetailTable() {
        // TODO Auto-generated constructor stub
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String kEY_ID) {
        KEY_ID = kEY_ID;
    }

    public String getKEY_MEC_ID() {
        return KEY_MEC_ID;
    }

    public void setKEY_MEC_ID(String kEY_MEC_ID) {
        KEY_MEC_ID = kEY_MEC_ID;
    }

    public String getKEY_MEC_USERNAME() {
        return KEY_MEC_USERNAME;
    }

    public void setKEY_MEC_USERNAME(String kEY_MEC_USERNAME) {
        KEY_MEC_USERNAME = kEY_MEC_USERNAME;
    }

    public byte[] getKEY_MEC_IMAGEPATH() {
        return KEY_MEC_IMAGEPATH;
    }

    public void setKEY_MEC_IMAGEPATH(byte[] kEY_MEC_IMAGEPATH) {
        KEY_MEC_IMAGEPATH = kEY_MEC_IMAGEPATH;
    }

    public String getKEY_MEC_ADDRESS() {
        return KEY_MEC_ADDRESS;
    }

    public void setKEY_MEC_ADDRESS(String kEY_MEC_ADDRESS) {
        KEY_MEC_ADDRESS = kEY_MEC_ADDRESS;
    }

    public String getKEY_MEC_CITYSTATEZIPCODE() {
        return KEY_MEC_CITYSTATEZIPCODE;
    }

    public void setKEY_MEC_CITYSTATEZIPCODE(String kEY_MEC_CITYSTATEZIPCODE) {
        KEY_MEC_CITYSTATEZIPCODE = kEY_MEC_CITYSTATEZIPCODE;
    }

    public String getKEY_MEC_HPHNO() {
        return KEY_MEC_HPHNO;
    }

    public void setKEY_MEC_HPHNO(String kEY_MEC_HPHNO) {
        KEY_MEC_HPHNO = kEY_MEC_HPHNO;
    }

    public String getKEY_MEC_OPHNO() {
        return KEY_MEC_OPHNO;
    }

    public void setKEY_MEC_OPHNO(String kEY_MEC_OPHNO) {
        KEY_MEC_OPHNO = kEY_MEC_OPHNO;
    }

    public String getKEY_MEC_PPHONO() {
        return KEY_MEC_PPHONO;
    }

    public void setKEY_MEC_PPHONO(String kEY_MEC_PPHONO) {
        KEY_MEC_PPHONO = kEY_MEC_PPHONO;
    }

    public String getKEY_MEC_EMAIL() {
        return KEY_MEC_EMAIL;
    }

    public void setKEY_MEC_EMAIL(String kEY_MEC_EMAIL) {
        KEY_MEC_EMAIL = kEY_MEC_EMAIL;
    }

    public String getRELATIONSHIP() {
        return RELATIONSHIP;
    }

    public void setRELATIONSHIP(String rELATIONSHIP) {
        RELATIONSHIP = rELATIONSHIP;
    }

}
