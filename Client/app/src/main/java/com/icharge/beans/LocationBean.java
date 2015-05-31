package com.icharge.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(Include.NON_EMPTY)
public class LocationBean {
	@JsonProperty("slow")
	private String slow_count;
	@JsonProperty("fast")
	private String fast_count;
	@JsonProperty("total")
	private String total_count;
    @JsonProperty("name")
	private String name;
    @JsonProperty("address")
	private String location;
    @JsonProperty("score")
    private String score;
    @JsonProperty("fast_valid")
    private String fastValid;
    @JsonProperty("slow_valid")
    private String slowValid;
	@JsonProperty("city")
    private String city;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("id")
    private String id;
    @JsonProperty("price")
    private String price;

    public LocationBean(String slow_count, String fast_count, String total_count, String name, String location, String score, String fastValid, String slowValid, String city, String longitude, String telephone, String latitude, String id, String price) {
        this.slow_count = slow_count;
        this.fast_count = fast_count;
        this.total_count = total_count;
        this.name = name;
        this.location = location;
        this.score = score;
        this.fastValid = fastValid;
        this.slowValid = slowValid;
        this.city = city;
        this.longitude = longitude;
        this.telephone = telephone;
        this.latitude = latitude;
        this.id = id;
        this.price = price;
    }

    public LocationBean(){}

    public String getSlow_count() {
        return slow_count;
    }

    public void setSlow_count(String slow_count) {
        this.slow_count = slow_count;
    }

    public String getFast_count() {
        return fast_count;
    }

    public void setFast_count(String fast_count) {
        this.fast_count = fast_count;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFastValid() {
        return fastValid;
    }

    public void setFastValid(String fastValid) {
        this.fastValid = fastValid;
    }

    public String getSlowValid() {
        return slowValid;
    }

    public void setSlowValid(String slowValid) {
        this.slowValid = slowValid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
