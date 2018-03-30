package com.mindyourlovedones.healthcare.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourlovedones.healthcare.model.Document;

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

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Advance Directive" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Advance Directive" + k + " :");
                messageAdvance.add("");*/

                Document s = adList.get(i);

                String type = "";
                String otherdoc="";
                if (s.getType() != null) {
                    type = s.getType();
                    if (s.getOtherDoc()!=null)
                    {
                        type=type+" - "+s.getOtherDoc();
                    }
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

                String holder = "";
                if (s.getHolder() != null) {
                    holder = s.getHolder();
                }
                cell = new PdfPCell(new Phrase("Distributed to : " + holder));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Distributed to :");
                messageAdvance.add(holder);

                String location = "";
                if (s.getLocation() != null) {
                    location = s.getLocation();
                }
                cell = new PdfPCell(new Phrase("Location of Original : " + location));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageAdvance.add("Location of Original :");
                messageAdvance.add(location);

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

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Other Document" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Other Document" + k + " :");
                messageOther.add("");*/

                Document s = otherList.get(i);

                String category = "";
                if (s.getCategory() != null) {
                    category = s.getCategory();
                    if (!s.getOtherCategory().equals(""))
                    {
                        category=category+" - "+s.getOtherCategory();
                    }
                }
                cell = new PdfPCell(new Phrase("Category : " + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Category : ");
                messageOther.add(category);

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

                String holder = "";
                if (s.getHolder() != null) {
                    holder = s.getHolder();
                }
                cell = new PdfPCell(new Phrase("Distributed to : " + holder));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Distributed to :");
                messageOther.add(holder);

                String location = "";
                if (s.getLocation() != null) {
                    location = s.getLocation();
                }
                cell = new PdfPCell(new Phrase("Location of Original : " + location));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageOther.add("Location of Original :");
                messageOther.add(location);


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

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Medical Record" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Medical Record" + k + " :");
                messageRecord.add("");*/

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

                String holder = "";
                if (s.getHolder() != null) {
                    holder = s.getHolder();
                }
                cell = new PdfPCell(new Phrase("Distributed to : " + holder));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Distributed to :");
                messageRecord.add(holder);

                String location = "";
                if (s.getLocation() != null) {
                    location = s.getLocation();
                }
                cell = new PdfPCell(new Phrase("Location of Original : " + location));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Location of Original :");
                messageRecord.add(location);

                String hosp = "";
                if (s.getHospital() != null) {
                    hosp = s.getHospital();
                }
                cell = new PdfPCell(new Phrase("Associated Hospital : " + hosp));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Associated Hospital :");
                messageRecord.add(hosp);


                String locator = "";
                if (s.getLocator() != null) {
                    locator = s.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Protected Health Information Record Locator (if applicable) : " + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageRecord.add("Electronic Protected Health Information Record Locator (if applicable) :");
                messageRecord.add(locator);

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
