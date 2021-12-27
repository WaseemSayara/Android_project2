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
public class CarDetailsViewFragment extends Fragment {

    public CarDetailsViewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.car_details_cardview, container, false);
        final TextView makeAndModelTextView = (TextView) fragView.findViewById(R.id.makeAndModelTitle);
        final TextView yearTextView = (TextView) fragView.findViewById(R.id.yearTextView);
        final TextView distanceTextView = (TextView) fragView.findViewById(R.id.distanceTextView);
        final TextView hadAccidentsTextView = (TextView) fragView.findViewById(R.id.accidentsTextView);
        final TextView priceTextView = (TextView) fragView.findViewById(R.id.priceTextView);
        final Button reserveButton = (Button) fragView.findViewById(R.id.reserveButton);
        final Button addToFavoritesButton = (Button) fragView.findViewById(R.id.addToFavButton);

        String carYearOfProduction = String.valueOf(getArguments().getInt("YEAR"));
        String carMake  = getArguments().getString("MAKE");
        String carModel = getArguments().getString("MODEL");
        String carDistanceTraveled = getArguments().getString("DISTANCE");
        String carPrice = getArguments().getString("PRICE");
        String carHadAccidents = getArguments().getBoolean("HAD_ACCIDENTS") ? "YES" : "NO";

        yearTextView.setText(carYearOfProduction);
        makeAndModelTextView.setText(carMake + " " + carModel);
        distanceTextView.setText(carDistanceTraveled);
        priceTextView.setText(
                carPrice.length() > 6 ?
                        "$ " + carPrice.substring(0,1) + "," +
                                carPrice.substring(carPrice.length() - 6, carPrice.length() - 3) +
                                "," + carPrice.substring(carPrice.length() - 3, carPrice.length()) :
                        "$ " + carPrice.substring(0, carPrice.length() - 3) +
                               "," + carPrice.substring(carPrice.length() - 3, carPrice.length()));

        hadAccidentsTextView.setText(carHadAccidents);


        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View reservePopupView = getLayoutInflater().inflate(R.layout.reservation_pop_up, null);
                final TextView popupMakeAndModel = (TextView) reservePopupView.findViewById(R.id.popupMakeAndModel);
                final TextView popupYearTextView = (TextView) reservePopupView.findViewById(R.id.popupYearTextView);
                final TextView popupDistanceTextView = (TextView) reservePopupView.findViewById(R.id.popupDistanceTextView);
                final TextView popupAccidentsTextView = (TextView) reservePopupView.findViewById(R.id.popupAccidentsTextView);
                final TextView popupPriceTextView = (TextView) reservePopupView.findViewById(R.id.popupPriceTextView);

                popupYearTextView.setText(carYearOfProduction);
                popupMakeAndModel.setText(carMake + " " + carModel);
                popupDistanceTextView.setText(carDistanceTraveled);
                popupPriceTextView.setText(
                        carPrice.length() > 6 ?
                                "$ " + carPrice.substring(0,1) + "," +
                                        carPrice.substring(carPrice.length() - 6, carPrice.length() - 3) +
                                        "," + carPrice.substring(carPrice.length() - 3, carPrice.length()) :
                                "$ " + carPrice.substring(0, carPrice.length() - 3) +
                                        "," + carPrice.substring(carPrice.length() - 3, carPrice.length()));

                popupAccidentsTextView.setText(carHadAccidents);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(reservePopupView)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                                Integer carId = getArguments().getInt("ID");
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                String dateTime = dtf.format(now);
                                String[] dateTimeArray = dateTime.split(" ");
                                String date = dateTimeArray[0];
                                String time = dateTimeArray[1];

                                Reservation reservation = new Reservation();
                                reservation.setCustomerId(customerId);
                                reservation.setCarId(carId);
                                reservation.setDate(date);
                                reservation.setTime(time);

                                databaseHelper.reserveCarByCustomer(reservation);
                                Snackbar.make(v, "Reserved Successfully", Snackbar.LENGTH_LONG).show();

                                //This is to reload the Cars Menu Fragment After Successful Reservation By Customer
                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment carsMenuFragment = fragmentManager.findFragmentByTag("Cars_Menu");
                                fragmentTransaction.detach(carsMenuFragment);
                                fragmentTransaction.attach(carsMenuFragment);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                alertDialog.show();
            }
        });

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer customerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
                Integer carId = getArguments().getInt("ID");
                databaseHelper.addFavoriteCarToCustomer(customerId, carId);
                Snackbar.make(v, "Added to Favorites", Snackbar.LENGTH_LONG).show();

                //This is to reload the Cars Menu Fragment after successfully adding a car to favorites by customer
                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment carsMenuFragment = fragmentManager.findFragmentByTag("Cars_Menu");
                fragmentTransaction.detach(carsMenuFragment);
                fragmentTransaction.attach(carsMenuFragment);
                fragmentTransaction.commit();
            }
        });

        return fragView;

    }


}
