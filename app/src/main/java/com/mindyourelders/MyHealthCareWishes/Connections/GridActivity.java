package com.mindyourelders.MyHealthCareWishes.Connections;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {
    Context context=this;
    StaggeredTextGridView gridRelation;
    ArrayList<String> relationArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        getRelationData();
        initUI();
       // initListener();
    }

    private void initUI() {
        gridRelation = (StaggeredTextGridView) findViewById(R.id.gridRelation);
        setRelationData();
    }

    private void setRelationData() {
        RelationAdapter relationAdapter=new RelationAdapter(context,relationArraylist);
        gridRelation.setAdapter(relationAdapter);
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

        relationArraylist.add("GrandFather");
        relationArraylist.add("GrandMother");
        relationArraylist.add("Friend");
        relationArraylist.add("GrandSon");
        relationArraylist.add("GrandDaughter");
        relationArraylist.add("Other");
    }
}
