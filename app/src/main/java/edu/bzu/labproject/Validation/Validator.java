package edu.bzu.labproject.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {
    public static boolean checkRequiredFieldConstraint(String field){
        if(!field.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean checkRequiredFieldConstraint(Integer field){
        if(field!=null)
            return true;
        else
            return false;
    }
    public static boolean checkFirstNameValidity(String firstName){
        if(firstName.length()< 3 || firstName.length() > 20)
            return false;
        else
            return true;
    }

    public static boolean checkLastNameValidity(String lastName){
        if(lastName.length()< 3 || lastName.length() > 20)
            return false;
        else
            return true;
    }

    public static boolean checkOccupationValidity(String occupation){
        if(occupation.length() > 20)
            return false;
        else
            return true;
    }

    public static boolean checkAgencyNameValidity(String agencyName){
        if(agencyName.length() > 20)
            return false;
        else
            return true;
    }

    public static boolean checkSalaryValidity(String salary){
        try {
            double d = Double.parseDouble(salary);
        } catch (NumberFormatException nfe) {
            return false;
        }
            return true;
    }

    public static boolean checkFamilySizeValidity(String familySize){
        try {
            double d = Double.parseDouble(familySize);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean checkPasswordValidity(String password){
        String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%?&])[A-Za-z\\d@#$!%?&]{8,15}$";
        if(password.matches(PASSWORD_REGEX))
            return true;
        else
            return false;
    }

    public static boolean checkIfPasswordsMatch (String password, String confirmPassword){
        if(password.equals(confirmPassword))
            return true;
        else
            return false;
    }
    public static boolean checkPhoneNumberValidity(String phoneNumber){
        String PHONE_NUMBER_REGEX = "^(?=.{9,})[0-9]+";
        if(phoneNumber.matches(PHONE_NUMBER_REGEX))
            return true;
        else
            return false;
    }
    public static boolean checkEmailAddressValidity(String emailAddress){
        String EMAIL_ADDRESS_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        if(emailAddress.matches(EMAIL_ADDRESS_REGEX))
            return true;
        else
            return false;
    }

    public static boolean checkDescriptionValidity(String description){
        if(description.length() > 20 && description.length() < 200)
            return true;
        else
            return false;
    }

    public static boolean checkDateValidity(String date){
        String Date_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if(date.matches(Date_REGEX))
            return true;
        else
            return false;
    }

    public static boolean checkPeriodValidity(String period, String Rented, String date1) throws ParseException {
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        Date date2=formatter1.parse(date1);
        long millis = date2.getTime();

        Date dateNow = new Date();
        long timeMilli = dateNow.getTime();

        if (period.isEmpty()){
            return false;
        }
        else if(Rented.compareTo("Rented") == 0){
            return false;
        }
        else if(millis > timeMilli){
            return false;
        }
        else {
            try {
                int d = Integer.parseInt(period);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

    }
}
