package edu.bzu.labproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.HashMap;

import edu.bzu.labproject.Models.AgencyUser;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.SecurityUtils;
import edu.bzu.labproject.Validation.Validator;

public class RegistrationRenting extends AppCompatActivity {

    private boolean allInputsValidated = true;
    private String emailAddress, agencyName, password, confirmPassword, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_renting);


        final Spinner countryAgencySpinner = (Spinner) findViewById(R.id.countryAgencySpinner);
        String[] countryOptions = {"Palestine", "Jordan", "Syria", "Lebanon", "Iraq", "Egypt"};
        ArrayAdapter countryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryOptions);
        countryAgencySpinner.setAdapter(countryArrayAdapter);

        final HashMap<String, String[]> citiesOfCountryMap = new HashMap<>();
        String[] citiesOfPalestine = {"Ramallah", "Nablus", "Jenin", "Bethlehem"};
        String[] citiesOfJordan = {"Amman", "Zarqa", "Jarash"};
        String[] citiesOfSyria = {"Aleppo", "Damascus", "Homs", "Raqqa"};
        String[] citiesOfLebanon = {"Beirut", "Tripoli", "Sidon", "Baalbek"};
        String[] citiesOfIraq = {"Baghdad", "Basra", "Najaf"};
        String[] citiesOfEgypt = {"Cairo", "Alexandria", "Mansoura", "Ismailia"};
        citiesOfCountryMap.put("Palestine", citiesOfPalestine);
        citiesOfCountryMap.put("Jordan", citiesOfJordan);
        citiesOfCountryMap.put("Syria", citiesOfSyria);
        citiesOfCountryMap.put("Lebanon", citiesOfLebanon);
        citiesOfCountryMap.put("Iraq", citiesOfIraq);
        citiesOfCountryMap.put("Egypt", citiesOfEgypt);

        final HashMap<String, String> phoneCodeOfCountryMap = new HashMap<>();
        phoneCodeOfCountryMap.put("Palestine", "+972");
        phoneCodeOfCountryMap.put("Jordan", "+962");
        phoneCodeOfCountryMap.put("Syria", "+963");
        phoneCodeOfCountryMap.put("Lebanon", "+961");
        phoneCodeOfCountryMap.put("Iraq", "+964");
        phoneCodeOfCountryMap.put("Egypt", "+20");

        final Spinner citySpinner = (Spinner) findViewById(R.id.cityAgencySpinner);
        final EditText phoneCode = (EditText) findViewById(R.id.phoneCodeAgencyField);

        countryAgencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cityOptions = citiesOfCountryMap.get(countryAgencySpinner.getSelectedItem());
                ArrayAdapter citiesArrayAdapter = new ArrayAdapter(edu.bzu.labproject.RegistrationRenting.this, android.R.layout.simple_spinner_item, cityOptions);
                citySpinner.setAdapter(citiesArrayAdapter);
                phoneCode.setText(phoneCodeOfCountryMap.get(countryAgencySpinner.getSelectedItem()));
                phoneCode.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText emailAddressInputField = (EditText) findViewById(R.id.emailAddressAgencyInput);
        final EditText agencyNameInputField = (EditText) findViewById(R.id.agencyNameInput);
        final EditText passwordInputField = (EditText) findViewById(R.id.passwordAgencyInput);
        final EditText confirmPasswordInputField = (EditText) findViewById(R.id.confirmPasswordAgencyInput);
        final EditText phoneNumberInputField = (EditText) findViewById(R.id.phoneNumberAgencyInput);

        final DatabaseHelper databaseHelper = new DatabaseHelper(edu.bzu.labproject.RegistrationRenting.this);


        final AlertDialog.Builder registrationConfirmationAlertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AgencyUser agencyUser = new AgencyUser();
                        agencyUser.setEmailAddress(emailAddress);
                        agencyUser.setHashedPassword(SecurityUtils.generate_MD5_Secure_Password(password));
                        agencyUser.setAgencytName(agencyName);
                        agencyUser.setCountry(countryAgencySpinner.getSelectedItem().toString());
                        agencyUser.setCity(citySpinner.getSelectedItem().toString());
                        agencyUser.setPhoneNumber(phoneCode.getText().toString() + " - " + phoneNumber);

                        boolean insertFlag;
                        if ((insertFlag = databaseHelper.addAgencyUser(agencyUser)))
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                        else {
                            Toast.makeText(getApplicationContext(), "ERROR: Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, null);


        final Button registerButton = (Button) findViewById(R.id.registerAgencyButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = emailAddressInputField.getText().toString().trim();
                agencyName = agencyNameInputField.getText().toString().trim();
                password = passwordInputField.getText().toString().trim();
                confirmPassword = confirmPasswordInputField.getText().toString().trim();
                phoneNumber = phoneNumberInputField.getText().toString().trim();
                allInputsValidated = true;

                if (Validator.checkRequiredFieldConstraint(emailAddress)) {
                    if (Validator.checkEmailAddressValidity(emailAddress)) {
                        if (!databaseHelper.userAlreadyExists(emailAddress)) {
                            emailAddressInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_check, 0);
                        } else {
                            emailAddressInputField.setError(getResources().getString(R.string.error_email_exists));
                            allInputsValidated = false;
                        }
                    } else {
                        emailAddressInputField.setError(getResources().getString(R.string.error_invalid_email));
                        allInputsValidated = false;
                    }
                } else {
                    emailAddressInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(agencyName)) {
                    if (Validator.checkAgencyNameValidity(agencyName)) {
                        agencyNameInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, R.drawable.ic_check, 0);
                    } else {
                        agencyNameInputField.setError(getResources().getString(R.string.error_invalid_firstname));
                        allInputsValidated = false;
                    }
                } else {
                    agencyNameInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }


                if (Validator.checkRequiredFieldConstraint(password)) {
                    if (Validator.checkPasswordValidity(password)) {
                        passwordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_check, 0);
                    } else {
                        passwordInputField.setError(getResources().getString(R.string.error_invalid_password));
                        allInputsValidated = false;
                    }
                } else {
                    passwordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(confirmPassword)) {
                    if (Validator.checkIfPasswordsMatch(password, confirmPassword)) {
                        confirmPasswordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_check, 0);
                    } else {
                        confirmPasswordInputField.setError(getResources().getString(R.string.error_passwords_mismatch));
                        allInputsValidated = false;
                    }
                } else {
                    confirmPasswordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(phoneNumber)) {
                    if (Validator.checkPhoneNumberValidity(phoneNumber)) {
                        phoneNumberInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_phone, 0, R.drawable.ic_check, 0);
                    } else {
                        phoneNumberInputField.setError(getResources().getString(R.string.error_invalid_phone));
                        allInputsValidated = false;
                    }
                } else {
                    phoneNumberInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (allInputsValidated) {
                    registrationConfirmationAlertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_validation_errors, Toast.LENGTH_SHORT);
                }
            }
        });

    }


}
