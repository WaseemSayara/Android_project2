package edu.bzu.labproject.Models;

import com.google.gson.annotations.SerializedName;

public class Car {


    private Integer carId;
    @SerializedName("year")
    private Integer yearOfProduction;
    @SerializedName("make")
    private String manufacturingCompany;
    @SerializedName("model")
    private String carModel;
    @SerializedName("distance")
    private String distanceTraveled;
    @SerializedName("price")
    private String carPrice;
    @SerializedName("accidents")
    private boolean hadAccidents;

    public Car(){}

    public Car(Integer yearOfProduction, String manufacturingCompany, String carModel, String distanceTraveled,
               String carPrice, boolean hadAccidents){

        this.yearOfProduction = yearOfProduction;
        this.manufacturingCompany = manufacturingCompany;
        this.carModel = carModel;
        this.distanceTraveled = distanceTraveled;
        this.carPrice = carPrice;
        this.hadAccidents = hadAccidents;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getManufacturingCompany() {
        return manufacturingCompany;
    }

    public void setManufacturingCompany(String manufacturingCompany) {
        this.manufacturingCompany = manufacturingCompany;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(String distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public boolean HadAccidents() {
        return hadAccidents;
    }

    public void setHadAccidents(boolean hadAccidents) {
        this.hadAccidents = hadAccidents;
    }

    @Override
    public String toString() {
        return "Car{" +
                "yearOfProduction=" + yearOfProduction +
                ", manufacturingCompany='" + manufacturingCompany + '\'' +
                ", carModel='" + carModel + '\'' +
                ", distanceTraveled='" + distanceTraveled + '\'' +
                ", carPrice='" + carPrice + '\'' +
                ", hadAccidents=" + hadAccidents + '}';
    }
}
