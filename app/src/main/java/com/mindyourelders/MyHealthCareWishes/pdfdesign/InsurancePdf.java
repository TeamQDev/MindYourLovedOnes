package com.mindyourelders.MyHealthCareWishes.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */


public class InsurancePdf {
    public static ArrayList<String> messageInsurance = new ArrayList<String>();


    public InsurancePdf(ArrayList<Insurance> insuranceList) {
        try {
            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            Header.addEmptyLine(1);
            Header.addChank("Insurance Information");
            messageInsurance.add("Insurance Information");
            Header.addEmptyLine(1);


            for (int i = 0; i < insuranceList.size(); i++) {
                cell = new PdfPCell(new Phrase("Insurance Information " + i + 1 + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Insurance Information " + i + 1 + " :");
                messageInsurance.add("");

                Insurance s = insuranceList.get(i);

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of Insurance Company : " +name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Name of Insurance Company:");
                messageInsurance.add(name);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                cell = new PdfPCell(new Phrase("Type of Insurance : " +type));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Type of Insurance :");
                messageInsurance.add(type);

                String memberId = "";
                if (s.getMember() != null) {
                    memberId = s.getMember();
                }
                cell = new PdfPCell(new Phrase("Member ID(Policy Number) : " +memberId));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Member ID(Policy Number) :");
                messageInsurance.add(memberId);

                String group = "";
                if ("" + s.getGroup() != null) {
                    group = "" + s.getGroup();
                }
                cell = new PdfPCell(new Phrase("Group : " +group));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Group :");
                messageInsurance.add(group);

                String nameofInsured = "";
                if ("" + s.getSubscriber() != null) {
                    nameofInsured = "" + s.getSubscriber();
                }
                cell = new PdfPCell(new Phrase("Name of Insured(Primary Subsriber) : " +nameofInsured));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Name of Insured(Primary Subsriber) :");
                messageInsurance.add(nameofInsured);

                String providerPhone = "";
                if (s.getPhone() != null) {
                    providerPhone = s.getPhone();
                }
                cell = new PdfPCell(new Phrase("Provider Phone : " +providerPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Phone :");
                messageInsurance.add(providerPhone);

                String providerFax = "";
                if (s.getFax() != null) {
                    providerFax = s.getFax();
                }
                cell = new PdfPCell(new Phrase("Provider Fax : " +providerFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Fax :");
                messageInsurance.add(providerFax);

                String providerEmail = "";
                if (s.getEmail() != null) {
                    providerEmail = s.getEmail();
                }
                cell = new PdfPCell(new Phrase("Provider Email : " +providerEmail));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Email :");
                messageInsurance.add(providerEmail);

                String website = "";
                if (s.getWebsite() != null) {
                    website = s.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website : " +website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Website :");
                messageInsurance.add(website);

                String agentname = "";
                if (s.getAgent() != null) {
                    agentname = s.getAgent();
                }
                cell = new PdfPCell(new Phrase("Agent Name and Phone and Email : " +agentname));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Agent Name and Phone and Email :");
                messageInsurance.add(agentname);

                String notes = "";
                if (s.getNote() != null) {
                    notes = s.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes : " +notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Notes :");
                messageInsurance.add(notes);

            }


            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
