package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.fichacadastral.R;

import java.io.Serializable;

import Classes.Deliveryman;

/**
 * Created by Rodrigo Henrique on 13/09/2017.
 */

public class Confirmation_Mode_Fragment extends Fragment implements View.OnClickListener {
    private static final String EXTRA_DELIVEYMAN = "deliveryman"; // tuple{Key,value}
    private static final String EXTRA_ORIGIN = "origin"; // tuple{Key,value}

    private Deliveryman deliveryman;

    private TextView txtConfirmacaoOpModo;
    private Button btnConfirmarViaQRCode;
    private Button btnConfirmarViaToken;
    private Button btnVoltar;
    private Button btnCancelar;

    public static Confirmation_Fragment newInstance(Deliveryman deliveryman) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_DELIVEYMAN, deliveryman);
        Confirmation_Fragment fragment = new Confirmation_Fragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryman = (Deliveryman) getArguments().getSerializable(EXTRA_DELIVEYMAN);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tela_confirmacao_opcoes_modo, container, false);
        txtConfirmacaoOpModo = view.findViewById(R.id.txtConfirmacaoOpModo);
        btnConfirmarViaQRCode = view.findViewById(R.id.btnconfirmarviaqrcode);
        btnConfirmarViaToken = view.findViewById(R.id.btnconfirmarviatoken);
        btnVoltar = view.findViewById(R.id.btnvoltar1);
        btnCancelar = view.findViewById(R.id.btncancelar2);

        if (deliveryman != null) {
            btnConfirmarViaQRCode.setOnClickListener(this);
            btnConfirmarViaToken.setOnClickListener(this);
            btnVoltar.setOnClickListener(this);
            btnVoltar.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btnconfirmarviaqrcode:
                break;
            case R.id.btnconfirmarviatoken:
                break;
            case R.id.btnvoltar1:
                break;
            case R.id.btncancelar2:
                break;
        }
    }
}
