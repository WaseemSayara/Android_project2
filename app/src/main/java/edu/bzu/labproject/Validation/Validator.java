package edu.bzu.labproject.Validation;

public class Validator {
    public static boolean checkRequiredFieldConstraint(String field){
        if(!field.isEmpty())
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
}
