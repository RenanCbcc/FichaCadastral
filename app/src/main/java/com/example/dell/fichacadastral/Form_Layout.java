package com.example.dell.fichacadastral;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Dell on 20/07/2017.
 */

public class Form_Layout extends Fragment implements TextWatcher {
    private EditText edtCep;
    private Spinner spinner;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_layout, container, false);
        spinner = (Spinner) view.findViewById(R.id.snp_Estados);
        configureSpinner();
        spinner.setSelection(1);
        edtCep = (EditText) view.findViewById(R.id.edt_Cep);
        edtCep.addTextChangedListener(this);
        if (isAdded()) {
            context = inflater.getContext();
        }
        return view;

    }

    private void configureSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.string_array_estados));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();
        if (zipCode.length() == 8) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

}
