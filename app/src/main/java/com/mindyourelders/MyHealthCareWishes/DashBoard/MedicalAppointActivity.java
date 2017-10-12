package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.EventNoteQuery;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;


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
      //  noteList=AppointmentQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        noteList=new ArrayList<>();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
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
                startActivityForResult(i,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&& data !=null)
        {
            Appoint appoint= (Appoint) data.getExtras().getSerializable("AppointObject");
            noteList.add(appoint);
            setNoteData();
        }
    }

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
       // getData();
        setNoteData();
    }
}
