package edu.bzu.labproject.Models;

public class AgencyUser {
    private Integer id;
    private String emailAddress;
    private String hashedPassword;
    private String agencyName;
    private String country;
    private String city;
    private String phoneNumber;

    public AgencyUser() {
    }

    public AgencyUser(Integer id, String emailAddress, String hashedPassword, String agencyName,
                 String country, String city, String phoneNumber) {

        this.id = id;
        this.emailAddress = emailAddress;
        this.hashedPassword = hashedPassword;
        this.agencyName = agencyName;
        this.country = country;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencytName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "AgencyUser{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", passwordHash='" + hashedPassword + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
