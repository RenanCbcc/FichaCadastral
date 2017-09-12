package com.example.dell.fichacadastral;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Francisco on 10/09/2017.
 */

public class Exibir_Solicitacoes  extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exibir_solicitacoes);
        new SendPostRequest().execute();



    }

   /* private List<Solicitacoes_Entregador> listarSolicitacoes(Solicitacoes_Entregador solicitacoesEntregador) {
        return new ArrayList<>(Arrays.asList(
                new Solicitacoes_Entregador(solicitacoesEntregador.getId(),"Teste","Teste","Teste",0,"Teste","Teste","Teste","Teste","Teste",0)));

    }
*/
    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://smart-delivery-labes.herokuapp.com/api/entregador/listarSolicitacoes/"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("idEntregador", "1");
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
        protected void onPostExecute(String result) {
            //Todo tratar aqui a string para ser exibida na listview
            /*ListView listaDeSolicitacoes = (ListView) findViewById(R.id.lista_solicitacoes);
            Solicitacoes_Entregador solicitacoesEntregador = new Solicitacoes_Entregador();
            solicitacoesEntregador.setId("Junior");

            List<Solicitacoes_Entregador> solicitacoes_entregadores = listarSolicitacoes(solicitacoesEntregador);
            ArrayAdapter<Solicitacoes_Entregador> adapter = new ArrayAdapter<>(Exibir_Solicitacoes.this, android.R.layout.simple_list_item_1, solicitacoes_entregadores);
            listaDeSolicitacoes.setAdapter(adapter);

            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
                    */


            ListView listView = (ListView)findViewById(R.id.lista_solicitacoes);

            List<Solicitacoes_Entregador> obj = null;
            try {
                obj = gerarSolicitacoes(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<Solicitacoes_Entregador> solicitacoesAdapter = new ArrayAdapter<Solicitacoes_Entregador>(Exibir_Solicitacoes.this, android.R.layout.simple_list_item_1, obj);
            listView.setAdapter(solicitacoesAdapter);
        }
    }

    private List<Solicitacoes_Entregador> gerarSolicitacoes(String result) throws JSONException {

            //Todo implementar aqui o array com os dados
            JSONObject objetoJSON = new JSONObject(result);
            JSONArray jsonArray = objetoJSON.getJSONArray("solicitacoes");

            Solicitacoes_Entregador solEnt = new Solicitacoes_Entregador();

            for (int i = 0; i < jsonArray.length(); i++) {
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

            }



            //solEnt.setId(objetoJSON.getString("solicitacoes"));//esta linha sera removida

            /*Toast.makeText(getApplicationContext(), solEnt.getId(),
                    Toast.LENGTH_LONG).show();*/


        List<Solicitacoes_Entregador> solicitacoes = new ArrayList<Solicitacoes_Entregador>();
        for(int i = 0; i<1; i++) {
            solicitacoes.add(solEnt);
        }

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
