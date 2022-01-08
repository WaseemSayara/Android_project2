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
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;

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

        removeFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                Integer houseId = getArguments().getInt("ID");
                databaseHelper.removeFromFavorites(customerId, houseId);
                Snackbar.make(v, "Removed From Favorites", Snackbar.LENGTH_LONG).show();
                System.out.println("helooooooooo");

                //This is to reload the Cars Menu Fragment after successfully adding a car to favorites by customer
                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("My_Favorites");
                System.out.println("helooooooooo2");
                fragmentTransaction.detach(houseMenuFragment);
                fragmentTransaction.attach(houseMenuFragment);
                fragmentTransaction.commit();
                System.out.println("helooooooooo3");
            }
        });





//        favsReserveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View reservePopupView = getLayoutInflater().inflate(R.layout.reservation_pop_up, null);
//                final TextView popupMakeAndModel = (TextView) reservePopupView.findViewById(R.id.popupMakeAndModel);
//                final TextView popupYearTextView = (TextView) reservePopupView.findViewById(R.id.popupYearTextView);
//                final TextView popupDistanceTextView = (TextView) reservePopupView.findViewById(R.id.popupDistanceTextView);
//                final TextView popupAccidentsTextView = (TextView) reservePopupView.findViewById(R.id.popupAccidentsTextView);
//                final TextView popupPriceTextView = (TextView) reservePopupView.findViewById(R.id.popupPriceTextView);
//
//                popupYearTextView.setText(carYearOfProduction);
//                popupMakeAndModel.setText(carMake + " " + carModel);
//                popupDistanceTextView.setText(carDistanceTraveled);
//                popupPriceTextView.setText(
//                        carPrice.length() > 6 ?
//                                "$ " + carPrice.substring(0,1) + "," +
//                                        carPrice.substring(carPrice.length() - 6, carPrice.length() - 3) +
//                                        "," + carPrice.substring(carPrice.length() - 3, carPrice.length()) :
//                                "$ " + carPrice.substring(0, carPrice.length() - 3) +
//                                        "," + carPrice.substring(carPrice.length() - 3, carPrice.length()));
//
//                popupAccidentsTextView.setText(carHadAccidents);
//
//                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
//                        .setView(reservePopupView)
//                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
//                                Integer carId = getArguments().getInt("ID");
//                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//                                LocalDateTime now = LocalDateTime.now();
//                                String dateTime = dtf.format(now);
//                                String[] dateTimeArray = dateTime.split(" ");
//                                String date = dateTimeArray[0];
//                                String time = dateTimeArray[1];
//
//                                Reservation reservation = new Reservation();
//                                reservation.setCustomerId(customerId);
//                                reservation.setCarId(carId);
//                                reservation.setDate(date);
//                                reservation.setTime(time);
//
//                                databaseHelper.reserveCarByCustomer(reservation);
//                                databaseHelper.removeFromFavorites(customerId, carId);
//                                Snackbar.make(v, "Reserved Successfully", Snackbar.LENGTH_LONG).show();
//
//                                //To Reload My Favorites Fragment After Successful Reservation By Customer
//                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Fragment myFavoritesFragment = fragmentManager.findFragmentByTag("My_Favorites");
//                                fragmentTransaction.detach(myFavoritesFragment);
//                                fragmentTransaction.attach(myFavoritesFragment);
//                                fragmentTransaction.commit();
//                            }
//                        })
//                        .setNegativeButton("Cancel", null);
//
//                alertDialog.show();
//            }
//        });

        return fragView;
    }

}
