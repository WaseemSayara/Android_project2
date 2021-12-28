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

import edu.bzu.labproject.Models.AgencyUser;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Validation.Validator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyUserProfileFragment extends Fragment {

    boolean allInputsValidated = true;
    public AgencyUserProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agency_user_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Agency User Profile");

        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity().getApplicationContext());
        final AgencyUser loggedInUser = loginSessionManager.getCurrentlyLoggedInAgencyUser();

        final ImageView userLogoImageView = (ImageView) getActivity().findViewById(R.id.userLogoImageView);
        final TextView nameTextView = (TextView) getActivity().findViewById(R.id.AgencyNameTextView);
        final TextView emailAddressTextView = (TextView) getActivity().findViewById(R.id.emailAgencyAddressTextView);
        final TextView locationTextView = (TextView) getActivity().findViewById(R.id.locationAgencyTextView);
        final TextView phoneNumberTextView = (TextView) getActivity().findViewById(R.id.phoneNumberAgencyTextView);

        nameTextView.setText(loggedInUser.getAgencyName());
        emailAddressTextView.setText(loggedInUser.getEmailAddress());
        locationTextView.setText(loggedInUser.getCity() + ", " + loggedInUser.getCountry());
        phoneNumberTextView.setText(loggedInUser.getPhoneNumber());

        final EditText updateAgencyNameEt = new EditText(getActivity());
        final EditText updatedPhoneEt = new EditText(getActivity());
        final EditText updateEmailAddressEt = new EditText(getActivity());

        updateAgencyNameEt.setHint("Agency Name");
        updateAgencyNameEt.setEnabled(true);
        updateAgencyNameEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updateAgencyNameEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updateAgencyNameEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);

        updatedPhoneEt.setHint("Phone");
        updatedPhoneEt.setEnabled(true);
        updatedPhoneEt.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        updatedPhoneEt.setHintTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        updatedPhoneEt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);


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
        updatePromptLayout.addView(updateAgencyNameEt);
        updatePromptLayout.addView(updatedPhoneEt);
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
                        AgencyUser updatedUser = new AgencyUser();
                        String updatedAgencyName, updatedPhone, updatedEmailAddress;
                        updatedAgencyName = updateAgencyNameEt.getText().toString().trim();
                        updatedPhone = updatedPhoneEt.getText().toString().trim();
                        updatedEmailAddress = updateEmailAddressEt.getText().toString().trim();


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

                        if(!updatedPhone.isEmpty()){
                            if(Validator.checkPhoneNumberValidity(updatedPhone)){
                                updatedUser.setPhoneNumber(updatedPhone);
                            }
                            else {
                                updatedPhoneEt.setError(getResources().getString(R.string.error_invalid_phone));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setPhoneNumber(loggedInUser.getPhoneNumber());
                        }

                        if(!updatedAgencyName.isEmpty()){
                            if(Validator.checkAgencyNameValidity(updatedAgencyName)){
                                updatedUser.setAgencyName(updatedAgencyName);
                            }
                            else {
                                updateAgencyNameEt.setError(getResources().getString(R.string.error_invalid_phone));
                                allInputsValidated = false;
                            }
                        }
                        else {
                            updatedUser.setAgencyName(loggedInUser.getAgencyName());
                        }

                        if(allInputsValidated) {
                            databaseHelper.updateAgencyInformation(updatedUser, loggedInUser.getEmailAddress());
                            loginSessionManager.updateCurrentAgencyLoginSession(updatedUser);
                            final TextView headerNameTextView = (TextView) getActivity().findViewById(R.id.headerCustomerFullName);
                            final TextView headerEmailTextView = (TextView) getActivity().findViewById(R.id.headerEmailAddress);
                            headerNameTextView.setText(updatedUser.getAgencyName());
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
