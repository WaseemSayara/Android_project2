package edu.bzu.labproject.Security;

import android.content.Context;
import android.content.SharedPreferences;

import edu.bzu.labproject.Models.AgencyUser;
import edu.bzu.labproject.Models.User;

public class LoginSessionManager {
    private static final String SHARED_PREF_FILE = "LoginSession";
    private static final String SHAREDPREF_KEY_ID = "ID";
    private static final String SHAREDPREF_KEY_FIRSTNAME = "Firstname";
    private static final String SHAREDPREF_KEY_LASTNAME = "Lastname";
    private static final String SHAREDPREF_KEY_EMAIL = "Email_Address";
    private static final String SHAREDPREF_KEY_GENDER = "Gender";
    private static final String SHAREDPREF_KEY_COUNTRY = "Country";
    private static final String SHAREDPREF_KEY_CITY = "City";
    private static final String SHAREDPREF_KEY_PHONE = "Phone_Number";
    private static final String SHAREDPREF_KEY_ISLOGGEDIN = "isUserLoggedIn";
    private static final String SHAREDPREF_KEY_NATIONALITY = "Nationality";
    private static final String SHAREDPREF_KEY_SALARY = "Salary";
    private static final String SHAREDPREF_KEY_FAMILY_SIZE = "Family_Size";
    private static final String SHAREDPREF_KEY_OCCUPATION = "Occupation";
    private static final String SHAREDPREF_KEY_AGENCYNAME = "Agencyname";
    private static final String SHAREDPREF_KEY_TYPE = "Type";

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isLoggedIn;
    // false is Agency user and true is  user
    private boolean type;


    public LoginSessionManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE );
        this.editor = this.sharedPreferences.edit();
        this.isLoggedIn=true;
    }
    public LoginSessionManager(Context context, boolean type){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE );
        this.editor = this.sharedPreferences.edit();
        this.isLoggedIn=true;
        this.type=type;
    }

    public void saveUserLoginSession(User loggedInUser){
        this.editor.putInt(SHAREDPREF_KEY_ID, loggedInUser.getId());
        this.editor.putString(SHAREDPREF_KEY_FIRSTNAME, loggedInUser.getFirstName());
        this.editor.putString(SHAREDPREF_KEY_LASTNAME, loggedInUser.getLastName());
        this.editor.putString(SHAREDPREF_KEY_EMAIL, loggedInUser.getEmailAddress());
        this.editor.putString(SHAREDPREF_KEY_GENDER, loggedInUser.getGender());
        this.editor.putString(SHAREDPREF_KEY_COUNTRY, loggedInUser.getCountry());
        this.editor.putString(SHAREDPREF_KEY_CITY, loggedInUser.getCity());
        this.editor.putString(SHAREDPREF_KEY_PHONE, loggedInUser.getPhoneNumber());
        this.editor.putString(SHAREDPREF_KEY_NATIONALITY, loggedInUser.getNationality());
        this.editor.putString(SHAREDPREF_KEY_SALARY, loggedInUser.getSalary());
        this.editor.putString(SHAREDPREF_KEY_FAMILY_SIZE, loggedInUser.getFamilySize());
        this.editor.putString(SHAREDPREF_KEY_OCCUPATION, loggedInUser.getOccupation());
        this.editor.putBoolean(SHAREDPREF_KEY_ISLOGGEDIN, this.isLoggedIn);
        this.editor.commit();
    }

    public void saveAgencyUserLoginSession(AgencyUser loggedInUser){
        this.editor.putInt(SHAREDPREF_KEY_ID, loggedInUser.getId());
        this.editor.putString(SHAREDPREF_KEY_AGENCYNAME, loggedInUser.getAgencyName());
        this.editor.putString(SHAREDPREF_KEY_EMAIL, loggedInUser.getEmailAddress());
        this.editor.putString(SHAREDPREF_KEY_COUNTRY, loggedInUser.getCountry());
        this.editor.putString(SHAREDPREF_KEY_CITY, loggedInUser.getCity());
        this.editor.putString(SHAREDPREF_KEY_PHONE, loggedInUser.getPhoneNumber());
        this.editor.putBoolean(SHAREDPREF_KEY_ISLOGGEDIN, this.isLoggedIn);
        this.editor.commit();
    }

    public void updateCurrentLoginSession(User updatedUser){
        this.editor.putString(SHAREDPREF_KEY_FIRSTNAME, updatedUser.getFirstName());
        this.editor.putString(SHAREDPREF_KEY_LASTNAME, updatedUser.getLastName());
        this.editor.putString(SHAREDPREF_KEY_EMAIL, updatedUser.getEmailAddress());
        this.editor.commit();
    }

    public User getCurrentlyLoggedInUser(){
        User loggedInUser = new User();
        loggedInUser.setId(this.sharedPreferences.getInt(SHAREDPREF_KEY_ID, -1));
        loggedInUser.setFirstName(this.sharedPreferences.getString(SHAREDPREF_KEY_FIRSTNAME,null));
        loggedInUser.setLastName(this.sharedPreferences.getString(SHAREDPREF_KEY_LASTNAME,null));
        loggedInUser.setEmailAddress(this.sharedPreferences.getString(SHAREDPREF_KEY_EMAIL,null));
        loggedInUser.setGender(this.sharedPreferences.getString(SHAREDPREF_KEY_GENDER,null));
        loggedInUser.setCountry(this.sharedPreferences.getString(SHAREDPREF_KEY_COUNTRY,null));
        loggedInUser.setCity(this.sharedPreferences.getString(SHAREDPREF_KEY_CITY,null));
        loggedInUser.setPhoneNumber(this.sharedPreferences.getString(SHAREDPREF_KEY_PHONE,null));
        loggedInUser.setNationality(this.sharedPreferences.getString(SHAREDPREF_KEY_NATIONALITY,null));
        loggedInUser.setSalary(this.sharedPreferences.getString(SHAREDPREF_KEY_SALARY,null));
        loggedInUser.setFamilySize(this.sharedPreferences.getString(SHAREDPREF_KEY_FAMILY_SIZE,null));
        loggedInUser.setOccupation(this.sharedPreferences.getString(SHAREDPREF_KEY_OCCUPATION,null));
        return loggedInUser;
    }

    public AgencyUser getCurrentlyLoggedInAgencyUser(){
        AgencyUser loggedInUser = new AgencyUser();
        loggedInUser.setId(this.sharedPreferences.getInt(SHAREDPREF_KEY_ID, -1));
        loggedInUser.setAgencytName(this.sharedPreferences.getString(SHAREDPREF_KEY_AGENCYNAME,null));
        loggedInUser.setEmailAddress(this.sharedPreferences.getString(SHAREDPREF_KEY_EMAIL,null));
        loggedInUser.setCountry(this.sharedPreferences.getString(SHAREDPREF_KEY_COUNTRY,null));
        loggedInUser.setCity(this.sharedPreferences.getString(SHAREDPREF_KEY_CITY,null));
        loggedInUser.setPhoneNumber(this.sharedPreferences.getString(SHAREDPREF_KEY_PHONE,null));
        return loggedInUser;
    }

    public boolean getUser_Type(){
        return this.sharedPreferences.getBoolean(SHAREDPREF_KEY_TYPE,false);
    }


    public void clearLoginSessionOnLogout(){
        this.editor.clear();
        this.editor.commit();
        this.isLoggedIn = false;
        this.editor.putBoolean(SHAREDPREF_KEY_ISLOGGEDIN, this.isLoggedIn);
        this.editor.commit();
    }
}
