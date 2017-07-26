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
        edtCep.addTextChangedListener(this);
        if (isAdded()) {
            //verify if the fragment is attached at the activity
        }

        if (!JsonRequest.hasConnection(getActivity())){
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

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();
        if (zipCode.length() == 8) {
            //TODO Invoke LoadedAddress here
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

            return null;
        }

        @Override
        protected void onPostExecute(LoadedAddress address) {
            super.onPostExecute(address);

            if (getActivity() != null) {
                edtCep.setEnabled(true);

                if (address != null) {
                    //TODO
                }
            }
        }

    }
}
