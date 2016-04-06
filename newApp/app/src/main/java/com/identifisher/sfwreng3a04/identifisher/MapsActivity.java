package com.identifisher.sfwreng3a04.identifisher;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GeoApiContext context = new GeoApiContext()
            .setApiKey("AIzaSyApENP9Avq4mABio54U3ERYRpK_5LEZuA8")
            .setQueryRateLimit(100, 0);
    double latitude;
    double longitude;
    int waitc = 0;



//    public static GeocodingApiRequest newRequest(GeoApiContext context) {
//        return new GeocodingApiRequest(context);
//    }

    public void testSimpleGeocode() throws Exception {
        GeocodingResult[] results = GeocodingApi.newRequest(context).address(LookUpLake.inputText).await();
        assignLatLong(results);
    }


    private void assignLatLong(GeocodingResult[] results) {
        latitude = results[0].geometry.location.lat;
        longitude = results[0].geometry.location.lng;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            testSimpleGeocode();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        while (waitc < 100000000) {
            waitc++;
        }
        mMap = googleMap;
        LatLng place = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(place).title(LookUpLake.inputText));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
    }
}
