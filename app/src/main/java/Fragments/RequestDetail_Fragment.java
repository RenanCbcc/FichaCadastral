package Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dell.fichacadastral.R;

import Classes.LoadedRequest;
import Interfaces.onModifyFragment;
import Interfaces.onRequestAccepted;

/**
 * Created by Dell on 08/09/2017.
 */

public class RequestDetail_Fragment extends Fragment {
    private static final String EXTRA_REQUEST = "request"; // Primary Key
    private LoadedRequest loadedRequest;
    private Button btnsend;

    public static Profile_Fragment newInstance(LoadedRequest loadedRequest) {//METODO CONSTRUTOR
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_REQUEST, loadedRequest);
        Profile_Fragment fragment = new Profile_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadedRequest = (LoadedRequest) getArguments().getSerializable(EXTRA_REQUEST);

    }


    @Override
    public void onClick(View view) {
        Activity activity = getActivity();
        if (activity instanceof onRequestAccepted) {
            if (view.getId() == R.id.btn_Salvar) {
                onModifyFragment listener = (onRequestAccepted) activity;
                listener.sendRequestAccepted(loadedRequest);
            } else if (view.getId() == R.id.btn_Cancelar) {
                // TODO implments something here!!!
            }

        }
    }



}
