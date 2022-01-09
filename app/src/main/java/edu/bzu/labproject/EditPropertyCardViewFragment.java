package edu.bzu.labproject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Validation.Validator;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPropertyCardViewFragment extends Fragment {


    public EditPropertyCardViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_edit_property_card_view, container, false);
        final TextView propeditcityTextView = (TextView) fragView.findViewById(R.id.propeditcityTextView);
        final TextView propeditaddressTextView = (TextView) fragView.findViewById(R.id.propeditpostalAddressTextView);
        final TextView propeditareaTextView = (TextView) fragView.findViewById(R.id.propeditsurfaceAreaTextView);
        final TextView propedityearTextView = (TextView) fragView.findViewById(R.id.propeditconstructionYearTextView);
        final TextView propeditbedroomTextView = (TextView) fragView.findViewById(R.id.propeditbedroomsTextView);
        final TextView propeditpriceTextView = (TextView) fragView.findViewById(R.id.propeditpriceTextView);
        final TextView propeditstatusTextView = (TextView) fragView.findViewById(R.id.propeditstatusTextView);
        final TextView propeditfurnishedTextView = (TextView) fragView.findViewById(R.id.propeditfurnishedTextView);
        final TextView propeditdateTextView = (TextView) fragView.findViewById(R.id.propeditavailabilityDateTextView);
        final TextView propeditdescriptionTextView = (TextView) fragView.findViewById(R.id.propeditdescriptionTextView);

        String houseCity = String.valueOf(getArguments().getString("CITY"));
        String houseAddress  = getArguments().getString("ADDRESS");
        Integer houseArea = getArguments().getInt("AREA");
        Integer houseYear = getArguments().getInt("YEAR");
        Integer houseBedroom = getArguments().getInt("BEDROOM");
        Integer housePrice = getArguments().getInt("PRICE");
        String houseStatus = getArguments().getBoolean("STATUS") ? "Rented" : "Un Rented";
        String houseFurnished = getArguments().getBoolean("FURNISHED") ? "YES" : "NO";
        String housePhoto = getArguments().getString("PHOTO");
        String houseDate = getArguments().getString("DATE");
        String houseDescription = getArguments().getString("DESCRIPTION");

        propeditcityTextView.setText(houseCity);
        propeditaddressTextView.setText(houseAddress);
        propeditareaTextView.setText(houseArea.toString());
        propedityearTextView.setText(houseYear.toString());
        propeditbedroomTextView.setText(houseBedroom.toString());
        propeditpriceTextView.setText("$ " + housePrice.toString());
        propeditstatusTextView.setText(houseStatus);
        propeditfurnishedTextView.setText(houseFurnished);
        propeditdateTextView.setText(houseDate);
        propeditdescriptionTextView.setText(houseDescription);

        final Button propeditEditButton = (Button) fragView.findViewById(R.id.propeditEditButton);
        final Button propeditRemoveButton = (Button) fragView.findViewById(R.id.propeditRemoveButton);

        propeditRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer houseId = getArguments().getInt("ID");
                databaseHelper.deleteHouseById(houseId);
                Snackbar.make(v, "Removed From propeditorites", Snackbar.LENGTH_LONG).show();

                //This is to reload the Cars Menu Fragment after successfully adding a car to propeditorites by customer
                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("Edit_Property");
                fragmentTransaction.detach(houseMenuFragment);
                fragmentTransaction.attach(houseMenuFragment);
                fragmentTransaction.commit();
            }
        });


        final android.support.v7.app.AlertDialog.Builder registrationConfirmationAlertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }});


        propeditEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View reservePopupView = getLayoutInflater().inflate(R.layout.edit_house_pop_up, null);
                final EditText popbedroomEditText = (EditText) reservePopupView.findViewById(R.id.popbedroomsTextView);
                final EditText poppriceEditText = (EditText) reservePopupView.findViewById(R.id.poppriceTextView);
                final Spinner popstatusEditText = (Spinner) reservePopupView.findViewById(R.id.popStatusspinner);
                final Spinner popfurnishedEditText = (Spinner) reservePopupView.findViewById(R.id.popFurnishedspinner);
                final EditText popdateEditText = (EditText) reservePopupView.findViewById(R.id.popavailabilityDateTextView);
                final EditText popdescriptionEditText = (EditText) reservePopupView.findViewById(R.id.popdescriptionTextView);

                popbedroomEditText.setHint(houseBedroom.toString());
                poppriceEditText.setHint(housePrice.toString());
                popdescriptionEditText.setHint(houseDescription);
                popdateEditText.setHint(houseDate);

                String[] statusOptions = {"Rented", "Un Rented"};
                ArrayAdapter statusArrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, statusOptions);
                popstatusEditText.setAdapter(statusArrayAdapter);

                String[] furnishedOptions = {"Furnished", "Un Furnished"};
                ArrayAdapter furnishedArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, furnishedOptions);
                popfurnishedEditText.setAdapter(furnishedArrayAdapter);

                if (houseStatus.compareTo("Rented") == 0){
                    popstatusEditText.setSelection(0);
                } else {
                    popstatusEditText.setSelection(1);
                }

                if (houseFurnished.compareTo("Furnished") == 0){
                    popfurnishedEditText.setSelection(0);
                } else {
                    popfurnishedEditText.setSelection(1);
                }



                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(reservePopupView)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String Bedroom = popbedroomEditText.getText().toString().trim();
                                String Price = poppriceEditText.getText().toString().trim();
                                String dateEdit = popdateEditText.getText().toString().trim();
                                String description = popdescriptionEditText.getText().toString().trim();
                                String status=popstatusEditText.getSelectedItem().toString();
                                String furnished=popfurnishedEditText.getSelectedItem().toString();
                                boolean valid= true;


                                House newHouse = new House();
                                try {
                                    if (Bedroom.isEmpty()){
                                        newHouse.setBedrooms(houseBedroom);
                                    }
                                    else if (Validator.checkSalaryValidity(Bedroom)){
                                        newHouse.setBedrooms(Integer.parseInt(Bedroom));

                                    }else{
                                        valid= false;

                                    }

                                    if (Price.isEmpty()){
                                        newHouse.setPrice(housePrice);
                                    }
                                    else if (Validator.checkSalaryValidity(Price)){
                                        newHouse.setPrice(Integer.parseInt(Price));

                                    }else{
                                        valid= false;

                                    }

                                    if (dateEdit.isEmpty()){
                                        newHouse.setAvailabilityDate(houseDate);
                                    }
                                    else if (Validator.checkDateValidity(dateEdit)){
                                        newHouse.setAvailabilityDate(dateEdit);

                                    }else{
                                        valid= false;

                                    }

                                    if (description.isEmpty()){
                                        newHouse.setDescription(houseDescription);
                                    }
                                    else if (Validator.checkDescriptionValidity(description)){
                                        newHouse.setDescription(description);
                                    }else{
                                        valid= false;

                                    }
                                    if (valid) {
                                        if (status.compareTo("Rented") == 0){
                                            newHouse.setStatus(true);
                                        } else {
                                            newHouse.setStatus(false);
                                        }

                                        if (furnished.compareTo("Furnished") == 0){
                                            newHouse.setFurnished(true);
                                        } else {
                                            newHouse.setFurnished(false);
                                        }
                                        newHouse.setHouseId(getArguments().getInt("ID"));
                                        databaseHelper.updateHouse(newHouse);
                                        Snackbar.make(v, "Edited Successfully", Snackbar.LENGTH_LONG).show();
                                    }else{
                                        Snackbar.make(v, "Edit Failed ", Snackbar.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //This is to reload the Cars Menu Fragment After Successful Reservation By Customer
                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment editFragment = fragmentManager.findFragmentByTag("Edit_Property");
                                fragmentTransaction.detach(editFragment);
                                fragmentTransaction.attach(editFragment);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                alertDialog.show();
            }
        });

        return fragView;
    }

}
