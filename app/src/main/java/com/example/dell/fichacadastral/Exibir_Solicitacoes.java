package com.example.dell.fichacadastral;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import Classes.Deliveryman;

/**
 * Created by Francisco on 10/09/2017.
 */

public class Exibir_Solicitacoes  extends AppCompatActivity{

    Deliverer_Activity delivererActivity;
    private List<Solicitacoes_Entregador> obj = null;
    private AlertDialog alerta;//atributo da classe.
    private int idEntregador;
    private int posicao;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exibir_solicitacoes);

        Deliveryman deliveryman = (Deliveryman) getIntent().getSerializableExtra("entregador");

        idEntregador = deliveryman.getId();
        //idEntregador = 1;

        new SendPostRequest().execute();

    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {



        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {
                URL url = new URL("https://smart-delivery-labes.herokuapp.com/api/entregador/listarSolicitacoes/"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("idEntregador", idEntregador);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(final String result) {
            ListView listView = (ListView)findViewById(R.id.lista_solicitacoes);

            try {
                obj = gerarSolicitacoes(result); //obj recebe um list com as solicitações
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Solicitacoes_Entregador> solicitacoesAdapter = new ArrayAdapter<Solicitacoes_Entregador>(Exibir_Solicitacoes.this, android.R.layout.simple_list_item_1, obj);
            View empty = findViewById(R.id.empty);
            listView.setEmptyView(empty);
            listView.setAdapter(solicitacoesAdapter);

            //Torna o list view clicável


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    posicao = position;
                    new SendPostRequestToken().execute();

                    //Código para o clique
                    /*Toast.makeText(getApplicationContext(), obj.get(0).getId(),
                            Toast.LENGTH_LONG).show();*/
                }
            });
        }
    }

    //Token
    public class SendPostRequestToken extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {
                URL url = new URL("https://smart-delivery-labes.herokuapp.com/api/solicitante/getTokensConfirmacao/"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("idSolicitacao", obj.get(posicao).getId());
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                JSONObject jsonObjeto = new JSONObject(result);
                String tokenEntrega = jsonObjeto.getString("tokenEntrega");
                String tokenColeta = jsonObjeto.getString("tokenColeta");

                /*Toast.makeText(getApplicationContext(), obj.get(0).getStatus(),
                        Toast.LENGTH_LONG).show();*/

                if(obj.get(posicao).getStatus().equals("PENDENTE_COLETA")) { //Todo: mudar para pendente confirmação
                    AlertDialog.Builder builder = new AlertDialog.Builder(Exibir_Solicitacoes.this);//Cria o gerador do AlertDialog
                    builder.setTitle("Token Coleta");//define o titulo
                    builder.setMessage(tokenColeta);//define a mensagem

                    alerta = builder.create();//cria o AlertDialog
                    alerta.show();//Exibe
                }
                else if(obj.get(posicao).getStatus().equals("COLETADO")) { //Todo: mudar para pendente confirmação
                    AlertDialog.Builder builder = new AlertDialog.Builder(Exibir_Solicitacoes.this);//Cria o gerador do AlertDialog
                    builder.setTitle("Token Entrega");//define o titulo
                    builder.setMessage(tokenEntrega);//define a mensagem

                    alerta = builder.create();//cria o AlertDialog
                    alerta.show();//Exibe
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Solicitacoes_Entregador> gerarSolicitacoes(String result) throws JSONException {

        JSONObject objetoJSON = new JSONObject(result);
        JSONArray jsonArray = objetoJSON.getJSONArray("solicitacoes");

        Solicitacoes_Entregador solEnt;
        List<Solicitacoes_Entregador> solicitacoes = new ArrayList<Solicitacoes_Entregador>();

        for (int i = 0; i < jsonArray.length(); i++) {
            solEnt = new Solicitacoes_Entregador();
            JSONObject e = jsonArray.getJSONObject(i);
            solEnt.setId(e.getString("id"));
            solEnt.setStatus(e.getString("status"));
            solEnt.setNomeSolicitante(e.getString("nomeSolicitante"));
            solEnt.setSobrenomeSolicitante(e.getString("sobrenomeSolicitante"));
            solEnt.setValor(e.getDouble("valor"));
            solEnt.setDataPrevistaColeta(e.getString("dataPrevistaColeta"));
            solEnt.setDataPrevistaEntrega(e.getString("dataPrevistaColeta"));
            solEnt.setDataRealColeta(e.getString("dataRealColeta"));
            solEnt.setDataRealEntrega(e.getString("dataRealEntrega"));
            solEnt.setReclamacao_id(e.getString("reclamacao_id"));
            solEnt.setValorTaxaServico(e.getDouble("valorTaxaServico"));

            solicitacoes.add(i,solEnt);
        }
        //solEnt.setId(objetoJSON.getString("solicitacoes"));//esta linha sera removida

        return solicitacoes;
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
