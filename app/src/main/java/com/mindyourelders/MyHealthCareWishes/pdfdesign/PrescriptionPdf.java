package com.mindyourelders.MyHealthCareWishes.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;

import java.util.ArrayList;

/**
 * Edited by nikita on 04/11/2017.
 */


public class PrescriptionPdf {
    public static ArrayList<String> messagePrescription = new ArrayList<String>();


    public PrescriptionPdf(ArrayList<Prescription> prescriptionList) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Prescription Tracker");
            messagePrescription.add("Prescription Tracker");
            Header.addEmptyLine(1);
           
            for (int i = 0; i < prescriptionList.size(); i++) {
               /* int k = i + 1;
                Header.addEmptyLine(1);
                Header.addChank("Prescription Tracker " + k + " : ");
                messagePrescription.add("Prescription Tracker " + k + " : ");
                Header.addEmptyLine(1);*/

                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

                String medicine = "";
                if (prescriptionList.get(i).getMedicine() != null) {
                    medicine = prescriptionList.get(i).getMedicine();
                }
                cell = new PdfPCell(new Phrase("Name of Medication or Supplement : " + medicine));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Name of Medication or Supplement :");
                messagePrescription.add(medicine);

                String dosage = "";
                if (prescriptionList.get(i).getDose() != null) {
                    dosage = prescriptionList.get(i).getDose();
                }
                cell = new PdfPCell(new Phrase("Dosage : " + dosage));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Dosage :");
                messagePrescription.add(dosage);

                String frequency = "";
                if (prescriptionList.get(i).getFrequency() != null) {
                    frequency = prescriptionList.get(i).getFrequency();
                }
                cell = new PdfPCell(new Phrase("Frequency : " + frequency));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Frequency :");
                messagePrescription.add(frequency);

                String rx = "";
                if (prescriptionList.get(i).getRX() != null) {
                    rx = prescriptionList.get(i).getRX();
                }
                cell = new PdfPCell(new Phrase("RX# : " + rx));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("RX# :");
                messagePrescription.add(rx);

                String doctor = "";
                if (prescriptionList.get(i).getDoctor() != null) {
                    doctor = prescriptionList.get(i).getDoctor();
                }
                cell = new PdfPCell(new Phrase("Prescribing Doctor : " + doctor));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Prescribing Doctor :");
                messagePrescription.add(doctor);

                String counter = "";
                if (prescriptionList.get(i).getPre() != null) {
                    counter = prescriptionList.get(i).getPre();
                }
                cell = new PdfPCell(new Phrase("Over-the-counter : " + counter));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Over-the-counter :");
                messagePrescription.add(counter);

                String date = "";
                if (prescriptionList.get(i).getDates() != null) {
                    date = prescriptionList.get(i).getDates();
                }
                cell = new PdfPCell(new Phrase("Date of Prescription : " + date));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Date of Prescription :");
                messagePrescription.add(date);

                String treatment = "";
                if (prescriptionList.get(i).getPurpose() != null) {
                    treatment = prescriptionList.get(i).getPurpose();
                }
                cell = new PdfPCell(new Phrase("Treatment For : " + treatment));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Treatment For :");
                messagePrescription.add(treatment);

                String notes = "";
                if (prescriptionList.get(i).getNote() != null) {
                    notes = prescriptionList.get(i).getNote();
                }
                cell = new PdfPCell(new Phrase("Notes : " + notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Notes :");
                messagePrescription.add(notes);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("");
                messagePrescription.add("");

                Header.document.add(table);

                Header.addEmptyLine(1);

                ArrayList<PrescribeImage> imagelist = new ArrayList<PrescribeImage>();
                if (prescriptionList.get(i).getPrescriptionImageList() != null) {
                    imagelist = prescriptionList.get(i).getPrescriptionImageList();

                    for (int j = 0; j < imagelist.size(); j++) {
/*
                        byte[] IMG1 = imagelist.get(j).getImage();

                        Image img1 = Image.getInstance(IMG1);
                        img1.scaleAbsolute(500,500);
                        img1.setBorder(Rectangle.BOX);
                        img1.setBorderColor(BaseColor.DARK_GRAY);

                        PdfPTable table1;
                        table1 = new PdfPTable(1);
                        table1.setWidthPercentage(100);

                        PdfPCell cell1 = new PdfPCell();
                        cell1.setBorder(Rectangle.NO_BORDER);
                        cell1.addElement(img1);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table1.addCell(cell1);

                        Header.document.add(table1);

                        Header.addEmptyLine(1);*/
                    }


                }


                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
