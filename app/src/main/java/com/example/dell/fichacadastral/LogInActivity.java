package com.example.dell.fichacadastral;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import Classes.JsonRequest;
import cz.msebera.android.httpclient.Header;
import Classes.Deliveryman;

public class LogInActivity extends AppCompatActivity {
    private Deliveryman deliveryman;
    private Button button;
    private EditText edt_senha;
    private EditText edt_email;
    private TextView txt_singup;
    private ProgressBar progressBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_singup = (TextView) findViewById(R.id.txt_singup);
        edt_senha = (EditText) findViewById(R.id.edt_Password);
        edt_email = (EditText) findViewById(R.id.edt_Email);
        button = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        textView = (TextView) findViewById(R.id.txtprogress);

        if (!JsonRequest.hasConnection(LogInActivity.this)) {
            Toast.makeText(LogInActivity.this, getString(R.string.error_msg_01), Toast.LENGTH_SHORT).show();
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (edt_senha.getText().toString().length() <= 0) {
                    edt_senha.setError(getString(R.string.error_msg_senha));
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()) {
                    edt_email.setError(getString(R.string.error_msg_email));
                } else {
                    logIn();
                }

            }
        });

        txt_singup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LogInActivity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void logIn() {
        exhibitPogress(true);
        String URL = "https://smart-delivery-labes.herokuapp.com/api/entregador/login/";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", edt_email.getText().toString());
        params.put("senha", edt_senha.getText().toString());
        client.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("success")) {
                        exhibitPogress(false);
                        String errorMsg = response.getString("errorMsg");
                        Toast.makeText(LogInActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    deliveryman = new Deliveryman();
                    deliveryman.setId(response.getInt("idEntregador"));
                    deliveryman.setNome(response.getString("nomeCompleto"));
                    deliveryman.setEmail(response.getString("email"));
                    deliveryman.setTelefone(response.getString("telefone"));
                    deliveryman.setPlaca_Veiculo(response.getString("placaVeiculo"));
                    deliveryman.setCEP(response.getString("modeloVeiculo"));
                    deliveryman.setTitular_banco(response.getString("titularConta"));
                    deliveryman.setDocumentoCadastral(response.getString("cpf_cnpj"));
                    deliveryman.setAgencia(response.getString("agencia"));
                    deliveryman.setDigito_agencia(response.getString("digitoAgencia"));
                    deliveryman.setConta(response.getString("conta"));
                    deliveryman.setDigito_conta(response.getString("digitoConta"));

                    deliveryman.setSaldoDevedorTotal(response.getString("saldoDevedorTotal"));
                    deliveryman.setDiaQuitacaoSaldoDevedor(response.getString("diaQuitacaoSaldoDevedor"));
                    Intent intent = new Intent(LogInActivity.this, Deliverer_Activity.class);
                    intent.putExtra("entregador", deliveryman);
                    exhibitPogress(false);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private void exhibitPogress(boolean exhibit) {
        if (exhibit) {
            textView.setText("Fazengo Login...");
        }
        textView.setVisibility(exhibit ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(exhibit ? View.VISIBLE : View.GONE);
    }

}
