package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.database.DosageQuery;
import com.mindyourelders.MyHealthCareWishes.model.Dosage;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.pdfCreation.Specialty.messageDoctor;

/**
 * Created by welcome on 11/7/2017.
 */


public class PrescriptionPdf {
    public static ArrayList<String> messagePrescription = new ArrayList<String>();


    public PrescriptionPdf(ArrayList<Prescription> prescriptionList) {

        Header.addChank("Prescription");
        messagePrescription.add("Prescription");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<prescriptionList.size();i++) {
            Header.addTable("Doctors " + i + 1 + " :");
            Header.addTable("");
            messageDoctor.add("Doctors " + i + 1 + " :");
            messageDoctor.add("");

            Prescription s = prescriptionList.get(i);



           /* String speciality = "";
            if (s.getType() != null) {
                speciality = s.getType();
            }
            Header.addTable("Speciality :");
            Header.addTable(speciality);
            messageDoctor.add("Speciality :");
            messageDoctor.add(speciality);

            String name = "";
            if (s.getName() != null) {
                name = s.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageDoctor.add("Name :");
            messageDoctor.add(name);

            String officePhone = "";
            if (s.getOfficePhone() != null) {
                officePhone = s.getOfficePhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageDoctor.add("Office Phone :");
            messageDoctor.add(officePhone);

            String afterHoursPhone = "";
            if (s.getHourPhone() != null) {
                afterHoursPhone = s.getHourPhone();
            }
            Header.addTable("After Hours Phone :");
            Header.addTable(afterHoursPhone);
            messageDoctor.add("After Hours Phone :");
            messageDoctor.add(afterHoursPhone);

            String otherPhone = "";
            if (s.getOtherPhone() != null) {
                otherPhone = s.getOtherPhone();
            }
            Header.addTable("Other Phone :");
            Header.addTable(otherPhone);
            messageDoctor.add("Other Phone :");
            messageDoctor.add(otherPhone);

            String officeFax = "";
            if (s.getFax() != null) {
                officeFax = s.getFax();
            }
            Header.addTable("Office Fax :");
            Header.addTable(officeFax);
            messageDoctor.add("Office Fax :");
            messageDoctor.add(officeFax);


            String address="";
            if (s.getAddress() != null) {
                address = s.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageDoctor.add("Address :");
            messageDoctor.add(address);

            String website="";
            if (s.getWebsite() != null) {
                website = s.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messageDoctor.add("Website :");
            messageDoctor.add(website);

            String medicalPracticeName="";
            if (s.getPracticeName() != null) {
                medicalPracticeName = s.getPracticeName();
            }
            Header.addTable("Medical Practice Name :");
            Header.addTable(medicalPracticeName);
            messageDoctor.add("Medical Practice Name :");
            messageDoctor.add(medicalPracticeName);

            String hospitalAffiliations="";
            if (s.getHospAffiliation() != null) {
                hospitalAffiliations = s.getHospAffiliation();
            }
            Header.addTable("Hospital Affiliations :");
            Header.addTable(hospitalAffiliations);
            messageDoctor.add("Hospital Affiliations :");
            messageDoctor.add(hospitalAffiliations);

            String networkStatus="";
            if (s.getNetwork() != null) {
                networkStatus = s.getNetwork();
            }
            Header.addTable("In Network Status :");
            Header.addTable(networkStatus);
            messageDoctor.add("In Network Status :");
            messageDoctor.add(networkStatus);

            String lastSeen="";
            if (s.getLastseen() != null) {
                lastSeen = s.getLastseen();
            }

            Header.addTable("Last Seen :");
            Header.addTable(lastSeen);
            messageDoctor.add("Last Seen :");
            messageDoctor.add(lastSeen);

            String note="";
            if (s.getNote() != null) {
                note = s.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messageDoctor.add("Notes :");
            messageDoctor.add(note);
 */
            ArrayList<Dosage> DosageList= DosageQuery.fetchAllDosageRecord(prescriptionList.get(i).getUserid(),prescriptionList.get(i).getUnique());
            for(int j=0;j<DosageList.size();j++){
                Header.addTable("Dosage " + j + 1 + " :");
                Header.addTable("");
                messagePrescription.add("Dosage " + j + 1 + " :");
                messagePrescription.add("");

                Dosage d = DosageList.get(j);
                

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
