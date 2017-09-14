package com.example.dell.fichacadastral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Classes.Deliveryman;
import Classes.SolicitacoesEntregador;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Rodrigo Henrique on 14/09/2017.
 */

public class Confirmation_Activity extends AppCompatActivity implements View.OnClickListener{

    Deliveryman deliveryman;
    String tipo = "";
    String modo = "";
    ArrayAdapter<SolicitacoesEntregador> adapterSolicitacoes;

    String token = "";
    String qrcode = "";

    private IntentIntegrator qrScan;
    private IntentResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null)  {
            deliveryman = new Gson().fromJson(extras.getString("deliveryman"), Deliveryman.class);
            carregarTelaConfirmacao1();
        }
    }

    void carregarTelaConfirmacao1() {
        setContentView(R.layout.tela_confirmacao_1);
        Button btnConfirmarColeta = (Button) findViewById(R.id.btnconfirmarcoleta);
        Button btnConfirmarEntrega = (Button) findViewById(R.id.btnconfirmarentrega);

        btnConfirmarColeta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tipo="coleta";
                carregarTelaConfirmacao2();
            }
        });

        btnConfirmarEntrega.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tipo="entrega";
                carregarTelaConfirmacao2();
            }
        });

    }

    void carregarTelaConfirmacao2() {
        setContentView(R.layout.tela_confirmacao_2);
        Button btnViaQRCode = (Button) findViewById(R.id.btnconfirmarviaqrcode);
        Button btnViaToken = (Button) findViewById(R.id.btnconfirmarviatoken);
        Button btnVoltar = (Button) findViewById(R.id.btnvoltar1);
        TextView txtConfirmacaoOpModo = (TextView) findViewById(R.id.txtConfirmacaoOpModo1);
        txtConfirmacaoOpModo.setText(tipo);

        btnViaQRCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                modo = "qrcode";
                carregarTelaConfirmacao3();
            }
        });

        btnViaToken.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                modo = "token";
                carregarTelaConfirmacao3();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                carregarTelaConfirmacao1();
            }
        });
    }

    void carregarTelaConfirmacao3() {
        setContentView(R.layout.tela_confirmacao_3);
        final ListView lvSolicitacoes = (ListView) findViewById(R.id.lvSolicitacoes);
        final Button btnVoltar = (Button) findViewById(R.id.btnvoltar2);

        final String URL = "https://smart-delivery-labes.herokuapp.com/api/entregador/listarSolicitacoes/";
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("idEntregador", deliveryman.getId());
        final String[] errorMsg = {""};
        client.post(URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("success")) {
                        errorMsg[0] = response.getString("errorMsg");
                        Toast.makeText(Confirmation_Activity.this, "Erro ao listar solicitaçoes: " + errorMsg[0], Toast.LENGTH_LONG).show();
                        return;
                    }

                    ArrayList<SolicitacoesEntregador> aSE = new ArrayList<SolicitacoesEntregador>();
                    JSONArray jaSolicitacoes = response.getJSONArray("solicitacoes");
                    for (int i = 0; i < jaSolicitacoes.length(); i++) {
                        JSONObject s = jaSolicitacoes.getJSONObject(i);
                        SolicitacoesEntregador se = new SolicitacoesEntregador();
                        if (s.has("id") && !s.isNull("id")) {
                            se.setId(s.getString("id"));
                        }
                        if (s.has("nomeSolicitante") && !s.isNull("nomeSolicitante")) {
                            se.setNomeSolicitante(s.getString("nomeSolicitante"));
                        }
                        if (s.has("sobrenomeSolicitante") && !s.isNull("sobrenomeSolicitante")) {
                            se.setSobrenomeSolicitante(s.getString("sobrenomeSolicitante"));
                        }
                        if (s.has("valor") && !s.isNull("valor")) {
                            se.setValor(s.getDouble("valor"));
                        }
                        if (s.has("dataPrevistaColeta") && !s.isNull("dataPrevistaColeta")) {
                            se.setDataPrevistaColeta(s.getString("dataPrevistaColeta"));
                        }
                        if (s.has("dataPrevistaEntrega") && !s.isNull("dataPrevistaEntrega")) {
                            se.setDataPrevistaEntrega(s.getString("dataPrevistaEntrega"));
                        }
                        if (s.has("dataRealColeta") && !s.isNull("dataRealColeta")) {
                            se.setDataRealColeta(s.getString("dataRealColeta"));
                        }
                        if (s.has("dataRealEntrega") && !s.isNull("dataRealEntrega")) {
                            se.setDataRealEntrega(s.getString("dataRealEntrega"));
                        }
                        if (s.has("reclamacao_id") && !s.isNull("reclamacao_id")) {
                            se.setReclamacao_id(s.getString("reclamacao_id"));
                        }
                        if (s.has("valorTaxaServico") && !s.isNull("valorTaxaServico")) {
                            se.setValorTaxaServico(s.getDouble("valorTaxaServico"));
                        }
                        if (s.has("status") && !s.isNull("status")) {
                            se.setStatus(s.getString("status"));
                            // As linhas abaixo sao usadas para delimitar a lista de solicitaçoes a serem exibidas na tela_confirmaacao_3, porem quando a linha 178 está descomentada, a condicional nunca retorna true, mesmo que os termos indiquem isso. Descomente 178, e veja você mesmo.
                            //if ((tipo == "coleta" && se.getStatus() == "PENDENTE_COLETA") || (tipo == "entrega" && se.getStatus() == "PENDENTE_ENTREGA"))
                                 aSE.add(se);
                            //else
                                //Toast.makeText(Confirmation_Activity.this, tipo + " " + se.getStatus(), Toast.LENGTH_LONG).show();
                        }
                    }
                    adapterSolicitacoes = new ArrayAdapter<SolicitacoesEntregador> (Confirmation_Activity.this, android.R.layout.simple_list_item_1, aSE);
                    lvSolicitacoes.setAdapter(adapterSolicitacoes);
                    lvSolicitacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            carregarTelaConfirmacao4(adapterSolicitacoes.getItem(i));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(Confirmation_Activity.this, "Erro ao listar solicitações", Toast.LENGTH_LONG).show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                carregarTelaConfirmacao2();
            }
        });

    }

    SolicitacoesEntregador solicitacao;

    void carregarTelaConfirmacao4(final SolicitacoesEntregador solicitacao) {
        this.solicitacao = solicitacao;
        if (modo == "qrcode") {
            setContentView(R.layout.tela_confirmacao_4_1);
            TextView txtConfirmacaoOpModo = (TextView) findViewById(R.id.txtConfirmacaoOpModo2);
            Button btnEscanearQRCode = (Button) findViewById(R.id.btnAbrirAPIQRCode);
            Button btnVoltar = (Button) findViewById(R.id.btnvoltar31);
            Button btnvoltarInicio = (Button) findViewById(R.id.btnvoltartudo1);
            txtConfirmacaoOpModo.setText(tipo);

            qrScan = new IntentIntegrator(this);
            btnEscanearQRCode.setOnClickListener(this);

            btnVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    carregarTelaConfirmacao3();
                }
            });

            btnvoltarInicio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    carregarTelaConfirmacao1();
                }
            });

        }
        if (modo =="token") {
            setContentView(R.layout.tela_confirmacao_4_2);
            TextView txtConfirmacaoOpModo = (TextView) findViewById(R.id.txtConfirmacaoOpModo3);
            final EditText etToken = (EditText) findViewById(R.id.etToken);
            Button btnAvancar = (Button) findViewById(R.id.btnavancar2);
            Button btnVoltar = (Button) findViewById(R.id.btnvoltar32);
            Button btnvoltarInicio = (Button) findViewById(R.id.btnvoltartudo2);
            txtConfirmacaoOpModo.setText(tipo);

            btnVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    carregarTelaConfirmacao3();
                }
            });

            btnvoltarInicio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    carregarTelaConfirmacao1();
                }
            });

            btnAvancar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    token = etToken.getText().toString();
                    carregarTelaConfirmacao5();
                }
            });
        }
    }

    void carregarTelaConfirmacao5() {
        if (modo == "qrcode") {
            if (tipo == "coleta") {
                confirmacao("https://smart-delivery-labes.herokuapp.com/api/entregador/confirmarColetaViaQRCode/", solicitacao.getId(), "qrCode", qrcode);
            }
            if (tipo == "entrega") {
                confirmacao("https://smart-delivery-labes.herokuapp.com/api/entregador/confirmarEntregaViaQRCode/", solicitacao.getId(), "qrCode", qrcode);
            }
        }
        if (modo =="token") {
            if (tipo == "coleta") {
                confirmacao("https://smart-delivery-labes.herokuapp.com/api/entregador/confirmarColetaViaTokenConfirmacao/", solicitacao.getId(), "tokenConfirmacaoColeta", token);
            }
            if (tipo == "entrega") {
                confirmacao("https://smart-delivery-labes.herokuapp.com/api/entregador/confirmarEntregaViaTokenConfirmacao/", solicitacao.getId(), "tokenConfirmacaoEntrega", token);
            }
        }
    }

    void confirmacao(String URL, String idSolicitacao, String tipoValidacao, String validador) {
        setContentView(R.layout.tela_confirmacao_resultado);
        TextView txtConfirmacaoResultado = (TextView) findViewById(R.id.txtConfirmacaoResultado);
        txtConfirmacaoResultado.setText(tipo);
        final TextView txtStatusConfirmacao = (TextView) findViewById(R.id.txtstatusconfirmacao);
        TextView txtString = (TextView) findViewById(R.id.txtstring);

        // Alinha abaixoexibe as informaçoe que estão indo para a requisiçao

        txtString.setText(URL + " " + idSolicitacao+ " " + tipoValidacao + " " +validador);

        Button btnVoltarTudo = (Button) findViewById(R.id.btnvoltartudo3);
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("idSolicitacao", idSolicitacao);
        params.put(tipoValidacao, validador);
        final String[] errorMsg = {""};
        client.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("success")) {
                        errorMsg[0] = response.getString("errorMsg");
                        txtStatusConfirmacao.setText("Não confirmada!!!");
                        Toast.makeText(Confirmation_Activity.this, "Erro ao confirmar o serviço: " + errorMsg[0], Toast.LENGTH_LONG).show();


                    } else {
                        txtStatusConfirmacao.setText("Confirmada!!! ");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(Confirmation_Activity.this, "Erro ao tentar confirmar a solicitação selecionada", Toast.LENGTH_LONG).show();
                txtStatusConfirmacao.setText("Falha de conexão...");
            }
        });

        btnVoltarTudo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                carregarTelaConfirmacao1();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                qrcode = result.getContents();
                carregarTelaConfirmacao5();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        qrScan.initiateScan();
    }
}