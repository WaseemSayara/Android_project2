package edu.bzu.labproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import edu.bzu.labproject.Models.AgencyUser;
import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Security.SecurityUtils;

public class LoginActivity extends Activity {
    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button openRegistrationForm;
    private Button loginButton;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressEditText = findViewById(R.id.loginEmailAddress);
        passwordEditText = findViewById(R.id.loginPassword);
        rememberMe = findViewById(R.id.rememberMeCheckbox);

        //Check Shared Preferences for Saved User's Email Address
        final SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
        String savedEmailAddress = sharedPreferences.getString("EmailAddress","NONE");
        String savedPassword = sharedPreferences.getString("Password", "");
        if(!savedEmailAddress.equals("NONE") && ! savedPassword.isEmpty()){
            emailAddressEditText.setText(savedEmailAddress);
            passwordEditText.setText(savedPassword);
        }

        openRegistrationForm = (Button) findViewById(R.id.openRegistrationFormButton);
        openRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toRegistrationActivityIntent = new Intent(LoginActivity.this, RegisterType.class);
                LoginActivity.this.startActivity(toRegistrationActivityIntent);

            }
        });

        Button guestButton = (Button) findViewById(R.id.guestButton);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginSessionManager loginSession = new LoginSessionManager(getApplicationContext());
                loginSession.clearLoginSessionOnLogout();
                loginSession.setUser_Guest(true);
                loginSession.setUser_Type(true);
                Intent toHomePageIntent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(toHomePageIntent);
                LoginActivity.this.finish();
            }});


        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailAddressEditText.getText().toString().trim();
                final DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
                User user = databaseHelper.getUserByEmailAddress(emailAddress);
                if(user != null){
                   //Compare Password to Stored Password Hash to Perform User Login
                    String password = passwordEditText.getText().toString().trim();
                    if(SecurityUtils.verifyPasswordsMatch(password, user.getHashedPassword())){
                        //Check if Remember Me Checkbox is Checked
                        if(rememberMe.isChecked()) {
                            //Save Email Address to Shared Preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("EmailAddress", emailAddress);
                            editor.putString("Password", password);
                            editor.commit();
                        }

                        //Save Logged-In User in a Login Session Using Shared Preferences
                        LoginSessionManager loginSession = new LoginSessionManager(getApplicationContext());
                        loginSession.saveUserLoginSession(user);
                        loginSession.setUser_Type(true);
                        loginSession.setUser_Guest(false);


                        Intent toHomePageIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(toHomePageIntent);
                        LoginActivity.this.finish();
                    }
                    else{
                        passwordEditText.setError(getResources().getString(R.string.error_wrong_password));
                    }
                }
                else {
                    AgencyUser agencyUser = databaseHelper.getAgencyUserByEmailAddress(emailAddress);
                    if (agencyUser != null) {

                        //Compare Password to Stored Password Hash to Perform User Login
                        String password = passwordEditText.getText().toString().trim();
                        if (SecurityUtils.verifyPasswordsMatch(password, agencyUser.getHashedPassword())) {
                            //Check if Remember Me Checkbox is Checked
                            if (rememberMe.isChecked()) {
                                //Save Email Address to Shared Preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("EmailAddress", emailAddress);
                                editor.putString("Password", password);
                                editor.commit();
                            }

                            //Save Logged-In User in a Login Session Using Shared Preferences
                            LoginSessionManager loginSession = new LoginSessionManager(getApplicationContext());
                            loginSession.saveAgencyUserLoginSession(agencyUser);
                            loginSession.setUser_Type(false);
                            loginSession.setUser_Guest(false);

                            Intent toHomePageIntent = new Intent(LoginActivity.this, HomeAgencyActivity.class);
                            LoginActivity.this.startActivity(toHomePageIntent);
                            LoginActivity.this.finish();
                        } else {
                            passwordEditText.setError(getResources().getString(R.string.error_wrong_password));
                        }

                    } else {
                        emailAddressEditText.setError(getResources().getString(R.string.error_email_doesnot_exist));
                    }
                }
            }
        });
    }
}
