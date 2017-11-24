package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.EventNoteQuery;
import com.mindyourelders.MyHealthCareWishes.model.Note;

public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
 ImageView imgBack,imgEdit,imgDelete;
    EditText etNote;
    TextView txtDate;
    int id,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

      //  getData();
        initUI();
        initListener();
        initComponent();
    }

    private void initComponent() {
        Intent intent=getIntent();
        if (intent.getExtras()!=null) {
            Note note= (Note) intent.getExtras().getSerializable("NoteObject");
            String notes =note.getTxtNote();
            String dates= note.getTxtDate();
            id=note.getId();
            userid=note.getUserid();
            etNote.setText(notes);
            txtDate.setText(dates);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
    }

    private void initUI() {


        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgEdit= (ImageView) findViewById(R.id.imgEdit);
        imgDelete= (ImageView) findViewById(R.id.imgDelete);
        etNote= (EditText) findViewById(R.id.etNote);
        txtDate= (TextView) findViewById(R.id.txtDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgEdit:
                String note=etNote.getText().toString();
                String date=txtDate.getText().toString();
                Boolean flag= EventNoteQuery.updateEvent(id,note,date);
                if (flag==true)
                {
                    Toast.makeText(context,"You have updated event successfully",Toast.LENGTH_SHORT).show();
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    finish();
                }
                else{
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imgDelete:
                boolean flags= EventNoteQuery.deleteRecord(id);
                if(flags==true)
                {
                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }
}
