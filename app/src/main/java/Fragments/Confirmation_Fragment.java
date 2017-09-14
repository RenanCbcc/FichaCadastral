package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dell.fichacadastral.R;

import Classes.Deliveryman;

/**
 * Created by Rodrigo Henrique on 13/09/2017.
 */

public class Confirmation_Fragment extends Fragment implements View.OnClickListener {
    private static final String EXTRA_DELIVEYMAN = "deliveryman"; // tuple{Key,value}
    private static final String EXTRA_ORIGIN = "origin"; // tuple{Key,value}

    private Deliveryman deliveryman;
    private Button confirmarColeta;
    private Button confirmarEntrega;
    private Button cancelar;

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

        View view = inflater.inflate(R.layout.tela_confirmacao_opcoes_tipo, container, false);
        confirmarColeta = view.findViewById(R.id.btnconfirmarcoleta);
        confirmarEntrega = view.findViewById(R.id.btnconfirmarentrega);
        cancelar = view.findViewById(R.id.btncancelar1);

        if (deliveryman != null) {
            confirmarColeta.setOnClickListener(this);
            confirmarEntrega.setOnClickListener(this);
            cancelar.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btncancelar1:
                getActivity().getFragmentManager().popBackStack();
            case R.id.btnconfirmarcoleta:
                deliveryman.setModoConfimacao("COLETA");
                fragment = Confirmation_Mode_Fragment.newInstance(deliveryman);

            case R.id.btnconfirmarentrega:
                deliveryman.setModoConfimacao("ENTREGA");
                fragment = Confirmation_Mode_Fragment.newInstance(deliveryman);

        }
    }
}
