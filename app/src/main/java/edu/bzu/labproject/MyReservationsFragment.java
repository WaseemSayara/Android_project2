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
import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyReservationsFragment extends Fragment {


    public MyReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_reservations, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Reservations");
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final Integer loggedInCustomerId = loginSessionManager.getCurrentlyLoggedInUser().getId();

        List<Reservation> reservationsList = databaseHelper.getReservationsByCustomerId(loggedInCustomerId);
        if(reservationsList != null){
            for(Reservation reservation: reservationsList){
                Car car = databaseHelper.getCarById(reservation.getCarId());
                Bundle args = new Bundle();
                args.putInt("ID", car.getCarId());
                args.putInt("YEAR", car.getYearOfProduction());
                args.putString("MAKE", car.getManufacturingCompany());
                args.putString("MODEL", car.getCarModel());
                args.putString("DISTANCE", car.getDistanceTraveled());
                args.putString("PRICE", car.getCarPrice());
                args.putBoolean("HAD_ACCIDENTS", car.HadAccidents());

                //Put Date and Time of Reservation in Arguments Bundle
                args.putString("RES_DATE", reservation.getDate());
                args.putString("RES_TIME", reservation.getTime());

                ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
                reservationViewFragment.setArguments(args);
                fragmentTransaction.add(R.id.reservationsLinearLayout, reservationViewFragment);
            }
            fragmentTransaction.commit();
        }
    }
}
