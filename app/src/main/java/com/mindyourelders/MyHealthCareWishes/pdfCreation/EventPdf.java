package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.DashBoard.DateClass;
import com.mindyourelders.MyHealthCareWishes.database.DateQuery;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.model.Note;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */

public class EventPdf {

    public static ArrayList<String> messageEvent = new ArrayList<String>();
    public static ArrayList<String> messageAppoint = new ArrayList<String>();

    public EventPdf(ArrayList<Appoint> appointList) {
        Header.addChank("Appointment Checklist");
        messageAppoint.add("Appointment Checklist");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i = 0; i < appointList.size(); i++) {
            int k = i + 1;
            Header.addTable("Appointment Checklist " + k + " :");
            Header.addTable("");
            messageAppoint.add("Appointment Checklist " + k + " :");
            messageAppoint.add("");

            Appoint s = appointList.get(i);

            String speciality = "";
            if (s.getType() != null) {
                speciality = s.getType();
            }
            Header.addTable("Specialist or Type Test :");
            Header.addTable(speciality);
            messageAppoint.add("Specialist or Type Test :");
            messageAppoint.add(speciality);

            String name = "";
            if (s.getDoctor() != null) {
                name = s.getDoctor();
            }
            Header.addTable("Name of Doctor(if aplicable) :");
            Header.addTable(name);
            messageAppoint.add("Name of Doctor(if aplicable) :");
            messageAppoint.add(name);

            String frequency = "";
            if (s.getFrequency() != null) {
                frequency = s.getFrequency();
            }
            Header.addTable("Frequency :");
            Header.addTable(frequency);
            messageAppoint.add("Frequency :");
            messageAppoint.add(frequency);

            ArrayList<DateClass> datelist = DateQuery.fetchAllDosageRecord(appointList.get(i).getUserid(), appointList.get(i).getUnique());
            for (int j = 0; j < datelist.size(); j++) {
                k = j + 1;
                Header.addTable("Date Completed" + k + " :");
                Header.addTable("");
                messageAppoint.add("Date Completed " + k + " :");
                messageAppoint.add("");

                DateClass d = datelist.get(j);

                String date = "";
                if (d.getDate() != null) {
                    date = d.getDate();
                }
                Header.addTable("Date :");
                Header.addTable(date);
                messageAppoint.add("Date :");
                messageAppoint.add(date);

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

    public EventPdf(ArrayList<Note> noteList, int i) {

        Header.addChank("Event Notes");
        messageEvent.add("Event Notes");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (i = 0; i < noteList.size(); i++) {
            int k = i + 1;
            Header.addTable("Event Notes " + k + " :");
            Header.addTable("");
            messageEvent.add("Event Notes " + k + " :");
            messageEvent.add("");

            Note s = noteList.get(i);

            String name = "";
            if (s.getTxtNote() != null) {
                name = s.getTxtNote();
            }
            Header.addTable("Event Note :");
            Header.addTable(name);
            messageEvent.add("Event Note :");
            messageEvent.add(name);


            String noteDate = "";
            if (s.getTxtDate() != null) {
                noteDate = s.getTxtDate();
            }
            Header.addTable("Event Date :");
            Header.addTable(noteDate);
            messageEvent.add("Event Date :");
            messageEvent.add(noteDate);

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
