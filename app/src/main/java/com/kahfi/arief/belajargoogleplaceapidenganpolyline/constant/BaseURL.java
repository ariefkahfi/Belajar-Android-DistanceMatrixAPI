package com.kahfi.arief.belajargoogleplaceapidenganpolyline.constant;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.R;

/**
 * Created by Arief on 9/5/2017.
 */

public final class BaseURL {
    public static final String BASE_URL
            ="https://maps.googleapis.com/maps/api/distancematrix/json?";


    public static String RequestURL(LatLng latLng1, LatLng latLng2,Context context){
        return BASE_URL+"origins="+latLng1.latitude+","+latLng1.longitude+"&destinations="+latLng2.latitude+","+latLng2.longitude+"&key=" +
                context.getResources().getString(R.string.google_maps_key);
    }

    public static String dummyURL(){
        return "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-2.9832214,104.729212&destinations=-2.9759399,104.7416514&key=AIzaSyAR6WPg06EYG94R2Q1Oq9AAovRsDJCEDjU";
    }
}
