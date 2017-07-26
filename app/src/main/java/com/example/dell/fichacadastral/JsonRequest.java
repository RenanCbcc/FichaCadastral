package com.example.dell.fichacadastral;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dell on 23/07/2017.
 */

public class JsonRequest {
    public static String request(String uri) throws Exception {

        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader r = new BufferedReader(new InputStreamReader(in));

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            jsonString.append(line);
        }

        urlConnection.disconnect();

        return jsonString.toString();
    }

    public static boolean hesConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected());
    }


}
