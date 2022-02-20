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

import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.LoginSessionManager;



/**
 * A simple {@link Fragment} subclass.
 */
public class HouseMenuFragment extends Fragment {
    private boolean isMaxPriceFiltering = true;
    private boolean withAccidentsFiltering = false;
    private List<House> houseList;

    public HouseMenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_house_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Houses");
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        final LoginSessionManager loginSessionManager = new LoginSessionManager(getActivity());
        final Integer loggedInCustomerId = loginSessionManager.getCurrentlyLoggedInUser().getId();
        houseList = databaseHelper.getAllHouses();


        int i =0;
        if (houseList != null)
        for(House house: houseList){
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

            HouseDetailsViewFragment houseDetailsFragment = new HouseDetailsViewFragment();
            houseDetailsFragment.setArguments(args);
            fragmentTransaction.add(R.id.housesLinearLayoutView, houseDetailsFragment);
            i++;
            if (i==5){
                break;
            }
        }
        fragmentTransaction.commit();
    }
}
