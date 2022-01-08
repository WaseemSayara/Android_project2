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

import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.Security.LoginSessionManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HouseMenuFragment houseMenuFragment = new HouseMenuFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeContent, houseMenuFragment, "House_Menu");
        fragmentTransaction.commit();

        LoginSessionManager userLoginSession = new LoginSessionManager(getApplicationContext());
        User loggedInUser = userLoginSession.getCurrentlyLoggedInUser();
        Toast.makeText(getApplicationContext(), "Welcome Back, " + loggedInUser.getFirstName(), Toast.LENGTH_SHORT).show();

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
        try {
            if (loggedInUser.getGender().equals("Male"))
                navHeaderImage.setImageResource(R.drawable.maleuser);
            else
                navHeaderImage.setImageResource(R.drawable.femaleuser);
        }catch (Exception e){

            navHeaderImage.setImageResource(R.drawable.maleuser);

        }

        navHeaderEmail.setText(loggedInUser.getEmailAddress());
        navHeaderName.setText(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

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

         } else if (id == R.id.nav_search) {
             final FragmentManager fragmentManager = getSupportFragmentManager();
             final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             SearchFragment searchFragment = new SearchFragment();
             fragmentTransaction.replace(R.id.homeContent, searchFragment, "Search_Fragment");
             fragmentTransaction.commit();

        } else if (id == R.id.nav_reservations) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyReservationsFragment myReservationsFragment = new MyReservationsFragment();
            fragmentTransaction.replace(R.id.homeContent, myReservationsFragment, "My_History");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_myfavorites) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyFavoritesFragment myFavoritesFragment = new MyFavoritesFragment();
            fragmentTransaction.replace(R.id.homeContent, myFavoritesFragment, "My_Favorites");
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
            Intent redirectToLoginPage = new Intent(HomeActivity.this, LoginActivity.class);
            HomeActivity.this.startActivity(redirectToLoginPage);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
