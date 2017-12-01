package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourelders.MyHealthCareWishes.database.AppointmentQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DateQuery;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.EventPdf;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MedicalAppointActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    SwipeMenuListView lvNote;
    ArrayList<Appoint> noteList=new ArrayList<>();
    ImageView imgBack,imgAdd,imgEdit,imgRight;
    TextView txtView;
    Preferences preferences;
    ArrayList<DateClass> dateList;
    DBHelper dbHelper;
    RelativeLayout header;
    final CharSequence[] dialog_items = {"View","Email","Fax"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_appoint);
        initComponent();
        //getData();
        initUI();
        initListener();
    }

    private void initListener() {
        imgAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        header= (RelativeLayout) findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorFour);
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgAdd= (ImageView) findViewById(R.id.imgAdd);
        imgRight= (ImageView) findViewById(R.id.imgRight);
        //imgEdit= (ImageView) findViewById(R.id.imgEdit);
        lvNote= (SwipeMenuListView) findViewById(R.id.lvNote);

       /* lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Appoint a=noteList.get(position);
                final ArrayList<DateClass> list=new ArrayList<DateClass>();
                 TextView txtDate= (TextView) view.findViewById(R.id.txtDate);
                final TextView txtDateTime= (TextView) view.findViewById(R.id.txtDateTime);
                txtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int id=a.getId();

                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                long selectedMilli = newDate.getTimeInMillis();

                                Date datePickerDate = new Date(selectedMilli);
                                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                                DateClass d=new DateClass();
                                d.setDate(reportDate);
                                list.add(d);

                                ArrayList<DateClass> ds= DateQuery.fetchAllDosageRecord(a.getUserid(),a.getUnique());
                                Boolean flag= DateQuery.insertDosageData(a.getUserid(),list,a.getUnique());

                                if (flag==true)
                                {
                                    Toast.makeText(context,"You have inserted date successfully",Toast.LENGTH_SHORT).show();
                                    getData();
                                    setNoteData();
                                }
                                else{
                                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                                }
                               *//* txtDateTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                noteList.get(position).setDate(dayOfMonth + "/" + (month + 1) + "/" + year);*//*
                            }
                        }, year, month, day);
                        dpd.show();
                    }
                });

            }
        });
*/

        txtView= (TextView) findViewById(R.id.txtView);
        if (noteList.size()!=0) {
            setNoteData();
        }
        lvNote.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createSingleMenu(context);
        lvNote.setMenuCreator(creator);
        lvNote.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Appoint item = noteList.get(position);
                switch (index) {

                    case 0:
                        // delete
                        deleteNote(item);
                        break;
                }
                return false;
            }
        });
    }
    public static String getFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        switch (day % 10) {
            case 1:
                return new SimpleDateFormat("MMM d'st', yyyy").format(date);
            case 2:
                return new SimpleDateFormat("MMM d'nd', yyyy").format(date);
            case 3:
                return new SimpleDateFormat("MMM d'rd', yyyy").format(date);
            default:
                return new SimpleDateFormat("MMM d'th', yyyy").format(date);
        }
    }
    private void deleteNote(Appoint item) {
        boolean flag= AppointmentQuery.deleteRecord(item.getUnique());
        if(flag==true)
        {
            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setNoteData();
        }
    }

    private void setNoteData() {
        if (noteList.size()!=0)
        {
            lvNote.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        }else{
            txtView.setVisibility(View.VISIBLE);
            lvNote.setVisibility(View.GONE);
        }
        AppointAdapter adapter = new AppointAdapter(context,noteList);
        lvNote.setAdapter(adapter);
    }

    private void getData() {
       noteList= AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
     //   noteList=new ArrayList<>();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        AppointmentQuery a=new AppointmentQuery(context,dbHelper);
        DateQuery d=new DateQuery(context,dbHelper);
  //      EventNoteQuery e=new EventNoteQuery(context,dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAdd:
            Intent i=new Intent(context,AddAppointmentActivity.class);
                i.putExtra("FROM","Add");
                startActivity(i);
                break;

            case R.id.imgRight:

                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Appointment.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addusereNameChank("Appointment Checklist");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);

               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Medical Appointment");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/
                ArrayList<Appoint> AppointList= AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
              //  ArrayList<Note> NoteList= EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
               // new EventPdf(NoteList,1);
                new EventPdf(AppointList);

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                + "/Appointment.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getAppointInfo());
                                new PDFDocumentProcess(path,
                                        context, result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,context,"Appointment Checklist");
                                break;
                            case 2://fax
                                new FaxCustomDialog(context, path).show();
                                break;

                        }
                    }

                });
                builder.create().show();
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==100&& data !=null)
//        {
//          //  Appoint appoint= (Appoint) data.getExtras().getSerializable("AppointObject");
//           // noteList.add(appoint);
//            getData();
//            setNoteData();
//        }
//    }

    /*private void showInputDialog(final Context context) {
            final Dialog customDialog;

            // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            //  View customView = inflater.inflate(R.layout.dialog_input, null);
            // Build the dialog
            customDialog = new Dialog(context);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.dialog_input);
            customDialog.setCancelable(false);
            final EditText etNote= (EditText) customDialog.findViewById(R.id.etNote);
            TextView btnAdd = (TextView) customDialog.findViewById(R.id.btnYes);
            TextView btnCancel = (TextView) customDialog.findViewById(R.id.btnNo);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String note=etNote.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String currentDateandTime = sdf.format(new Date());
                    if (note.length()!=0) {
                        Boolean flag = EventNoteQuery.insertNoteData(preferences.getInt(PrefConstants.CONNECTED_USERID),note,currentDateandTime);
                        if (flag == true) {
                            Toast.makeText(context, "Event Note Added Succesfully", Toast.LENGTH_SHORT).show();
                            getData();
                            setNoteData();
                            customDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                        *//*Note notes=new Note();
                    notes.setTxtDate(currentDateandTime);
                    notes.setTxtNote(note);
                    noteList.add(notes);
                    customDialog.dismiss();
                    setNoteData();*//*
                }
                else {
                    Toast.makeText(context,"Enter Note",Toast.LENGTH_SHORT).show();
                }

            }
        });

        customDialog.show();
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setNoteData();
    }

    public void SetDate(final Appoint a, final int position) {
        final ArrayList<DateClass> list=new ArrayList<DateClass>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               int id=a.getId();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                long selectedMilli = newDate.getTimeInMillis();

                Date datePickerDate = new Date(selectedMilli);
                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                DateClass d=new DateClass();
                d.setDate(reportDate);
                list.add(d);

                ArrayList<DateClass> ds= DateQuery.fetchAllDosageRecord(a.getUserid(),a.getUnique());
                Boolean flag= DateQuery.insertDosageData(a.getUserid(),list,a.getUnique());

                if (flag==true)
                {
                    Toast.makeText(context,"You have inserted date successfully",Toast.LENGTH_SHORT).show();
                    getData();
                    setNoteData();
                }
                else{
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }

                noteList.get(position).setDate(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        dpd.show();
    }
}
