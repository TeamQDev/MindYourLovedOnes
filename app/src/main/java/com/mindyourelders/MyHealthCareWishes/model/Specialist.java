package com.mindyourelders.MyHealthCareWishes.model;

/**
 * Created by varsha on 8/28/2017.
 */

public class Specialist {
    public int getIsPhysician() {
        return isPhysician;
    }

    public void setIsPhysician(int isPhysician) {
        this.isPhysician = isPhysician;
    }

    int isPhysician;
    String Fax;
    String network="";

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getHospAffiliation() {
        return HospAffiliation;
    }

    public void setHospAffiliation(String hospAffiliation) {
        HospAffiliation = hospAffiliation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String HospAffiliation="";
    String note="";
    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    String practiceName;
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    byte[] photo;
    int id;
    String email="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    String mobile="";
    String workPhone="";
    String name="";
    String type="";
    String address="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    String phone="";
    int image;


}
