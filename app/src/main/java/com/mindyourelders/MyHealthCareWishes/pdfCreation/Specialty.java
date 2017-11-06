package com.mindyourelders.MyHealthCareWishes.pdfCreation;

import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;

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

    }

    public Specialty(String hospital, ArrayList<Hospital> hospitalList) {

    }

    public Specialty(ArrayList<Pharmacy> pharmacyList) {

    }


    public Specialty(ArrayList<Aides> aidesList, int i) {
    }

    public Specialty(int i, ArrayList<Finance> financeList) {
    }
}
