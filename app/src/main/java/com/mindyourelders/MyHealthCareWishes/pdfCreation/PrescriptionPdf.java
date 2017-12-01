package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.database.DosageQuery;
import com.mindyourelders.MyHealthCareWishes.model.Dosage;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */


public class PrescriptionPdf {
    public static ArrayList<String> messagePrescription = new ArrayList<String>();


    public PrescriptionPdf(ArrayList<Prescription> prescriptionList) {

        Header.addChank("Prescription Tracker");
        messagePrescription.add("Prescription Tracker");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<prescriptionList.size();i++) {
            int k=i+1;
            Header.addTable("Prescription Tracker " +k + " :");
            Header.addTable("");
            messagePrescription.add("Prescription Tracker " +k + " :");
            messagePrescription.add("");

            Prescription s = prescriptionList.get(i);

            String supplement = "";
            if (s.getMedicine() != null) {
                supplement = s.getMedicine();
            }
            Header.addTable("Name of Medication or Suppliment :");
            Header.addTable(supplement);
            messagePrescription.add("Name of Medication or Suppliment :");
            messagePrescription.add(supplement);

            String dosage = "";
            if (s.getDose() != null) {
                dosage = s.getDose();
            }
            Header.addTable("Dosage :");
            Header.addTable(dosage);
            messagePrescription.add("Dosage :");
            messagePrescription.add(dosage);

            String rx = "";
            if (s.getRX() != null) {
                rx = s.getRX();
            }
            Header.addTable("RX :");
            Header.addTable(rx);
            messagePrescription.add("RX :");
            messagePrescription.add(rx);

            String doctor = "";
            if (s.getDoctor() != null) {
                doctor = s.getDoctor();
            }
            Header.addTable("Prescribing Doctor :");
            Header.addTable(doctor);
            messagePrescription.add("Prescribing Doctor :");
            messagePrescription.add(doctor);

            String counter = "";
            if (s.getPre() != null) {
                counter = s.getPre();
            }
            Header.addTable("Over-the-counter :");
            Header.addTable(counter);
            messagePrescription.add("Over-the-counter :");
            messagePrescription.add(counter);

            String date = "";
            if (s.getDates() != null) {
                date = s.getDates();
            }
            Header.addTable("Date of Prescription :");
            Header.addTable(date);
            messagePrescription.add("Date of Prescription :");
            messagePrescription.add(date);

            String treatment = "";
            if (s.getPurpose() != null) {
                treatment = s.getPurpose();
            }
            Header.addTable("Treatment For :");
            Header.addTable(treatment);
            messagePrescription.add("Treatment For :");
            messagePrescription.add(treatment);

            String notes = "";
            if (s.getNote() != null) {
                notes = s.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(notes);
            messagePrescription.add("Notes :");
            messagePrescription.add(notes);


            ArrayList<Dosage> DosageList= DosageQuery.fetchAllDosageRecord(prescriptionList.get(i).getUserid(),prescriptionList.get(i).getUnique());
            for(int j=0;j<DosageList.size();j++){
                k=j+1;
                Header.addTable("Medicine and Dosage " +k + " :");
                Header.addTable("");
                messagePrescription.add("Medicine and Dosage " +k + " :");
                messagePrescription.add("");

               // Dosage s = DosageList.get(j);

                String medicineName = "";
                if (s.getMedicine() != null) {
                    medicineName = s.getMedicine();
                }
                Header.addTable("Medicine Name :");
                Header.addTable(medicineName);
                messagePrescription.add("Medicine Name :");
                messagePrescription.add(medicineName);

                String RX = "";
                if (s.getRx() != null) {
                    RX = s.getRx();
                }
                Header.addTable("RX :");
                Header.addTable(RX);
                messagePrescription.add("RX :");
                messagePrescription.add(RX);

                String Dose = "";
                if (s.getDose() != null) {
                    Dose = s.getDose();
                }
                Header.addTable("Dose :");
                Header.addTable(Dose);
                messagePrescription.add("Dose :");
                messagePrescription.add(Dose);

                String frequency = "";
                if (s.getFrequency() != null) {
                    frequency = s.getFrequency();
                }
                Header.addTable("Frequency :");
                Header.addTable(frequency);
                messagePrescription.add("Frequency :");
                messagePrescription.add(frequency);

            }

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
