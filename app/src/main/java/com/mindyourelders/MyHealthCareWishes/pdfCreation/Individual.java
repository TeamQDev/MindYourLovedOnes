package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.model.MedInfo;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/1/2017.
 */

public class Individual {
    String name="";
    String address="";
    String realtion="";
    String mPhone="";
    String hPhone="";
    String bdate="";
    String gender="";
    String height="";
    String weight="";
    String eyes="";
    String employedBy="";
    String telephone="";
    String language="";
    String marital_status="";
    String religionNote="";
    String profession="";
    String Veteran="";
    String idNumber="";


    public static ArrayList<String> messageInfo = new ArrayList<String>();
    public static ArrayList<String> messageInfo2 = new ArrayList<String>();
    public static ArrayList<String> messageInfo3 = new ArrayList<String>();


    public Individual(RelativeConnection connection) {
        Header.addChank("Personal Profile");
        messageInfo.add("Personal Profile");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (connection.getName() != null) {
            name = connection.getName();
        }
        Header.addTable("Profile Name :");
        Header.addTable(name);
        messageInfo.add("Profile Name :");
        messageInfo.add(name);

        if (connection.getRelationType()!= null) {
            realtion = connection.getRelationType();
        }
        Header.addTable("Relationship :");
        Header.addTable(realtion);
        messageInfo.add("Relationship :");
        messageInfo.add(realtion);

        if (connection.getAddress() != null) {
            address = connection.getAddress();
        }
        Header.addTable("Address :");
        Header.addTable(address);
        messageInfo.add("Address :");
        messageInfo.add(address);

        if (connection.getMobile() != null) {
            mPhone = connection.getMobile();
        }
        Header.addTable("Mobile :");
        Header.addTable(mPhone);
        messageInfo.add("Mobile :");
        messageInfo.add(mPhone); String bdate="";

        if (connection.getPhone() != null) {
            hPhone = connection.getPhone();
        }
        Header.addTable("Home Phone :");
        Header.addTable(hPhone);
        messageInfo.add("Home Phone :");
        messageInfo.add(hPhone);

        if (connection.getHeight() != null) {
            height = connection.getHeight();
        }
        Header.addTable("Height :");
        Header.addTable(height);
        messageInfo.add("Height :");
        messageInfo.add(height);

        if (connection.getWeight() != null) {
            weight = connection.getWeight();
        }
        Header.addTable("Weight :");
        Header.addTable(weight);
        messageInfo.add("Weight :");
        messageInfo.add(weight);

        if (connection.getEyes() != null) {
            eyes = connection.getEyes();
        }
        Header.addTable("Eyes :");
        Header.addTable(eyes);
        messageInfo.add("Eyes :");
        messageInfo.add(eyes);

        if (connection.getWeight() != null) {
            employedBy = connection.getWeight();
        }
        Header.addTable("Employed By :");
        Header.addTable(employedBy);
        messageInfo.add("Employed By :");
        messageInfo.add(employedBy);

        if (connection.getManager_phone() != null) {
            telephone = connection.getManager_phone();
        }
        Header.addTable("Manager Phone :");
        Header.addTable(telephone);
        messageInfo.add("Manager Phone :");
        messageInfo.add(telephone);

        if (connection.getLanguage() != null) {
            language = connection.getLanguage();
        }
        Header.addTable("Language Spoken :");
        Header.addTable(language);
        messageInfo.add("Language Spoken :");
        messageInfo.add(language);

        if (connection.getMarital_status() != null) {
            marital_status = connection.getMarital_status();
        }
        Header.addTable("Marital Status :");
        Header.addTable(marital_status);
        messageInfo.add("Marital Status :");
        messageInfo.add(marital_status);

        if (connection.getReligion() != null) {
            religionNote = connection.getReligion();
        }
        Header.addTable("Religion Note :");
        Header.addTable(religionNote);
        messageInfo.add("Religion Note :");
        messageInfo.add(religionNote);

        if (connection.getProfession() != null) {
            profession = connection.getProfession();
        }
        Header.addTable("Profession :");
        Header.addTable(profession);
        messageInfo.add("Profession :");
        messageInfo.add(profession);

        if (connection.getVeteran() != null) {
            Veteran = connection.getVeteran();
        }
        Header.addTable("Veteran :");
        Header.addTable(Veteran);
        messageInfo.add("Veteran :");
        messageInfo.add(Veteran);

        if (connection.getIdnumber() != null) {
            idNumber = connection.getIdnumber();
        }
        Header.addTable("Id Number :");
        Header.addTable(idNumber);
        messageInfo.add("Id Number :");
        messageInfo.add(idNumber);

          /*  if (connection.get == null) {
                oPhone = "";
            }
            Header.addTable("Medical Conditions :");
            Header.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            Header.addTable("Preferred Hospital :");
            Header.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*/
        Header.table.setWidthPercentage(100f);

        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(PersonalInfo connection) {
        Header.addChank("Personal Profile");
        messageInfo2.add("Personal Profile");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (connection.getName() != null) {
            name = connection.getName();
        }
        Header.addTable("Profile Name :");
        Header.addTable(name);
        messageInfo.add("Profile Name :");
        messageInfo.add(name);

        if (connection.getEmail()!= null) {
            realtion = connection.getEmail();
        }
        Header.addTable("Email :");
        Header.addTable(realtion);
        messageInfo.add("Email :");
        messageInfo.add(realtion);

        if (connection.getAddress() != null) {
            address = connection.getAddress();
        }
        Header.addTable("Address :");
        Header.addTable(address);
        messageInfo2.add("Address :");
        messageInfo2.add(address);
        if (connection.getPhone() != null) {
            mPhone = connection.getPhone();
        }
        Header.addTable("Mobile :");
        Header.addTable(mPhone);
        messageInfo2.add("Mobile :");
        messageInfo2.add(mPhone);

        if (connection.getHomePhone() != null) {
            hPhone = connection.getHomePhone();
        }
        Header.addTable("Home Phone :");
        Header.addTable(hPhone);
        messageInfo2.add("Home Phone :");
        messageInfo2.add(hPhone);

        if (connection.getGender() != null) {
            gender = connection.getGender();
        }
        Header.addTable("Gender :");
        Header.addTable(gender);
        messageInfo2.add("Gender :");
        messageInfo2.add(gender);

        if (connection.getHeight() != null) {
            height = connection.getHeight();
        }
        Header.addTable("Height :");
        Header.addTable(height);
        messageInfo2.add("Height :");
        messageInfo2.add(height);

        if (connection.getWeight() != null) {
            weight = connection.getWeight();
        }
        Header.addTable("Weight :");
        Header.addTable(weight);
        messageInfo2.add("Weight :");
        messageInfo2.add(weight);

        if (connection.getEyes() != null) {
            eyes = connection.getEyes();
        }
        Header.addTable("Eyes :");
        Header.addTable(eyes);
        messageInfo2.add("Eyes :");
        messageInfo2.add(eyes);

        if (connection.getWeight() != null) {
            employedBy = connection.getWeight();
        }
        Header.addTable("Employed By :");
        Header.addTable(employedBy);
        messageInfo2.add("Employed By :");
        messageInfo2.add(employedBy);

        if (connection.getManager_phone() != null) {
            telephone = connection.getManager_phone();
        }
        Header.addTable("Manager Phone :");
        Header.addTable(telephone);
        messageInfo2.add("Manager Phone :");
        messageInfo2.add(telephone);

        if (connection.getLanguage() != null) {
            language = connection.getLanguage();
        }
        Header.addTable("Language Spoken :");
        Header.addTable(language);
        messageInfo2.add("Language Spoken :");
        messageInfo2.add(language);

        if (connection.getMarital_status() != null) {
            marital_status = connection.getMarital_status();
        }
        Header.addTable("Marital Status :");
        Header.addTable(marital_status);
        messageInfo2.add("Marital Status :");
        messageInfo2.add(marital_status);

        if (connection.getReligion() != null) {
            religionNote = connection.getReligion();
        }
        Header.addTable("Religion Note :");
        Header.addTable(religionNote);
        messageInfo2.add("Religion Note :");
        messageInfo2.add(religionNote);

        if (connection.getProfession() != null) {
            profession = connection.getProfession();
        }
        Header.addTable("Profession :");
        Header.addTable(profession);
        messageInfo2.add("Profession :");
        messageInfo2.add(profession);

        if (connection.getVeteran() != null) {
            Veteran = connection.getVeteran();
        }
        Header.addTable("Veteran :");
        Header.addTable(Veteran);
        messageInfo2.add("Veteran :");
        messageInfo2.add(Veteran);

        if (connection.getIdnumber() != null) {
            idNumber = connection.getIdnumber();
        }
        Header.addTable("Id Number :");
        Header.addTable(idNumber);
        messageInfo2.add("Id Number :");
        messageInfo2.add(idNumber);



          /*  if (connection.get == null) {
                oPhone = "";
            }
            Header.addTable("Medical Conditions :");
            Header.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            Header.addTable("Preferred Hospital :");
            Header.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*/
        Header.table.setWidthPercentage(100f);

        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(MedInfo medInfo) {
        String preNote="";String glass="";String lense="";String blind="";String upper=""; String lower="";String visionNote="";
        Header.addChank("Medical Profile");
        messageInfo3.add("Medical Profile");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (medInfo.getNote() != null) {
            preNote = medInfo.getNote();
        }
        Header.addTable("Pre Existing Note :");
        Header.addTable(preNote);
        messageInfo3.add("Pre Existing Note :");
        messageInfo3.add(preNote);

        if (medInfo.getNote() != null) {
            preNote = medInfo.getNote();
        }
        Header.addTable("Pre Existing Note :");
        Header.addTable(preNote);
        messageInfo3.add("Pre Existing Note :");
        messageInfo3.add(preNote);


        Header.addTable("Vision :");
        Header.addTable("");
        messageInfo3.add("Vision :");
        messageInfo3.add("");

        if (medInfo.getGlass() != null) {
            glass = medInfo.getGlass();
        }
        Header.addTable("Glasses :");
        Header.addTable(glass);
        messageInfo3.add("Glasses :");
        messageInfo3.add(glass);

        if (medInfo.getLense() != null) {
            lense = medInfo.getLense();
        }
        Header.addTable("Contact Lense :");
        Header.addTable(lense);
        messageInfo3.add("Contact Lense :");
        messageInfo3.add(lense);

        if (medInfo.getBlind() != null) {
            blind = medInfo.getBlind();
        }
        Header.addTable("Color Blind :");
        Header.addTable(blind);
        messageInfo3.add("Color Blind :");
        messageInfo3.add(blind);

        if (medInfo.getVisionNote() != null) {
            visionNote = medInfo.getVisionNote();
        }
        Header.addTable("Notes:");
        Header.addTable(visionNote);
        messageInfo3.add("Notes :");
        messageInfo3.add(visionNote);


        Header.addTable("Mouth ");
        Header.addTable("");
        messageInfo3.add("Mouth ");
        messageInfo3.add("");

        if (medInfo.getFalses() != null) {
            upper = medInfo.getFalses();
        }
        Header.addTable("Dentures- Removable Upper :");
        Header.addTable(upper);
        messageInfo3.add("Dentures- Removable Upper :");
        messageInfo3.add(upper);

        if (medInfo.getImplants() != null) {
            lower = medInfo.getImplants();
        }
        Header.addTable("Dentures- Removable Lower :");
        Header.addTable(lower);
        messageInfo3.add("Dentures- Removable Lower :");
        messageInfo3.add(lower);

        String drymouth="";
        if (medInfo.getMouth() != null) {
            drymouth= medInfo.getMouth();
        }
        Header.addTable("Dry Mouth :");
        Header.addTable(drymouth);
        messageInfo3.add("Dry Mouth :");
        messageInfo3.add(drymouth);

        String mouthnote="";
        if (medInfo.getMouthnote() != null) {
            mouthnote = medInfo.getMouthnote();
        }
        Header.addTable("Notes :");
        Header.addTable(mouthnote);
        messageInfo3.add("Notes :");
        messageInfo3.add(mouthnote);

        Header.addTable("Hearing & Speech :");
        Header.addTable("");
        messageInfo3.add("Hearing & Speech :");
        messageInfo3.add("");

        String aid="";
        if (medInfo.getAid() != null) {
            aid = medInfo.getAid();
        }
        Header.addTable("Hearing Aids :");
        Header.addTable(aid);
        messageInfo3.add("Hearing Aids :");
        messageInfo3.add(aid);

        String speech="";
        if (medInfo.getSpeech() != null) {
            speech = medInfo.getSpeech();
        }
        Header.addTable("Speech Impaired :");
        Header.addTable(speech);
        messageInfo3.add("Speech Impaired :");
        messageInfo3.add(speech);

        String aidNote="";
        if (medInfo.getAideNote() != null) {
            aidNote = medInfo.getAideNote();
        }
        Header.addTable("Hearing Notes :");
        Header.addTable(aidNote);
        messageInfo3.add("Hearing Notes :");
        messageInfo3.add(aidNote);

        Header.addTable("Functional Status :");
        Header.addTable("");
        messageInfo3.add("Functional Status :");
        messageInfo3.add("");

        String feeding="";
        if (medInfo.getFeed() != null) {
            feeding = medInfo.getFeed();
        }
        Header.addTable("Feeding :");
        Header.addTable(feeding);
        messageInfo3.add("Feeding :");
        messageInfo3.add(feeding);

        String toileting="";
        if (medInfo.getToilet() != null) {
            toileting = medInfo.getToilet();
        }
        Header.addTable("Toileting :");
        Header.addTable(toileting);
        messageInfo3.add("Toileting :");
        messageInfo3.add(toileting);

        String selfMedicate="";
        if (medInfo.getMedicate() != null) {
            selfMedicate = medInfo.getMedicate();
        }
        Header.addTable("Self Medicate :");
        Header.addTable(selfMedicate);
        messageInfo3.add("Self Medicate :");
        messageInfo3.add(selfMedicate);

        String functionNote="";
        if (medInfo.getFunctionnote() != null) {
            functionNote = medInfo.getFunctionnote();
        }
        Header.addTable("Function Notes :");
        Header.addTable(functionNote);
        messageInfo3.add("Function Notes :");
        messageInfo3.add(functionNote);

        Header.addTable("Diet :");
        Header.addTable("");
        messageInfo3.add("Diet :");
        messageInfo3.add("");

        String dietNote="";
        if (medInfo.getDietNote() != null) {
            dietNote = medInfo.getDietNote();
        }
        Header.addTable("Diet Notes :");
        Header.addTable(dietNote);
        messageInfo3.add("Diet Notes :");
        messageInfo3.add(dietNote);

        Header.addTable("Blood Type :");
        Header.addTable("");
        messageInfo3.add("Blood Type :");
        messageInfo3.add("");

        String bloodNote="";
        if (medInfo.getBloodType() != null) {
            bloodNote = medInfo.getBloodType();
        }
        Header.addTable("Blood Type :");
        Header.addTable(bloodNote);
        messageInfo3.add("Blood Type :");
        messageInfo3.add(bloodNote);


        String organDonor="";
        if (medInfo.getDonor() != null) {
            organDonor = medInfo.getDonor();
        }
        Header.addTable("Organ Donor :");
        Header.addTable(organDonor);
        messageInfo3.add("Organ Donor :");
        messageInfo3.add(organDonor);

















        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }
}
