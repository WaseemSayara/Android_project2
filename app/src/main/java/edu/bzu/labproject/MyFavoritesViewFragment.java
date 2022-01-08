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
public class MyFavoritesViewFragment extends Fragment {


    public MyFavoritesViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.favorites_card_view, container, false);
        final TextView favcityTextView = (TextView) fragView.findViewById(R.id.favcityTextView);
        final TextView favaddressTextView = (TextView) fragView.findViewById(R.id.favpostalAddressTextView);
        final TextView favareaTextView = (TextView) fragView.findViewById(R.id.favsurfaceAreaTextView);
        final TextView favyearTextView = (TextView) fragView.findViewById(R.id.favconstructionYearTextView);
        final TextView favbedroomTextView = (TextView) fragView.findViewById(R.id.favbedroomsTextView);
        final TextView favpriceTextView = (TextView) fragView.findViewById(R.id.favpriceTextView);
        final TextView favstatusTextView = (TextView) fragView.findViewById(R.id.favstatusTextView);
        final TextView favfurnishedTextView = (TextView) fragView.findViewById(R.id.favfurnishedTextView);
        final TextView favdateTextView = (TextView) fragView.findViewById(R.id.favavailabilityDateTextView);
        final TextView favdescriptionTextView = (TextView) fragView.findViewById(R.id.favdescriptionTextView);

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

        favcityTextView.setText(houseCity);
        favaddressTextView.setText(houseAddress);
        favareaTextView.setText(houseArea.toString());
        favyearTextView.setText(houseYear.toString());
        favbedroomTextView.setText(houseBedroom.toString());
        favpriceTextView.setText("$ " + housePrice.toString());
        favstatusTextView.setText(houseStatus);
        favfurnishedTextView.setText(houseFurnished);
        favdateTextView.setText(houseDate);
        favdescriptionTextView.setText(houseDescription);

        final Button removeFavoriteButton = (Button) fragView.findViewById(R.id.favRemoveButton);
        final Button favReserveButton = (Button) fragView.findViewById(R.id.favreserveButton);

        removeFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                Integer houseId = getArguments().getInt("ID");
                databaseHelper.removeFromFavorites(customerId, houseId);
                Snackbar.make(v, "Removed From Favorites", Snackbar.LENGTH_LONG).show();

                //This is to reload the Cars Menu Fragment after successfully adding a car to favorites by customer
                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("My_Favorites");
                fragmentTransaction.detach(houseMenuFragment);
                fragmentTransaction.attach(houseMenuFragment);
                fragmentTransaction.commit();
            }
        });





        favReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        String dateTime = dtf.format(now);
                                        String[] dateTimeArray = dateTime.split(" ");
                                        String date = dateTimeArray[0];
                                        String time = dateTimeArray[1];

                                        Reservation reservation = new Reservation();
                                        reservation.setCustomerId(customerId);
                                        reservation.setHouseId(houseId);
                                        reservation.setDate(date);
                                        reservation.setTime(time);
                                        reservation.setPeriod(period);

                                        databaseHelper.reserveHouseByCustomer(reservation);
                                        Snackbar.make(v, "Reserved Successfully", Snackbar.LENGTH_LONG).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //This is to reload the Cars Menu Fragment After Successful Reservation By Customer
                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("My_Favorites");
                                fragmentTransaction.detach(houseMenuFragment);
                                fragmentTransaction.attach(houseMenuFragment);
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
