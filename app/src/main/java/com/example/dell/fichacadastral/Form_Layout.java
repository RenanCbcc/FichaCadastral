package com.example.dell.fichacadastral;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
    private EditText edtRua;
    private EditText edtCidade;
    private EditText edtComplemento;
    private EditText edtBairro;

    private Spinner spinner;
    private AddressTask addressTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_layout, container, false);
        spinner = (Spinner) view.findViewById(R.id.snp_Estados);
        configureSpinner();
        spinner.setSelection(1);
        edtCep = (EditText) view.findViewById(R.id.edt_Cep);
        edtRua = (EditText) view.findViewById(R.id.edt_rua);
        edtCidade = (EditText) view.findViewById(R.id.edt_cidade);
        edtComplemento = (EditText) view.findViewById(R.id.edt_complemento);
        edtBairro = (EditText) view.findViewById(R.id.edt_bairro);

        edtCep.addTextChangedListener(this);

        if (isAdded()) {
            //verify if the fragment is attached at the activity
        }

        if (!JsonRequest.hasConnection(getActivity())) {
            edtCep.setText("No connection");
        }
        return view;

    }

    private void configureSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.string_array_estados));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    /**
     * This method verify whether the field text has changed, if yes; it executes a search for
     * the Cep.
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();
        if (zipCode.length() == 8) {
            if (addressTask != null || addressTask.getStatus() != AsyncTask.Status.RUNNING) {
                addressTask = new AddressTask();
                addressTask.execute();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /**
     * this class will make a requisition.
     */
    public class AddressTask extends AsyncTask<Void, Void, LoadedAddress> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            edtCep.setEnabled(false);

        }

        @Override
        protected LoadedAddress doInBackground(Void... voids) {
            try {
                LoadedAddress loadedAddress = JsonRequest.loadJsonAddress("MyCEP");
                return loadedAddress;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(LoadedAddress address) {
            super.onPostExecute(address);

            if (getActivity() != null) {
                edtCep.setEnabled(true);

                if (address != null) {
                    edtCep.setText(address.getCep());
                    edtRua.setText(address.getLogradouro());
                    edtBairro.setText(address.getBairro());
                    edtComplemento.setText(address.getComplemento());
                    edtCidade.setText(address.getLocalidade());
                }
            }
        }

    }
}
