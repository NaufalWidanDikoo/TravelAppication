package com.example.travelapp.model;

import com.google.android.gms.maps.model.LatLng;

public class ModelMap {

    public String name;
    public LatLng center;

    public ModelMap() {}

    public ModelMap(String name, double lat, double lng) {
        this.name = name;
        this.center = new LatLng(lat, lng);
    }
}
