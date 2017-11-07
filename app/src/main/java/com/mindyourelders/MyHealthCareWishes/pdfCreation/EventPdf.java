package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */

public class EventPdf {
    public static ArrayList<String> messageAppoint = new ArrayList<String>();
    public EventPdf(ArrayList<Appoint> appointList) {
        Header.addChank("Appointment Checklist");
        messageAppoint.add("Appointment Checklist");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<appointList.size();i++) {
            Header.addTable("Appointment Checklist " + i + 1 + " :");
            Header.addTable("");
            messageAppoint.add("Appointment Checklist " + i + 1 + " :");
            messageAppoint.add("");

            Appoint s = appointList.get(i);

            String speciality = "";
            if (s.getType() != null) {
                speciality = s.getType();
            }
            Header.addTable("Specialist to see OR Type Test :");
            Header.addTable(speciality);
            messageAppoint.add("Specialist to see OR Type Test :");
            messageAppoint.add(speciality);

           String name = "";
            if (s.getDoctor() != null) {
                name = s.getDoctor();
            }
            Header.addTable("Name of Dr associated(if any) :");
            Header.addTable(name);
            messageAppoint.add("Name of Dr associated(if any) :");
            messageAppoint.add(name);

            String frequency = "";
            if (s.getFrequency() != null) {
                frequency = s.getFrequency();
            }
            Header.addTable("Frequency :");
            Header.addTable(frequency);
            messageAppoint.add("Frequency :");
            messageAppoint.add(frequency);

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
