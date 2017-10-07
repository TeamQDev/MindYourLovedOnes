package com.mindyourelders.MyHealthCareWishes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by varsha on 7/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
     static final String DATABASE_NAME = "MHCW";
     static  final int DATABASE_VERSION = 1;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Contact Table
        String createContactTableQuery=ContactTableQuery.createContactTable();
        db.execSQL(createContactTableQuery);

        //PersonalInfoQuery Table
        db.execSQL(PersonalInfoQuery.createPersonalInfoTable());

        //MyConnectionsQuery Table
        db.execSQL(MyConnectionsQuery.createMyConnectionsTable());

        //MedInfoQuery Table
        db.execSQL(MedInfoQuery.createMedInfoTable());

        //Allergy Table
        db.execSQL(AllergyQuery.createAllergyTable());

        //Implants Table
        db.execSQL(MedicalImplantsQuery.createImplantsTable());

        //Hsopital Table
        db.execSQL(HospitalQuery.createHospitalTable());

        //History Table
        db.execSQL(HistoryQuery.createHistoryTable());

        //Doctor Table
        db.execSQL(SpecialistQuery.createDoctorTable());

        //Event Note Table
        db.execSQL(EventNoteQuery.createNoteTable());

        //Prescription Table
        db.execSQL(PrescribeImageQuery.createImageTable());
        db.execSQL(DosageQuery.createDosageTable());
        db.execSQL(PrescriptionQuery.createPrescriptionTable());
        db.execSQL(PharmacyQuery.createPharmacyTable());
        db.execSQL(AideQuery.createAideTable());
        db.execSQL(FinanceQuery.createFinanceTable());
        db.execSQL(InsuranceQuery.createInsuranceTable());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Contact Table
        db.execSQL(ContactTableQuery.dropTable());

        //PersonalInfoQuery Table
        db.execSQL(PersonalInfoQuery.dropTable());

        //MyConnectionsQuery Table
        db.execSQL(MyConnectionsQuery.dropTable());

        //MedInfoQuery Table
        db.execSQL(MedInfoQuery.dropTable());

        //Allergy Table
        db.execSQL(AllergyQuery.dropTable());

        //Implants Table
        db.execSQL(MedicalImplantsQuery.dropTable());

        //Hospital Table
        db.execSQL(HospitalQuery.dropTable());

        //History Table
        db.execSQL(HistoryQuery.dropTable());

        //Doctor Table
        db.execSQL(SpecialistQuery.dropTable());

        //Event Note
        db.execSQL(EventNoteQuery.dropTable());

        //Prescription
        db.execSQL(PrescribeImageQuery.dropTable());
        db.execSQL(PrescriptionQuery.dropTable());
        db.execSQL(DosageQuery.dropTable());
        db.execSQL(PharmacyQuery.dropTable());
        db.execSQL(AideQuery.dropTable());
        db.execSQL(FinanceQuery.dropTable());
        db.execSQL(InsuranceQuery.dropTable());

        onCreate(db);
    }
}
