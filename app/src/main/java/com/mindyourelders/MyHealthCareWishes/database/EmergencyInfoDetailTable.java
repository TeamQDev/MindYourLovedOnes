package com.mindyourelders.MyHealthCareWishes.database;

public class EmergencyInfoDetailTable {

    private String KEY_ID = "_id";
    private String KEY_MEI_ID = "medicalemergencyid";
    private String KEY_MEI_BLOODGR = "bloodgrouptype";
    private String KEY_MEI_ALLERGIES = "allergies";
    private String KEY_MEI_PRESDRUGS = "prescriptiondrugs";
    private String KEY_MEI_MEDICAL_CONDITIONS = "medicalconditions";
    private String KEY_MEI_PREFFERED_HOSPITALS = "preferredhospital";


    public EmergencyInfoDetailTable(String bloodType, String allergies, String prescriptionDrugs, String medicalCondition, String preffredHospital, String medicalEmerId, String globalUserId2) {
        KEY_MEI_BLOODGR = bloodType;
        KEY_MEI_ALLERGIES = allergies;
        KEY_MEI_PRESDRUGS = prescriptionDrugs;
        KEY_MEI_MEDICAL_CONDITIONS = medicalCondition;
        KEY_MEI_PREFFERED_HOSPITALS = preffredHospital;

        KEY_MEI_ID = medicalEmerId;
        KEY_ID = globalUserId2;

    }


    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String kEY_ID) {
        KEY_ID = kEY_ID;
    }

    public String getKEY_MEI_ID() {
        return KEY_MEI_ID;
    }

    public void setKEY_MEI_ID(String kEY_MEI_ID) {
        KEY_MEI_ID = kEY_MEI_ID;
    }

    public String getKEY_MEI_BLOODGR() {
        return KEY_MEI_BLOODGR;
    }

    public void setKEY_MEI_BLOODGR(String kEY_MEI_BLOODGR) {
        KEY_MEI_BLOODGR = kEY_MEI_BLOODGR;
    }

    public String getKEY_MEI_ALLERGIES() {
        return KEY_MEI_ALLERGIES;
    }

    public void setKEY_MEI_ALLERGIES(String kEY_MEI_ALLERGIES) {
        KEY_MEI_ALLERGIES = kEY_MEI_ALLERGIES;
    }

    public String getKEY_MEI_PRESDRUGS() {
        return KEY_MEI_PRESDRUGS;
    }

    public void setKEY_MEI_PRESDRUGS(String kEY_MEI_PRESDRUGS) {
        KEY_MEI_PRESDRUGS = kEY_MEI_PRESDRUGS;
    }

    public String getKEY_MEI_MEDICAL_CONDITIONS() {
        return KEY_MEI_MEDICAL_CONDITIONS;
    }

    public void setKEY_MEI_MEDICAL_CONDITIONS(String kEY_MEI_MEDICAL_CONDITIONS) {
        KEY_MEI_MEDICAL_CONDITIONS = kEY_MEI_MEDICAL_CONDITIONS;
    }

    public String getKEY_MEI_PREFFERED_HOSPITALS() {
        return KEY_MEI_PREFFERED_HOSPITALS;
    }

    public void setKEY_MEI_PREFFERED_HOSPITALS(String kEY_MEI_PREFFERED_HOSPITALS) {
        KEY_MEI_PREFFERED_HOSPITALS = kEY_MEI_PREFFERED_HOSPITALS;
    }


}
