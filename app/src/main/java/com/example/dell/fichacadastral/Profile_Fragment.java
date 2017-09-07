package com.example.dell.fichacadastral;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Dell on 05/08/2017.
 */

public class Profile_Fragment extends Fragment implements TextWatcher, View.OnClickListener, TextView.OnEditorActionListener {
    private static final String EXTRA_CUSTOMER = "customer"; // Primary Key

    private EditText edtTelefone;
    private EditText edtNome;
    private EditText edtCep;
    private EditText edtRua;
    private EditText edtCidade;
    private EditText edtComplemento;
    private EditText edtBairro;
    private ProgressBar progressBar;
    private Spinner spnEstado;
    private EditText edtSenha;
    private EditText EdtSenhaRep;
    private EditText edtEmail;
    private EditText edtPlacaViculo;
    private EditText edtMArcaViculo;
    private EditText edtModelViculo;
    private RelativeLayout layout_pai;
    private ImageView foto;
    private EditText edtTitular;
    private EditText edtBanco;
    private EditText edtAgencia;
    private EditText edtConta;
    private Button btnSalvar;
    private Button btnCancelar;
    private Customer customer;

    public static Profile_Fragment newInstance(Customer customer) {//METODO CONSTRUTOR
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CUSTOMER, customer);
        Profile_Fragment fragment = new Profile_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getSerializable(EXTRA_CUSTOMER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        edtTelefone = view.findViewById(R.id.edt_nCelular);
        edtNome = view.findViewById(R.id.edt_nome);
        edtCep = view.findViewById(R.id.edt_Cep);
        edtRua = view.findViewById(R.id.edt_rua);
        edtCidade = view.findViewById(R.id.edt_cidade);
        edtComplemento = view.findViewById(R.id.edt_complemento);
        edtBairro = view.findViewById(R.id.edt_bairro);
        spnEstado = view.findViewById(R.id.snp_Estados);
        edtSenha = view.findViewById(R.id.edt_senha);
        EdtSenhaRep = view.findViewById(R.id.edt_rp_senha);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPlacaViculo = view.findViewById(R.id.edt_placaVeiculo);
        edtMArcaViculo = view.findViewById(R.id.edt_marca);
        edtModelViculo = view.findViewById(R.id.edt_modelo);
        edtTitular = view.findViewById(R.id.edt_titular);
        edtBanco = view.findViewById(R.id.edt_banco);
        edtAgencia = view.findViewById(R.id.edt_agencia);
        edtConta = view.findViewById(R.id.edt_conta);
        foto = view.findViewById(R.id.img_Foto);
        btnSalvar = view.findViewById(R.id.btn_Salvar);
        btnCancelar = view.findViewById(R.id.btn_Cancelar);

        edtSenha.setOnEditorActionListener(this);
        EdtSenhaRep.setOnEditorActionListener(this);
        edtEmail.setOnEditorActionListener(this);
        btnSalvar.setOnClickListener(this);
        edtTitular.setOnEditorActionListener(this);
        edtBanco.setOnEditorActionListener(this);
        edtAgencia.setOnEditorActionListener(this);
        edtConta.setOnEditorActionListener(this);
        btnCancelar.setOnClickListener(this);

        edtNome.setEnabled(false);
        edtTelefone.setEnabled(false);
        edtRua.setEnabled(false);
        edtCep.setEnabled(false);
        edtCep.setEnabled(false);
        edtCidade.setEnabled(false);
        edtComplemento.setEnabled(false);
        edtBairro.setEnabled(false);
        spnEstado.setEnabled(false);
        edtEmail.setEnabled(false);
        edtPlacaViculo.setEnabled(false);
        edtMArcaViculo.setEnabled(false);
        edtModelViculo.setEnabled(false);

        if (customer != null) {
            edtNome.setText(customer.getNome());
            edtTelefone.setText(customer.getTelefone());
            edtCep.setText(customer.getLoadedAddress().getCep());
            edtRua.setText(customer.getLoadedAddress().getLogradouro());
            edtCidade.setText(customer.getLoadedAddress().getLocalidade());
            edtComplemento.setText(customer.getLoadedAddress().getComplemento());
            edtBairro.setText(customer.getLoadedAddress().getBairro());
            spnEstado.setSelection(0); //TODO costumer.getLoadedAddress().getUf()
            edtSenha.setText(customer.getSenha());
            edtSenha.setEnabled(true);
            EdtSenhaRep.setText(customer.getSenha());
            EdtSenhaRep.setEnabled(true);
            edtEmail.setText(customer.getEmail());
            edtPlacaViculo.setText(customer.getPlaca_Veiculo());
            edtMArcaViculo.setText(customer.getMarca_Veiculo());
            edtModelViculo.setText(customer.getModel_Veiculo());
            foto.setImageBitmap(customer.getFoto());
            edtTitular.setText(customer.getTitular_banco());
            edtTitular.setEnabled(true);
            edtBanco.setText(customer.getBanco());
            edtBanco.setEnabled(true);
            edtAgencia.setText(customer.getAgencia());
            edtAgencia.setEnabled(true);
            edtConta.setText(customer.getConta());
            edtConta.setEnabled(true);

        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();
        if (activity instanceof Deliveries_Fragment.onModifyFragment) {
            if (view.getId() == R.id.btn_Salvar) {
                Deliveries_Fragment.onModifyFragment listener = (Deliveries_Fragment.onModifyFragment) activity;
                listener.saveAllModifications(customer);
            } else if (view.getId() == R.id.btn_Cancelar) {
                // TODO implments something here!!!
            }

        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (textView == edtSenha || textView == EdtSenhaRep || textView == edtEmail
                || textView == edtTitular || textView == edtBanco || textView == edtAgencia
                || textView == edtConta) {
            boolean ok = true;
            if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                edtEmail.setError(getString(R.string.error_msg_email));
                ok = false;
            }
            if (!edtSenha.getText().toString().equals(EdtSenhaRep.getText().toString())) {
                EdtSenhaRep.setError(getString(R.string.error_msg_senharp));

                ok = false;
            }
            if (edtTitular.getText().length() <= 10) {
                edtTitular.setError(getString(R.string.error_msg_invalido));
                ok = false;
                if (edtTitular.getText().length() == 0) {
                    edtTitular.setError(getString(R.string.error_msg_vazio));
                    ok = false;
                }
            }

            if (edtBanco.getText().length() <= 10) {
                edtBanco.setError(getString(R.string.error_msg_invalido));
                ok = false;
                if (edtBanco.getText().length() == 0) {
                    edtBanco.setError(getString(R.string.error_msg_vazio));
                    ok = false;
                }
            }
            if (edtAgencia.getText().length() <= 10) {
                edtAgencia.setError(getString(R.string.error_msg_invalido));
                ok = false;
                if (edtAgencia.getText().length() == 0) {
                    edtAgencia.setError(getString(R.string.error_msg_vazio));
                    ok = false;
                }
            }
            if (edtConta.getText().length() <= 10) {
                edtConta.setError(getString(R.string.error_msg_invalido));
                ok = false;
                if (edtConta.getText().length() == 0) {
                    edtConta.setError(getString(R.string.error_msg_vazio));
                    ok = false;
                }
            }
            if (ok) {
                //Toast.makeText(this,getString(R.string.sucsses_msg_01),Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public interface onModifyFragment {
        void saveAllModifications(Customer customer);
    }

}
