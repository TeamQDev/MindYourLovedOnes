package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
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
}
