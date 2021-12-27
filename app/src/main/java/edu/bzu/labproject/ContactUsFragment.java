package edu.bzu.labproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Contact Us");

        final Button callUsButton = (Button) getActivity().findViewById(R.id.callUsButton);
        final Button openMapsButton = (Button) getActivity().findViewById(R.id.mapsButton);
        final Button emailUsButton = (Button) getActivity().findViewById(R.id.emailUsButton);

        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(dialIntent);
            }
        });

        openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent();
                mapsIntent.setAction(Intent.ACTION_VIEW);
                mapsIntent.setData(Uri.parse("geo:31.903765,35.203419"));
                startActivity(mapsIntent);
            }
        });

        emailUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent();
                emailIntent.setAction(Intent.ACTION_SENDTO);
                emailIntent.setType("message/rfc822");
                emailIntent.setData(Uri.parse("mailto:CarDealer@cars.com"));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Unexpected Error Report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I would like to report error");

                startActivity(emailIntent);
            }
        });
    }
}
