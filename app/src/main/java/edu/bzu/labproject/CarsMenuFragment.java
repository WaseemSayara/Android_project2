package edu.bzu.labproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;



/**
 * A simple {@link Fragment} subclass.
 */
public class CarsMenuFragment extends Fragment {
    private boolean isMaxPriceFiltering = true;
    private boolean withAccidentsFiltering = false;
    private List<Car> carsList;

    public CarsMenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Cars");
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final Integer loggedInCustomerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
        carsList = databaseHelper.getCustomerAvailableCars(loggedInCustomerId);



        /**
         * Read and Apply Filters
         */
        final EditText priceFilterValue = (EditText) getActivity().findViewById(R.id.priceFilterVal);
        final EditText yearFilterValue = (EditText) getActivity().findViewById(R.id.yearFilterValue);
        final Button applyUserFiltersBtn = (Button) getActivity().findViewById(R.id.applyFilterBtn);
        final Button minPriceFilteringBtn = (Button) getActivity().findViewById(R.id.minPriceFilteringBtn);
        final Button maxPriceFilteringBtn = (Button) getActivity().findViewById(R.id.maxPriceFilteringBtn);
        final RadioButton hadAccidentsBtn = (RadioButton) getActivity().findViewById(R.id.hadAccidentsRadioBtn);
        final RadioButton freeOfAccidentsBtn = (RadioButton) getActivity().findViewById(R.id.noAccidentsRadioBtn);

        minPriceFilteringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isMaxPriceFiltering = false;
            }
        });

        maxPriceFilteringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isMaxPriceFiltering = true;
            }
        });

        hadAccidentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                withAccidentsFiltering = true;
                freeOfAccidentsBtn.setEnabled(false); // Disable Opposing Radio Button
            }
        });

        freeOfAccidentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                withAccidentsFiltering = false;
                hadAccidentsBtn.setEnabled(false); // Disable Opposing Radio Button
            }
        });

        applyUserFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i("HAS_ACCIDENTS", "with accidents ? " + hadAccidentsBtn.isChecked());
                Log.i("NO_ACCIDENTS", "NO ACCIDENTS? " + freeOfAccidentsBtn.isChecked());
                Log.i("PRICE_FILTERING", "MAX FILTERING ? " + isMaxPriceFiltering);
                Log.i("PRICE_ENTERED", priceFilterValue.getText().toString());

                /** Apply Filters **/
                carsList = carsList.stream()
                        .filter(car -> {
                            String priceVal = priceFilterValue.getText().toString().trim();
                            String modelYearVal = yearFilterValue.getText().toString().trim();

                            if (! priceVal.isEmpty()) {
                                if (! modelYearVal.isEmpty()) {
                                    boolean baseCondition = car.getYearOfProduction() == Integer.valueOf(modelYearVal) &&
                                            car.HadAccidents() == withAccidentsFiltering;

                                    if (isMaxPriceFiltering) {
                                        return baseCondition && Integer.valueOf(car.getCarPrice()) <= Integer.valueOf(priceVal);
                                    }

                                    return baseCondition && Integer.valueOf(car.getCarPrice()) >= Integer.valueOf(priceVal);
                                } else {
                                    boolean baseCondition = car.HadAccidents() == withAccidentsFiltering;

                                    if (isMaxPriceFiltering) {
                                        return baseCondition && Integer.valueOf(car.getCarPrice()) <= Integer.valueOf(priceVal);
                                    }

                                    return baseCondition && Integer.valueOf(car.getCarPrice()) >= Integer.valueOf(priceVal);
                                }
                            } else {
                                if (! modelYearVal.isEmpty()) {
                                    return car.getYearOfProduction() == Integer.valueOf(modelYearVal) &&
                                            car.HadAccidents() == withAccidentsFiltering;
                                } else {
                                    return car.HadAccidents() == withAccidentsFiltering;
                                }
                            }
                        })
                        .collect(Collectors.toList());
//
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//                for(Car car: carsList){
//                    Bundle args = new Bundle();
//                    args.putInt("ID", car.getCarId());
//                    args.putInt("YEAR", car.getYearOfProduction());
//                    args.putString("MAKE", car.getManufacturingCompany());
//                    args.putString("MODEL", car.getCarModel());
//                    args.putString("DISTANCE", car.getDistanceTraveled());
//                    args.putString("PRICE", car.getCarPrice());
//                    args.putBoolean("HAD_ACCIDENTS", car.HadAccidents());
//
//                    CarDetailsViewFragment carDetailsFragment = new CarDetailsViewFragment();
//                    carDetailsFragment.setArguments(args);
//                    ft.add(R.id.carsLinearLayoutView, carDetailsFragment);
//                }
//                ft.commit();
            }
        });

        for(Car car: carsList){
            Bundle args = new Bundle();
            args.putInt("ID", car.getCarId());
            args.putInt("YEAR", car.getYearOfProduction());
            args.putString("MAKE", car.getManufacturingCompany());
            args.putString("MODEL", car.getCarModel());
            args.putString("DISTANCE", car.getDistanceTraveled());
            args.putString("PRICE", car.getCarPrice());
            args.putBoolean("HAD_ACCIDENTS", car.HadAccidents());

            CarDetailsViewFragment carDetailsFragment = new CarDetailsViewFragment();
            carDetailsFragment.setArguments(args);
            fragmentTransaction.add(R.id.carsLinearLayoutView, carDetailsFragment);
        }
        fragmentTransaction.commit();
    }
}
