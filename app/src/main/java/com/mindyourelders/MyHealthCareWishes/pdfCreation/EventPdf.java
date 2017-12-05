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
import com.mindyourelders.MyHealthCareWishes.model.Living;
import com.mindyourelders.MyHealthCareWishes.model.Note;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */

public class EventPdf {

    public static ArrayList<String> messageEvent = new ArrayList<String>();
    public static ArrayList<String> messageAppoint = new ArrayList<String>();
    public static ArrayList<String> messageLiving = new ArrayList<String>();

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

    public EventPdf(int k,ArrayList<Living> livingList, int i) {

        try {
            Header.addEmptyLine(1);
            Header.addChank("Activities Of Daily Living");
            messageLiving.add("Activities Of Daily Living");
            Header.addEmptyLine(1);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            for (i=0;i<livingList.size();i++) {

                cell = new PdfPCell(new Phrase("Activities Of Daily Living(ADL) " +" :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Activities Of Daily Living(ADL)" + " :");
                messageLiving.add("");

                Living s = livingList.get(i);

                String bathing = "";
                if (s.getBath() != null) {
                    bathing = s.getBath();
                }

                cell = new PdfPCell(new Phrase("Bathing : " + bathing));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Bathing :");
                messageLiving.add(bathing);

                String continence = "";
                if (s.getContinence() != null) {
                    continence = s.getContinence();
                }
                cell = new PdfPCell(new Phrase("Continence : " + continence));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Continence :");
                messageLiving.add(continence);

                String dressing = "";
                if (s.getDress() != null) {
                    dressing = s.getDress();
                }
                cell = new PdfPCell(new Phrase("Dressing : " + dressing));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageLiving.add("Dressing :");
                messageLiving.add(dressing);

                String eating = "";
                if (s.getFeed() != null) {
                    eating = s.getFeed();
                }
                cell = new PdfPCell(new Phrase("Eating : " + eating));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Eating :");
                messageLiving.add(eating);

                String toileting = "";
                if (s.getToileting() != null) {
                    toileting = s.getToileting();
                }

                cell = new PdfPCell(new Phrase("Toileting : " + toileting));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Toileting :");
                messageLiving.add(toileting);

                String transfering = "";
                if (s.getTransfer() != null) {
                    transfering = s.getTransfer();
                }

                cell = new PdfPCell(new Phrase("Transfering : " + transfering));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Transfering :");
                messageLiving.add(transfering);

                String functionOther = "";
                if (s.getFunctionOther() != null) {
                    functionOther = s.getFunctionOther();
                }
                cell = new PdfPCell(new Phrase("Other-specify : " + functionOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageLiving.add("Other-specify :");
                messageLiving.add(functionOther);

                String functionNote = "";
                if (s.getFunctionNote() != null) {
                    functionNote = s.getFunctionNote();
                }
                cell = new PdfPCell(new Phrase("Note : " + functionNote));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Note :");
                messageLiving.add(functionNote);

                cell = new PdfPCell(new Phrase("Instrumental Activities Of Daily Living(IADL) " +" :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Instrumental Activities Of Daily Living(IADL)" + " :");
                messageLiving.add("");

                String access = "";
                if (s.getTransport() != null) {
                    access = s.getTransport();
                }
                cell = new PdfPCell(new Phrase("Accessing transportation : " + access));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Accessing transportation :");
                messageLiving.add(access);

                String carePet = "";
                if (s.getPets() != null) {
                    carePet = s.getPets();
                }
                cell = new PdfPCell(new Phrase("Caring for pets : " + carePet));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Caring for pets :");
                messageLiving.add(carePet);

                String driving = "";
                if (s.getDrive() != null) {
                    driving = s.getDrive();
                }
                cell = new PdfPCell(new Phrase("Driving : " + driving));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Driving :");
                messageLiving.add(driving);

                String housekeeping = "";
                if (s.getKeep() != null) {
                    housekeeping = s.getKeep();
                }
                cell = new PdfPCell(new Phrase("Housekeeping : " + housekeeping));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Housekeeping :");
                messageLiving.add(housekeeping);

                String manage = "";
                if (s.getMedication() != null) {
                    manage = s.getMedication();
                }
                cell = new PdfPCell(new Phrase("Manage medications : " + manage));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Manage medications :");
                messageLiving.add(manage);

                String finance = "";
                if (s.getFinance() != null) {
                    finance = s.getFinance();
                }
                cell = new PdfPCell(new Phrase("Managing personal finances : " + finance));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageLiving.add("Managing personal finances :");
                messageLiving.add(finance);

                String meal = "";
                if (s.getPrepare() != null) {
                    meal = s.getPrepare();
                }
                cell = new PdfPCell(new Phrase("Preparing meals : " + meal));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Preparing meals :");
                messageLiving.add(meal);

                String shopping = "";
                if (s.getShop() != null) {
                    shopping = s.getShop();
                }
                cell = new PdfPCell(new Phrase("Shopping for groceries or clothes : " + shopping));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Shopping for groceries or clothes :");
                messageLiving.add(shopping);

                String telephone = "";
                if (s.getUse() != null) {
                    telephone = s.getUse();
                }
                cell = new PdfPCell(new Phrase("Using telephone : " + telephone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Using telephone :");
                messageLiving.add(telephone);

                String instOther = "";
                if (s.getInstOther() != null) {
                    instOther = s.getInstOther();
                }
                cell = new PdfPCell(new Phrase("Other-specify : " + instOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Other-specify :");
                messageLiving.add(instOther);

                String instNote = "";
                if (s.getInstNote() != null) {
                    instNote = s.getInstNote();
                }
                cell = new PdfPCell(new Phrase("Note : " + instNote));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageLiving.add("Note :");
                messageLiving.add(instNote);

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
