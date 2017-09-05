package com.kahfi.arief.belajargoogleplaceapidenganpolyline.webService;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.dialogHandler.AlertDialogMaker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arief on 9/5/2017.
 */

public class AsyncRequestDistanceMatrix extends AsyncTask<String,Integer,Map<String,Object>> {


    private AlertDialogMaker maker ;
    private Context context;


    public AsyncRequestDistanceMatrix(Context context) {
        this.context=context;
        maker = new AlertDialogMaker();
    }

    @Override
    protected Map<String, Object> doInBackground(String... params) {
        Map<String,Object> map = null;

        try {
            map = new HashMap<>();

            URL link = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)link.openConnection();

            JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));

            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(reader);

            JsonArray dest = el.getAsJsonObject().get("destination_addresses").getAsJsonArray();
            JsonArray origin = el.getAsJsonObject().get("origin_addresses").getAsJsonArray();

            JsonArray rows = el.getAsJsonObject().get("rows").getAsJsonArray();
            JsonArray elements = rows.get(0).getAsJsonObject().get("elements").getAsJsonArray();


            JsonElement distance = elements.get(0).getAsJsonObject().get("distance");
            JsonElement duration =  elements.get(0).getAsJsonObject().get("duration");


            String destKe0 = dest.get(0).getAsString();
            String originKe0 = origin.get(0).getAsString();

            String textDistance = distance.getAsJsonObject().get("text").getAsString();
            String valueDistance = distance.getAsJsonObject().get("value").getAsString();

            String durationText = duration.getAsJsonObject().get("text").getAsString();
            String durationValue = duration.getAsJsonObject().get("value").getAsString();

            map.put("dest",destKe0);
            map.put("origin",originKe0);

            map.put("text_distance",textDistance);
            map.put("value_distance",valueDistance);

            map.put("text_duration",durationText);
            map.put("value_duration",durationValue);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;

    }

    @Override
    protected void onPostExecute(Map<String, Object> map) {
        super.onPostExecute(map);

        Log.i("INFO_DEST", String.valueOf(map.get("dest")));
        Log.i("INFO_ORIGIN", String.valueOf(map.get("origin")));

        Log.i("INFO_TEXT_DISTANCE", String.valueOf(map.get("text_distance")));
        Log.i("INFO_VALUE_DISTANCE",String.valueOf(map.get("value_distance")));

        Log.i("INFO_TEXT_DURATION",String.valueOf(map.get("text_duration")));
        Log.i("INFO_VALUE_DISTANCE",String.valueOf(map.get("value_duration")));


        maker.buatAlertDialog(context,map);

    }
}
