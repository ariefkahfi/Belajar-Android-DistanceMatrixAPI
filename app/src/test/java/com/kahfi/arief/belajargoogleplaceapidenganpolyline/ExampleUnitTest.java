package com.kahfi.arief.belajargoogleplaceapidenganpolyline;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kahfi.arief.belajargoogleplaceapidenganpolyline.constant.BaseURL;

import org.junit.Test;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBacaJson()throws  Exception{
        URL url = new URL(BaseURL.dummyURL());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();


        InputStreamReader iReader = new InputStreamReader(con.getInputStream());


        JsonReader reader = new JsonReader(iReader);

        JsonElement el = new JsonParser().parse(reader);

        JsonArray dest = el.getAsJsonObject().get("destination_addresses").getAsJsonArray();
        JsonArray origin = el.getAsJsonObject().get("origin_addresses").getAsJsonArray();


        String destKe0 = dest.get(0).getAsString();
        String originKe0 = origin.get(0).getAsString();


        System.err.println(destKe0);
        System.err.println(originKe0);


        JsonArray rows = el.getAsJsonObject().get("rows").getAsJsonArray();

        JsonArray elements = rows.get(0).getAsJsonObject().get("elements").getAsJsonArray();




    }
}