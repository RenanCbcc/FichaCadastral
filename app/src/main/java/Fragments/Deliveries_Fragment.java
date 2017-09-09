package Fragments;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import Classes.Deliveryman;
import Classes.JsonRequest;
import Classes.LoadedRequest;
import Interfaces.onModifyFragment;
import Interfaces.onRequestClick;
import com.example.dell.fichacadastral.R;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 05/08/2017.
 */

public class Deliveries_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private static final String EXTRA_DELIVEYMAN = "deliveryman"; // tuple{Key,value}
    private static final String EXTRA_ORIGIN = "origin"; // tuple{Key,value}

    private Deliveryman deliveryman;
    private TextView txt_Name;
    private TextView txt_Nivel;
    private TextView txt_Xp;
    private TextView txt_Feed;
    private TextView txt_Mean;
    private ToggleButton toggleButton;
    private List<LoadedRequest> loadedRequestList;
    private ArrayAdapter<LoadedRequest> requestArrayAdapter;
    private TextView textView;
    private ListView listView;
    private ProgressBar progressBar;
    private LatLng origin;

    public static Deliveries_Fragment newInstance(Deliveryman deliveryman ) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_DELIVEYMAN, deliveryman);
        Deliveries_Fragment fragment = new Deliveries_Fragment();
        fragment.setArguments(parametros);
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

        View view = inflater.inflate(R.layout.deliveries_layout, container, false);
        txt_Name = view.findViewById(R.id.nome_id);
        txt_Nivel = view.findViewById(R.id.nivel_id);
        txt_Xp = view.findViewById(R.id.xp_id);
        txt_Feed = view.findViewById(R.id.feedbad_id);
        txt_Mean = view.findViewById(R.id.media_id);
        toggleButton = view.findViewById(R.id.tgb_isAvailable);
        textView = (TextView)view.findViewById(android.R.id.empty);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        listView = (ListView)view.findViewById(R.id.list);

        listView.setEmptyView(textView);
        textView.setText("Habilite a disponibilidade");
        listView.setOnItemClickListener(this);

        toggleButton.setOnCheckedChangeListener(this);

        if (deliveryman != null) {
            txt_Name.setText(String.format("Nome: %s", deliveryman.getNome()));
            txt_Nivel.setText(String.format("%s%s", "Nivel: ".toUpperCase(), deliveryman.getNome()));
            txt_Xp.setText(String.format("XP: %s", deliveryman.getNome()));
            txt_Feed.setText(String.format("%s%s", "FeedBack: ".toUpperCase(), deliveryman.getNome()));
            txt_Mean.setText(String.format("%s%s", "Média: ".toUpperCase(), deliveryman.getNome()));
            toggleButton.setChecked(deliveryman.isAvailable());

        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Activity activity = getActivity();
        if (activity instanceof onModifyFragment) {
            if (toggleButton.getText().toString().equals("Disponivel")) {
                toggleButton.setChecked(false);
                if (deliveryman != null) {
                    deliveryman.setAvailable(false);
                    Toast.makeText(getActivity(), "Estou indisponível para realizar Entregas",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                toggleButton.setChecked(true);
                getResquests();

                if (deliveryman != null) {
                    deliveryman.setAvailable(true);
                    Toast.makeText(getActivity(), "Estou disponível para realizar Entregas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor", Toast.LENGTH_SHORT).show();
                }

            }
            onModifyFragment listener = (onModifyFragment) activity;
            listener.saveAllModifications(deliveryman);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (loadedRequestList == null) {
            loadedRequestList = new ArrayList<LoadedRequest>();
        }
        requestArrayAdapter = new ArrayAdapter<LoadedRequest>(getActivity(),android.R.layout.simple_list_item_1);

        listView.setAdapter(requestArrayAdapter);

       if(!JsonRequest.hasConnection(getActivity())) {
            textView.setText("Sem conexão");
       }
    }


    private void exhibitPogress(boolean exhibit) {
        if (exhibit) {
            textView.setText("Carregando as requisiçoes disponíveis...");
        }
        textView.setVisibility(exhibit ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(exhibit ? View.VISIBLE : View.GONE);
    }

    public void getResquests() {
        String URL = "https://smart-delivery-labes.herokuapp.com/api/entregador/procurarNovasSolicitacoes/";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if (deliveryman!=null) {
            params.put("idEntregador", deliveryman.getId());
            params.put("latitude", deliveryman.getLocal().latitude);
            params.put("longitude", deliveryman.getLocal().longitude);
            client.post(URL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if (!response.getBoolean("success")) {
                            String errorMsg = response.getString("errorMsg");
                            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            loadedRequestList.clear();
                            loadedRequestList.addAll(readRequests(response));
                            requestArrayAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                }
            });
        }else{Toast.makeText(getActivity(), "Erro null pointer", Toast.LENGTH_LONG).show();}

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Activity activity = getActivity();
        if(activity instanceof onRequestClick){
            LoadedRequest loadedRequest = (LoadedRequest) adapterView.getItemAtPosition(i);
            onRequestClick requestClick = (onRequestClick) activity;
            requestClick.selectRequest(loadedRequest);
        }
    }

    public static List<LoadedRequest> readRequests (JSONObject json) throws JSONException {
        List<LoadedRequest> requestsList = new ArrayList<LoadedRequest>();
        JSONArray jsonResquestsArray = json.getJSONArray("solicitacoes");

        for (int i = 0; i < jsonResquestsArray.length(); i++) {
            JSONObject jsonRequest = jsonResquestsArray.getJSONObject(i);

            LoadedRequest loadedRequest = new LoadedRequest(
                    jsonRequest.getInt("id"),
                    jsonRequest.getString("dataHoraSolicitacao"),
                    jsonRequest.getString("tipo"),
                    jsonRequest.getInt("quantidade"),
                    jsonRequest.getDouble("peso"),
                    jsonRequest.getDouble("tamanho"),
                    jsonRequest.getString("latitude"),
                    jsonRequest.getString("longitude"),
                    jsonRequest.getString("complemento"),
                    jsonRequest.getInt("numero"),
                    jsonRequest.getString("enderecoGPS"),
                    jsonRequest.getString("latitude"),
                    jsonRequest.getString("longitude"),
                    jsonRequest.getString("complemento"),
                    jsonRequest.getInt("numero"),
                    jsonRequest.getString("enderecoGPS")
                );
                requestsList.add(loadedRequest);

        }
        return requestsList;
    }

}
