package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.CardQuery;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.model.Card;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.pdfdesign.InsurancePdf;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.utility.SwipeMenuCreation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

import static com.mindyourlovedones.healthcare.HomeActivity.R.id.imgBack;

/**
 * Created by welcome on 9/22/2017.
 */

public class FragementInsuarnceCard extends Fragment implements View.OnClickListener {
    Preferences preferences;
    View rootview;
    SwipeMenuListView lvCard;
    ImageView imgRight;
    ArrayList<Card> CardList;
    RelativeLayout llAddCard;
    TextView txtView,txtMsg,txtFTU;
    public static final int REQUEST_PRES = 100;
    DBHelper dbHelper;
    final String dialog_items[]={"View","Email","Fax"};
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlGuide;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_insurance_card, null);
        initComponent();
        initImageLoader();
        try {
            getData();
        } catch (Exception e) {
        }
        initUI();
        initListener();
        setCardData();
        return rootview;
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ins_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());

    }

    private void getData() {
        dbHelper = new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
        CardQuery c = new CardQuery(getActivity(), dbHelper);
        CardList = CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* CardList=new ArrayList<>();

        Card c1=new Card();
        c1.setName("Jackson Montana");
        c1.setType("Supplemental Insurance");
        c1.setImgFront(R.drawable.front);
        c1.setImgBack(R.drawable.back);

        Card c2=new Card();
        c2.setName("James Johnson");
        c2.setType("Medicare");
        c2.setImgFront(R.drawable.fronts);
        c2.setImgBack(R.drawable.backs);

        CardList.add(c1);
        CardList.add(c2);*/
    }

    private void initListener() {
        llAddCard.setOnClickListener(this);
        imgRight.setOnClickListener(this);

    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>get started</b> click the green bar at the bottom of the screen Add Insurance Card." +
                "<br><br>" +
                "To <b>add</b> information type  the Provider name and the Type of Insurance and click the check mark on the top right side of the screen." +
                "<br><br>" +
                "To <b>take a picture</b> of your insurance card (front and back). Click the <b>plus box</b>. It is recommended that you hold your phone horizontal when taking a picture of the card." +
                "<br><br>" +
                "To <b>save</b> your information click the check mark on the top right side of the screen." +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>check mark</b> again." +
                "<br><br>" +
                "To <b>delete</b> the entry swipe right to left the arrow symbol on the right side." +
                "<br><br>" +
                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen.";

        txtMsg.setText(Html.fromHtml(msg));
        txtFTU=rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsg.setVisibility(View.VISIBLE);
            }
        });
        llAddCard = (RelativeLayout) rootview.findViewById(R.id.llAddCard);
        rlGuide=rootview.findViewById(R.id.rlGuide);
        lvCard = (SwipeMenuListView) rootview.findViewById(R.id.lvCard);
        txtView = (TextView) rootview.findViewById(R.id.txtView);
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
    }

    private void setCardData() {
       /* if (CardList.size()!=0)
        {
            lvCard.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        }else{
            txtView.setVisibility(View.VISIBLE);
            lvCard.setVisibility(View.GONE);
        }*/
        if (CardList.size() != 0) {
            rlGuide.setVisibility(View.GONE);
            lvCard.setVisibility(View.VISIBLE);
            CardAdapter adapter = new CardAdapter(getActivity(), CardList);
            lvCard.setAdapter(adapter);

            lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final ImageView imgFront = (ImageView) view.findViewById(R.id.imgFront);
                    final ImageView imgPre = (ImageView) view.findViewById(imgBack);
                    final ImageView imgCard = (ImageView) view.findViewById(R.id.imgCard);
                    ImageView imgForword = (ImageView) view.findViewById(R.id.imgForword);
                    imgFront.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* byte[] photo = CardList.get(position).getImgFront();
                            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                            imgCard.setImageBitmap(bmp);*/
                            File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),CardList.get(position).getImgFront());
                            if (imgFile.exists()) {
                                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgCard,displayImageOptions);
                            /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
                            }
                            else{
                                imgCard.setImageResource(R.drawable.ins_card);
                            }
                            //  imageLoader.displayImage(String.valueOf(CardList.get(position).getImgFront()),imgCard,displayImageOptions);
                            imgPre.setImageResource(R.drawable.white_dot);
                            imgFront.setImageResource(R.drawable.blue_dot);
                        }
                    });
                    imgPre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* byte[] photo1 = CardList.get(position).getImgBack();
                            Bitmap bmp1 = BitmapFactory.decodeByteArray(photo1, 0, photo1.length);
                            imgCard.setImageBitmap(bmp1);*/
                            File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),CardList.get(position).getImgBack());
                            if (imgFile.exists()) {
                                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgCard,displayImageOptions);
                            }else{
                                imgCard.setImageResource(R.drawable.ins_card);
                            }
                            // imageLoader.displayImage(String.valueOf(CardList.get(position).getImgBack()),imgCard,displayImageOptions);

                            imgPre.setImageResource(R.drawable.blue_dot);
                            imgFront.setImageResource(R.drawable.white_dot);
                        }
                    });

                    imgForword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), ViewCardActivity.class);
                            i.putExtra("Card", CardList.get(position));
                            startActivity(i);
                        }
                    });
                }
            });
        } else {
            lvCard.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }


        lvCard.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s = new SwipeMenuCreation();
        SwipeMenuCreator creator = s.createSingleMenu(getActivity());
        lvCard.setMenuCreator(creator);
        lvCard.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Card item = CardList.get(position);
                switch (index) {
                 /*   case 0:
                        // open
                        //  open(item);
                        break;*/
                    case 0:
                        // delete
                        deleteInsuranceCard(item);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteInsuranceCard(final Card item) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = CardQuery.deleteRecord(item.getId(), 1);
                if (flag == true) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getData();
                    setCardData();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddCard:
                // preferences.putString(PrefConstants.SOURCE,"Card");
                Intent i = new Intent(getActivity(), AddCardActivity.class);
                startActivityForResult(i, REQUEST_PRES);
                break;

            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "InsuranceCard.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",getActivity());
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Insurance Card");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Insurance Information");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                // Header.addEmptyLine(2);*/

                ArrayList<Card> CardList= CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new InsurancePdf(CardList,1);

                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/InsuranceCard.pdf";
                        switch (itemPos) {
                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getInsuranceCard());


                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Insurance Card");
                                break;
                            case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;
                        }
                    }

                });
                builder.create().show();
                break;
        }
    }
/*
    protected void onActivityResult(int requ estCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRES && data != null) {

      *//*  Card p=new Card();
        p.setDates(data.getExtras().getString("Date"));
        p.setDoctor(data.getExtras().getString("Name"));
        p.setDosageList((ArrayList<Dosage>)data.getExtras().getSerializable("Dosage"));
        p.setCardImageList((ArrayList<PrescribeImage>) data.getExtras().getSerializable("Image"));*//*

            CardList.add((Card) data.getSerializableExtra("PrObj"));
            setCardData();
        }

    }*/

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setCardData();
    }
}
