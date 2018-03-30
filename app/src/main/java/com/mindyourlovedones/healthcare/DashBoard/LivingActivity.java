package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.LivingQuery;
import com.mindyourlovedones.healthcare.model.Living;
import com.mindyourlovedones.healthcare.pdfCreation.EventPdf;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

public class LivingActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Context context=this;
    View rootview;
    RelativeLayout rlLiving;
    ImageView imgBack, imgDone,imgRight,imgInfo;
    Preferences preferences;
    TextView txtTitle, txtName;
    DBHelper dbHelper;
    ImageView imgInfoF, imgInfoI;
    EditText etOtherFunction, etFunctionalNote, etOtherInstrument, etInstrumentalNote;

    ToggleButton tbAlert,tbComputer,tbRemote,tbFinances, tbPreparing, tbShopping, tbUsing, tbBathing, tbContinence, tbDressing, tbfeed, tbToileting, tbTranfering, tbTransport, tbPets, tbDriving, tbKeeping, tbMedication;
    String alert = "No", computer = "No", remote = "No",finance = "No", prepare = "No", shop = "No", use = "No", bath = "No", continence = "No", dress = "No", feed = "No", toileting = "No", transfer = "No", transport = "No", pets = "No", drive = "No", keep = "No", medication = "No";
    String functionnote = "", fouctionOther = "", instaOther = "", instaNote = "";
    final CharSequence[] dialog_items = {"View","Email","Fax","First Time User Instructions"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);
        preferences = new Preferences(context);
        initComponent();
        initUI();
        initListener();
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

        tbRemote.setOnCheckedChangeListener(this);
        tbComputer.setOnCheckedChangeListener(this);
        tbAlert.setOnCheckedChangeListener(this);

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
        imgInfo=findViewById(R.id.imgInfo);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LivingActivity.this,InstructionActivity.class);
                i.putExtra("From","Living");
                startActivity(i);
               /* final Dialog customDialog;
                customDialog = new Dialog(LivingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.dialog_living);
                customDialog.setCancelable(false);
                TextView txtNotes= (TextView) customDialog.findViewById(R.id.txtNotes);
                String msg="To save information click the check mark" +
                        " on the top right side of the screen." +
                        "<br><br>" +
                        "To edit information simply change the data and then save your edits by clicking on the check mark on the top right side of the screen." +
                        "<br><br>" +
                        "To view, email, or fax the data in each section click on the three dots on the top right side of the screen.";

                txtNotes.setText(Html.fromHtml(msg));
                TextView txtNoteHeader= (TextView) customDialog.findViewById(R.id.txtNoteHeader);
                txtNoteHeader.setText("Help");
                TextView btnYes= (TextView) customDialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();*/
            }
        });
        rlLiving= (RelativeLayout) findViewById(R.id.rlLiving);
        txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("ACTIVITIES OF DAILY\nLIVING");

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgRight = (ImageView)findViewById(R.id.imgRight);
        imgDone = (ImageView)findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);

        imgInfoF = (ImageView) findViewById(R.id.imgInfoF);
        imgInfoI = (ImageView) findViewById(R.id.imgInfoI);

        etOtherFunction = (EditText) findViewById(R.id.etOtherFunction);
        etFunctionalNote = (EditText) findViewById(R.id.etFunctionalNote);
        etOtherInstrument = (EditText) findViewById(R.id.etOtherInstrument);
        etInstrumentalNote = (EditText) findViewById(R.id.etInstrumentalNote);

        tbFinances = (ToggleButton) findViewById(R.id.tbFinances);
        tbRemote = (ToggleButton) findViewById(R.id.tbRemote);
        tbComputer = (ToggleButton) findViewById(R.id.tbComputer);
        tbAlert = (ToggleButton) findViewById(R.id.tbAlert);

        tbPreparing = (ToggleButton) findViewById(R.id.tbPreparing);
        tbShopping = (ToggleButton) findViewById(R.id.tbShopping);
        tbUsing = (ToggleButton) findViewById(R.id.tbUsing);
        tbBathing = (ToggleButton) findViewById(R.id.tbBathing);
        tbContinence = (ToggleButton) findViewById(R.id.tbContinence);
        tbDressing = (ToggleButton) findViewById(R.id.tbDressing);
        tbfeed = (ToggleButton) findViewById(R.id.tbfeed);
        tbToileting = (ToggleButton) findViewById(R.id.tbToileting);
        tbTranfering = (ToggleButton) findViewById(R.id.tbTranfering);
        tbTransport = (ToggleButton) findViewById(R.id.tbTransport);
        tbPets = (ToggleButton) findViewById(R.id.tbPets);
        tbDriving = (ToggleButton) findViewById(R.id.tbDriving);
        tbKeeping = (ToggleButton) findViewById(R.id.tbKeeping);
        tbMedication = (ToggleButton) findViewById(R.id.tbMedication);

        rlLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        setLivingInfo();
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

            if (medInfo.getAlert().equals("Yes")) {
                tbAlert.setChecked(true);
                alert = "Yes";
            } else if (medInfo.getAlert().equals("No")) {
                tbAlert.setChecked(false);
                alert = "No";
            }

            if (medInfo.getComputer().equals("Yes")) {
                tbComputer.setChecked(true);
                computer = "Yes";
            } else if (medInfo.getComputer().equals("No")) {
                tbComputer.setChecked(false);
                computer = "No";
            }

            if (medInfo.getRemote().equals("Yes")) {
                tbRemote.setChecked(true);
                remote = "Yes";
            } else if (medInfo.getRemote().equals("No")) {
                tbRemote.setChecked(false);
                remote = "No";
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
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        LivingQuery p = new LivingQuery(context, dbHelper);
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
                showViewDialog(context, msg, title);
                break;
            case R.id.imgRight:

                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "ActivityLiving.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",context);
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Activities Of Daily Living");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);

                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                Paragraph p = new Paragraph(" ");
                LineSeparator line = new LineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                try {
                    Header.document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);
                /*new Header().createPdfHeader(file.getAbsolutePath(),
                        "Activities Of Daily Living");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                Living Live=LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                ArrayList<Living> LivingList=new ArrayList<Living>();
                LivingList.add(Live);
                new EventPdf(1,LivingList,1);

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/ActivityLiving.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getLivingInfo());
                                new PDFDocumentProcess(path,
                                        context, result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,context,"Activities of Daily Living");
                                break;
                            case 2://fax
                                new FaxCustomDialog(context, path).show();
                                break;

                            case 3://fax
                                Intent i=new Intent(context,InstructionActivity.class);
                                i.putExtra("From","Living");
                                startActivity(i);
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
                Boolean flag = LivingQuery.insertLivingData(preferences.getInt(PrefConstants.CONNECTED_USERID), finance, prepare, shop, use, bath, continence, dress, feed, toileting, transfer, transport, pets, drive, keep, medication, functionnote, fouctionOther, instaNote, instaOther,remote,alert,computer);
                if (flag == true) {
                    Toast.makeText(context, "Activity Living Info Saved", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard();
                    finish();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imgBack:
                hideSoftKeyboard();
                finish();
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

            case R.id.tbRemote:
                if (isChecked == true)
                    remote = "Yes";
                else
                    remote = "No";
                break;

            case R.id.tbAlert:
                if (isChecked == true)
                    alert = "Yes";
                else
                    alert = "No";
                break;

            case R.id.tbComputer:
                if (isChecked == true)
                    computer = "Yes";
                else
                    computer = "No";
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
