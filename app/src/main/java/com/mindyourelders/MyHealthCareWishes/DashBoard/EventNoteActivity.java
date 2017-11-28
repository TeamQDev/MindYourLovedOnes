package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.EventNoteQuery;
import com.mindyourelders.MyHealthCareWishes.model.Note;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventNoteActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    SwipeMenuListView lvNote;
    ArrayList<Note> noteList = new ArrayList<>();
    ImageView imgBack, imgAdd, imgEdit;
    TextView txtView;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout header,rlEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_note);
        initComponent();
        getData();
        initUI();
        initListener();
    }

    private void initListener() {
        imgAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        //txtDateTime.setOnClickListener(this);

    }

    private void initUI() {
        rlEvent= (RelativeLayout) findViewById(R.id.rlEvent);
        header = (RelativeLayout) findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorFour);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        //imgEdit= (ImageView) findViewById(R.id.imgEdit);
        lvNote = (SwipeMenuListView) findViewById(R.id.lvNote);
        rlEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               // hideSoftKeyboard();
                TextView txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
                ImageView imgForward = (ImageView) view.findViewById(R.id.imgForword);
                imgForward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ViewEventActivity.class);
                        intent.putExtra("NoteObject", noteList.get(position));
                        startActivity(intent);
                    }
                });
                txtDateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                long selectedMilli = newDate.getTimeInMillis();

                                Date datePickerDate = new Date(selectedMilli);
                                String reportDate = new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                                DateClass d = new DateClass();
                                d.setDate(reportDate);

                                Boolean flag = EventNoteQuery.updateNoteDate(noteList.get(position).getId(), reportDate);

                                if (flag == true) {
                                    Toast.makeText(context, "Event Note Date Updated Succesfully", Toast.LENGTH_SHORT).show();
                                    getData();
                                    setNoteData();
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                                //noteList.get(position).setTxtDate(reportDate);

                            }
                        }, year, month, day);
                        dpd.show();
                    }
                });
            }
        });
        txtView = (TextView) findViewById(R.id.txtView);
        if (noteList.size() != 0) {
            setNoteData();
        }
        lvNote.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s = new SwipeMenuCreation();
        SwipeMenuCreator creator = s.createSingleMenu(context);
        lvNote.setMenuCreator(creator);
        lvNote.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Note item = noteList.get(position);
                switch (index) {
                   /* case 0:
                        // open
                        //  open(item);
                        break;*/
                    case 0:
                        // delete
                        deleteNote(item);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteNote(Note item) {
        boolean flag = EventNoteQuery.deleteRecord(item.getId());
        if (flag == true) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setNoteData();
        }
    }

    private void setNoteData() {
        if (noteList.size() != 0) {
            lvNote.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        } else {
            txtView.setVisibility(View.VISIBLE);
            lvNote.setVisibility(View.GONE);
        }
        NoteAdapter adapter = new NoteAdapter(context, noteList);
        lvNote.setAdapter(adapter);
    }

    private void getData() {
        noteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context);
        EventNoteQuery e = new EventNoteQuery(context, dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                hideSoftKeyboard();
               finish();
                break;
            case R.id.imgAdd:
                showInputDialog(context);
                break;
            /*case R.id.txtDateTime:

                break;*/

        }
    }

    private void showInputDialog(final Context context) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input);
        customDialog.setCancelable(false);
        final EditText etNote = (EditText) customDialog.findViewById(R.id.etNote);
        TextView btnAdd = (TextView) customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = (TextView) customDialog.findViewById(R.id.btnNo);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                hideSoftKeyboard();
            }
        });
      /*  etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                long selectedMilli = newDate.getTimeInMillis();

                                Date datePickerDate = new Date(selectedMilli);
                                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                                DateClass d=new DateClass();
                                d.setDate(reportDate);
                                etDate.setText(reportDate);
                            }
                        }, year, month, day);
                        dpd.show();
                    }
                });
            }
        });*/

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = etNote.getText().toString();
                //   String date=etDate.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
                String currentDateandTime = sdf.format(new Date());
                if (note.length() != 0) {
                    Boolean flag = EventNoteQuery.insertNoteData(preferences.getInt(PrefConstants.CONNECTED_USERID), note, currentDateandTime);
                    if (flag == true) {

                        Toast.makeText(context, "Event Note Added Succesfully", Toast.LENGTH_SHORT).show();
                        getData();
                        setNoteData();
                        hideSoftKeyboard();
                        customDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    /*Note notes=new Note();
                    notes.setTxtDate(currentDateandTime);
                    notes.setTxtNote(note);
                    noteList.add(notes);
                    customDialog.dismiss();
                    setNoteData();*/
                } else {
                    Toast.makeText(context, "Enter Note", Toast.LENGTH_SHORT).show();
                }

            }
        });

        customDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setNoteData();
    }

   /* public static void closeKeyboard(Activity context) {
        try {
            InputMethodManager inm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
//Todo:Handle Exception
        }
    }*/
   public void hideSoftKeyboard() {
       if(getCurrentFocus()!=null) {
           InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
           inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
       }
   }


}
