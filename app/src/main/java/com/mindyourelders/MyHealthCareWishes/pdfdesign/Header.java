package com.mindyourelders.MyHealthCareWishes.pdfdesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Header {
    public static Font GreenFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
            Font.BOLD);
    public static Font BlackFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    public static Document document;
    public static float[] widths = {0.15f, 0.85f};
    public static PdfPTable table;
    public static String headertext;
    private static PdfPCell cell;
    public static PdfWriter writer;


    /**
     * This function drow a border for all side
     *
     * @param cb =PdfContentByte
     */
    public static void drowBorder(PdfContentByte cb) {
        cb.setLineWidth(2.0f); // Make a bit thicker than 1.0 default
         cb.setColorStroke(BaseColor.LIGHT_GRAY);
//        cb.setRGBColorStroke(75, 47, 19);
        /**
         * left
         */
        cb.moveTo(20, 20);
        cb.lineTo(20, 825);
        /**
         * Right
         */
        cb.moveTo(575, 825);
        cb.lineTo(575, 20);

        /**
         * top
         */
        cb.moveTo(20, 824);
        cb.lineTo(575, 824);
        /**
         * Bottom
         */
        cb.moveTo(20, 21);
        cb.lineTo(575, 21);

        cb.stroke();
    }

    public class Background extends PdfPageEventHelper {
        protected PdfPTable header;
        protected PdfPTable footer;
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            PdfContentByte cby = writer.getDirectContent();
            drowBorder(cby);
            // header = new Phrase(headertext, GreenFont);
            header = new PdfPTable(2);
            header.setTotalWidth(530);
//            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//            header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());
//            header.addCell(new Phrase("Date : "+formattedDate));

            PdfPCell cells = new PdfPCell(new Phrase(headertext));
            cells.setBorder(Rectangle.NO_BORDER);
            cells.setHorizontalAlignment(Element.ALIGN_LEFT);
            header.addCell(cells);

            cells = new PdfPCell(new Phrase("Date : "+formattedDate));
            cells.setBorder(Rectangle.NO_BORDER);
            cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
            header.addCell(cells);

            footer = new PdfPTable(1);
            footer.setTotalWidth(300);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            footer.getDefaultCell()
                    .setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(new Phrase(String.format(""
                    + (writer.getPageNumber()))));
            PdfContentByte cb = writer.getDirectContent();
            header.writeSelectedRows(
                    0,
                    -1,
                    (document.right() - document.left() - 530) / 2
                            + document.leftMargin(), document.top() + 20, cb);
            footer.writeSelectedRows(
                    0,
                    -1,
                    (document.right() - document.left() - 300) / 2
                            + document.leftMargin(), document.bottom() - 10, cb);
            cb.setLineWidth(.50f); // Make a bit thicker than 1.0 default
            cb.setGrayStroke(0.50f);
            cb.moveTo(30, 793);
            cb.lineTo(560, 793);
            cb.stroke();
        }
    }
    /**
     * Creates a PDF document.
     *
     * @throws DocumentException
     * @throws IOException
     * @throws SQLException
     */
    public void createPdfHeader(String RESULT, String header) {
        document = new Document(PageSize.A4, 30, 30, 50, 50);

        try {
            headertext = header;
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    RESULT));
            Background event = new Background();
            writer.setPageEvent(event);
            document.open();


        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void addImage(String path) {
        Image image = null;
        try {
                          // get input stream
                InputStream ims = new FileInputStream(path);
                Bitmap bmp = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image = Image.getInstance(stream.toByteArray());

                image.setAlignment(Element.ALIGN_RIGHT);
                image.scaleAbsoluteHeight(30);
                image.scaleAbsoluteWidth(15);
                image.scalePercent(50);
                image.setAbsolutePosition(50, 720);
                image.scaleAbsolute(59f, 59f);
                document.add(image);



        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    public static void addPhoto(byte[] photo) {
        Image image = null;
        try {
            // get input stream
           /* InputStream ims = new FileInputStream(path);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);*/
            image = Image.getInstance(photo);

            image.setAlignment(Element.ALIGN_RIGHT);
            image.scaleAbsoluteHeight(30);
            image.scaleAbsoluteWidth(15);
            image.scalePercent(50);
            image.setAbsolutePosition(50, 720);
            image.scaleAbsolute(59f, 59f);
            document.add(image);



        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    /**
     * add paragraph
     */

    public static void addParagraph(String[][] data) {
        BlackFont.setColor(00, 00, 00);
        for (int i = 0; i < data.length; i++) {

            try {
                Paragraph p = new Paragraph(data[i][0] + ":" + data[i][1],
                        BlackFont);
                document.add(p);

            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */

    public static void addParagraph(String data) {
        BlackFont.setColor(00, 00, 00);

        try {
            Paragraph p = new Paragraph(data, BlackFont);
            document.add(p);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * add underline with text (chank)
     */

    public static void addChank(String chunk) {
        BlackFont.setColor(102, 204, 0);//255, 99, 26);
        Chunk underline = new Chunk(chunk, BlackFont);
//        underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
        Paragraph p = new Paragraph(underline);
        p.setAlignment(Paragraph.ALIGN_LEFT);

        try {
            document.add(p);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * http://www.geek-tutorials.com/java/itext/itext_table.php
     * http://www.java2s
     * .com/Code/Java/PDF-RTF/SpecificCellwithDifferentWidth.htm
     * http://www.java2s.com/Code/Java/PDF-RTF/LockingtableCellWidths.htm
     *
     * @param data
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException
     */
    public static void addTable(String data) {

        cell = new PdfPCell(new Phrase(data, BlackFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

    }


    public static void addEmptyLine(int number) {
        for (int i = 0; i < number; i++) {
            try {
                document.add(new Paragraph(" "));
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void addusereNameChank(String username) {
        GreenFont.setColor(0,153,153);
        Chunk underline = new Chunk(username, GreenFont);
//        underline.setUnderline(0.1f, -3f); // 0.1 thick, -2 y-location
        Paragraph p = new Paragraph(underline);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        try {
            document.add(p);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}