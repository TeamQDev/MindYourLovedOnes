package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AppointmentQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.EventNoteQuery;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MedicalAppointActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    SwipeMenuListView lvNote;
    ArrayList<Appoint> noteList=new ArrayList<>();
    ImageView imgBack,imgAdd,imgEdit;
    TextView txtView;
    Preferences preferences;
    DBHelper dbHelper;

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
    }

    private void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgAdd= (ImageView) findViewById(R.id.imgAdd);
        //imgEdit= (ImageView) findViewById(R.id.imgEdit);
        lvNote= (SwipeMenuListView) findViewById(R.id.lvNote);

        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
                                int id=noteList.get(position).getId();

                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                long selectedMilli = newDate.getTimeInMillis();

                                Date datePickerDate = new Date(selectedMilli);
                                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                                Boolean flag= AppointmentQuery.updateDate(id,reportDate);
                                if (flag==true)
                                {
                                    Toast.makeText(context,"You have updated date successfully",Toast.LENGTH_SHORT).show();
                                    getData();
                                    setNoteData();

                                }
                                else{
                                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                                }
                               /* txtDateTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                noteList.get(position).setDate(dayOfMonth + "/" + (month + 1) + "/" + year);*/
                            }
                        }, year, month, day);
                        dpd.show();
                    }
                });

            }
        });


        txtView= (TextView) findViewById(R.id.txtView);
        if (noteList.size()!=0) {
            setNoteData();
        }
        lvNote.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(context);
        lvNote.setMenuCreator(creator);
        lvNote.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Appoint item = noteList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
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
        boolean flag= EventNoteQuery.deleteRecord(item.getId());
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
                startActivity(i);
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
}