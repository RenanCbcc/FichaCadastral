package com.example.dell.fichacadastral;

import android.content.Context;
import android.os.AsyncTask;
import android.location.Address;
import java.lang.ref.WeakReference;

/**
 * Created by Dell on 23/07/2017.
 */

public class AddressRequest extends AsyncTask<Void, Void,Address> {

    private Context activity;

    public AddressRequest( Context activity ){
        this.activity =  activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.get().lockFields( true );
    }

    @Override
    protected Address doInBackground(Void... voids) {

        try{
            String jsonString = JsonRequest.request( activity.get().getUriRequest() );
            Json gson = new Json();

            return gson.fromJson(jsonString, Address.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

}
