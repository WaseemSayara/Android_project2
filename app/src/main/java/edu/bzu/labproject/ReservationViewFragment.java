package edu.bzu.labproject;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.bzu.labproject.SQLite.DatabaseHelper;

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
        final TextView respriceTextView = (TextView) fragView.findViewById(R.id.respriceTextView);
        final TextView resdescriptionTextView = (TextView) fragView.findViewById(R.id.resdescriptionTextView);
        final TextView resperiodTextView = (TextView) fragView.findViewById(R.id.resPeriodTextView);
        final TextView resagencyTextView = (TextView) fragView.findViewById(R.id.resAgencyLabelTextView);

        final ImageView houseImage = (ImageView) fragView.findViewById(R.id.houseImage);



        String houseCity = String.valueOf(getArguments().getString("CITY"));
        String houseAddress  = getArguments().getString("ADDRESS");
        Integer houseArea = getArguments().getInt("AREA");
        Integer houseYear = getArguments().getInt("YEAR");
        Integer housePrice = getArguments().getInt("PRICE");
        String houseDescription = getArguments().getString("DESCRIPTION");
        String reservePeriod = getArguments().getString("PERIOD");
        String housePhoto = getArguments().getString("PHOTO");


        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        String agencyName = databaseHelper.getAgencyNameByHouseId(getArguments().getInt("ID"));


        rescityTextView.setText(houseCity);
        resaddressTextView.setText(houseAddress);
        resareaTextView.setText(houseArea.toString());
        resyearTextView.setText(houseYear.toString());
        respriceTextView.setText("$ " + housePrice.toString());
        resdescriptionTextView.setText(houseDescription);
        resperiodTextView.setText(reservePeriod);
        resagencyTextView.setText(agencyName);
        try {
            houseImage.setImageURI(Uri.parse(housePhoto));
        } catch (Exception e){
            houseImage.setImageResource(R.drawable.i_house_logo);
        }


        return fragView;

    }

}
