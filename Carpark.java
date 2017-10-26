package com.example.louis987.mapfeature;

/**
 * Created by anthonykasiyazi on 23/09/2017.
 */

public class Carpark {

    private int id;
    private String name;
    private String address;
    private int cap;
    private int available;

    private double latitude;
    private double longitude;

    public Carpark()
    {
    }
    public Carpark(int id,String name,String address,int available , int cap, double latitude, double longitude)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.available = available;
        this.cap=cap;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCap(int cap) {

        this.cap = cap;
    }

    public void setAvailable(int available){

        this.available = this.available + available;

    }

    public void saveSpot(){

        this.available = this.available - 1;
    }
    public void setLatitude(double latitude){

        this.latitude = latitude;

    }public void setLongitude(double longitude){

        this.longitude = longitude;}
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public int getCap(){
        return cap;
    }

    public int getAvailable(){
        return available;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }






}
