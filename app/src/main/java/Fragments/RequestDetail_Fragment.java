package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import Classes.LoadedRequest;

/**
 * Created by Dell on 08/09/2017.
 */

public class RequestDetail_Fragment extends DialogFragment {
    private static final String EXTRA_REQUEST = "request"; // Primary Key
    private LoadedRequest loadedRequest;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public static RequestDetail_Fragment newInstance(LoadedRequest loadedRequest) {//METODO CONSTRUTOR
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_REQUEST, loadedRequest);
        RequestDetail_Fragment fragment = new RequestDetail_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadedRequest = (LoadedRequest) getArguments().getSerializable(EXTRA_REQUEST);

    }





}
