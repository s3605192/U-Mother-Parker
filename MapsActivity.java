package com.example.louis987.mapfeature;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

import java.io.*;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnInfoWindowClickListener, DirectionFinderListener {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int numCarparks = 10;
    private String[] names = new String[numCarparks];


    private CarParkDatabase db = new CarParkDatabase(15);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        startActivity(new Intent(MapsActivity.this, LoginActivity.class));


        // Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");


        db.addCarPark(new Carpark(1, "Wilson Parking", "62 La Trobe Street, Melbourne 3000", 24, 24, -37.8084, 144.9670));
        db.addCarPark(new Carpark(2, "Wilson Parking Melbourne Central Car Park", "300 Lonsdale Street,Melbourne 3000", 203, 880, -37.8117, 144.9634));
        db.addCarPark(new Carpark(3, "Secure Parking QV", "180 Lonsdale Street, Melbourne 3000", 433, 800, -37.8107, 144.9665));
        db.addCarPark(new Carpark(4, "Metro Parking Management St Francis", "312 Lonsdale Street, Melbourne 3000", 12, 314, -37.8122, 144.9628));
        db.addCarPark(new Carpark(5, "JM Car Parks", "148 Lonsdale Street, Melbourne 3000", 50, 50, -37.8099, 144.9683));
        db.addCarPark(new Carpark(6, "Wilson Parking Southgate Avenue Car Park", "Southgate Avenue, Southbank 3006", 49, 480, -37.8203, 144.9663));
        db.addCarPark(new Carpark(7, "Wilson Parking Eureka", "70 City Road, Southbank 3006", 118, 620, -37.8217, 144.9652));
        db.addCarPark(new Carpark(8, "Wilson Parking Digital Harbour", "Harbour Esplanade, Docklands 3008", 23, 210, -37.8171, 144.9458));
        db.addCarPark(new Carpark(9, "Care Park Harbour Town East", "90 Waterfront Way, Docklands 3008", 548, 2225, -37.8129, 144.9380));
        db.addCarPark(new Carpark(10, "Care Park", "370 Docklands Drive, Docklands 3008", 88, 138, -37.8128, 144.9420));
        db.addCarPark(new Carpark(11, "Crown Metropol Multi Level Parking", "8 Whiteman St, Southbank VIC 3006", 434, 3105, -37.8238081, 144.9598946));

        db.addCarPark(new Carpark(12, "Arts Centre Melbourne Car Park", "4 Sturt St, Southbank VIC 3004", 99, 210, -37.8216984, 144.9678276));


        // Reading all shops
        Log.d("Reading: ", "Reading all shops..");
        List<Carpark> carParks = db.getAllCarParks();


        for (Carpark carPark : carParks) {
            String log = "Id: " + carPark.getId() + " ,Name: " + carPark.getName() + " ,Lat: " + carPark.getLatitude() +
                    " ,Long: " + carPark.getLongitude() +
                    " ,Capacity: " + carPark.getCap() + " ,Address: " + carPark.getAddress();
            // Writing shops  to log
            Log.d("Shop: : ", log);


        }




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int count = 0;


        // Add a marker at RMIT and move the camera
        LatLng rmit = new LatLng(-37.8136, 144.9631);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rmit, 13));






        // TextView textView = (TextView) findViewById(R.id.text1);

        //  CarParkDatabase carParkDatabase = new CarParkDatabase();
        //  carParkDatabase.displayMarkers(mMap);




