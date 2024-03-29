package edu.bzu.labproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.bzu.labproject.Models.AgencyUser;
import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.Security.LoginSessionManager;

public class HomeAgencyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_agency);

        HouseMenuFragment houseMenuFragment = new HouseMenuFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeContent, houseMenuFragment, "House_Menu");
        fragmentTransaction.commit();

        LoginSessionManager userLoginSession = new LoginSessionManager(getApplicationContext());
        AgencyUser loggedInUser = userLoginSession.getCurrentlyLoggedInAgencyUser();
        Toast.makeText(getApplicationContext(), "Welcome Back, " + loggedInUser.getAgencyName(), Toast.LENGTH_SHORT).show();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        ImageView navHeaderImage = (ImageView) navHeaderView.findViewById(R.id.headerImageView);
        TextView navHeaderEmail = (TextView) navHeaderView.findViewById(R.id.headerEmailAddress);
        TextView navHeaderName = (TextView) navHeaderView.findViewById(R.id.headerCustomerFullName);

        navHeaderImage.setImageResource(R.drawable.maleuser);

        navHeaderEmail.setText(loggedInUser.getEmailAddress());
        navHeaderName.setText(loggedInUser.getAgencyName());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HouseMenuFragment houseMenuFragment = new HouseMenuFragment();
            fragmentTransaction.replace(R.id.homeContent, houseMenuFragment, "House_Menu");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_post_property) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddPropertiesFragment addPropertiesFragment = new AddPropertiesFragment();
            fragmentTransaction.replace(R.id.homeContent,addPropertiesFragment , "Add_Properties");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_rental_agency_history) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AgencyReservationFragment agencyReservationsFragment = new AgencyReservationFragment();
            fragmentTransaction.replace(R.id.homeContent, agencyReservationsFragment, "My_History");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_edit_property) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            EditPropertyFragment editPropertyFragment = new EditPropertyFragment();
            fragmentTransaction.replace(R.id.homeContent, editPropertyFragment, "Edit_Property");
            fragmentTransaction.commit();

        }else if (id == R.id.nav_agency_rental_application) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AgencyRentApplicationMenuFragment agencyRentApplicationMenuFragment = new AgencyRentApplicationMenuFragment();
            fragmentTransaction.replace(R.id.homeContent, agencyRentApplicationMenuFragment, "Rental_Application");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_contactus) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            fragmentTransaction.replace(R.id.homeContent, contactUsFragment, "Contact_Us");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_user_profile) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginSessionManager loginSession = new LoginSessionManager(getApplicationContext());
            boolean type = loginSession.getUser_Type();
            System.out.println(type);
            if (type) {
                UserProfileFragment userProfileFragment = new UserProfileFragment();
                fragmentTransaction.replace(R.id.homeContent, userProfileFragment);
                fragmentTransaction.commit();
            } else {

                AgencyUserProfileFragment agencyUserProfileFragment = new AgencyUserProfileFragment();
                fragmentTransaction.replace(R.id.homeContent, agencyUserProfileFragment);
                fragmentTransaction.commit();
            }


        } else if (id == R.id.nav_logout) {
            LoginSessionManager session = new LoginSessionManager(getApplicationContext());
            session.clearLoginSessionOnLogout();
            Intent redirectToLoginPage = new Intent(HomeAgencyActivity.this, LoginActivity.class);
            HomeAgencyActivity.this.startActivity(redirectToLoginPage);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
