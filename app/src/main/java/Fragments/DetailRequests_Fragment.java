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
    private static final String DIALOG_TAG = "detailDialog";
    private static final String EXTRA_LOADEDREQUEST = "detail";
    private TextView txt_EnderecoGPS_Coleta;
    private TextView txt_quantidade;
    private TextView txt_peso;
    private TextView txt_tamanho;
    private TextView txt_endereco;
    private Button btn_Aceitar;
    private Button btn_Cancelar;
    private LoadedRequest loadedRequest;

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
        loadedRequest = (LoadedRequest) getArguments().getSerializable(EXTRA_LOADEDREQUEST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.detailrequests_layout, container, false);
        txt_EnderecoGPS_Coleta = (TextView) layout.findViewById(R.id.txt_EnderecoGPS_Coleta);
        txt_quantidade = (TextView) layout.findViewById(R.id.txt_quantidade);
        txt_peso = (TextView) layout.findViewById(R.id.txt_peso);
        txt_tamanho = (TextView) layout.findViewById(R.id.txt_tamanho);
        txt_endereco = (TextView) layout.findViewById(R.id.txt_endereco);
        btn_Aceitar = (Button) layout.findViewById(R.id.btn_accept);
        btn_Cancelar = (Button) layout.findViewById(R.id.btn_accept);

        btn_Aceitar.setOnClickListener(this);
        btn_Cancelar.setOnClickListener(this);

        getDialog().setTitle("Deseja aceitar esta Solicitação?");
        Toast.makeText(getActivity(),loadedRequest.toString() , Toast.LENGTH_LONG).show();

        if (loadedRequest != null) {
            txt_EnderecoGPS_Coleta.setText(loadedRequest.getEnderecoGPS_Coleta());
            txt_tamanho.setText(String.valueOf(loadedRequest.getComplemento_Coleta()));
            txt_quantidade.setText(String.valueOf(loadedRequest.getComplemento_Entrega()));
            txt_peso.setText(String.valueOf(loadedRequest.getComplemento_Entrega()));
            //txt_endereco.setText(loadedRequest);

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
                }
        }


    }
    public void open(FragmentManager fragmentManager) {
        if (fragmentManager.findFragmentByTag(DIALOG_TAG) == null) {
            show(fragmentManager, DIALOG_TAG);
        }
    }


/*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE ){
                    Toast.makeText(getActivity(), "Enviando Solicitação: ",Toast.LENGTH_LONG).show();
                }
            }
        };

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())

                .setTitle("Deseja aceitar esta Solicitação?")
                .setMessage("Detalhes da Solicitação:")
                .setPositiveButton("",listener)
                .create();

        return alertDialog;
    }

*/

}
