package edu.bzu.labproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationViewFragment extends Fragment {


    public ReservationViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.car_reservation_cardview, container, false);
        final TextView reserveMakeAndModel = (TextView) fragView.findViewById(R.id.reserveMakeAndModel);
        final TextView reserveYearTextView = (TextView) fragView.findViewById(R.id.reserveYearTextView);
        final TextView reserveDistanceTextView = (TextView) fragView.findViewById(R.id.reserveDistanceTextView);
        final TextView reserveAccidentsTextView = (TextView) fragView.findViewById(R.id.reserveAccidentsTextView);
        final TextView reservePriceTextView = (TextView) fragView.findViewById(R.id.reservePriceTextView);
        final TextView reserveDateTextView = (TextView) fragView.findViewById(R.id.reserveDateTextView);
        final TextView reserveTimeTextView = (TextView) fragView.findViewById(R.id.reserveTimeTextView);

        String carYearOfProduction = String.valueOf(getArguments().getInt("YEAR"));
        String carMake  = getArguments().getString("MAKE");
        String carModel = getArguments().getString("MODEL");
        String carDistanceTraveled = getArguments().getString("DISTANCE");
        String carPrice = getArguments().getString("PRICE");
        String carHadAccidents = getArguments().getBoolean("HAD_ACCIDENTS") ? "YES" : "NO";
        String reservationDate = getArguments().getString("RES_DATE");
        String reservationTime = getArguments().getString("RES_TIME");

        reserveYearTextView.setText(carYearOfProduction);
        reserveMakeAndModel.setText(carMake + " " + carModel);
        reserveDistanceTextView.setText(carDistanceTraveled);
        reservePriceTextView.setText(
                carPrice.length() > 6 ?
                        "$ " + carPrice.substring(0,1) + "," +
                                carPrice.substring(carPrice.length() - 6, carPrice.length() - 3) +
                                "," + carPrice.substring(carPrice.length() - 3, carPrice.length()) :
                        "$ " + carPrice.substring(0, carPrice.length() - 3) +
                                "," + carPrice.substring(carPrice.length() - 3, carPrice.length()));

        reserveAccidentsTextView.setText(carHadAccidents);
        reserveDateTextView.setText(reservationDate);
        reserveTimeTextView.setText(reservationTime);

        return fragView;

    }

}
