package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    RelativeLayout rlLogin;
    TextView txtSignIn, txtNew, txtForgotPassword;
    ImageView imgFbSignup, imgGoogleSignup;
    TextView txtUserName, txtPassword;
    String username, password;
    Preferences preferences;
    TextInputLayout tilUserName;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        initUI();
        initListener();

    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context);
        PersonalInfoQuery p = new PersonalInfoQuery(context, dbHelper);
    }

    private void initListener() {
        txtSignIn.setOnClickListener(this);
        txtNew.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        imgFbSignup.setOnClickListener(this);
        imgGoogleSignup.setOnClickListener(this);
    }

    private void initUI() {
        rlLogin = (RelativeLayout) findViewById(R.id.rlLogin);
        txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        txtNew = (TextView) findViewById(R.id.txtNew);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        imgFbSignup = (ImageView) findViewById(R.id.imgFbSignup);
        imgGoogleSignup = (ImageView) findViewById(R.id.imgGoogleSignup);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        tilUserName = (TextInputLayout) findViewById(R.id.tilUserName);
        tilUserName.setHintEnabled(false);
        txtUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilUserName.setHintEnabled(true);
                txtUserName.setFocusable(true);

                return false;
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtSignIn:
                if (validate()) {
                    ArrayList<PersonalInfo> PersonList = PersonalInfoQuery.fetchOneRecord(username, password);
                    if (PersonList.size() != 0) {
                        for (int i = 0; i < PersonList.size(); i++) {
                            if (username.equals(PersonList.get(i).getEmail()) && password.equals(PersonList.get(i).getPassword())) {
                                Toast.makeText(context, "You have Logged in Successfully", Toast.LENGTH_SHORT).show();
                                preferences.putString(PrefConstants.USER_EMAIL, PersonList.get(i).getEmail());
                                preferences.putString(PrefConstants.USER_NAME, PersonList.get(i).getName());
                                String saveThis = Base64.encodeToString(PersonList.get(i).getPhoto(), Base64.DEFAULT);
                                preferences.putString(PrefConstants.USER_PROFILEIMAGE, saveThis);
                                preferences.putInt(PrefConstants.USER_ID, PersonList.get(i).getId());
                                preferences.setREGISTERED(true);
                                preferences.setLogin(true);
                                Intent signinIntent = new Intent(context, BaseActivity.class);
                                startActivity(signinIntent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(context, "Enter correct Username or Password", Toast.LENGTH_SHORT).show();
                        txtUserName.setText("");
                        txtPassword.setText("");
                    }

                }

               /* Intent signinIntent = new Intent(context, BaseActivity.class);
                startActivity(signinIntent);
                finish();*/
                break;
            case R.id.txtNew:
                Intent signupIntent = new Intent(context, SignUpActivity.class);
                startActivity(signupIntent);
                finish();
                break;
            case R.id.txtForgotPassword:

                break;

            case R.id.imgFbSignup:

                break;

            case R.id.imgGoogleSignup:

                break;
        }
    }

    private boolean validate() {
        username = txtUserName.getText().toString().trim();
        password = txtPassword.getText().toString().trim();

        if (username.equals("") && password.equals("")) {
            txtUserName.setError("Please enter user name and password");
            showAlert("Please enter user name and password", context);
        } else if (username.equals("")) {
            txtUserName.setError("Please enter user name");
            showAlert("Please enter user name", context);
        } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtUserName.setError("Please enter valid email");
            showAlert("Please enter valid email", context);
        } else if (password.trim().equals("")) {
            txtPassword.setError("Please enter password");
            showAlert("Please enter password", context);
        } else return true;

        return false;
    }
    public void hideSoftKeyboard()
    {
        if (getCurrentFocus()!=null)
        {
        InputMethodManager inm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
}
}
