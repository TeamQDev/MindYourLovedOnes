package com.mindyourelders.MyHealthCareWishes.database;


public class PhysicainSpecialistTable {

    private String KEY_ID = "_id";
    private String KEY_PSI_ID = "proxyid";
    private String KEY_PSI_NAME = "proxyname";
    private String KEY_PSI_SPECIALITY = "physicianspeciality";
    private String KEY_PSI_OPHNO = "officephonenumber";
    private String KEY_PSI_PPHNO = "personalphonenumber";
    private String KEY_PSI_EMAIL = "email";
    private String KEY_PSI_ISPRIMARYORSECONDARY = "isprimaryorsecondaryphysician";
    private byte[] KEY_PSI_IMGPATH = null;


    public PhysicainSpecialistTable(String name, String oPhone, String cellPhone, String email, String phySpeciality, byte[] profileImgByte, String flag,
                                    String physicianId, String globalUseId2) {
        super();

        KEY_PSI_NAME = name;
        KEY_PSI_OPHNO = oPhone;
        KEY_PSI_PPHNO = cellPhone;
        KEY_PSI_IMGPATH = profileImgByte;
        KEY_PSI_ISPRIMARYORSECONDARY = flag;
        KEY_PSI_ID = physicianId;
        KEY_ID = globalUseId2;

        KEY_PSI_EMAIL = email;
        KEY_PSI_SPECIALITY = phySpeciality;

		/*if(flag.equals("1"))
        {
			KEY_PSI_EMAIL = emailOrSpeciality;
			Log.v("userID", ""+globalUseId2+ "KEY_PSI_EMAIL :"+KEY_PSI_EMAIL);


		}
		else if (flag.equals("2"))
		{
			KEY_PSI_SPECIALITY = emailOrSpeciality;
			Log.v("userID", ""+globalUseId2+ "KEY_PSI_SPECIALITY :"+KEY_PSI_SPECIALITY+KEY_PSI_NAME);

		}*/


    }


    public PhysicainSpecialistTable() {
        // TODO Auto-generated constructor stub
    }


    public String getKEY_ID() {
        return KEY_ID;
    }


    public void setKEY_ID(String kEY_ID) {
        KEY_ID = kEY_ID;
    }


    public String getKEY_PSI_ID() {
        return KEY_PSI_ID;
    }


    public void setKEY_PSI_ID(String kEY_PSI_ID) {
        KEY_PSI_ID = kEY_PSI_ID;
    }


    public String getKEY_PSI_NAME() {
        return KEY_PSI_NAME;
    }


    public void setKEY_PSI_NAME(String kEY_PSI_NAME) {
        KEY_PSI_NAME = kEY_PSI_NAME;
    }


    public String getKEY_PSI_SPECIALITY() {
        return KEY_PSI_SPECIALITY;
    }


    public void setKEY_PSI_SPECIALITY(String kEY_PSI_SPECIALITY) {
        KEY_PSI_SPECIALITY = kEY_PSI_SPECIALITY;
    }


    public String getKEY_PSI_OPHNO() {
        return KEY_PSI_OPHNO;
    }


    public void setKEY_PSI_OPHNO(String kEY_PSI_OPHNO) {
        KEY_PSI_OPHNO = kEY_PSI_OPHNO;
    }


    public String getKEY_PSI_PPHNO() {
        return KEY_PSI_PPHNO;
    }


    public void setKEY_PSI_PPHNO(String kEY_PSI_PPHNO) {
        KEY_PSI_PPHNO = kEY_PSI_PPHNO;
    }


    public String getKEY_PSI_EMAIL() {
        return KEY_PSI_EMAIL;
    }


    public void setKEY_PSI_EMAIL(String kEY_PSI_EMAIL) {
        KEY_PSI_EMAIL = kEY_PSI_EMAIL;
    }


    public String getKEY_PSI_ISPRIMARYORSECONDARY() {
        return KEY_PSI_ISPRIMARYORSECONDARY;
    }


    public void setKEY_PSI_ISPRIMARYORSECONDARY(String kEY_PSI_ISPRIMARYORSECONDARY) {
        KEY_PSI_ISPRIMARYORSECONDARY = kEY_PSI_ISPRIMARYORSECONDARY;
    }


    public byte[] getKEY_PSI_IMGPATH() {
        return KEY_PSI_IMGPATH;
    }


    public void setKEY_PSI_IMGPATH(byte[] kEY_PSI_IMGPATH) {
        KEY_PSI_IMGPATH = kEY_PSI_IMGPATH;
    }


}