/*

        try {
            MapsActivity trip1 = new MapsActivity();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        displayMarkers();

*/
      /*  LatLng[] carparks = new LatLng[numCarparks];
        List<Carpark> carParks = db.getAllShops();


        int x=0;
        for (Carpark carPark : carParks) {

            //Can't display markers because of shops at the beginning

            carparks[x] = new LatLng(carPark.getLatitude(), carPark.getLongitude());
            mMap.addMarker(new MarkerOptions().position(carparks[x]).title(carPark.getName())
                    .snippet("Available spots: " + carPark.getAvailable() + "/" + carPark.getCap())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            x++;
        }



*/


        //displayMarkers();



     //   LatLng carpark1 = new LatLng(carPark.getLatitude(), carPark.getLongitude());
        mMap.addMarker(new MarkerOptions().position(rmit).title("Wilson Parking")
                .snippet("Available spots: " )
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        List<Carpark> carParks = db.getAllCarParks();


        for (Carpark carPark : carParks) {
            float markerColor;

            if (carPark.getAvailable() == carPark.getCap()){
                markerColor = BitmapDescriptorFactory.HUE_RED;
            }
            else markerColor = BitmapDescriptorFactory.HUE_GREEN;



            LatLng carpark1 = new LatLng(carPark.getLatitude(), carPark.getLongitude());
            mMap.addMarker(new MarkerOptions().position(carpark1).title("Wilson Parking")
                    .snippet("Available spots: " + carPark.getAvailable() + "/" + carPark.getCap())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));





        }

        mMap.setOnInfoWindowClickListener(this);

        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(Color.BLUE).
                width(10);


        polylinePaths.add(mMap.addPolyline(polylineOptions));


    }


    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            // ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //  ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }


    private void sendRequest(MarkerOptions x, Marker y) {
        String origin = x.getTitle();
        String destination = y.getTitle();
        if (x.getTitle().isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onInfoWindowClick(final Marker marker) {

        final LatLng rmi = new LatLng(-37.8076, 144.9633);


        final MarkerOptions rmit2 = new MarkerOptions();
        rmit2.title("RMIT");

        //Toast.makeText(this, "Info window clicked",
        //  Toast.LENGTH_SHORT).show();

        // TextView textView = (TextView) findViewById(R.id.text_view_id);
        // textView.setText(marker.getTitle());


        final RelativeLayout relative2 = (RelativeLayout) findViewById(R.id.relativeLayout);
        relative2.setVisibility(View.VISIBLE);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText(marker.getTitle());
        textView4.setText(marker.getSnippet());


        final Button button = findViewById(R.id.button3);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                relative2.setVisibility(View.GONE);
                button.setVisibility(View.INVISIBLE);

            }
        });

        final Button button1 = findViewById(R.id.button6);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendRequest(rmit2, marker);


            }
        });

        final Button button2 = findViewById(R.id.button7);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                List<Carpark> carParks = db.getAllCarParks();


                for (Carpark carPark : carParks) {

                    if(marker.getTitle().equals(carPark.getName())){
                        carPark.saveSpot();
                        if (marker.getTitle().equals(carPark)){
                            carPark.setAvailable(-1);
                            String s = "AVA" + carPark.getAvailable();
                            Log.d("Shop: : ", s);
                        }

                    }

                }

            }
        });



    }


    public void onInfoWindowClose(Marker marker){

        RelativeLayout relative2 = (RelativeLayout)findViewById(R.id.relativeLayout);
        relative2.setVisibility(View.INVISIBLE);

    }


    public void displayMarkers() {

       /* Carpark carpark1  = new Carpark();

        int i = 65;
        int x = 0;

        while (i < 75) {

            carpark1 = db.getCarPark(i);
            carparks[x] = new LatLng(carpark1.getLatitude(), carpark1.getLongitude());
            mMap.addMarker(new MarkerOptions().position(carparks[x]).title(carpark1.getName())
                    .snippet("Available spots: " + carpark1.getAvailable() + "/" + carpark1.getCap())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            i++;
            x++;
            }*/

        List<Carpark> carParks = db.getAllCarParks();
        LatLng[] carparks = new LatLng[numCarparks];

        int i=0;
        for (Carpark carPark : carParks) {

            //Can't display markers because of shops at the beginning

            carparks[i] = new LatLng(carPark.getLatitude(), carPark.getLongitude());
            mMap.addMarker(new MarkerOptions().position(carparks[i]).title(carPark.getName())
                    .snippet("Available spots: " + carPark.getAvailable() + "/" + carPark.getCap())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


        }




    }






}
