package edu.bzu.labproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;

import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.REST.RestApi;
import edu.bzu.labproject.SQLite.DatabaseHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionActivity extends Activity {
    String ERROR_CONNECTION_FAILED = "ERROR: CONNECTION FAILED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final CardView buttonCardView = (CardView) findViewById(R.id.connectButtonCardView);
        final ProgressBar connectProgressBar = (ProgressBar) findViewById(R.id.connectProgressBar);
        final Button connectButton = (Button)  findViewById(R.id.connectButton);
        final DatabaseHelper databaseHelper = new DatabaseHelper(ConnectionActivity.this);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectButton.setText("Connecting ...");
                connectButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                buttonCardView.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                connectProgressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RestApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi restApi = retrofit.create(RestApi.class);
                Call<List<Car>> callToFetchListOfCars = restApi.getCars();
                callToFetchListOfCars.enqueue(new Callback<List<Car>>() {
                    @Override
                    public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Connection Successful", Toast.LENGTH_SHORT).show();
                            connectProgressBar.setVisibility(View.GONE);
                            if(databaseHelper.getAllCars() == null){
                                List<Car> carsList = response.body();
                                for(Car car: carsList){
                                    databaseHelper.addCar(car);
                                }
                            }

                            Intent dataFetchSuccessIntent = new Intent(ConnectionActivity.this, LoginActivity.class);
                            ConnectionActivity.this.startActivity(dataFetchSuccessIntent);
                            ConnectionActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Car>> call, Throwable t) {
                        connectProgressBar.setVisibility(View.GONE);
                        connectButton.setText("Reconnect..");
                        connectButton.setBackgroundColor(Color.rgb(0,255,0));
                        buttonCardView.setCardBackgroundColor(Color.rgb(0,255,0));
                        Toast.makeText(getApplicationContext(), ERROR_CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                        Log.d("RETROFIT_REST",t.getMessage());
                    }
                });
            }
        });
    }
}
