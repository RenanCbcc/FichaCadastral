package Fragments;

import android.app.Activity;
import android.content.Intent;
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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Classes.Deliveryman;
import Interfaces.onModifyFragment;
import cz.msebera.android.httpclient.Header;

import com.example.dell.fichacadastral.Deliverer_Activity;
import com.example.dell.fichacadastral.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05/08/2017.
 */

public class Profile_Fragment extends Fragment implements TextWatcher, View.OnClickListener {
    private static final String EXTRA_DELIVEYMAN = "deliveryman"; // Primary Key

    private TextView textView;
    private RelativeLayout background;

    private EditText edtTelefone;
    private EditText edtNome;
    private EditText edtCep;
    private EditText edtRua;
    private EditText edtCidade;
    private EditText edtComplemento;
    private EditText edtBairro;
    private ProgressBar progressBar;
    private Spinner spnEstado;
    private EditText edtSenhaAntiga;
    private EditText EdtSenhaNova;
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
    private Button btnAlterar;

    private Deliveryman deliveryman;

    public static Profile_Fragment newInstance(Deliveryman deliveryman) {//METODO CONSTRUTOR
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DELIVEYMAN, deliveryman);
        Profile_Fragment fragment = new Profile_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryman = (Deliveryman) getArguments().getSerializable(EXTRA_DELIVEYMAN);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        textView = (TextView) view.findViewById(R.id.txtprogress);
        background = (RelativeLayout) view.findViewById(R.id.layout_Profile);
        edtTelefone = view.findViewById(R.id.edt_nCelular);
        edtNome = view.findViewById(R.id.edt_nome);
        edtCep = view.findViewById(R.id.edt_Cep);
        edtRua = view.findViewById(R.id.edt_rua);
        edtCidade = view.findViewById(R.id.edt_cidade);
        edtComplemento = view.findViewById(R.id.edt_complemento);
        edtBairro = view.findViewById(R.id.edt_bairro);
        spnEstado = view.findViewById(R.id.snp_Estados);
        edtSenhaAntiga = view.findViewById(R.id.edt_at_senha);
        EdtSenhaNova = view.findViewById(R.id.edt_nv_senha);
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
        btnAlterar = view.findViewById(R.id.btn_Alterar);

        btnAlterar.setOnClickListener(this);
        btnSalvar.setOnClickListener(this);
        edtTitular.addTextChangedListener(this);
        edtBanco.addTextChangedListener(this);
        edtPlacaViculo.addTextChangedListener(this);
        edtMArcaViculo.addTextChangedListener(this);
        edtModelViculo.addTextChangedListener(this);
        edtTitular.addTextChangedListener(this);
        edtBanco.addTextChangedListener(this);
        edtAgencia.addTextChangedListener(this);
        edtConta.addTextChangedListener(this);
        edtTelefone.addTextChangedListener(this);
        edtTelefone.addTextChangedListener(this);
        edtSenhaAntiga.addTextChangedListener(this);
        EdtSenhaNova.addTextChangedListener(this);

        edtNome.setEnabled(false);
        edtRua.setEnabled(false);
        edtCep.setEnabled(false);
        edtCidade.setEnabled(false);
        edtComplemento.setEnabled(false);
        edtBairro.setEnabled(false);
        spnEstado.setEnabled(false);
        edtEmail.setEnabled(false);

        if (deliveryman != null) {
            edtNome.setText(deliveryman.getNome());
            edtTelefone.setText(deliveryman.getTelefone());
            if (deliveryman.getLoadedAddress() != null) {
                edtCep.setText(deliveryman.getLoadedAddress().getCep());
                edtRua.setText(deliveryman.getLoadedAddress().getLogradouro());
                edtCidade.setText(deliveryman.getLoadedAddress().getLocalidade());
                edtComplemento.setText(deliveryman.getLoadedAddress().getComplemento());
                edtBairro.setText(deliveryman.getLoadedAddress().getBairro());
            }
            spnEstado.setSelection(0); //TODO costumer.getLoadedAddress().getUf()

            edtEmail.setText(deliveryman.getEmail());
            edtPlacaViculo.setText(deliveryman.getPlaca_Veiculo());
            edtMArcaViculo.setText(deliveryman.getMarca_Veiculo());
            edtModelViculo.setText(deliveryman.getModel_Veiculo());
            edtTitular.setText(deliveryman.getTitular_banco());
            edtBanco.setText(deliveryman.getBanco());
            edtAgencia.setText(deliveryman.getAgencia());
            edtConta.setText(deliveryman.getConta());
            edtTelefone.setText(deliveryman.getTelefone());

        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();
        if (activity instanceof onModifyFragment) {
            if (view.getId() == R.id.btn_Salvar) {
                onModifyFragment listener = (onModifyFragment) activity;
                listener.saveAllModifications(deliveryman);
            }
            else if (view.getId() == R.id.btn_Alterar) {
                onModifyFragment listener = (onModifyFragment) activity;
                listener.saveModifications(deliveryman);
            }

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable == edtPlacaViculo.getEditableText()) {

            if (edtPlacaViculo.getText().length() == 0) {
                edtPlacaViculo.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setPlaca_Veiculo(edtPlacaViculo.getText().toString());
            }

        } else if (editable == edtMArcaViculo.getEditableText()) {
            if (edtMArcaViculo.getText().length() == 0) {
                edtMArcaViculo.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setMarca_Veiculo(edtMArcaViculo.getText().toString());
            }

        } else if (editable == edtModelViculo.getEditableText()) {
            if (edtModelViculo.getText().length() == 0) {
                edtModelViculo.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setModel_Veiculo(edtModelViculo.getText().toString());
            }

        } else if (editable == edtTitular.getEditableText()) {
            if (edtTitular.getText().length() == 0) {
                edtTitular.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setTitular_banco(edtTitular.getText().toString());
            }

        } else if (editable == edtBanco.getEditableText()) {
            if (edtBanco.getText().length() == 0) {
                edtBanco.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setBanco(edtBanco.getText().toString());


            }
        } else if (editable == edtTelefone.getEditableText()) {
            if (edtTelefone.getText().length() == 0) {
                edtTelefone.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setTelefone(edtTelefone.getText().toString());
            }

        }

        else if (editable == edtSenhaAntiga.getEditableText()) {
            if (edtSenhaAntiga.getText().length() == 0) {
                edtSenhaAntiga.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setSenhaAntiga(edtSenhaAntiga.getText().toString());
            }

        }
        else if (editable == EdtSenhaNova.getEditableText()) {
            if (EdtSenhaNova.getText().length() == 0) {
                EdtSenhaNova.setError(getString(R.string.error_msg_vazio));
            } else {
                deliveryman.setSenha(EdtSenhaNova.getText().toString());
            }

        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private void exhibitPogress(boolean exhibit) {
        if (exhibit) {
            textView.setText("Aguarde");
            background.setBackgroundColor(getResources().getColor(R.color.background_color_loading));
        }
        textView.setVisibility(exhibit ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(exhibit ? View.VISIBLE : View.GONE);
        background.setBackgroundColor(getResources().getColor(R.color.color_whight));

    }



}