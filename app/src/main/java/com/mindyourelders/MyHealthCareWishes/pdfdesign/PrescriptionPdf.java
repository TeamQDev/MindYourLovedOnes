package com.mindyourelders.MyHealthCareWishes.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */


public class PrescriptionPdf {
    public static ArrayList<String> messagePrescription = new ArrayList<String>();


    public PrescriptionPdf(ArrayList<Prescription> prescriptionList) {
        try {
            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            Header.addEmptyLine(1);
            Header.addChank("Prescription Tracker");
            messagePrescription.add("Prescription Tracker");
            Header.addEmptyLine(1);



            for (int i = 0; i < prescriptionList.size(); i++) {
                int k = i + 1;
                cell = new PdfPCell(new Phrase("Prescription Tracker " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Prescription Tracker " + k + " :");
                messagePrescription.add("");

                Prescription s = prescriptionList.get(i);

                String medicine = "";
                if (s.getMedicine() != null) {
                    medicine = s.getMedicine();
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
                if (s.getDose() != null) {
                    dosage = s.getDose();
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
                if (s.getFrequency() != null) {
                    frequency = s.getFrequency();
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
                if (s.getRX() != null) {
                    rx = s.getRX();
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
                if (s.getDoctor() != null) {
                    doctor = s.getDoctor();
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
                if (s.getPre() != null) {
                    counter = s.getPre();
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
                if (s.getDates() != null) {
                    date = s.getDates();
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
                if (s.getPurpose() != null) {
                    treatment = s.getPurpose();
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
                if (s.getNote() != null) {
                    notes = s.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes : " + notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("Notes :");
                messagePrescription.add(notes);


              /*  ArrayList<PrescribeImage> imagelist=new ArrayList<>();
                if (s.getPrescriptionImageList() != null) {
                    imagelist =s.getPrescriptionImageList();
                }

                for(int j=0;j<imagelist.size();j++) {
                   byte[] prphoto=imagelist.get(j).getImage();
                }
*/
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
