package edu.bzu.labproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.SecurityUtils;
import edu.bzu.labproject.Validation.Validator;

public class RegistrationActivity extends Activity {
    private boolean allInputsValidated = true;
    private String emailAddress, firstName, lastName, password, confirmPassword, phoneNumber, salary, occupation, familySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter genderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions);
        genderSpinner.setAdapter(genderArrayAdapter);

        final Spinner nationalitySpinner = (Spinner) findViewById(R.id.nationalitySpinner);
        String[] nationalityOptions = {"Palestinian", "Jordanian", "Syrian", "Lebanese", "Iraqi", "Egyptian"};
        ArrayAdapter nationalityArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nationalityOptions);
        nationalitySpinner.setAdapter(nationalityArrayAdapter);
        final Spinner countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        String[] countryOptions = {"Palestine", "Jordan", "Syria", "Lebanon", "Iraq", "Egypt"};
        ArrayAdapter countryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryOptions);
        countrySpinner.setAdapter(countryArrayAdapter);

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

        final Spinner citySpinner = (Spinner) findViewById(R.id.citySpinner);
        final EditText phoneCode = (EditText) findViewById(R.id.phoneCodeField);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cityOptions = citiesOfCountryMap.get(countrySpinner.getSelectedItem());
                ArrayAdapter citiesArrayAdapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_item, cityOptions);
                citySpinner.setAdapter(citiesArrayAdapter);
                phoneCode.setText(phoneCodeOfCountryMap.get(countrySpinner.getSelectedItem()));
                phoneCode.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText emailAddressInputField = (EditText) findViewById(R.id.emailAddressInput);
        final EditText firstNameInputField = (EditText) findViewById(R.id.firstNameInput);
        final EditText lastNameInputField = (EditText) findViewById(R.id.lastNameInput);
        final EditText passwordInputField = (EditText) findViewById(R.id.passwordInput);
        final EditText salaryInputField = (EditText) findViewById(R.id.salaryInput);
        final EditText familySizeInputField = (EditText) findViewById(R.id.familySizeInput);
        final EditText occupationInputField = (EditText) findViewById(R.id.occupationInput);
        final EditText confirmPasswordInputField = (EditText) findViewById(R.id.confirmPasswordInput);
        final EditText phoneNumberInputField = (EditText) findViewById(R.id.phoneNumberInput);

        final DatabaseHelper databaseHelper = new DatabaseHelper(RegistrationActivity.this);


        final AlertDialog.Builder registrationConfirmationAlertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User();
                        user.setEmailAddress(emailAddress);
                        user.setHashedPassword(SecurityUtils.generate_MD5_Secure_Password(password));
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setGender(genderSpinner.getSelectedItem().toString());
                        user.setCountry(countrySpinner.getSelectedItem().toString());
                        user.setCity(citySpinner.getSelectedItem().toString());
                        user.setPhoneNumber(phoneCode.getText().toString() + " - " + phoneNumber);

                        boolean insertFlag;
                        if((insertFlag = databaseHelper.addUser(user)))
                        Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();

                        else {
                            Toast.makeText(getApplicationContext(),"ERROR: Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, null);



        final Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = emailAddressInputField.getText().toString().trim();
                firstName = firstNameInputField.getText().toString().trim();
                lastName = lastNameInputField.getText().toString().trim();
                password = passwordInputField.getText().toString().trim();
                confirmPassword = confirmPasswordInputField.getText().toString().trim();
                salary = salaryInputField.getText().toString().trim();
                occupation = occupationInputField.getText().toString().trim();
                familySize = familySizeInputField.getText().toString().trim();
                lastName = lastNameInputField.getText().toString().trim();
                phoneNumber = phoneNumberInputField.getText().toString().trim();
                allInputsValidated = true;

                if(Validator.checkRequiredFieldConstraint(emailAddress)){
                    if(Validator.checkEmailAddressValidity(emailAddress)){
                        if(!databaseHelper.userAlreadyExists(emailAddress)) {
                            emailAddressInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_check, 0);
                        }
                        else{
                            emailAddressInputField.setError(getResources().getString(R.string.error_email_exists));
                            allInputsValidated = false;
                        }
                    }
                    else {
                        emailAddressInputField.setError(getResources().getString(R.string.error_invalid_email));
                        allInputsValidated = false;
                    }
                }
                else {
                    emailAddressInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(firstName)){
                    if(Validator.checkFirstNameValidity(firstName)){
                        firstNameInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person,0,R.drawable.ic_check,0);
                        }
                    else {
                        firstNameInputField.setError(getResources().getString(R.string.error_invalid_firstname));
                        allInputsValidated = false;
                    }
                }
                else {
                    firstNameInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(lastName)){
                    if(Validator.checkLastNameValidity(lastName)){
                        lastNameInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person,0,R.drawable.ic_check,0);
                    }
                    else {
                        lastNameInputField.setError(getResources().getString(R.string.error_invalid_lastname));
                        allInputsValidated = false;
                    }
                }
                else {
                    lastNameInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(password)) {
                    if (Validator.checkPasswordValidity(password)) {
                        passwordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_check, 0);
                    } else {
                        passwordInputField.setError(getResources().getString(R.string.error_invalid_password));
                        allInputsValidated = false;
                    }
                }
                else {
                    passwordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(confirmPassword)) {
                    if (Validator.checkIfPasswordsMatch(password, confirmPassword)) {
                        confirmPasswordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_check, 0);
                    } else {
                        confirmPasswordInputField.setError(getResources().getString(R.string.error_passwords_mismatch));
                        allInputsValidated = false;
                    }
                }
                else {
                    confirmPasswordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(salary)){
                    if(Validator.checkLastNameValidity(salary)){
                        salaryInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person,0,R.drawable.ic_check,0);
                    }
                    else {
                        salaryInputField.setError(getResources().getString(R.string.error_invalid_salary));
                        allInputsValidated = false;
                    }
                }
                else {
                    salaryInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(familySize)){
                    if(Validator.checkLastNameValidity(familySize)){
                        familySizeInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person,0,R.drawable.ic_check,0);
                    }
                    else {
                        familySizeInputField.setError(getResources().getString(R.string.error_invalid_family));
                        allInputsValidated = false;
                    }
                }
                else {
                    familySizeInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(occupation)){
                    if(Validator.checkLastNameValidity(occupation)){
                        occupationInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person,0,R.drawable.ic_check,0);
                    }
                    else {
                        occupationInputField.setError(getResources().getString(R.string.error_invalid_occupation_length));
                        allInputsValidated = false;
                    }
                }
                else {
                    occupationInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(Validator.checkRequiredFieldConstraint(phoneNumber)) {
                    if (Validator.checkPhoneNumberValidity(phoneNumber)) {
                        phoneNumberInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_phone, 0, R.drawable.ic_check, 0);
                    } else {
                        phoneNumberInputField.setError(getResources().getString(R.string.error_invalid_phone));
                        allInputsValidated = false;
                    }
                }
                else {
                    phoneNumberInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if(allInputsValidated){
                    registrationConfirmationAlertDialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.check_validation_errors, Toast.LENGTH_SHORT);
                }
            }
        });

    }


}
