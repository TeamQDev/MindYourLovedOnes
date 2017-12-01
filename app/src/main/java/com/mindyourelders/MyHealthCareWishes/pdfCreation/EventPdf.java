package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourelders.MyHealthCareWishes.DashBoard.DateClass;
import com.mindyourelders.MyHealthCareWishes.database.DateQuery;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.model.Note;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */

public class EventPdf {

    public static ArrayList<String> messageEvent = new ArrayList<String>();
    public static ArrayList<String> messageAppoint = new ArrayList<String>();

    public EventPdf(ArrayList<Appoint> appointList) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Appointment Checklist");
            messageAppoint.add("Appointment Checklist");
            Header.addEmptyLine(1);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            for (int i = 0; i < appointList.size(); i++) {
                int k = i + 1;
                cell = new PdfPCell(new Phrase("Appointment Checklist " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAppoint.add("Appointment Checklist " + k + " :");
                messageAppoint.add("");

                Appoint s = appointList.get(i);

                String speciality = "";
                if (s.getType() != null) {
                    speciality = s.getType();
                }
                cell = new PdfPCell(new Phrase("Specialist or Type Test : " + speciality));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAppoint.add("Specialist or Type Test :");
                messageAppoint.add(speciality);

                String name = "";
                if (s.getDoctor() != null) {
                    name = s.getDoctor();
                }
                cell = new PdfPCell(new Phrase("Name of Doctor(if aplicable) : " + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAppoint.add("Name of Doctor(if aplicable) :");
                messageAppoint.add(name);

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

                messageAppoint.add("Frequency :");
                messageAppoint.add(frequency);

                ArrayList<DateClass> datelist = DateQuery.fetchAllDosageRecord(appointList.get(i).getUserid(), appointList.get(i).getUnique());
                for (int j = 0; j < datelist.size(); j++) {
                    k = j + 1;
                    cell = new PdfPCell(new Phrase("Date Completed " + k + " :"));
                    cell.setBorder(Rectangle.BOTTOM);
                    cell.setUseBorderPadding(true);
                    cell.setBorderWidthBottom(5);
                    cell.setBorderColorBottom(BaseColor.WHITE);
                    table.addCell(cell);
                    messageAppoint.add("Date Completed " + k + " :");
                    messageAppoint.add("");

                    DateClass d = datelist.get(j);

                    String date = "";
                    if (d.getDate() != null) {
                        date = d.getDate();
                    }
                    cell = new PdfPCell(new Phrase("Date : " + date));
                    cell.setBorder(Rectangle.BOTTOM);
                    cell.setUseBorderPadding(true);
                    cell.setBorderWidthBottom(5);
                    cell.setBorderColorBottom(BaseColor.WHITE);
                    table.addCell(cell);
                    messageAppoint.add("Date :");
                    messageAppoint.add(date);

                }

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

    public EventPdf(ArrayList<Note> noteList, int i) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Event Notes");
            messageAppoint.add("Event Notes");
            Header.addEmptyLine(1);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);


            for (i = 0; i < noteList.size(); i++) {
                int k = i + 1;
                cell = new PdfPCell(new Phrase("Event Notes " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEvent.add("Event Notes " + k + " :");
                messageEvent.add("");

                Note s = noteList.get(i);

                String name = "";
                if (s.getTxtNote() != null) {
                    name = s.getTxtNote();
                }
                cell = new PdfPCell(new Phrase("Event Note : " + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEvent.add("Event Note :");
                messageEvent.add(name);


                String noteDate = "";
                if (s.getTxtDate() != null) {
                    noteDate = s.getTxtDate();
                }
                cell = new PdfPCell(new Phrase("Event Date : " + noteDate));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEvent.add("Event Date :");
                messageEvent.add(noteDate);

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
