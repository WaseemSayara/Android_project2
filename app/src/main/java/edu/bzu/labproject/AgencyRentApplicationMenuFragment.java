package edu.bzu.labproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyRentApplicationMenuFragment extends Fragment {


    public AgencyRentApplicationMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agency_rent_application_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Rental Application");
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final Integer agencyId = loginSessionManager.getCurrentlyLoggedInAgencyUser().getId();

        List<Reservation> reservationsList = databaseHelper.getPendingReservationsByAgencyId(agencyId);
        if(reservationsList != null){
            for(Reservation reservation: reservationsList){
                try {
                    Bundle args = new Bundle();
                    //Put Date and Time of Reservation in Arguments Bundle
                    args.putInt("AGENCY_ID", reservation.getAgencyId());
                    args.putInt("HOUSE_ID", reservation.getHouseId());
                    args.putString("PERIOD", reservation.getPeriod());
                    args.putInt("CUSTOMER_ID", reservation.getCustomerId());


                    AgencyRentalApplicationFragment agencyRentalApplicationFragment = new AgencyRentalApplicationFragment();
                    agencyRentalApplicationFragment.setArguments(args);
                    fragmentTransaction.add(R.id.agencyPendingReservationsLinearLayout, agencyRentalApplicationFragment);
                }catch (Exception e){

                }
            }
            fragmentTransaction.commit();
        }
    }
}
