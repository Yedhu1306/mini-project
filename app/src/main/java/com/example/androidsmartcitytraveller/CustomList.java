package com.example.androidsmartcitytraveller;

public class CustomList {
    String placename;
    String lat, lng;

    public CustomList(String placename, String lat, String lng) {

        this.placename = placename;
        this.lat = lat;
        this.lng=lng;
    }

    public String getPlacename() {
        return placename;
    }

    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }



}
