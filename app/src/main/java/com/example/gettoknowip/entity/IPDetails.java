package com.example.gettoknowip.entity;

import com.google.gson.annotations.SerializedName;

public class IPDetails {
    @SerializedName(value = "country")
    private String country;
    @SerializedName(value = "region")
    private String region;
    @SerializedName(value = "city")
    private String city;
    @SerializedName(value = "postal")
    private String postal;
    @SerializedName(value = "org")
    private String organization;
    @SerializedName(value = "loc")
    private String latLong;

    public IPDetails(
            String country,
            String region,
            String city,
            String postal,
            String organization,
            String latLong
    ) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.postal = postal;
        this.organization = organization;
        this.latLong = latLong;
    }

    //Getters.
    public String getRegion() { return region; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getOrganization() { return organization; }
    public String getLatLong() { return latLong; }
    public String getPostal() { return postal; }

    //Setters.
    public void setRegion(String region) { this.region = region; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setOrganization(String hostName) { this.organization = hostName; }
    public void setLatLong(String latLong) { this.latLong = latLong; }
    public void setPostal(String postal) { this.postal = postal; }
}
