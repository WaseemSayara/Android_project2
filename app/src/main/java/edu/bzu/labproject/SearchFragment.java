package edu.bzu.labproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;
import edu.bzu.labproject.Validation.Validator;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    List<String> searchList = new ArrayList<String>();
    List<String> cities = new ArrayList<String>();

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Search");
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final Integer loggedInCustomerId = loginSessionManager.getCurrentlyLoggedInUser().getId();


        cities=databaseHelper.getAllCities();
        String[] citiesOptions = new String[cities.size()];
        citiesOptions = cities.toArray(citiesOptions);

        Spinner citiesSpinner = (Spinner) getActivity().findViewById(R.id.search_city_Spinner);
        final EditText areaMinEditText = (EditText) getActivity().findViewById(R.id.search_min_area_edittext);
        final EditText areaMaxEditText = (EditText) getActivity().findViewById(R.id.search_max_area_edittext);
        final EditText bedroomMinEditText = (EditText) getActivity().findViewById(R.id.search_min_bedroom_edittext);
        final EditText bedroomMaxEditText = (EditText) getActivity().findViewById(R.id.search_max_bedroom_edittext);
        final EditText priceMinEditText = (EditText) getActivity().findViewById(R.id.search_min_price_edittext);
        final EditText priceMaxEditText = (EditText) getActivity().findViewById(R.id.search_max_price_edittext);
        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.search_status_radioGroup);
        Button searchButton = (Button) getActivity().findViewById(R.id.search_click_button);
        Spinner citySpinner = (Spinner) getActivity().findViewById(R.id.search_city_Spinner);

        ArrayAdapter citiesArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, citiesOptions);
        citiesSpinner.setAdapter(citiesArrayAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                searchList.clear();
                String areaMin, areaMax, priceMin, priceMax, bedroomMin, bedroomMax, city;
                String selectedStatusString;

                areaMin = areaMinEditText.getText().toString().trim();
                areaMax = areaMaxEditText.getText().toString().trim();
                priceMin = priceMinEditText.getText().toString().trim();
                priceMax = priceMaxEditText.getText().toString().trim();
                bedroomMin = bedroomMinEditText.getText().toString().trim();
                bedroomMax = bedroomMaxEditText.getText().toString().trim();
                city = citySpinner.getSelectedItem().toString();

                System.out.println(city);

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selected = radioGroup.findViewById(selectedId);
                try {
                    selectedStatusString = selected.getText().toString();
                }catch (Exception e){
                    selectedStatusString = null;
                }
                System.out.println(selectedStatusString);
                System.out.println(selectedId);

                boolean valid =true;

                if (areaMin.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(areaMin)){
                    searchList.add("AREA > "+areaMin);
                }else{
                    valid= false;

                }
                if (areaMax.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(areaMax)){
                    searchList.add("AREA < "+areaMax);
                }else{
                    valid= false;

                }
                if (bedroomMin.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(bedroomMin)){
                    searchList.add("BEDROOMS > "+bedroomMin);
                }else{
                    valid= false;

                }

                if (bedroomMax.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(bedroomMax)){
                    searchList.add("BEDROOMS < "+bedroomMax);
                }else{
                    valid= false;

                }

                if (priceMin.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(priceMin)){
                    searchList.add("PRICE > "+priceMin);
                }else{
                    valid= false;

                }
                if (priceMax.isEmpty()){
                }
                else if (Validator.checkSalaryValidity(priceMax)){
                    searchList.add("PRICE < "+priceMax);
                }else{
                    valid= false;

                }
                if (selectedStatusString == null){}
                else if (selectedStatusString.compareTo("Furnished") == 0){
                    searchList.add("FURNISHED = 1");
                }
                else {
                    searchList.add("FURNISHED = 0");
                }
//                if (city.compareTo("all") == 0){
//
//                }
//                else {
//                    searchList.add("CITY = "+city);
//                }

                if (valid){
                    String query = "";
                    if (searchList != null && searchList.size() > 0) {
                        for (String search : searchList) {
                            query += search;
                            query += " AND ";
                        }
                        query = query.substring(0,query.length()-5);
                        System.out.println(query);
                        List<House> searchedHouses = new ArrayList<House>();
                        searchedHouses = databaseHelper.getSearchedHouses(query);
//                        searchedHouses = databaseHelper.getAllHouses();

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        LinearLayout ln = (LinearLayout) getActivity().findViewById(R.id.searchedHousesLinearLayoutView);
                        ln.removeAllViews();

                        if (searchedHouses != null)
                            for(House house: searchedHouses){
                                Bundle args = new Bundle();
                                args.putInt("ID", house.getHouseId());
                                args.putInt("AGENCY_ID", house.getAgencyId());
                                args.putString("CITY", house.getCity());
                                args.putString("ADDRESS", house.getPostalAddress());
                                args.putInt("AREA", house.getArea());
                                args.putInt("YEAR", house.getConstructionYear());
                                args.putInt("BEDROOM", house.getBedrooms());
                                args.putInt("PRICE", house.getPrice());
                                args.putBoolean("STATUS", house.isStatus());
                                args.putBoolean("FURNISHED", house.isFurnished());
                                args.putString("PHOTO", house.getPhotos());
                                args.putString("DATE", house.getAvailabilityDate());
                                args.putString("DESCRIPTION", house.getDescription());

                                System.out.println(house.toString());

                                SearchDeatilsViewFragment houseDetailsFragment = new SearchDeatilsViewFragment();
                                houseDetailsFragment.setArguments(args);
                                fragmentTransaction.add(R.id.searchedHousesLinearLayoutView, houseDetailsFragment);
                            }
                        fragmentTransaction.commit();

                    }
                }
            }
        });



        // /////////////////////////////////////////////////



        /**
         * Read and Apply Filters
         */


