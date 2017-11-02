package com.mindyourelders.MyHealthCareWishes.utility;

import com.itextpdf.text.BadElementException;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class Header {
	public static Font GreenFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	public static Font BlackFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.BOLD);
	public static Document document;
	public static float[] widths = { 0.15f, 0.85f };
	public static PdfPTable table;
	public static String headertext;
	private static PdfPCell cell;
	PdfWriter writer;

	/**
	 * Inner class to add a table as header.
	 */
	class TableHeader extends PdfPageEventHelper {
		protected PdfPTable header;
		protected PdfPTable footer;

		public void setHeader(String header) {

			headertext = header;
		}

		/**
		 * http://www.javagenious.com/itext/create-header-footer-in-pdf-using-
		 * itext-java.html http://itextpdf.com/themes/keyword.php?id=221
		 */
		public void onEndPage(PdfWriter writer, Document document) {

			PdfContentByte cby = writer.getDirectContent();
			drowBorder(cby);
			GreenFont.setColor(31, 99, 26);
			// header = new Phrase(headertext, GreenFont);
			header = new PdfPTable(1);
			header.setTotalWidth(530);
			header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			header.addCell(new Phrase(headertext, GreenFont));

			footer = new PdfPTable(1);
			footer.setTotalWidth(300);
			footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			footer.getDefaultCell()
					.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.addCell(new Phrase(String.format("Page  "
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
	 * This function drow a border for all side
	 * 
	 * @param cb
	 *            =PdfContentByte
	 */
	public static void drowBorder(PdfContentByte cb) {
		cb.setLineWidth(2.0f); // Make a bit thicker than 1.0 default
		// cb.setColorStroke(BaseColor.BLUE);
		cb.setRGBColorStroke(75, 47, 19);
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

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void createPdfHeader(String RESULT, String header) {
		document = new Document(PageSize.A4, 30, 30, 50, 50);

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					RESULT));
			TableHeader event = new TableHeader();
			writer.setPageEvent(event);

			// step 3 - fill in the document
			document.open();

			event.setHeader(header);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addImage(String path) {
		Image image = null;
		try {
			image = Image.getInstance(path);
			image.setAlignment(Element.ALIGN_RIGHT);
			image.scaleAbsoluteHeight(20);
			image.scaleAbsoluteWidth(10);
			image.scalePercent(50);
			image.setAbsolutePosition(50, 670);
			image.scaleAbsolute(59f, 59f);
		} catch (BadElementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			document.add(image);
		} catch (DocumentException e) {
			e.printStackTrace();
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
		GreenFont.setColor(255, 99, 26);
		Chunk underline = new Chunk(chunk, GreenFont);
		underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
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
	 * 
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
		GreenFont.setColor(255, 99, 26);
		Chunk underline = new Chunk(username, GreenFont);
		underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
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