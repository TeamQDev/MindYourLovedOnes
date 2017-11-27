package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

/**
 * Created by welcome on 11/24/2017.
 */

public class FragmentLiving extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    View rootview;
    ImageView imgBack, imgDone;
    Preferences preferences;
    TextView txtTitle, txtName;
    DBHelper dbHelper;
    ImageView imgInfoF,imgInfoI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_living, null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
        return rootview;
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgInfoF.setOnClickListener(this);
        imgInfoI.setOnClickListener(this);
    }

    private void initUI() {
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("ACTIVITIES OF DAILY LIVING");

        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);

        imgInfoF = (ImageView) rootview.findViewById(R.id.imgInfoF);
        imgInfoI = (ImageView) rootview.findViewById(R.id.imgInfoI);
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgInfoF:
                String msg="<b>Bathing.</b> <i>The ability to clean oneself and perform grooming activities like shaving and brushing teeth.</i>  <br><br>" +
                        "<b>Dressing.</b> <i> The ability to get dressed by oneself without struggling with buttons and zippers.</i><br><br>" +
                        "<b>Eating.</b> <i> The ability to feed oneself.</i><br><br>" +
                        "<b>Transferring.</b> <i> Being able to either walk or move oneself from a bed to a wheelchair and back again.</i><br><br>" +
                        "<b>Toileting.</b> <i> The ability to get on and off the toilet.</i><br><br>" +
                        "<b>Continence.</b> <i> The ability to control one's bladder and bowel functions.</i>";
                String title="Activities of Daily Living";
                showViewDialog(getActivity(),msg,title);
                break;
            case R.id.imgInfoI:

                break;
            case R.id.imgDone:
      /*  ft = etFt.getText().toString().trim();
        inch = etInch.getText().toString().trim();
        weight = etWeight.getText().toString().trim();
        color = spinnerEyes.getSelectedItem().toString();
        lang1 = spinnerLang.getSelectedItem().toString();
        lang2 = etAdditional.getText().toString();
        blood = spinnerBlood.getSelectedItem().toString();
        pet = etPet.getText().toString().trim();
        note = etPreNote.getText().toString().trim();
        mouthnote=etMouthNote.getText().toString().trim();

        visionnote=etVisionNote.getText().toString().trim();
        Aidenote=etAideNote.getText().toString().trim();
        functionnote=etFunctionalNote.getText().toString().trim();
        dietnote=etDietNote.getText().toString().trim();

        Boolean flag = MedInfoQuery.insertMedInfoData(preferences.getInt(PrefConstants.CONNECTED_USERID), ft, inch, weight, color, lang1, lang2, pet, blood, glass, lense, falses, implants, aid, donor,note,mouth,mouthnote,visionnote,Aidenote,functionnote,dietnote,blind,speech,medicate,toilet,feed);
        if (flag == true) {
            Toast.makeText(getActivity(), "Medical Profile Saved", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }*/

                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
        }
    }

    private void showViewDialog(Context context,String Message,String title) {
            final Dialog customDialog;

            // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            //  View customView = inflater.inflate(R.layout.dialog_input, null);
            // Build the dialog
            customDialog = new Dialog(context);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.dialog_living);
            customDialog.setCancelable(false);
            TextView txtNotes= (TextView) customDialog.findViewById(R.id.txtNotes);
            txtNotes.setText(Html.fromHtml(Message));
            TextView txtNoteHeader= (TextView) customDialog.findViewById(R.id.txtNoteHeader);
            txtNoteHeader.setText(title);
            TextView btnYes= (TextView) customDialog.findViewById(R.id.btnYes);
             btnYes.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     customDialog.dismiss();
                 }
             });
        customDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
