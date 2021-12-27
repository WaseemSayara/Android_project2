package edu.bzu.labproject.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
    public static String generate_MD5_Secure_Password(String password){
        String generatedSecurePassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] hashedPassInBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< hashedPassInBytes.length ;i++)
            {
                sb.append(Integer.toString((hashedPassInBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedSecurePassword = sb.toString();

        }

        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedSecurePassword;
    }

    public static boolean verifyPasswordsMatch(String passwordToCheck, String passwordHash){
        String MD5PasswordHash = generate_MD5_Secure_Password(passwordToCheck);
        if(MD5PasswordHash.equals(passwordHash))
            return true;
        else
            return false;
    }

}

