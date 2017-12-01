package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.LivingQuery;
import com.mindyourelders.MyHealthCareWishes.model.Living;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Individual;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 11/24/2017.
 */

public class FragmentLiving extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    View rootview;
    RelativeLayout rlLiving;
    ImageView imgBack, imgDone,imgRight;
    Preferences preferences;
    TextView txtTitle, txtName;
    DBHelper dbHelper;
    ImageView imgInfoF, imgInfoI;
    EditText etOtherFunction, etFunctionalNote, etOtherInstrument, etInstrumentalNote;

    ToggleButton tbFinances, tbPreparing, tbShopping, tbUsing, tbBathing, tbContinence, tbDressing, tbfeed, tbToileting, tbTranfering, tbTransport, tbPets, tbDriving, tbKeeping, tbMedication;
    String finance = "No", prepare = "No", shop = "No", use = "No", bath = "No", continence = "No", dress = "No", feed = "No", toileting = "No", transfer = "No", transport = "No", pets = "No", drive = "No", keep = "No", medication = "No";
    String functionnote = "", fouctionOther = "", instaOther = "", instaNote = "";
    final CharSequence[] dialog_items = {"View","Email","Fax"};
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
        imgRight.setOnClickListener(this);
        tbFinances.setOnCheckedChangeListener(this);
        tbPreparing.setOnCheckedChangeListener(this);
        tbShopping.setOnCheckedChangeListener(this);
        tbUsing.setOnCheckedChangeListener(this);
        tbBathing.setOnCheckedChangeListener(this);
        tbContinence.setOnCheckedChangeListener(this);
        tbDressing.setOnCheckedChangeListener(this);
        tbfeed.setOnCheckedChangeListener(this);
        tbToileting.setOnCheckedChangeListener(this);
        tbTranfering.setOnCheckedChangeListener(this);
        tbTransport.setOnCheckedChangeListener(this);
        tbPets.setOnCheckedChangeListener(this);
        tbDriving.setOnCheckedChangeListener(this);
        tbKeeping.setOnCheckedChangeListener(this);
        tbMedication.setOnCheckedChangeListener(this);
    }

    private void initUI() {
        rlLiving= (RelativeLayout) rootview.findViewById(R.id.rlLiving);
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("ACTIVITIES OF DAILY\nLIVING");

        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgRight = (ImageView) getActivity().findViewById(R.id.imgRight);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);

        imgInfoF = (ImageView) rootview.findViewById(R.id.imgInfoF);
        imgInfoI = (ImageView) rootview.findViewById(R.id.imgInfoI);

        etOtherFunction = (EditText) rootview.findViewById(R.id.etOtherFunction);
        etFunctionalNote = (EditText) rootview.findViewById(R.id.etFunctionalNote);
        etOtherInstrument = (EditText) rootview.findViewById(R.id.etOtherInstrument);
        etInstrumentalNote = (EditText) rootview.findViewById(R.id.etInstrumentalNote);

        tbFinances = (ToggleButton) rootview.findViewById(R.id.tbFinances);
        tbPreparing = (ToggleButton) rootview.findViewById(R.id.tbPreparing);
        tbShopping = (ToggleButton) rootview.findViewById(R.id.tbShopping);
        tbUsing = (ToggleButton) rootview.findViewById(R.id.tbUsing);
        tbBathing = (ToggleButton) rootview.findViewById(R.id.tbBathing);
        tbContinence = (ToggleButton) rootview.findViewById(R.id.tbContinence);
        tbDressing = (ToggleButton) rootview.findViewById(R.id.tbDressing);
        tbfeed = (ToggleButton) rootview.findViewById(R.id.tbfeed);
        tbToileting = (ToggleButton) rootview.findViewById(R.id.tbToileting);
        tbTranfering = (ToggleButton) rootview.findViewById(R.id.tbTranfering);
        tbTransport = (ToggleButton) rootview.findViewById(R.id.tbTransport);
        tbPets = (ToggleButton) rootview.findViewById(R.id.tbPets);
        tbDriving = (ToggleButton) rootview.findViewById(R.id.tbDriving);
        tbKeeping = (ToggleButton) rootview.findViewById(R.id.tbKeeping);
        tbMedication = (ToggleButton) rootview.findViewById(R.id.tbMedication);

        rlLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        setLivingInfo();
    }

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setLivingInfo() {
        Living medInfo = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (medInfo != null) {
            etFunctionalNote.setText(medInfo.getFunctionNote());
            etOtherFunction.setText(medInfo.getFunctionOther());
            etInstrumentalNote.setText(medInfo.getInstNote());
            etOtherInstrument.setText(medInfo.getInstOther());


            if (medInfo.getFinance().equals("Yes")) {
                tbFinances.setChecked(true);
                finance = "Yes";
            } else if (medInfo.getFinance().equals("No")) {
                tbFinances.setChecked(false);
                finance = "No";
            }

            if (medInfo.getPrepare().equals("Yes")) {
                tbPreparing.setChecked(true);
                prepare = "Yes";
            } else if (medInfo.getPrepare().equals("No")) {
                tbPreparing.setChecked(false);
                prepare = "No";
            }

            if (medInfo.getShop().equals("Yes")) {
                tbShopping.setChecked(true);
                shop = "Yes";
            } else if (medInfo.getShop().equals("No")) {
                tbShopping.setChecked(false);
                shop = "No";
            }

            if (medInfo.getUse().equals("Yes")) {
                tbUsing.setChecked(true);
                use = "Yes";
            } else if (medInfo.getUse().equals("No")) {
                tbUsing.setChecked(false);
                use = "No";
            }
            if (medInfo.getBath().equals("Yes")) {
                tbBathing.setChecked(true);
                bath = "Yes";
            } else if (medInfo.getBath().equals("No")) {
                tbBathing.setChecked(false);
                bath = "No";
            }
            if (medInfo.getContinence().equals("Yes")) {
                tbContinence.setChecked(true);
                continence = "Yes";
            } else if (medInfo.getContinence().equals("No")) {
                tbContinence.setChecked(false);
                continence = "No";
            }

            if (medInfo.getDress().equals("Yes")) {
                tbDressing.setChecked(true);
                dress = "Yes";
            } else if (medInfo.getDress().equals("No")) {
                tbDressing.setChecked(false);
                dress = "No";
            }

            if (medInfo.getFeed().equals("Yes")) {
                tbfeed.setChecked(true);
                feed = "Yes";
            } else if (medInfo.getFeed().equals("No")) {
                tbfeed.setChecked(false);
                feed = "No";
            }

            if (medInfo.getToileting().equals("Yes")) {
                tbToileting.setChecked(true);
                toileting = "Yes";
            } else if (medInfo.getToileting().equals("No")) {
                tbToileting.setChecked(false);
                toileting = "No";
            }

            if (medInfo.getTransfer().equals("Yes")) {
                tbTranfering.setChecked(true);
                transfer = "Yes";
            } else if (medInfo.getTransfer().equals("No")) {
                tbTranfering.setChecked(false);
                transfer = "No";
            }

            if (medInfo.getTransport().equals("Yes")) {
                tbTransport.setChecked(true);
                transport = "Yes";
            } else if (medInfo.getTransport().equals("No")) {
                tbTransport.setChecked(false);
                transport = "No";
            }

            if (medInfo.getPets().equals("Yes")) {
                tbPets.setChecked(true);
                pets = "Yes";
            } else if (medInfo.getPets().equals("No")) {
                tbPets.setChecked(false);
                pets = "No";
            }

            if (medInfo.getDrive().equals("Yes")) {
                tbDriving.setChecked(true);
                drive = "Yes";
            } else if (medInfo.getDrive().equals("No")) {
                tbDriving.setChecked(false);
                drive = "No";
            }

            if (medInfo.getKeep().equals("Yes")) {
                tbKeeping.setChecked(true);
                keep = "Yes";
            } else if (medInfo.getKeep().equals("No")) {
                tbKeeping.setChecked(false);
                keep = "No";
            }

            if (medInfo.getMedication().equals("Yes")) {
                tbMedication.setChecked(true);
                medication = "Yes";
            } else if (medInfo.getMedication().equals("No")) {
                tbMedication.setChecked(false);
                medication = "No";
            }
        }
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
        LivingQuery p = new LivingQuery(getActivity(), dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgInfoF:
                String msg = "<b>Bathing.</b> <i>The ability to clean oneself and perform grooming activities like shaving and brushing teeth.</i>  <br><br>" +
                        "<b>Dressing.</b> <i> The ability to get dressed by oneself without struggling with buttons and zippers.</i><br><br>" +
                        "<b>Eating.</b> <i> The ability to feed oneself.</i><br><br>" +
                        "<b>Transferring.</b> <i> Being able to either walk or move oneself from a bed to a wheelchair and back again.</i><br><br>" +
                        "<b>Toileting.</b> <i> The ability to get on and off the toilet.</i><br><br>" +
                        "<b>Continence.</b> <i> The ability to control one's bladder and bowel functions.</i>";
                String title = "Activities of Daily Living";
                showViewDialog(getActivity(), msg, title);
                break;
            case R.id.imgRight:

                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "ActivityLiving.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addusereNameChank("Activities Of Daily Living");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                /*new Header().createPdfHeader(file.getAbsolutePath(),
                        "Activities Of Daily Living");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                Living Live=LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                ArrayList<Living> LivingList=new ArrayList<Living>();
                LivingList.add(Live);
                new Individual(LivingList,1);

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                + "/ActivityLiving.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getLivingInfo());
                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Activities of Daily Living");
                                break;
                            case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;

                        }
                    }

                });
                builder.create().show();
                break;
            case R.id.imgDone:
                functionnote = etFunctionalNote.getText().toString().trim();
                fouctionOther = etOtherFunction.getText().toString().trim();
                instaOther = etOtherInstrument.getText().toString().trim();
                instaNote = etInstrumentalNote.getText().toString().trim();
                Boolean flag = LivingQuery.insertLivingData(preferences.getInt(PrefConstants.CONNECTED_USERID), finance, prepare, shop, use, bath, continence, dress, feed, toileting, transfer, transport, pets, drive, keep, medication, functionnote, fouctionOther, instaNote, instaOther);
                if (flag == true) {
                    Toast.makeText(getActivity(), "Activity Living Info Saved", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imgBack:
                hideSoftKeyboard();
                getActivity().finish();
                break;
        }
    }

    private void showViewDialog(Context context, String Message, String title) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_living);
        customDialog.setCancelable(false);
        TextView txtNotes = (TextView) customDialog.findViewById(R.id.txtNotes);
        txtNotes.setText(Html.fromHtml(Message));
        TextView txtNoteHeader = (TextView) customDialog.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setText(title);
        TextView btnYes = (TextView) customDialog.findViewById(R.id.btnYes);
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

        switch (buttonView.getId()) {
            case R.id.tbFinances:
                if (isChecked == true)
                    finance = "Yes";
                else
                    finance = "No";
                break;

            case R.id.tbPreparing:
                if (isChecked == true)
                    prepare = "Yes";
                else
                    prepare = "No";
                break;

            case R.id.tbShopping:
                if (isChecked == true)
                    shop = "Yes";
                else
                    shop = "No";
                break;

            case R.id.tbUsing:
                if (isChecked == true)
                    use = "Yes";
                else
                    use = "No";
                break;

            case R.id.tbBathing:
                if (isChecked == true)
                    bath = "Yes";
                else
                    bath = "No";
                break;

            case R.id.tbContinence:
                if (isChecked == true)
                    continence = "Yes";
                else
                    continence = "No";
                break;

            case R.id.tbDressing:
                if (isChecked == true)
                    dress = "Yes";
                else
                    dress = "No";
                break;

            case R.id.tbfeed:
                if (isChecked == true)
                    feed = "Yes";
                else
                    feed = "No";
                break;

            case R.id.tbToileting:
                if (isChecked == true)
                    toileting = "Yes";
                else
                    toileting = "No";
                break;

            case R.id.tbTranfering:
                if (isChecked == true)
                    transfer = "Yes";
                else
                    transfer = "No";
                break;

            case R.id.tbTransport:
                if (isChecked == true)
                    transport = "Yes";
                else
                    transport = "No";
                break;

            case R.id.tbMedication:
                if (isChecked == true)
                    medication = "Yes";
                else
                    medication = "No";
                break;

            case R.id.tbKeeping:
                if (isChecked == true)
                    keep = "Yes";
                else
                    keep = "No";
                break;

            case R.id.tbDriving:
                if (isChecked == true)
                    drive = "Yes";
                else
                    drive = "No";
                break;

            case R.id.tbPets:
                if (isChecked == true)
                    pets = "Yes";
                else
                    pets = "No";
                break;

        }
    }
}
