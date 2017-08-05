package com.example.dell.fichacadastral;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Created by Dell on 20/07/2017.
 */

public class Form_Layout extends Fragment implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {
    private EditText edtCep;
    private EditText edtRua;
    private EditText edtCidade;
    private EditText edtComplemento;
    private EditText edtBairro;
    private ProgressBar progressBar;
    private Spinner spinner;
    private EditText edtSenha;
    private EditText EdtSenhaRep;
    private EditText edtEmail;
    private AddressTask addressTask;
    private RelativeLayout layout_pai;
    private ImageView foto;
    private Bitmap bitmap;
    private Costumer costumer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_layout, container, false);
        spinner = (Spinner) view.findViewById(R.id.snp_Estados);
        progressBar = (ProgressBar) view.findViewById(R.id.pgb_progress);
        edtCep = (EditText) view.findViewById(R.id.edt_Cep);
        edtRua = (EditText) view.findViewById(R.id.edt_rua);
        edtCidade = (EditText) view.findViewById(R.id.edt_cidade);
        edtComplemento = (EditText) view.findViewById(R.id.edt_complemento);
        edtBairro = (EditText) view.findViewById(R.id.edt_bairro);
        edtSenha = (EditText) view.findViewById(R.id.edt_senha);
        EdtSenhaRep = (EditText) view.findViewById(R.id.edt_rp_senha);
        foto = (ImageView) view.findViewById(R.id.imageView);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        layout_pai = (RelativeLayout) view.findViewById(R.id.layout_pai);
        edtCep.addTextChangedListener(this);
        foto.setOnClickListener(this);
        costumer = new Costumer();
        configureSpinner();

        if (isAdded()) {
            //verify if the fragment is attached at the activity
        }

        if (!JsonRequest.hasConnection(getActivity())) {
            //TODO por um pop up aqui
            edtCep.setText("No connection");
        }
        return view;

    }

    private void configureSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.string_array_estados));


        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);

    }

    private void setSpinner(int arrayId, String uf) {
        String[] states = getResources().getStringArray(arrayId);
        for (int i = 0; i < states.length; i++) {
            if (states[i].endsWith("(" + uf + ")")) {
                spinner.setSelection(i);
                break;
            }
        }
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
            if (addressTask == null || addressTask.getStatus() != AsyncTask.Status.RUNNING) {
                addressTask = new AddressTask(zipCode);
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

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == EdtSenhaRep || (textView == EdtSenhaRep && EditorInfo.IME_ACTION_NEXT == actionId)) {

            if (!edtSenha.getText().toString().equals(EdtSenhaRep.getText().toString())) {
                EdtSenhaRep.setError(getString(R.string.error_msg_senharp));

            }
        }
        if (textView == edtEmail) {
            if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                edtEmail.setError(getString(R.string.error_msg_email));

            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeStream(
                    getActivity().getContentResolver().openInputStream(data.getData()),
                    null,
                    options
            );
            foto.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), R.string.error_msg_image, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this class will make a requisition.
     */
    public class AddressTask extends AsyncTask<Void, Void, LoadedAddress> {

        private final String url;

        public AddressTask(String cep) {
            this.url = "https://viacep.com.br/ws/" + cep + "/json/";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            edtCep.setEnabled(false);
            layout_pai.setBackgroundColor(getResources().getColor(R.color.background_color_loading));
            progressBar.setVisibility(true ? View.VISIBLE : View.GONE);
        }

        @Override
        protected LoadedAddress doInBackground(Void... voids) {
            try {
                LoadedAddress loadedAddress = JsonRequest.loadJsonAddress(url);

                return loadedAddress;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(LoadedAddress address) {
            super.onPostExecute(address);
            progressBar.setVisibility(false ? View.VISIBLE : View.GONE);
            layout_pai.setBackgroundColor(getResources().getColor(R.color.color_whight));
            if (getActivity() != null) {
                edtCep.setEnabled(true);

                if (address != null) {
                    edtCep.setText(address.getCep());
                    edtRua.setText(address.getLogradouro());
                    edtBairro.setText(address.getBairro());
                    edtComplemento.setText(address.getComplemento());
                    edtCidade.setText(address.getLocalidade());
                    setSpinner(R.array.string_array_estados, address.getUf());
                    costumer.setLoadedAddress(address);
                }
            }
        }

    }
}
