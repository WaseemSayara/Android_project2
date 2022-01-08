package edu.bzu.labproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.bzu.labproject.SQLite.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyReservationViewFragment extends Fragment {


    public AgencyReservationViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_agency_reservation_view, container, false);
        final TextView hiscityTextView = (TextView) fragView.findViewById(R.id.hiscityTextView);
        final TextView hisaddressTextView = (TextView) fragView.findViewById(R.id.hispostalAddressTextView);
        final TextView hisareaTextView = (TextView) fragView.findViewById(R.id.hissurfaceAreaTextView);
        final TextView hisyearTextView = (TextView) fragView.findViewById(R.id.hisconstructionYearTextView);
        final TextView hispriceTextView = (TextView) fragView.findViewById(R.id.hispriceTextView);
        final TextView hisdescriptionTextView = (TextView) fragView.findViewById(R.id.hisdescriptionTextView);
        final TextView hisperiodTextView = (TextView) fragView.findViewById(R.id.hisPeriodTextView);
        final TextView hisagencyTextView = (TextView) fragView.findViewById(R.id.histenantTextView);


        String houseCity = String.valueOf(getArguments().getString("CITY"));
        String houseAddress  = getArguments().getString("ADDRESS");
        Integer houseArea = getArguments().getInt("AREA");
        Integer houseYear = getArguments().getInt("YEAR");
        Integer housePrice = getArguments().getInt("PRICE");
        String houseDescription = getArguments().getString("DESCRIPTION");
        String hiservePeriod = getArguments().getString("PERIOD");

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        String CUSTOMER_ID = databaseHelper.getUserNameByUserId(getArguments().getInt("CUSTOMER_ID"));


        hiscityTextView.setText(houseCity);
        hisaddressTextView.setText(houseAddress);
        hisareaTextView.setText(houseArea.toString());
        hisyearTextView.setText(houseYear.toString());
        hispriceTextView.setText("$ " + housePrice.toString());
        hisdescriptionTextView.setText(houseDescription);
        hisperiodTextView.setText(hiservePeriod);
        hisagencyTextView.setText(CUSTOMER_ID);

        return fragView;

    }

}
