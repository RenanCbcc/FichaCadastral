package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.fichacadastral.R;

import Classes.LoadedRequest;
import Interfaces.onRequestAccepted;

/**
 * Created by Dell on 07/09/2017.
 */

public class DetailRequests_Fragment extends DialogFragment implements View.OnClickListener {
    private LoadedRequest loadedRequest;
    private static final String DIALOG_TAG = "detailDialog";
    private static final String EXTRA_LOADEDREQUEST = "detail";
    private TextView EnderecoGPS_Coleta;
    private TextView Complemento_Coleta;
    private TextView EnderecoGPS_Entrega;
    private TextView Complemento_Entrega;
    private Button btn_Aceitar;
    private Button btn_Cancelar;

    public static DetailRequests_Fragment newInstance(LoadedRequest loadedRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_LOADEDREQUEST, loadedRequest);
        DetailRequests_Fragment dialog = new DetailRequests_Fragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadedRequest = (LoadedRequest) getArguments().getSerializable(EXTRA_LOADEDREQUEST);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.detailrequests_layout, container, false);
        EnderecoGPS_Coleta = (TextView) layout.findViewById(R.id.txt_enderecoGPS_Coleta);
        Complemento_Coleta = (TextView) layout.findViewById(R.id.txt_complemento_Coleta);
        EnderecoGPS_Entrega = (TextView) layout.findViewById(R.id.txt_enderecoGPS_Entrega);
        Complemento_Entrega = (TextView) layout.findViewById(R.id.txt_complemento_Entrega);
        btn_Aceitar = (Button) layout.findViewById(R.id.btn_accept);
        btn_Cancelar = (Button) layout.findViewById(R.id.btn_accept);

        btn_Aceitar.setOnClickListener(this);
        btn_Cancelar.setOnClickListener(this);

        getDialog().setTitle("Deseja aceitar esta Solicitação?");
        if (loadedRequest != null) {

            EnderecoGPS_Coleta.setText("Coleta: "+loadedRequest.getEnderecoGPS_Coleta().toString());
            Complemento_Coleta.setText("Complemento: "+loadedRequest.getComplemento_Coleta());
            EnderecoGPS_Entrega.setText("Entrega: "+loadedRequest.getComplemento_Entrega());
            Complemento_Entrega.setText("Complemento: "+loadedRequest.getComplemento_Entrega());

        }

        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
            case R.id.btn_accept:
                Activity activity = getActivity();
                if (activity instanceof onRequestAccepted) {
                    onRequestAccepted listener = (onRequestAccepted) activity;
                    listener.sendRequestAccepted(this.loadedRequest);
                    dismiss();
                }
        }


    }

    public void open(FragmentManager fragmentManager) {
        if (fragmentManager.findFragmentByTag(DIALOG_TAG) == null) {
            show(fragmentManager, DIALOG_TAG);
        }
    }

}
