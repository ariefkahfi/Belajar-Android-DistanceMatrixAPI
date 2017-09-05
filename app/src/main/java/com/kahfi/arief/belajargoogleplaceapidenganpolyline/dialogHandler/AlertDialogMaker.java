package com.kahfi.arief.belajargoogleplaceapidenganpolyline.dialogHandler;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kahfi.arief.belajargoogleplaceapidenganpolyline.MapsActivity;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.R;

import java.util.Map;

/**
 * Created by Arief on 9/5/2017.
 */

public class AlertDialogMaker {

    public void buatAlertDialog(Context context, Map<String,Object> map){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_ui,null);

        dialog.setView(v);

        dialog.setCancelable(false);
        dialog.setTitle("Result View");

        TextView tampilAsal = (TextView)v.findViewById(R.id.tampilAsal);
        TextView tampilTujuan = (TextView)v.findViewById(R.id.tampilTujuan);
        TextView tampilJarak = (TextView)v.findViewById(R.id.tampilJarak);
        TextView tampilJam = (TextView)v.findViewById(R.id.tampilJam);

        tampilAsal.setText(String.valueOf(map.get("origin")));
        tampilTujuan.setText(String.valueOf(map.get("dest")));


        tampilJarak.setText(String.valueOf(map.get("text_distance")));
        tampilJam.setText(String.valueOf(map.get("text_duration")));


        dialog.setNeutralButton("close",null);

        dialog.show();
    }
}
