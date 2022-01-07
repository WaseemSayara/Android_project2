package edu.bzu.labproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import edu.bzu.labproject.Security.SecurityUtils;
import edu.bzu.labproject.Validation.Validator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPropertiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPropertiesFragment extends Fragment {

    private String city, address, description ,photo;
    private Integer  area, year, bedroom, price;
    private String date;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPropertiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddpropertiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPropertiesFragment newInstance(String param1, String param2) {
        AddPropertiesFragment fragment = new AddPropertiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_properties, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Post Properties");

        final Button takePhoto = (Button) getActivity().findViewById(R.id.Take_Photo_button);
        final Button choosePhoto = (Button) getActivity().findViewById(R.id.Choose_Photo_button);
        final Button postProperty = (Button) getActivity().findViewById(R.id.Post_button);

        final EditText cityInputField = (EditText) getActivity().findViewById(R.id.Post_City_editText);
        final EditText postalAddressInputField = (EditText) getActivity().findViewById(R.id.Post_Postal_Address_editText);
        final EditText areaInputField = (EditText) getActivity().findViewById(R.id.Post_Surface_Area_editText);
        final EditText yearInputField = (EditText) getActivity().findViewById(R.id.Post_Construction_Year_editText);
        final EditText bedroomInputField = (EditText) getActivity().findViewById(R.id.Post_Bedrooms_editText);
        final EditText priceInputField = (EditText) getActivity().findViewById(R.id.Post_Price_editText);
        final EditText dateInputField = (EditText) getActivity().findViewById(R.id.Post_editTextDate);
        final EditText descriptionInputField = (EditText) getActivity().findViewById(R.id.Post_Description_editText);


//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 0);
//            }
//        });
//
//        choosePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
//
//            }
//        });


        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());


        final AlertDialog.Builder registrationConfirmationAlertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        House house = new House();
                        house.setCity(city);
                        house.setPostalAddress(SecurityUtils.generate_MD5_Secure_Password(address));
                        house.setArea(area);
                        house.setConstructionYear(year);
                        house.setBedrooms(bedroom);
                        house.setPrice(price);
                        house.setAvailabilityDate(date);
                        house.setDescription(description);
                        house.setPhotos(photo);


                        boolean insertFlag;
                        if ((insertFlag = databaseHelper.addHouse(house))) {
                            Toast.makeText(getActivity().getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent toRegistrationActivityIntent = new Intent(getActivity(), HomeAgencyActivity.class);
                            getActivity().startActivity(toRegistrationActivityIntent);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "ERROR: Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, null);

        postProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean allInputsValidated;
                city = cityInputField.getText().toString().trim();
                address = postalAddressInputField.getText().toString().trim();
                try {
                    area = Integer.valueOf(areaInputField.getText().toString().trim());
                    year = Integer.valueOf(yearInputField.getText().toString().trim());
                    bedroom = Integer.valueOf(bedroomInputField.getText().toString().trim());
                    price = Integer.valueOf(priceInputField.getText().toString().trim());

                }catch (Exception e){

                }

                date = dateInputField.getText().toString().trim();
                description = descriptionInputField.getText().toString().trim();
                photo = "none";
                allInputsValidated = true;



                if (!Validator.checkRequiredFieldConstraint(city)) {

                    cityInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (!Validator.checkRequiredFieldConstraint(address)) {

                    postalAddressInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }


                if (!Validator.checkRequiredFieldConstraint(area)) {

                    areaInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (!Validator.checkRequiredFieldConstraint(year)) {

                    yearInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (!Validator.checkRequiredFieldConstraint(bedroom)) {

                    bedroomInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (!Validator.checkRequiredFieldConstraint(price)) {

                    priceInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }




                if (Validator.checkRequiredFieldConstraint(date)) {
                    if (Validator.checkDateValidity(date)) {
                    } else {
                        dateInputField.setError(getResources().getString(R.string.error_invalid_date));
                        allInputsValidated = false;
                    }
                } else {
                    dateInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(description)) {
                    if (Validator.checkDescriptionValidity(description)) {
                    } else {
                        descriptionInputField.setError(getResources().getString(R.string.error_invalid_description));
                        allInputsValidated = false;
                    }
                } else {
                    descriptionInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }


                if (allInputsValidated) {
                    registrationConfirmationAlertDialog.show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.check_validation_errors, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}





