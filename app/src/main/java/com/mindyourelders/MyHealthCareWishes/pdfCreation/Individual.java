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
    String hPhone="";
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
            hPhone = connection.getMobile();
        }
        Header.addTable("Mobile :");
        Header.addTable(hPhone);
        messageInfo.add("Mobile :");
        messageInfo.add(hPhone);

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
            hPhone = connection.getPhone();
        }
        Header.addTable("Mobile :");
        Header.addTable(hPhone);
        messageInfo2.add("Mobile :");
        messageInfo2.add(hPhone);
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
