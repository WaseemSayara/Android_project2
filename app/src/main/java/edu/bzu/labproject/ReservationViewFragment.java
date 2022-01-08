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
        View fragView = inflater.inflate(R.layout.house_reservation_cardview, container, false);
        final TextView rescityTextView = (TextView) fragView.findViewById(R.id.rescityTextView);
        final TextView resaddressTextView = (TextView) fragView.findViewById(R.id.respostalAddressTextView);
        final TextView resareaTextView = (TextView) fragView.findViewById(R.id.ressurfaceAreaTextView);
        final TextView resyearTextView = (TextView) fragView.findViewById(R.id.resconstructionYearTextView);
        final TextView resbedroomTextView = (TextView) fragView.findViewById(R.id.resbedroomsTextView);
        final TextView respriceTextView = (TextView) fragView.findViewById(R.id.respriceTextView);
        final TextView resstatusTextView = (TextView) fragView.findViewById(R.id.resstatusTextView);
        final TextView resfurnishedTextView = (TextView) fragView.findViewById(R.id.resfurnishedTextView);
        final TextView resdateTextView = (TextView) fragView.findViewById(R.id.resavailabilityDateTextView);
        final TextView resdescriptionTextView = (TextView) fragView.findViewById(R.id.resdescriptionTextView);


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
        String reservationDate = getArguments().getString("RES_DATE");
        String reservationTime = getArguments().getString("RES_TIME");

        rescityTextView.setText(houseCity);
        resaddressTextView.setText(houseAddress);
        resareaTextView.setText(houseArea.toString());
        resyearTextView.setText(houseYear.toString());
        resbedroomTextView.setText(houseBedroom.toString());
        respriceTextView.setText("$ " + housePrice.toString());
        resstatusTextView.setText(houseStatus);
        resfurnishedTextView.setText(houseFurnished);
        resdateTextView.setText(houseDate);
        resdescriptionTextView.setText(houseDescription);

        return fragView;

    }

}
