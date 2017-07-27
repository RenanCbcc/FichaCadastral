package com.example.dell.fichacadastral;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dell on 23/07/2017.
 * This class is responsible for to establish the connection with the server, download the JSON
 * object and return an Address object
 */

public class JsonRequest {


    private static HttpURLConnection toConnect(String file) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(file);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10 * SEGUNDOS);
        connection.setConnectTimeout(15 * SEGUNDOS);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();
        return connection;
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * This method reads the Json file and create the Address objects
     *
     * @return LoadedAddress
     */
    public static LoadedAddress loadJsonAddress(final String CEP_URL_JSON) {
        try {
            HttpURLConnection connection = toConnect(CEP_URL_JSON);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(byteToString(inputStream));
                connection.disconnect();
                return new LoadedAddress(
                        json.getString("cep"),
                        json.getString("logradouro"),
                        json.getString("complemento"),
                        json.getString("bairro"),
                        json.getString("localidade"),
                        json.getString("uf"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String byteToString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        // hugeBuffer will store all the read bytes.
        ByteArrayOutputStream hugeBuffer = new ByteArrayOutputStream();
        // It is necessary to recognize how many was already read.
        int readBytes;
        // We reed 1KB at a time...
        while ((readBytes = is.read(buffer)) != -1) {
            //We copied the amount of bytes read from the buffer to the huge buffer
            hugeBuffer.write(buffer, 0, readBytes);
        }
        return new String(hugeBuffer.toByteArray(), "UTF-8");
    }


}
