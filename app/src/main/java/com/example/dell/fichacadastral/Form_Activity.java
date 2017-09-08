package com.example.dell.fichacadastral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by Dell on 20/07/2017.
 */

public class Form_Activity extends Fragment implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {
    private EditText edtNome;
    private EditText edtCPF;
    private EditText edtCNPJ;
    private EditText edtCel;
    private EditText edtCep;
    private EditText edtRua;
    private EditText edtCidade;
    private EditText edtPais;
    private EditText edtComplemento;
    private EditText edtBairro;
    private EditText edtNum;

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
    private RadioButton radioCPF;
    private RadioButton radioCNPJ;

    //Pertenciam ao segundo fragment
    private EditText edtPlaca;
    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtTitular;
    private EditText edtBanco;
    private EditText edtAgencia;
    private EditText edtConta;
    private boolean boolCPF;
    private boolean boolCNPJ;
    private boolean boolEmail;
    private boolean senhasIguais;
    //private Costumer costumer;
    //




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_layout, container, false);
        spinner = (Spinner) view.findViewById(R.id.snp_Estados);
        progressBar = (ProgressBar) view.findViewById(R.id.pgb_progress);
        edtNome = (EditText) view.findViewById(R.id.edt_nome);
        edtCel = (EditText) view.findViewById(R.id.edt_nCelular);
        edtCPF = (EditText) view.findViewById(R.id.edt_cpf);
        edtCPF.addTextChangedListener(Mask.insert("###.###.###-##", edtCPF));

        edtCNPJ = (EditText) view.findViewById(R.id.edt_cnpj);
        edtCNPJ.addTextChangedListener(Mask.insert("##.###.###/####-##", edtCNPJ));

        radioCPF = (RadioButton) view.findViewById(R.id.radioBtnPF);
        radioCNPJ = (RadioButton) view.findViewById(R.id.radioBtnPJ);

        edtCep = (EditText) view.findViewById(R.id.edt_Cep);
        edtRua = (EditText) view.findViewById(R.id.edt_rua);
        edtCidade = (EditText) view.findViewById(R.id.edt_cidade);
        edtPais = (EditText) view.findViewById(R.id.edt_pais);
        edtComplemento = (EditText) view.findViewById(R.id.edt_complemento);
        edtBairro = (EditText) view.findViewById(R.id.edt_bairro);
        edtNum = (EditText) view.findViewById(R.id.edt_numero);


        edtSenha = (EditText) view.findViewById(R.id.edt_senha);
        EdtSenhaRep = (EditText) view.findViewById(R.id.edt_rp_senha);
        foto = (ImageView) view.findViewById(R.id.imageView);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        layout_pai = (RelativeLayout) view.findViewById(R.id.layout_pai);
        edtCep.addTextChangedListener(this);
        foto.setOnClickListener(this);
        costumer = new Costumer();
        configureSpinner();
        edtCPF.setVisibility(View.VISIBLE);
        edtCNPJ.setVisibility(View.INVISIBLE);

        //Pertenciam ao segundo fragment
        edtPlaca = (EditText) view.findViewById(R.id.edt_placaVeiculo);
        edtMarca = (EditText) view.findViewById(R.id.edt_marca);
        edtModelo = (EditText) view.findViewById(R.id.edt_modelo);
        edtTitular = (EditText) view.findViewById(R.id.edt_titular);
        edtBanco = (EditText) view.findViewById(R.id.edt_banco);
        edtAgencia = (EditText) view.findViewById(R.id.edt_agencia);
        edtConta = (EditText) view.findViewById(R.id.edt_conta);
        //


        radioCPF.setChecked(true);
        radioCPF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edtCPF.setVisibility(View.VISIBLE);
                    edtCNPJ.setVisibility(View.INVISIBLE);
                    boolCPF = true;
                }
            }
        });
        radioCNPJ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edtCNPJ.setVisibility(View.VISIBLE);
                    edtCPF.setVisibility(View.INVISIBLE);
                    boolCNPJ = true;
                }
            }
        });

        //Pertenciam ao segundo fragment
        Button btnFinalizar = view.findViewById(R.id.button);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Validator.validateNotNull(edtNome,"Preencha o campo nome");
                Validator.validateNotNull(edtCPF,"Preencha o campo CPF");
                Validator.validateNotNull(edtCNPJ,"Preencha o campo CNPJ");
                Validator.validateNotNull(edtCel,"Preencha o campo numero de celular");
                Validator.validateNotNull(edtEmail,"Preencha o campo email");
                Validator.validateNotNull(edtSenha,"Preencha o campo senha");
                Validator.validateNotNull(EdtSenhaRep,"Preencha o campo repetir senha");
                Validator.validateNotNull(edtCep,"Preencha o campo CEP");
                Validator.validateNotNull(edtRua,"Preencha o campo rua");
                Validator.validateNotNull(edtNum,"Preencha o campo número");
                Validator.validateNotNull(edtComplemento,"Preencha o campo complemento");
                Validator.validateNotNull(edtBairro,"Preencha o campo bairro");
                Validator.validateNotNull(edtCidade,"Preencha o campo cidade");
                Validator.validateNotNull(edtPais,"Preencha o campo país");

                Validator.validateNotNull(edtPlaca,"Preencha o campo placa");
                Validator.validateNotNull(edtMarca,"Preencha o campo marca");
                Validator.validateNotNull(edtModelo,"Preencha o campo modelo");
                Validator.validateNotNull(edtTitular,"Preencha o campo titular");
                Validator.validateNotNull(edtBanco,"Preencha o campo banco");
                Validator.validateNotNull(edtAgencia,"Preencha o campo agencia");
                Validator.validateNotNull(edtConta,"Preencha o campo conta");

                if(!(edtSenha.getText().toString().equals(EdtSenhaRep.getText().toString()))){
                    EdtSenhaRep.setError("Senhas não conferem");
                    EdtSenhaRep.setFocusable(true);
                    EdtSenhaRep.requestFocus();
                    senhasIguais = false;
                }
                else {
                    senhasIguais = true;
                }

                boolean cpf_valido = Validator.validateCPF(edtCPF.getText().toString());
                if(!cpf_valido){
                    edtCPF.setError("CPF inválido");
                    edtCPF.setFocusable(true);
                    edtCPF.requestFocus();
                }

                boolean cnpj_valido = Validator.isCNPJ(edtCNPJ.getText().toString());
                if(!cnpj_valido){
                    edtCNPJ.setError("CNPJ inválido");
                    edtCNPJ.setFocusable(true);
                    edtCNPJ.requestFocus();
                }

                boolean email_valido = Validator.validateEmail(edtEmail.getText().toString());
                if(!email_valido){
                    edtEmail.setError("Email inválido");
                    edtEmail.setFocusable(true);
                    edtEmail.requestFocus();
                    boolEmail = false;
                }
                else
                    boolEmail = true;

                if((Validator.validateNotNull(edtNome,"Preencha o campo nome")&&
                        Validator.validateNotNull(edtCel,"Preencha o campo numero de celular")&&
                        Validator.validateNotNull(edtEmail,"Preencha o campo email")&&
                        (boolEmail)&&(senhasIguais)&&
                        Validator.validateNotNull(edtSenha,"Preencha o campo senha")&&
                        Validator.validateNotNull(EdtSenhaRep,"Preencha o campo repetir senha")&&
                        Validator.validateNotNull(edtCep,"Preencha o campo CEP")&&
                        Validator.validateNotNull(edtRua,"Preencha o campo rua")&&
                        Validator.validateNotNull(edtNum,"Preencha o campo número")&&
                        Validator.validateNotNull(edtComplemento,"Preencha o campo complemento")&&
                        Validator.validateNotNull(edtBairro,"Preencha o campo bairro")&&
                        Validator.validateNotNull(edtCidade,"Preencha o campo cidade")&&
                        Validator.validateNotNull(edtPais,"Preencha o campo país")&&
                        Validator.validateNotNull(edtPlaca,"Preencha o campo placa")&&
                        Validator.validateNotNull(edtMarca,"Preencha o campo marca")&&
                        Validator.validateNotNull(edtModelo,"Preencha o campo modelo")&&
                        Validator.validateNotNull(edtTitular,"Preencha o campo titular")&&
                        Validator.validateNotNull(edtBanco,"Preencha o campo banco")&&
                        Validator.validateNotNull(edtAgencia,"Preencha o campo agencia")&&
                        Validator.validateNotNull(edtConta,"Preencha o campo conta"))){

                        if(boolCPF){
                            Validator.validateNotNull(edtCPF,"Preencha o campo cpf");
                            String cpf = edtCPF.getText().toString();
                            costumer.setCPF(cpf);

                        }
                        else if(boolCNPJ){
                            Validator.validateNotNull(edtCNPJ,"Preencha o campo cnpj");
                            String cnpj = edtCNPJ.getText().toString();
                            costumer.setCNPJ(cnpj);
                        }

                            String nome = edtNome.getText().toString();
                            String celular = edtCel.getText().toString();
                            String email = edtEmail.getText().toString();
                            String senha = edtSenha.getText().toString();

                            String placa = edtPlaca.getText().toString();
                            String marca = edtMarca.getText().toString();
                            String modelo = edtModelo.getText().toString();
                            String titular = edtTitular.getText().toString();
                            String banco = edtBanco.getText().toString();
                            String agencia = edtAgencia.getText().toString();
                            String conta = edtConta.getText().toString();

                            costumer.setNome(nome);

                            costumer.setContato(celular);
                            costumer.setEmail(email);
                            costumer.setSenha(senha);
                            costumer.setPlaca_Veiculo(placa);
                            costumer.setMarca_Veiculo(marca);
                            costumer.setModel_Veiculo(modelo);
                            costumer.setTitular_banco(titular);
                            costumer.setBanco(banco);
                            costumer.setAgencia(agencia);
                            costumer.setConta(conta);

                            Toast.makeText( getActivity() , "Dados cadastrados com sucesso!" , Toast.LENGTH_LONG).show();


                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);

                }
            }
        });
        //

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
            if(resultCode != RESULT_CANCELED){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bitmap = BitmapFactory.decodeStream(
                        getActivity().getContentResolver().openInputStream(data.getData()),
                        null,
                        options
                );
                foto.setImageBitmap(bitmap);
            }

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
                    String pais = edtPais.getText().toString();
                    address.setPais(pais);
                    setSpinner(R.array.string_array_estados, address.getUf());
                    costumer.setLoadedAddress(address);

                }
            }
        }

    }
}
