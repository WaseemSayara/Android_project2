package edu.bzu.labproject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Validation.Validator;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    boolean allInputsValidated = true;
    public UserProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("User Profile");

        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity().getApplicationContext());
        final User loggedInUser = loginSessionManager.getCurrentlyLoggedInUser();

        final ImageView userLogoImageView = (ImageView) getActivity().findViewById(R.id.userLogoImageView);
        final TextView nameTextView = (TextView) getActivity().findViewById(R.id.customerNameTextView);
        final TextView emailAddressTextView = (TextView) getActivity().findViewById(R.id.emailAddressTextView);
        final TextView genderTextView = (TextView) getActivity().findViewById(R.id.genderTextView);
        final TextView locationTextView = (TextView) getActivity().findViewById(R.id.locationTextView);
        final TextView phoneNumberTextView = (TextView) getActivity().findViewById(R.id.phoneNumberTextView);
        final TextView nationalityTextView = (TextView) getActivity().findViewById(R.id.nationalityTextView);
        final TextView familySizeTextView = (TextView) getActivity().findViewById(R.id.familySizeTextView);
        final TextView occupationTextView = (TextView) getActivity().findViewById(R.id.occupationTextView);
        final TextView salaryTextView = (TextView) getActivity().findViewById(R.id.salaryTextView);



        if(loggedInUser.getGender().equals("Male"))
            userLogoImageView.setImageResource(R.drawable.maleuser);
        else
            userLogoImageView.setImageResource(R.drawable.femaleuser);

        nameTextView.setText(loggedInUser.getFirstName()+ " " + loggedInUser.getLastName());
        emailAddressTextView.setText(loggedInUser.getEmailAddress());
        genderTextView.setText(loggedInUser.getGender());
        locationTextView.setText(loggedInUser.getCity() + ", " + loggedInUser.getCountry());
        phoneNumberTextView.setText(loggedInUser.getPhoneNumber());
        nationalityTextView.setText(loggedInUser.getNationality());
        salaryTextView.setText(loggedInUser.getSalary());
        familySizeTextView.setText(loggedInUser.getFamilySize());
        occupationTextView.setText(loggedInUser.getOccupation());

        final EditText updateFirstNameEt = new EditText(getActivity());
        final EditText updateLastNameEt = new EditText(getActivity());
        final EditText updatePhoneEt = new EditText(getActivity());
        final EditText updateSalaryEt = new EditText(getActivity());
        final EditText updateFamilySizeEt = new EditText(getActivity());
        final EditText updateOccupationEt = new EditText(getActivity());

        updateFirstNameEt.setHint("First Name");
        updateFirstNameEt.setEnabled(true);
        updateFirstNameEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateFirstNameEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateFirstNameEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);

        updateLastNameEt.setHint("Last Name");
        updateLastNameEt.setEnabled(true);
        updateLastNameEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateLastNameEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateLastNameEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);


        updatePhoneEt.setHint("Phone");
        updatePhoneEt.setEnabled(true);
        updatePhoneEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updatePhoneEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updatePhoneEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);

        updateSalaryEt.setHint("Salary");
        updateSalaryEt.setEnabled(true);
        updateSalaryEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateSalaryEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateSalaryEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);

        updateFamilySizeEt.setHint("Family Size");
        updateFamilySizeEt.setEnabled(true);
        updateFamilySizeEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateFamilySizeEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateFamilySizeEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);

        updateOccupationEt.setHint("Occupation");
        updateOccupationEt.setEnabled(true);
        updateOccupationEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateOccupationEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateOccupationEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);


        final LinearLayout updatePromptLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        updatePromptLayout.setLayoutParams(layoutParams);
        updatePromptLayout.setPadding(20,20,20,20);
        updatePromptLayout.setOrientation(LinearLayout.VERTICAL);
        updatePromptLayout.addView(updateFirstNameEt);
        updatePromptLayout.addView(updateLastNameEt);
        updatePromptLayout.addView(updatePhoneEt);
        updatePromptLayout.addView(updateOccupationEt);
        updatePromptLayout.addView(updateSalaryEt);
        updatePromptLayout.addView(updateFamilySizeEt);

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        final AlertDialog updateAlertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Update Information")
                .setPositiveButton(R.string.dialog_ok_button, null)
                .setNegativeButton(R.string.dialog_cancel_button, null)
                .setView(updatePromptLayout)
                .create();

        updateAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = ((AlertDialog) updateAlertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User updatedUser = new User();
                        String updatedFirstName, updatedLastName, updatedEmailAddress, updatePhone, updateSalary,
                                updateFamilySize, updateOccupation;
                        updatedFirstName = updateFirstNameEt.getText().toString().trim();
                        updatedLastName = updateLastNameEt.getText().toString().trim();
                        updatePhone = updatePhoneEt.getText().toString().trim();
                        updateSalary = updateSalaryEt.getText().toString().trim();
                        updateFamilySize = updateFamilySizeEt.getText().toString().trim();
                        updateOccupation = updateOccupationEt.getText().toString().trim();

                        if(!updatedFirstName.isEmpty()){
                            if(Validator.checkFirstNameValidity(updatedFirstName)){
                                updatedUser.setFirstName(updatedFirstName);
                            }
                            else {
                                updateFirstNameEt.setError(getResources().getString(R.string.error_invalid_firstname));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setFirstName(loggedInUser.getFirstName());
                        }

                        if(!updatedLastName.isEmpty()){
                            if(Validator.checkFirstNameValidity(updatedLastName)){
                                updatedUser.setLastName(updatedLastName);
                            }
                            else {
                                updateLastNameEt.setError(getResources().getString(R.string.error_invalid_lastname));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setLastName(loggedInUser.getLastName());
                        }

                        if(!updatePhone.isEmpty()){
                            if(Validator.checkPhoneNumberValidity(updatePhone)){
                                updatedUser.setPhoneNumber(updatePhone);
                            }
                            else {
                                updatePhoneEt.setError(getResources().getString(R.string.error_invalid_phone));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setPhoneNumber(loggedInUser.getPhoneNumber());
                        }

                        if(!updateSalary.isEmpty()){
                            if(Validator.checkSalaryValidity(updateSalary)){
                                updatedUser.setSalary(updateSalary);
                            }
                            else {
                                updateSalaryEt.setError(getResources().getString(R.string.error_invalid_salary));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setSalary(loggedInUser.getSalary());
                        }

                        if(!updateFamilySize.isEmpty()){
                            if(Validator.checkFamilySizeValidity(updateFamilySize)){
                                updatedUser.setFamilySize(updateFamilySize);
                            }
                            else {
                                updateFamilySizeEt.setError(getResources().getString(R.string.error_invalid_family));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setFamilySize(loggedInUser.getFamilySize());
                        }

                        if(!updateOccupation.isEmpty()){
                            if(Validator.checkOccupationValidity(updateOccupation)){
                                updatedUser.setOccupation(updateOccupation);
                            }
                            else {
                                updateOccupationEt.setError(getResources().getString(R.string.error_invalid_occupation_length));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setOccupation(loggedInUser.getOccupation());
                        }



                        if(allInputsValidated) {
                            databaseHelper.updateCustomerInformation(updatedUser, loggedInUser.getEmailAddress());
                            loginSessionManager.updateCurrentLoginSession(updatedUser);
                            final TextView headerNameTextView = (TextView) getActivity().findViewById(R.id.headerCustomerFullName);
                            final TextView headerEmailTextView = (TextView) getActivity().findViewById(R.id.headerEmailAddress);
                            headerNameTextView.setText(updatedUser.getFirstName() + " " + updatedUser.getLastName());
                            headerEmailTextView.setText(updatedUser.getEmailAddress());
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                            updateAlertDialog.dismiss();

                        }
                    }
                });
            }
        });


        final FloatingActionButton updateFab = (FloatingActionButton) getActivity().findViewById(R.id.updateFab);
        updateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlertDialog.show();
            }
        });

    }
}
