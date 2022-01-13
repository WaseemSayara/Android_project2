package edu.bzu.labproject;


import android.annotation.SuppressLint;
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
import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Validation.Validator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyRentalApplicationFragment extends Fragment {

    public AgencyRentalApplicationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_agency_rental_application, container, false);
        final TextView rentHouseId = (TextView) fragView.findViewById(R.id.rentHouseIdTextView);
        final TextView rentCustomerName = (TextView) fragView.findViewById(R.id.rentTenantNameTextView);
        final TextView rentPeriod = (TextView) fragView.findViewById(R.id.rentPeriodTextView);
        final Button viewTenantButton = (Button) fragView.findViewById(R.id.rentViewTenantButton);


        Integer houseID = getArguments().getInt("HOUSE_ID");
        Integer customerID = getArguments().getInt("CUSTOMER_ID");
        String period = getArguments().getString("PERIOD");


        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        User customer = databaseHelper.getCustomerNameById(customerID);
        rentHouseId.setText(houseID.toString());
        rentCustomerName.setText(customer.getFirstName() + " " + customer.getLastName());
        rentPeriod.setText(period);

        viewTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View reservePopupView = getLayoutInflater().inflate(R.layout.pending_reservation_pop_up, null);
                final TextView rentPopNameTextView = (TextView) reservePopupView.findViewById(R.id.rentPopNameTextView);
                final TextView rentPopEmailTextView = (TextView) reservePopupView.findViewById(R.id.rentPopEmailTextView);
                final TextView rentPopGenderTextView = (TextView) reservePopupView.findViewById(R.id.rentPopGenderTextView);
                final TextView rentPopAddressTextView = (TextView) reservePopupView.findViewById(R.id.rentPopAddressTextView);
                final TextView rentPopNationalityTextView = (TextView) reservePopupView.findViewById(R.id.rentPopNationalityTextView);
                final TextView rentPopPhoneTextView = (TextView) reservePopupView.findViewById(R.id.rentPopPhoneTextView);
                final TextView rentPopOccupationTextView = (TextView) reservePopupView.findViewById(R.id.rentPopOccupationTextView);
                final TextView rentPopSalaryTextView = (TextView) reservePopupView.findViewById(R.id.rentPopSalaryTextView);
                final TextView rentPopFamilyTextView = (TextView) reservePopupView.findViewById(R.id.rentPopFamilyTextView);

                User user = databaseHelper.getUserByUserID(customerID);
                rentPopNameTextView.setText(customer.getFirstName() + " " + customer.getLastName());
                rentPopEmailTextView.setText(user.getEmailAddress());
                rentPopGenderTextView.setText(user.getGender());
                rentPopAddressTextView.setText(user.getCity() +", " + user.getCountry());
                rentPopNationalityTextView.setText(user.getNationality());
                rentPopPhoneTextView.setText(user.getPhoneNumber());
                rentPopOccupationTextView.setText(user.getOccupation());
                rentPopSalaryTextView.setText(user.getSalary());
                rentPopFamilyTextView.setText(user.getFamilySize());

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(reservePopupView)
                        .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {

                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        String dateTime = dtf.format(now);
                                        String[] dateTimeArray = dateTime.split(" ");
                                        String date = dateTimeArray[0];
                                        String time = dateTimeArray[1];
                                        Integer agencyId = getArguments().getInt("AGENCY_ID");

                                        Reservation reservation = new Reservation();
                                        reservation.setCustomerId(customerID);
                                        reservation.setHouseId(houseID);
                                        reservation.setDate(date);
                                        reservation.setTime(time);
                                        reservation.setPeriod(period);
                                        reservation.setAgencyId(agencyId);

                                        databaseHelper.reserveHouseByCustomer(reservation);
                                        databaseHelper.removeFromPendingReserve(houseID);
                                        Snackbar.make(v, "Approved Reserve Successfully", Snackbar.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //This is to reload the Cars Menu Fragment After Successful Reservation By Customer
                                final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment houseMenuFragment = fragmentManager.findFragmentByTag("Rental_Application");
                                fragmentTransaction.detach(houseMenuFragment);
                                fragmentTransaction.attach(houseMenuFragment);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.removeFromPendingReserve(houseID);
                                Snackbar.make(v, "Rejected Reserve Successfully", Snackbar.LENGTH_LONG).show();
                            }
                            });

                alertDialog.show();
            }
        });


        return fragView;

    }

}
