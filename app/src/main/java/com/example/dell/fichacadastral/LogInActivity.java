package com.example.dell.fichacadastral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import Classes.Deliveryman;

public class LogInActivity extends AppCompatActivity  {
    private Deliveryman deliveryman;
    private Button button;
    private EditText txt_senha;
    private EditText txt_loguin;
    private TextView txt_singup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deliveryman = new Deliveryman();
        txt_singup = (TextView) findViewById(R.id.txt_singup);
        txt_senha = (EditText) findViewById(R.id.edt_Password);
        txt_loguin = (EditText) findViewById(R.id.edt_Email);
        button = (Button) findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String URL = "https://smart-delivery-labes.herokuapp.com/api/entregador/login/";
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("email", txt_loguin.getText().toString());
                params.put("senha", txt_senha.getText().toString());
                client.post(URL, params, new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (!response.getBoolean("success"))
                            {
                                String errorMsg = response.getString("errorMsg");
                                Toast.makeText(LogInActivity.this, errorMsg,Toast.LENGTH_SHORT).show();
                                return;
                            }
                            deliveryman.setId(response.getInt("idEntregador"));
                            Intent intent;
                            intent = new Intent(LogInActivity.this, Deliverer_Activity.class);
                            intent.putExtra("entregador",deliveryman);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    }
                });

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
}
