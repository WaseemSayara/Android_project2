package edu.bzu.labproject.Models;

import com.google.gson.annotations.SerializedName;

public class House {


    private Integer houseId;
    @SerializedName("city")
    private String city;
    @SerializedName("postalAddress")
    private String postalAddress;
    @SerializedName("area")
    private Integer area;
    @SerializedName("constructionYear")
    private Integer constructionYear;
    @SerializedName("bedrooms")
    private Integer bedrooms;
    @SerializedName("price")
    private Integer price;
    @SerializedName("status")
    private boolean status;
    @SerializedName("furnished")
    private boolean furnished;
    @SerializedName("photos")
    private String photos;
    @SerializedName("availabilityDate")
    private String availabilityDate;
    @SerializedName("description")
    private String description;
    @SerializedName("agencyName")
    private String agencyName;

    public House() {
    }

    public House(Integer carId, String city, String postalAddress, Integer area,
                 Integer constructionYear, Integer bedrooms, Integer price, boolean status,
                 boolean furnished, String photos, String availabilityDate, String description) {
        this.houseId = carId;
        this.city = city;
        this.postalAddress = postalAddress;
        this.area = area;
        this.constructionYear = constructionYear;
        this.bedrooms = bedrooms;
        this.price = price;
        this.status = status;
        this.furnished = furnished;
        this.photos = photos;
        this.availabilityDate = availabilityDate;
        this.description = description;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer carId) {
        this.houseId = carId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "House{" +
                "carId=" + houseId +
                ", city='" + city + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", area=" + area +
                ", constructionYear=" + constructionYear +
                ", bedrooms=" + bedrooms +
                ", price=" + price +
                ", status=" + status +
                ", furnished=" + furnished +
                ", photos='" + photos + '\'' +
                ", availabilityDate=" + availabilityDate +
                ", description='" + description + '\'' +
                '}';
    }
}