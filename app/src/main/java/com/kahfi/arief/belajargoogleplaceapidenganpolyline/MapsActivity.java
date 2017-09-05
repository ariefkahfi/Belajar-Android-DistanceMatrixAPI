package com.kahfi.arief.belajargoogleplaceapidenganpolyline;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.constant.BaseURL;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.dialogHandler.AlertDialogMaker;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.listener.StatusListener;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.webService.AsyncRequestDistanceMatrix;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener,StatusListener {

    private GoogleMap mMap;
    private Button pickPP,gantiLokasi;

    private int statusPick ;

    private List<LatLng> latLngList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        pickPP = (Button)findViewById(R.id.pickPlacePicker);
        gantiLokasi = (Button)findViewById(R.id.changeLocation);

        pickPP.setOnClickListener(this);
        gantiLokasi.setOnClickListener(this);

        gantiLokasi.setEnabled(false);

        mapFragment.getMapAsync(this);



    }


    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.pickPlacePicker :
              ambilPlacePicker();
              break;
          case R.id.changeLocation :
              changeLocation();
              break;


      }
    }


    private void changeLocation(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);


        dialog.setTitle("Alert dialog info title");
        dialog.setMessage("Are you sure to change your location and clear all markers now ? ");

        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickPP.setEnabled(true);
                mMap.clear();
                statusPick = 0;
                latLngList.clear();
                gantiLokasi.setEnabled(false);
            }
        });

        dialog.setNegativeButton("close",null);

        dialog.show();
    }





    private void drawPolyline(){

        Log.i("INFO","Drawing Polyline....");

        mMap.addPolyline(new PolylineOptions()
        .width(5).color(Color.RED).addAll(latLngList));

        Log.i("INFO","Drawing Polyline finished....");


        distanceMatrixAPIResponse();

    }


    private void distanceMatrixAPIResponse() {
       LatLng latlng1 = latLngList.get(0);
       LatLng latlng2 = latLngList.get(1);

       String url =  BaseURL.RequestURL(latlng1,latlng2,MapsActivity.this);

       Log.i("INFO_URL",url);


       new AsyncRequestDistanceMatrix(MapsActivity.this)
               .execute(url);

    }


    private void ambilPlacePicker(){
        try{
            Intent inten = new PlacePicker.IntentBuilder().build(MapsActivity.this);
            startActivityForResult(inten,1);
        }catch (Exception ex){
            Log.e("Err ambilPlacePicker()",ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Place place = PlacePicker.getPlace(MapsActivity.this,data);

            LatLng latLng = place.getLatLng();


                mMap.addMarker(new MarkerOptions().position(latLng).title(place.getAddress().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                latLngList.add(latLng);
                Log.i("INFO","sudah mMap addMarker()");

                statusPick++;

                if(statusPick==2){
                    pickPP.setEnabled(false);
                    setStatus(statusPick);
                }



        }else{
            //TODO else
            Log.i("INFO","else onActivityResult()");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void setStatus(int status) {
       Log.i("INFO","Status pick = " + status);
        if(status==2){
            drawPolyline();
            gantiLokasi.setEnabled(true);
        }
    }
}
