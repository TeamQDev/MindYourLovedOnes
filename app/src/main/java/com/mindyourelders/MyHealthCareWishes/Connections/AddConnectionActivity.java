package com.mindyourelders.MyHealthCareWishes.Connections;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.utility.DialogManager;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;


public class AddConnectionActivity extends AppCompatActivity implements View.OnClickListener {
    Preferences preferences;
    Context context=this;
    ImageView imgBack;
    TextView txtGrab,btnShowMore,btnShowLess,btnSon,txtSendRequest,txtEmail;
    LinearLayout llThree,llFour,llFive;
    StaggeredTextGridView gridRelation;
    ArrayList<String> relationArraylist;
    RelationAdapter relationAdapter;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection);
        preferences = new Preferences(context);
        getRelationData();
        initUI();
        initListener();
    }

    private void getRelationData() {
        relationArraylist=new ArrayList<>();
        relationArraylist.add("Mother");
        relationArraylist.add("Father");
        relationArraylist.add("Wife");
        relationArraylist.add("Husband");
        relationArraylist.add("Daughter");
        relationArraylist.add("Son");
        relationArraylist.add("Sister");
        relationArraylist.add("Brother");
        relationArraylist.add("Friend");
        relationArraylist.add("GrandFather");
        relationArraylist.add("GrandMother");
        relationArraylist.add("GrandSon");
        relationArraylist.add("GrandDaughter");
        relationArraylist.add("Other");
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        txtGrab.setOnClickListener(this);
        txtSendRequest.setOnClickListener(this);
       /* btnShowMore.setOnClickListener(this);
        btnShowLess.setOnClickListener(this);*/
    }

    private void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        txtGrab= (TextView) findViewById(R.id.txtGrab);
        txtSendRequest= (TextView) findViewById(R.id.txtSendRequest);
        gridRelation = (StaggeredTextGridView) findViewById(R.id.gridRelation);
        txtEmail= (TextView) findViewById(R.id.txtEmail);
       /* btnShowMore= (TextView) findViewById(R.id.btnShowMore);
        btnShowLess= (TextView) findViewById(R.id.btnShowLess);
        btnSon= (TextView) findViewById(R.id.btnSon);
        llThree= (LinearLayout) findViewById(R.id.llThree);
        llFour= (LinearLayout) findViewById(R.id.llFour);
        llFive =(LinearLayout) findViewById(R.id.llFive);*/
       setRelationData();
    }

    public void setRelationData() {
        relationAdapter=new RelationAdapter(context,relationArraylist);
        gridRelation.setAdapter(relationAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;

            case R.id.txtSendRequest:
                if (validate())
                {

                }
                else {
                    DialogManager dialogManager = new DialogManager();
                    dialogManager.showCommonDialog("Send?", "Do you want to send link request?", context, "SEND_REQUEST", null);
                }
                break;

            case R.id.txtGrab:
                preferences.putString(PrefConstants.SOURCE,"Connection");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                startActivity(i);
                finish();
                break;
           /* case R.id.btnShowMore:
                llThree.setVisibility(View.VISIBLE);
                llFour.setVisibility(View.VISIBLE);
                llFive.setVisibility(View.VISIBLE);
                btnSon.setVisibility(View.VISIBLE);
                btnShowMore.setVisibility(View.GONE);
                btnShowLess.setVisibility(View.VISIBLE);
                break;
            case R.id.btnShowLess:
                llThree.setVisibility(View.GONE);
                llFour.setVisibility(View.GONE);
                llFive.setVisibility(View.GONE);
                btnSon.setVisibility(View.GONE);
                btnShowMore.setVisibility(View.VISIBLE);
                btnShowLess.setVisibility(View.GONE);
                break;*/
        }
    }

    private boolean validate() {
        email = txtEmail.getText().toString().trim();
        if (email.equals("")) {
            txtEmail.setError("Please enter email");
            showAlert("Please enter email", context);
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
        {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email", context);
        }else
        {
            return true;
        }
        return false;
    }


    public void postCommonDialog() {

    }


}
