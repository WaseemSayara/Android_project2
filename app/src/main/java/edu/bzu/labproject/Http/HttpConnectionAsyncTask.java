package edu.bzu.labproject.Http;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import edu.bzu.labproject.ConnectionActivity;
import edu.bzu.labproject.LoginActivity;
import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.R;
import edu.bzu.labproject.REST.RestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpConnectionAsyncTask extends AsyncTask<String, String, String> {
    private Activity connectionActivity;
    String ERROR_CONNECTION_FAILED = "ERROR: CONNECTION FAILED";
    Button connectButton;
    ProgressBar connectProgressBar;
    CardView connectButtonCardView;
    public HttpConnectionAsyncTask(ConnectionActivity connectionActivity){
        this.connectionActivity = connectionActivity;
        this.connectButton = (connectionActivity.findViewById(R.id.connectButton));
        this.connectProgressBar = (connectionActivity.findViewById(R.id.connectProgressBar));
        this.connectButtonCardView = (connectionActivity.findViewById(R.id.connectButtonCardView));
    }

    @Override
    protected void onPreExecute() {
        connectButton.setText("Connecting ...");
        connectButton.setBackgroundColor(connectionActivity.getResources().getColor(android.R.color.darker_gray));
        connectButtonCardView.setCardBackgroundColor(connectionActivity.getResources().getColor(android.R.color.darker_gray));
        super.onPreExecute();
        connectProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
       String carsData = HttpManager.getData(params[0]);
        return carsData;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if(response != null){
            connectProgressBar.setVisibility(View.GONE);
            Intent dataFetchSuccessIntent = new Intent(connectionActivity, LoginActivity.class);
            connectionActivity.startActivity(dataFetchSuccessIntent);
            connectionActivity.finish();
        }
        else {
            connectProgressBar.setVisibility(View.GONE);
            connectButton.setText("Reconnect..");
            connectButton.setBackgroundColor(Color.rgb(0,255,0));
            connectButtonCardView.setCardBackgroundColor(Color.rgb(0,255,0));
            Toast.makeText(connectionActivity.getApplicationContext(), ERROR_CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
        }

    }
}
