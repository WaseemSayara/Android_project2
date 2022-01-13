package edu.bzu.labproject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Validation.Validator;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDeatilsViewFragment extends Fragment {

    public SearchDeatilsViewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_house_details_view, container, false);
        final TextView cityTextView = (TextView) fragView.findViewById(R.id.cityTextView);
        final TextView addressTextView = (TextView) fragView.findViewById(R.id.postalAddressTextView);
        final TextView areaTextView = (TextView) fragView.findViewById(R.id.surfaceAreaTextView);
        final TextView yearTextView = (TextView) fragView.findViewById(R.id.constructionYearTextView);
        final TextView bedroomTextView = (TextView) fragView.findViewById(R.id.bedroomsTextView);
        final TextView priceTextView = (TextView) fragView.findViewById(R.id.priceTextView);
        final TextView statusTextView = (TextView) fragView.findViewById(R.id.statusTextView);
        final TextView furnishedTextView = (TextView) fragView.findViewById(R.id.furnishedTextView);
        final TextView dateTextView = (TextView) fragView.findViewById(R.id.availabilityDateTextView);
        final TextView descriptionTextView = (TextView) fragView.findViewById(R.id.descriptionTextView);

        final Button reserveButton = (Button) fragView.findViewById(R.id.reserveButton);
        final Button addToFavoritesButton = (Button) fragView.findViewById(R.id.addToFavButton);

        System.out.println(loginSessionManager.getUser_Type());
        System.out.println(loginSessionManager.getUser_Guest());

        if (!loginSessionManager.getUser_Type()){
            reserveButton.setVisibility(View.INVISIBLE);
            addToFavoritesButton.setVisibility(View.INVISIBLE);
        }

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

        cityTextView.setText(houseCity);
        addressTextView.setText(houseAddress);
        areaTextView.setText(houseArea.toString());
        yearTextView.setText(houseYear.toString());
        bedroomTextView.setText(houseBedroom.toString());
        priceTextView.setText("$ " + housePrice.toString());
        statusTextView.setText(houseStatus);
        furnishedTextView.setText(houseFurnished);
        dateTextView.setText(houseDate);
        descriptionTextView.setText(houseDescription);



        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSessionManager.getUser_Guest()){
                    Intent toHomePageIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(toHomePageIntent);
                    getActivity().finish();
                }
                View reservePopupView = getLayoutInflater().inflate(R.layout.reservation_house_pop_up, null);
                final TextView popcityTextView = (TextView) reservePopupView.findViewById(R.id.popcityTextView);
                final TextView popaddressTextView = (TextView) reservePopupView.findViewById(R.id.poppostalAddressTextView);
                final TextView popareaTextView = (TextView) reservePopupView.findViewById(R.id.popsurfaceAreaTextView);
                final TextView popyearTextView = (TextView) reservePopupView.findViewById(R.id.popconstructionYearTextView);
                final TextView popbedroomTextView = (TextView) reservePopupView.findViewById(R.id.popbedroomsTextView);
                final TextView poppriceTextView = (TextView) reservePopupView.findViewById(R.id.poppriceTextView);
                final TextView popstatusTextView = (TextView) reservePopupView.findViewById(R.id.popstatusTextView);
                final TextView popfurnishedTextView = (TextView) reservePopupView.findViewById(R.id.popfurnishedTextView);
                final TextView popdateTextView = (TextView) reservePopupView.findViewById(R.id.popavailabilityDateTextView);
                final TextView popdescriptionTextView = (TextView) reservePopupView.findViewById(R.id.popdescriptionTextView);

                popcityTextView.setText(houseCity);
                popaddressTextView.setText(houseAddress);
                popareaTextView.setText(houseArea.toString());
                popyearTextView.setText(houseYear.toString());
                popbedroomTextView.setText(houseBedroom.toString());
                poppriceTextView.setText("$ " +housePrice.toString());
                popstatusTextView.setText(houseStatus);
                popfurnishedTextView.setText(houseFurnished);
                popdateTextView.setText(houseDate);
                popdescriptionTextView.setText(houseDescription);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(reservePopupView)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final EditText periodEditText = (EditText)reservePopupView.findViewById(R.id.popPeriodDateEditText);

                                String period = periodEditText.getText().toString().trim();

                                try {
                                    if (!Validator.checkPeriodValidity(period, houseStatus, houseDate)){
                                        Snackbar.make(v, "Reserved Failed", Snackbar.LENGTH_LONG).show();
                                    }
                                    else {

                                        Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                                        Integer houseId = getArguments().getInt("ID");
                                        Integer agencyId = getArguments().getInt("AGENCY_ID");

                                        Reservation reservation = new Reservation();
                                        reservation.setCustomerId(customerId);
                                        reservation.setHouseId(houseId);
                                        reservation.setAgencyId(agencyId);
                                        reservation.setPeriod(period);


                                        databaseHelper.newPendingReserve(reservation);
                                        Snackbar.make(v, "Reserved Successfully", Snackbar.LENGTH_LONG).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //This is to reload the Cars Menu Fragment After Successful Reservation By Customer
                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("Search_Fragment");
                                fragmentTransaction.detach(houseMenuFragment);
                                fragmentTransaction.attach(houseMenuFragment);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                if (!loginSessionManager.getUser_Guest()){
                    alertDialog.show();
                }
            }
        });

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSessionManager.getUser_Guest()){
                    Intent toHomePageIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(toHomePageIntent);
                    getActivity().finish();
                }
                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                Integer houseId = getArguments().getInt("ID");
                databaseHelper.addFavoriteHouseToCustomer(customerId, houseId);
                Snackbar.make(v, "Added to Favorites", Snackbar.LENGTH_LONG).show();

                //This is to reload the Cars Menu Fragment after successfully adding a car to favorites by customer
                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("Search_Fragment");
                fragmentTransaction.detach(houseMenuFragment);
                fragmentTransaction.attach(houseMenuFragment);
                fragmentTransaction.commit();
            }
        });

        return fragView;

    }


}