//        final EditText priceFilterValue = (EditText) getActivity().findViewById(R.id.priceFilterVal);
//        final EditText yearFilterValue = (EditText) getActivity().findViewById(R.id.yearFilterValue);
//        final Button applyUserFiltersBtn = (Button) getActivity().findViewById(R.id.applyFilterBtn);
//        final Button minPriceFilteringBtn = (Button) getActivity().findViewById(R.id.minPriceFilteringBtn);
//        final Button maxPriceFilteringBtn = (Button) getActivity().findViewById(R.id.maxPriceFilteringBtn);
//        final RadioButton hadAccidentsBtn = (RadioButton) getActivity().findViewById(R.id.hadAccidentsRadioBtn);
//        final RadioButton freeOfAccidentsBtn = (RadioButton) getActivity().findViewById(R.id.noAccidentsRadioBtn);
//
//        minPriceFilteringBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                isMaxPriceFiltering = false;
//            }
//        });
//
//        maxPriceFilteringBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                isMaxPriceFiltering = true;
//            }
//        });
//
//        hadAccidentsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                withAccidentsFiltering = true;
//                freeOfAccidentsBtn.setEnabled(false); // Disable Opposing Radio Button
//            }
//        });
//
//        freeOfAccidentsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                withAccidentsFiltering = false;
//                hadAccidentsBtn.setEnabled(false); // Disable Opposing Radio Button
//            }
//        });
//
//        applyUserFiltersBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Log.i("HAS_ACCIDENTS", "with accidents ? " + hadAccidentsBtn.isChecked());
//                Log.i("NO_ACCIDENTS", "NO ACCIDENTS? " + freeOfAccidentsBtn.isChecked());
//                Log.i("PRICE_FILTERING", "MAX FILTERING ? " + isMaxPriceFiltering);
//                Log.i("PRICE_ENTERED", priceFilterValue.getText().toString());
//
//                /** Apply Filters **/
//                carsList = carsList.stream()
//                        .filter(car -> {
//                            String priceVal = priceFilterValue.getText().toString().trim();
//                            String modelYearVal = yearFilterValue.getText().toString().trim();
//
//                            if (! priceVal.isEmpty()) {
//                                if (! modelYearVal.isEmpty()) {
//                                    boolean baseCondition = car.getYearOfProduction() == Integer.valueOf(modelYearVal) &&
//                                            car.HadAccidents() == withAccidentsFiltering;
//
//                                    if (isMaxPriceFiltering) {
//                                        return baseCondition && Integer.valueOf(car.getCarPrice()) <= Integer.valueOf(priceVal);
//                                    }
//
//                                    return baseCondition && Integer.valueOf(car.getCarPrice()) >= Integer.valueOf(priceVal);
//                                } else {
//                                    boolean baseCondition = car.HadAccidents() == withAccidentsFiltering;
//
//                                    if (isMaxPriceFiltering) {
//                                        return baseCondition && Integer.valueOf(car.getCarPrice()) <= Integer.valueOf(priceVal);
//                                    }
//
//                                    return baseCondition && Integer.valueOf(car.getCarPrice()) >= Integer.valueOf(priceVal);
//                                }
//                            } else {
//                                if (! modelYearVal.isEmpty()) {
//                                    return car.getYearOfProduction() == Integer.valueOf(modelYearVal) &&
//                                            car.HadAccidents() == withAccidentsFiltering;
//                                } else {
//                                    return car.HadAccidents() == withAccidentsFiltering;
//                                }
//                            }
//                        })
//                        .collect(Collectors.toList());
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
//            }
//        });

    }
}
