package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/6/2017.
 */

public class Specialty {

    public static ArrayList<String> messageDoctor = new ArrayList<String>();
    public static ArrayList<String> messageHospital = new ArrayList<String>();
    public static ArrayList<String> messagePharmacy = new ArrayList<String>();
    public static ArrayList<String> messageAides = new ArrayList<String>();
    public static ArrayList<String> messageFinance = new ArrayList<String>();


    public Specialty(ArrayList<Specialist> specialistsList, String doctors) {
        Header.addChank("Doctors");
        messageDoctor.add("Doctors");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<specialistsList.size();i++) {
            int k=i+1;
            Header.addTable("Doctors " +k + " :");
            Header.addTable("");
            messageDoctor.add("Doctors " +k + " :");
            messageDoctor.add("");

            Specialist s = specialistsList.get(i);

            String speciality = "";
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

    public Specialty(String hospital, ArrayList<Hospital> hospitalList) {

        Header.addChank("Hospitals And Other Health Professionals");
        messageHospital.add("Hospitals And Other Health Professionals");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<hospitalList.size();i++) {
            int k=i+1;
            Header.addTable("Hospitals And Other Health Professionals " +k + " :");
            Header.addTable("");
            messageHospital.add("Hospitals And Other Health Professionals " +k + " :");
            messageHospital.add("");

            Hospital h = hospitalList.get(i);

            String category = "";
            if (h.getCategory() != null) {
                category = h.getCategory();
            }
            Header.addTable("Category :");
            Header.addTable(category);
            messageHospital.add("Category :");
            messageHospital.add(category);

            String name = "";
            if (h.getName() != null) {
                name = h.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageHospital.add("Name :");
            messageHospital.add(name);

            String officePhone = "";
            if (h.getOfficePhone() != null) {
                officePhone = h.getOfficePhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageHospital.add("Office Phone :");
            messageHospital.add(officePhone);

            String mobile = "";
            if (h.getHourPhone() != null) {
                mobile = h.getHourPhone();
            }
            Header.addTable("Mobile Phone :");
            Header.addTable(mobile);
            messageHospital.add("Mobile Phone :");
            messageHospital.add(mobile);

            String otherPhone = "";
            if (h.getOtherPhone() != null) {
                otherPhone = h.getOtherPhone();
            }
            Header.addTable("Other Phone :");
            Header.addTable(otherPhone);
            messageHospital.add("Other Phone :");
            messageHospital.add(otherPhone);

            String officeFax = "";
            if (h.getFax() != null) {
                officeFax = h.getFax();
            }
            Header.addTable("Office Fax :");
            Header.addTable(officeFax);
            messageHospital.add("Office Fax :");
            messageHospital.add(officeFax);


            String address="";
            if (h.getAddress() != null) {
                address = h.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageHospital.add("Address :");
            messageHospital.add(address);

            String website="";
            if (h.getWebsite() != null) {
                website = h.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messageHospital.add("Website :");
            messageHospital.add(website);

            String companyName="";
            if (h.getPracticeName() != null) {
                companyName = h.getPracticeName();
            }
            Header.addTable("Company Name :");
            Header.addTable(companyName);
            messageHospital.add("Company Name :");
            messageHospital.add(companyName);

            String lastSeen="";
            if (h.getLastseen() != null) {
                lastSeen = h.getLastseen();
            }
            Header.addTable("Last Seen :");
            Header.addTable(lastSeen);
            messageHospital.add("Last Seen :");
            messageHospital.add(lastSeen);

            String note="";
            if (h.getNote() != null) {
                note = h.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messageHospital.add("Notes :");
            messageHospital.add(note);

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

    public Specialty(ArrayList<Pharmacy> pharmacyList) {
        Header.addChank("Pharmacies And Home Medical Equipment");
        messagePharmacy.add("Pharmacies And Home Medical Equipment");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i=0;i<pharmacyList.size();i++) {
            int k=i+1;
            Header.addTable("Pharmacies And Home Medical Equipment " +k + " :");
            Header.addTable("");
            messagePharmacy.add("Pharmacies And Home Medical Equipment " +k + " :");
            messagePharmacy.add("");

            Pharmacy p = pharmacyList.get(i);

            String name = "";
            if (p.getName() != null) {
                name = p.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messagePharmacy.add("Name :");
            messagePharmacy.add(name);

            String address="";
            if (p.getAddress() != null) {
                address = p.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messagePharmacy.add("Address :");
            messagePharmacy.add(address);

            String phone = "";
            if (p.getPhone() != null) {
                phone = p.getPhone();
            }
            Header.addTable("Phone :");
            Header.addTable(phone);
            messagePharmacy.add("Phone :");
            messagePharmacy.add(phone);

            String Fax = "";
            if (p.getFax() != null) {
                Fax = p.getFax();
            }
            Header.addTable("Fax :");
            Header.addTable(Fax);
            messagePharmacy.add("Fax :");
            messagePharmacy.add(Fax);


            String website="";
            if (p.getWebsite() != null) {
                website = p.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messagePharmacy.add("Website :");
            messagePharmacy.add(website);

            String note="";
            if (p.getNote() != null) {
                note = p.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messagePharmacy.add("Notes :");
            messagePharmacy.add(note);

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


    public Specialty(ArrayList<Aides> aidesList, int i) {
        Header.addChank("Home Health Services");
        messageAides.add("Home Health Services");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for ( i=0;i<aidesList.size();i++) {
            int k=i+1;
            Header.addTable("Home Health Services" +k + " :");
            Header.addTable("");
            messageAides.add("Home Health Services" +k + " :");
            messageAides.add("");

            Aides a = aidesList.get(i);

            String name = "";
            if (a.getAidName() != null) {
                name = a.getAidName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageAides.add("Name :");
            messageAides.add(name);

            String officePhone = "";
            if (a.getOfficePhone() != null) {
                officePhone = a.getOfficePhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageAides.add("Office Phone :");
            messageAides.add(officePhone);

            String afterHoursPhone = "";
            if (a.getHourPhone() != null) {
                afterHoursPhone = a.getHourPhone();
            }
            Header.addTable("After Hours Phone :");
            Header.addTable(afterHoursPhone);
            messageAides.add("After Hours Phone :");
            messageAides.add(afterHoursPhone);

            String otherPhone = "";
            if (a.getOtherPhone() != null) {
                otherPhone = a.getOtherPhone();
            }
            Header.addTable("Other Phone :");
            Header.addTable(otherPhone);
            messageAides.add("Other Phone :");
            messageAides.add(otherPhone);

            String officeFax = "";
            if (a.getFax() != null) {
                officeFax = a.getFax();
            }
            Header.addTable("Office Fax :");
            Header.addTable(officeFax);
            messageAides.add("Office Fax :");
            messageAides.add(officeFax);

            String email = "";
            if (a.getEmail() != null) {
                email = a.getEmail();
            }
            Header.addTable("Email :");
            Header.addTable(email);
            messageAides.add("Email :");
            messageAides.add(email);


            String address="";
            if (a.getAddress() != null) {
                address = a.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageAides.add("Address :");
            messageAides.add(address);

            String website="";
            if (a.getWebsite() != null) {
                website = a.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messageAides.add("Website :");
            messageAides.add(website);

            String note="";
            if (a.getNote() != null) {
                note = a.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messageAides.add("Notes :");
            messageAides.add(note);

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

    public Specialty(int i, ArrayList<Finance> financeList) {
        Header.addChank("Finance,Insurance,Legal");
        messageFinance.add("Finance,Insurance,Legal");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (i=0;i<financeList.size();i++) {
            int k=i+1;
            Header.addTable("Finance,Insurance,Legal " +k + " :");
            Header.addTable("");
            messageFinance.add("Finance,Insurance,Legal " +k + " :");
            messageFinance.add("");

            Finance f = financeList.get(i);

            String category = "";
            if (f.getCategory() != null) {
                category = f.getCategory();
            }
            Header.addTable("Category :");
            Header.addTable(category);
            messageFinance.add("Category :");
            messageFinance.add(category);

            String name = "";
            if (f.getName() != null) {
                name = f.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageFinance.add("Name :");
            messageFinance.add(name);

            String email = "";
            if (f.getEmail() != null) {
                email = f.getEmail();
            }
            Header.addTable("Email :");
            Header.addTable(email);
            messageFinance.add("Email :");
            messageFinance.add(email);


            String officePhone = "";
            if (f.getOfficePhone() != null) {
                officePhone = f.getOfficePhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageFinance.add("Office Phone :");
            messageFinance.add(officePhone);

            String mobile = "";
            if (f.getHourPhone() != null) {
                mobile = f.getHourPhone();
            }
            Header.addTable("Mobile Phone :");
            Header.addTable(mobile);
            messageFinance.add("Mobile Phone :");
            messageFinance.add(mobile);

            String otherPhone = "";
            if (f.getOtherPhone() != null) {
                otherPhone = f.getOtherPhone();
            }
            Header.addTable("Other Phone :");
            Header.addTable(otherPhone);
            messageFinance.add("Other Phone :");
            messageFinance.add(otherPhone);

            String location = "";
            if (f.getLocation() != null) {
                location = f.getLocation();
            }
            Header.addTable("Location :");
            Header.addTable(location);
            messageFinance.add("Location :");
            messageFinance.add(location);

            String officeFax = "";
            if (f.getFax() != null) {
                officeFax = f.getFax();
            }
            Header.addTable("Office Fax :");
            Header.addTable(officeFax);
            messageFinance.add("Office Fax :");
            messageFinance.add(officeFax);


            String address="";
            if (f.getAddress() != null) {
                address = f.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageFinance.add("Address :");
            messageFinance.add(address);

            String website="";
            if (f.getWebsite() != null) {
                website = f.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messageFinance.add("Website :");
            messageFinance.add(website);

            String companyName="";
            if (f.getPracticeName() != null) {
                companyName = f.getFirm();
            }
            Header.addTable("Company Name :");
            Header.addTable(companyName);
            messageFinance.add("Company Name :");
            messageFinance.add(companyName);

            String lastSeen="";
            if (f.getLastseen() != null) {
                lastSeen = f.getLastseen();
            }
            Header.addTable("Last Seen :");
            Header.addTable(lastSeen);
            messageFinance.add("Last Seen :");
            messageFinance.add(lastSeen);

            String note="";
            if (f.getNote() != null) {
                note = f.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messageFinance.add("Notes :");
            messageFinance.add(note);

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
