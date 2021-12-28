package edu.bzu.labproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RegisterType extends AppCompatActivity {

    private Button openTenantRegistrationForm;
    private Button openAgencyRegistrationForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);

        openTenantRegistrationForm = (Button) findViewById(R.id.tenantRegisterButton);
        openTenantRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegistrationActivityIntent = new Intent(RegisterType.this, RegistrationActivity.class);
                RegisterType.this.startActivity(toRegistrationActivityIntent);

            }
        });

        openAgencyRegistrationForm = (Button) findViewById(R.id.rentingAgencyButton);
        openAgencyRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegistrationActivityIntent = new Intent(RegisterType.this, RegistrationRenting.class);
                RegisterType.this.startActivity(toRegistrationActivityIntent);

            }
        });

    }


}