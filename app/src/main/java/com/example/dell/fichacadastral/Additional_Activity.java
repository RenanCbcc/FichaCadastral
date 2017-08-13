package com.example.dell.fichacadastral;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dell on 20/07/2017.
 */

public class Additional_Activity extends Fragment {

    private EditText edtPlaca;
    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtTitular;
    private EditText edtBanco;
    private EditText edtAgencia;
    private EditText edtConta;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.additional_layout, container, false);

        edtPlaca = (EditText) view.findViewById(R.id.edt_placaVeiculo);
        edtMarca = (EditText) view.findViewById(R.id.edt_marca);
        edtModelo = (EditText) view.findViewById(R.id.edt_modelo);
        edtTitular = (EditText) view.findViewById(R.id.edt_titular);
        edtBanco = (EditText) view.findViewById(R.id.edt_banco);
        edtAgencia = (EditText) view.findViewById(R.id.edt_agencia);
        edtConta = (EditText) view.findViewById(R.id.edt_conta);

        Button btnFinalizar = view.findViewById(R.id.button);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validator.validateNotNull(edtPlaca,"Preencha o campo placa");
                Validator.validateNotNull(edtMarca,"Preencha o campo marca");
                Validator.validateNotNull(edtModelo,"Preencha o campo modelo");
                Validator.validateNotNull(edtTitular,"Preencha o campo titular");
                Validator.validateNotNull(edtBanco,"Preencha o campo banco");
                Validator.validateNotNull(edtAgencia,"Preencha o campo agencia");
                Validator.validateNotNull(edtConta,"Preencha o campo conta");
                Toast.makeText( getActivity() , "Cadastrado com Sucesso" , Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
