package com.example.louis987.mapfeature;

import android.content.res.AssetManager;
import android.content.res.*;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonykasiyazi on 23/09/2017.
 */

public class CarParkDatabase extends AppCompatActivity{


    private Carpark[] listofCarparks;
    private int maxNumCarParks;
    private  int currentNum;
    private LatLng[] carparks = new LatLng[maxNumCarParks];


    public CarParkDatabase(int maxNumCarParks){

        this.currentNum = 0;
        this.maxNumCarParks = maxNumCarParks;
        this.listofCarparks= new Carpark[maxNumCarParks];


    }


    public void loadCarparks() {

    }




    public void displayMarkers(GoogleMap mMap1) {

        int i = 0;
        while (i < 10) {
            carparks[i] = new LatLng(listofCarparks[i].getLatitude(), listofCarparks[i].getLongitude());
            mMap1.addMarker(new MarkerOptions().position(carparks[i]).title(listofCarparks[i].getName())
                    .snippet("Available spots: " + listofCarparks[i].getAvailable() + "/" + listofCarparks[i].getCap())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            i++;



        }
    }


    public void addCarPark(Carpark newcarpark){


        if(currentNum<maxNumCarParks) {
            this.listofCarparks[this.currentNum] = newcarpark;
            this.currentNum = this.currentNum + 1;

        }
    }

    public Carpark getCarParkByName(Carpark searchTarget){



            int i =0;
            while(i< this.maxNumCarParks)
            {
                if(this.listofCarparks[i].getName().equals(searchTarget)) {
                    return this.listofCarparks[i];
                }
                i = i+i;
            }
            return null;


    }

    public List<Carpark> getAllCarParks(){


        List<Carpark> carparkList = new ArrayList<Carpark>();

        int i =0;
        while(i< this.currentNum)
        {

            carparkList.add(listofCarparks[i]);
                i++;
        }

        return carparkList;

    }



}


