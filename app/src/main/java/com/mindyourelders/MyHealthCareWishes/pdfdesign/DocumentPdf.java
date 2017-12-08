package com.mindyourelders.MyHealthCareWishes.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourelders.MyHealthCareWishes.model.Document;

import java.util.ArrayList;

/**
 * Created by welcome on 12/8/2017.
 */

public class DocumentPdf {

    public static ArrayList<String> messageAdvance = new ArrayList<String>();
    public static ArrayList<String> messageOther = new ArrayList<String>();
    public static ArrayList<String> messageRecord = new ArrayList<String>();
    public DocumentPdf(ArrayList<Document> adList) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Advance Directives");
            messageAdvance.add("Advance Directives");
            Header.addEmptyLine(1);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);


            for (int i = 0; i < adList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

                int k = i + 1;
                cell = new PdfPCell(new Phrase("Advance Directive" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Advance Directive" + k + " :");
                messageAdvance.add("");

                Document s = adList.get(i);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                cell = new PdfPCell(new Phrase("Document Type : " + type));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Document Type : ");
                messageAdvance.add(type);


                String person = "";
                if (s.getPerson() != null) {
                    person = s.getPerson();
                }
                cell = new PdfPCell(new Phrase("Name of Person : " + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Name of Person :");
                messageAdvance.add(person);

                String file = "";
                if (s.getName() != null) {
                    file = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of File : " + file));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Name of File :");
                messageAdvance.add(file);

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
                cell = new PdfPCell(new Phrase("Date Signed : " + date));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Date Signed :");
                messageAdvance.add(date);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("");
                messageAdvance.add("");

                Header.document.add(table);

                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }
            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public DocumentPdf(ArrayList<Document> otherList, int y) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Other Documents");
            messageOther.add("Other Documents");
            Header.addEmptyLine(1);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);


            for (int i = 0; i < otherList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

                int k = i + 1;
                cell = new PdfPCell(new Phrase("Other Document" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Other Document" + k + " :");
                messageOther.add("");

                Document s = otherList.get(i);

                String category = "";
                if (s.getCategory() != null) {
                    category = s.getCategory();
                }
                cell = new PdfPCell(new Phrase("Category : " + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Category : ");
                messageOther.add(category);

                String othercategory = "";
                if (s.getOtherCategory() != null) {
                    othercategory = s.getOtherCategory();
                }
                cell = new PdfPCell(new Phrase("Other Category : " + othercategory));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Other Category : ");
                messageOther.add(othercategory);


                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                cell = new PdfPCell(new Phrase("Document Type : " + type));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Document Type : ");
                messageOther.add(type);


                String person = "";
                if (s.getPerson() != null) {
                    person = s.getPerson();
                }
                cell = new PdfPCell(new Phrase("Name of Person : " + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Name of Person :");
                messageOther.add(person);

                String file = "";
                if (s.getName() != null) {
                    file = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of File : " + file));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Name of File :");
                messageOther.add(file);

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
                cell = new PdfPCell(new Phrase("Date Signed : " + date));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Date Signed :");
                messageOther.add(date);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("");
                messageOther.add("");

                Header.document.add(table);

                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }
            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public DocumentPdf(ArrayList<Document> recordList, String record) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Medical Records");
            messageRecord.add("Medical Records");
            Header.addEmptyLine(1);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);


            for (int i = 0; i < recordList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

                int k = i + 1;
                cell = new PdfPCell(new Phrase("Medical Record" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Medical Record" + k + " :");
                messageRecord.add("");

                Document s = recordList.get(i);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                cell = new PdfPCell(new Phrase("Document Type : " + type));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Document Type : ");
                messageRecord.add(type);


                String person = "";
                if (s.getPerson() != null) {
                    person = s.getPerson();
                }
                cell = new PdfPCell(new Phrase("Name on Document : " + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Name on Document :");
                messageRecord.add(person);

                String file = "";
                if (s.getName() != null) {
                    file = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of File : " + file));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Name of File :");
                messageRecord.add(file);

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
                cell = new PdfPCell(new Phrase("Date Signed : " + date));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Date Signed :");
                messageRecord.add(date);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("");
                messageRecord.add("");

                Header.document.add(table);

                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }
            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
