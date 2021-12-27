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


        if(loggedInUser.getGender().equals("Male"))
            userLogoImageView.setImageResource(R.drawable.maleuser);
        else
            userLogoImageView.setImageResource(R.drawable.femaleuser);

        nameTextView.setText(loggedInUser.getFirstName()+ " " + loggedInUser.getLastName());
        emailAddressTextView.setText(loggedInUser.getEmailAddress());
        genderTextView.setText(loggedInUser.getGender());
        locationTextView.setText(loggedInUser.getCity() + ", " + loggedInUser.getCountry());
        phoneNumberTextView.setText(loggedInUser.getPhoneNumber());

        final EditText updateFirstNameEt = new EditText(getActivity());
        final EditText updateLastNameEt = new EditText(getActivity());
        final EditText updateEmailAddressEt = new EditText(getActivity());
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


        updateEmailAddressEt.setHint("Email");
        updateEmailAddressEt.setEnabled(true);
        updateEmailAddressEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateEmailAddressEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateEmailAddressEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);



        final LinearLayout updatePromptLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        updatePromptLayout.setLayoutParams(layoutParams);
        updatePromptLayout.setPadding(20,20,20,20);
        updatePromptLayout.setOrientation(LinearLayout.VERTICAL);
        updatePromptLayout.addView(updateFirstNameEt);
        updatePromptLayout.addView(updateLastNameEt);
        updatePromptLayout.addView(updateEmailAddressEt);

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
                        String updatedFirstName, updatedLastName, updatedEmailAddress;
                        updatedFirstName = updateFirstNameEt.getText().toString().trim();
                        updatedLastName = updateLastNameEt.getText().toString().trim();
                        updatedEmailAddress = updateEmailAddressEt.getText().toString().trim();

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

                        if(!updatedEmailAddress.isEmpty()){
                            if(Validator.checkEmailAddressValidity(updatedEmailAddress)){
                                if(!databaseHelper.userAlreadyExists(updatedEmailAddress)) {
                                    updatedUser.setEmailAddress(updatedEmailAddress);
                                }
                                else {
                                    updateEmailAddressEt.setError(getResources().getString(R.string.error_email_exists));
                                    allInputsValidated = false;
                                }
                            }
                            else {
                                updateEmailAddressEt.setError(getResources().getString(R.string.error_invalid_email));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setEmailAddress(loggedInUser.getEmailAddress());
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
