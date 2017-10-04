package com.mindyourelders.MyHealthCareWishes.database;

public class PersonalInfoTable {

    // private variables

    private String KEY_ID = "_id";
    private String KEY_USERNAME = "username";
    private byte[] KEY_IMAGEPATH = null;
    private String KEY_ADDRESS = "address";
    private String KEY_CITYSTATEZIPCODE = "citystatezipcode";
    private String KEY_HPHNO = "homephonenumber";
    private String KEY_OPHNO = "officephonenumber";
    private String KEY_PPHONO = "personalphonenumber";
    private String KEY_EMAIL = "email";
    private String KEY_RELATION = "realationship";
    private int KEY_AMD = -1;
    private int KEY_DNDORDER = -1;
    private int KEY_POLST = -1;

    /**
     * @return the kEY_RELATION
     */
    public String getKEY_RELATION() {
        return KEY_RELATION;
    }

    /**
     * @param kEY_RELATION the kEY_RELATION to set
     */
    public void setKEY_RELATION(String kEY_RELATION) {
        KEY_RELATION = kEY_RELATION;
    }

    private int KEY_ANATOMICALGIFT = -1;

    private int KEY_LOCADVDOC = -1;

    private String KEY_OTHRLOC;
    // private String KEY_REGNO = "-1";
    private String KEY_UPDATETIME;

    public PersonalInfoTable(String name, String address, String city,
                             String hPhone, String oPhone, String cellPhone, String email,
                             byte[] profileImgByte, String userID, String updatedDateTime,
                             String relationship) {

        KEY_USERNAME = name;
        KEY_ADDRESS = address;
        KEY_CITYSTATEZIPCODE = city;
        KEY_HPHNO = hPhone;
        KEY_OPHNO = oPhone;
        KEY_PPHONO = cellPhone;
        KEY_EMAIL = email;
        KEY_IMAGEPATH = profileImgByte;
        KEY_UPDATETIME = updatedDateTime;
        KEY_RELATION = relationship;
        if (!userID.equals("")) {
            KEY_ID = userID;

        }

		/*
         * KEY_UPDATETIME = updatedatetime;
		 */
    }

    public PersonalInfoTable(int amdValue, int dnrValue, int polstValue,
                             int giftValue, String globalUseId2) {
        KEY_AMD = amdValue;
        KEY_DNDORDER = dnrValue;
        KEY_POLST = polstValue;
        KEY_ANATOMICALGIFT = giftValue;
        KEY_ID = globalUseId2;

    }

    public PersonalInfoTable(int saveToSMartPhVal, String docSave,
                             String globalUseId2) {
        KEY_LOCADVDOC = saveToSMartPhVal;
        KEY_OTHRLOC = docSave;
        // KEY_REGNO = registerVal;
        KEY_ID = globalUseId2;

    }

    public PersonalInfoTable(String globalUserId2, String updatedDateTime) {
        KEY_UPDATETIME = updatedDateTime;
        KEY_ID = globalUserId2;

    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String kEY_ID) {
        KEY_ID = kEY_ID;
    }

    public String getKEY_USERNAME() {
        return KEY_USERNAME;
    }

    public void setKEY_USERNAME(String kEY_USERNAME) {
        KEY_USERNAME = kEY_USERNAME;
    }

    public byte[] getKEY_IMAGEPATH() {
        return KEY_IMAGEPATH;
    }

    public void setKEY_IMAGEPATH(byte[] kEY_IMAGEPATH) {
        KEY_IMAGEPATH = kEY_IMAGEPATH;
    }

    public String getKEY_ADDRESS() {
        return KEY_ADDRESS;
    }

    public void setKEY_ADDRESS(String kEY_ADDRESS) {
        KEY_ADDRESS = kEY_ADDRESS;
    }

    public String getKEY_CITYSTATEZIPCODE() {
        return KEY_CITYSTATEZIPCODE;
    }

    public void setKEY_CITYSTATEZIPCODE(String kEY_CITYSTATEZIPCODE) {
        KEY_CITYSTATEZIPCODE = kEY_CITYSTATEZIPCODE;
    }

    public String getKEY_HPHNO() {
        return KEY_HPHNO;
    }

    public void setKEY_HPHNO(String kEY_HPHNO) {
        KEY_HPHNO = kEY_HPHNO;
    }

    public String getKEY_OPHNO() {
        return KEY_OPHNO;
    }

    public void setKEY_OPHNO(String kEY_OPHNO) {
        KEY_OPHNO = kEY_OPHNO;
    }

    public String getKEY_PPHONO() {
        return KEY_PPHONO;
    }

    public void setKEY_PPHONO(String kEY_PPHONO) {
        KEY_PPHONO = kEY_PPHONO;
    }

    public String getKEY_EMAIL() {
        return KEY_EMAIL;
    }

    public void setKEY_EMAIL(String kEY_EMAIL) {
        KEY_EMAIL = kEY_EMAIL;
    }

    public int getKEY_AMD() {
        return KEY_AMD;
    }

    public void setKEY_AMD(int kEY_AMD) {
        KEY_AMD = kEY_AMD;
    }

    public int getKEY_DNDORDER() {
        return KEY_DNDORDER;
    }

    public void setKEY_DNDORDER(int kEY_DNDORDER) {
        KEY_DNDORDER = kEY_DNDORDER;
    }

    public int getKEY_POLST() {
        return KEY_POLST;
    }

    public void setKEY_POLST(int kEY_POLST) {
        KEY_POLST = kEY_POLST;
    }

    public int getKEY_ANATOMICALGIFT() {
        return KEY_ANATOMICALGIFT;
    }

    public void setKEY_ANATOMICALGIFT(int kEY_ANATOMICALGIFT) {
        KEY_ANATOMICALGIFT = kEY_ANATOMICALGIFT;
    }

    public int getKEY_LOCADVDOC() {
        return KEY_LOCADVDOC;
    }

    public void setKEY_LOCADVDOC(int kEY_LOCADVDOC) {
        KEY_LOCADVDOC = kEY_LOCADVDOC;
    }

    public String getKEY_OTHRLOC() {
        return KEY_OTHRLOC;
    }

    public void setKEY_OTHRLOC(String kEY_OTHRLOC) {
        KEY_OTHRLOC = kEY_OTHRLOC;
    }

    // public String getKEY_REGNO() {
    // return KEY_REGNO;
    // }
    // public void setKEY_REGNO(String kEY_REGNO) {
    // KEY_REGNO = kEY_REGNO;
    // }
    public String getKEY_UPDATETIME() {
        return KEY_UPDATETIME;
    }

    public void setKEY_UPDATETIME(String kEY_UPDATETIME) {
        KEY_UPDATETIME = kEY_UPDATETIME;
    }

}
