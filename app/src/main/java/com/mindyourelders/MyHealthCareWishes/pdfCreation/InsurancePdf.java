package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */


public class InsurancePdf {
    public static ArrayList<String> messageInsurance = new ArrayList<String>();


    public InsurancePdf(ArrayList<Insurance> insuranceList) {

        Header.addChank("Insurance Information");
        messageInsurance.add("Insurance Information");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<insuranceList.size();i++) {
            Header.addTable("Insurance Information " + i + 1 + " :");
            Header.addTable("");
            messageInsurance.add("Insurance Information " + i + 1 + " :");
            messageInsurance.add("");

            Insurance s = insuranceList.get(i);

            String name = "";
            if (s.getName() != null) {
                name = s.getName();
            }
            Header.addTable("Name of Insurance Company:");
            Header.addTable(name);
            messageInsurance.add("Name of Insurance Company:");
            messageInsurance.add(name);

            String type = "";
            if (s.getType() != null) {
                type = s.getType();
            }
            Header.addTable("Type of Insurance :");
            Header.addTable(type);
            messageInsurance.add("Type of Insurance :");
            messageInsurance.add(type);

            String memberId = "";
            if (s.getMember() != null) {
                memberId = s.getMember();
            }
            Header.addTable("Member ID(Policy Number) :");
            Header.addTable(memberId);
            messageInsurance.add("Member ID(Policy Number) :");
            messageInsurance.add(memberId);

            String group = "";
            if (""+s.getGroup() != null) {
                group = ""+s.getGroup();
            }
            Header.addTable("Group :");
            Header.addTable(group);
            messageInsurance.add("Group :");
            messageInsurance.add(group);

            String nameofInsured = "";
            if (""+s.getSubscriber() != null) {
                nameofInsured = ""+s.getSubscriber();
            }
            Header.addTable("Name of Insured(Primary Subsriber) :");
            Header.addTable(nameofInsured);
            messageInsurance.add("Name of Insured(Primary Subsriber) :");
            messageInsurance.add(nameofInsured);

            String providerPhone = "";
            if (s.getPhone() != null) {
                providerPhone = s.getPhone();
            }
            Header.addTable("Provider Phone :");
            Header.addTable(providerPhone);
            messageInsurance.add("Provider Phone :");
            messageInsurance.add(providerPhone);

            String providerFax = "";
            if (s.getFax() != null) {
                providerFax = s.getFax();
            }
            Header.addTable("Provider Fax :");
            Header.addTable(providerFax);
            messageInsurance.add("Provider Fax :");
            messageInsurance.add(providerFax);

            String providerEmail = "";
            if (s.getEmail() != null) {
                providerEmail = s.getEmail();
            }
            Header.addTable("Provider Email :");
            Header.addTable(providerEmail);
            messageInsurance.add("Provider Email :");
            messageInsurance.add(providerEmail);

            String website = "";
            if (s.getWebsite() != null) {
                website = s.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messageInsurance.add("Website :");
            messageInsurance.add(website);

            String agentname = "";
            if (s.getAgent() != null) {
                agentname = s.getAgent();
            }
            Header.addTable("Agent Name and Phone and Email :");
            Header.addTable(agentname);
            messageInsurance.add("Agent Name and Phone and Email :");
            messageInsurance.add(agentname);

            String notes = "";
            if (s.getNote() != null) {
                notes = s.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(notes);
            messageInsurance.add("Notes :");
            messageInsurance.add(notes);

        }


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
