package Fragments;

import android.app.Activity;
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
import Interfaces.onPositionUpdated;
import Interfaces.onSelectedRequest;

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

public class Deliveries_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener
        , AdapterView.OnItemClickListener {
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

    public static Deliveries_Fragment newInstance(Deliveryman deliveryman) {
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
        textView = (TextView) view.findViewById(android.R.id.empty);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setEmptyView(textView);
        listView.setOnItemClickListener(this);

        toggleButton.setOnCheckedChangeListener(this);

        if (deliveryman != null) {
            txt_Name.setText(String.format("Nome: %s", deliveryman.getNome()));
            txt_Nivel.setText(String.format("%s%s", "Nivel: ".toUpperCase(), deliveryman.getNivel()));
            txt_Xp.setText(String.format("XP: %s", deliveryman.getExperiencia()));
            txt_Feed.setText(String.format("%s%s", "FeedBack: ".toUpperCase(), deliveryman.getFeed()));
            txt_Mean.setText(String.format("%s%s", "Média: ".toUpperCase(), deliveryman.getMedia()));
            toggleButton.setChecked(deliveryman.isAvailable());
        }
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Activity activity = getActivity();
        if (activity instanceof onPositionUpdated) {
            onPositionUpdated listener = (onPositionUpdated) activity;
            if (toggleButton.getText().toString().equals("Disponivel")) {
                toggleButton.setChecked(false);
                if (deliveryman != null) {
                    deliveryman.setAvailable(false);
                    listener.upDatePosition(false);
                    Toast.makeText(getActivity(), getString(R.string.sucsses_msg_04),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_msg_02),
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                toggleButton.setChecked(true);

                if (deliveryman != null) {
                    deliveryman.setAvailable(true);
                    LatLng origin = listener.upDatePosition(true);
                    if (origin == null) {
                    } else {
                        deliveryman.setLocal(origin);
                        Toast.makeText(getActivity(), getString(R.string.sucsses_msg_03), Toast.LENGTH_SHORT).show();
                        getResquests();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_msg_02), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "onActivityCreated", Toast.LENGTH_SHORT).show();
        super.onActivityCreated(savedInstanceState);
        if (loadedRequestList == null) {
            loadedRequestList = new ArrayList<LoadedRequest>();
        }
        //TODO
        requestArrayAdapter = new ArrayAdapter<LoadedRequest>(getActivity(),
                android.R.layout.simple_list_item_1, loadedRequestList);

        listView.setAdapter(requestArrayAdapter);

        if (!JsonRequest.hasConnection(getActivity())) {
            textView.setText(getString(R.string.error_msg_01));
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
        exhibitPogress(true);
        String URL = "https://smart-delivery-labes.herokuapp.com/api/entregador/procurarNovasSolicitacoes/";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idEntregador", 1);
        params.put("latitude", -1.47439601);
        params.put("longitude", -48.4532220);
        client.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                exhibitPogress(false);
                try {
                    if (!response.getBoolean("success")) {
                        String errorMsg = response.getString("errorMsg");
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        loadedRequestList.clear();
                        List<LoadedRequest> requestList = readRequests(response);
                        if (requestList != null) {

                            loadedRequestList.addAll(requestList);
                            requestArrayAdapter.notifyDataSetChanged();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                exhibitPogress(false);
                Toast.makeText(getActivity(), getString(R.string.error_msg_01), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    //TODO
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Activity activity = getActivity();
        if (activity instanceof onSelectedRequest) {
            LoadedRequest loadedRequest = (LoadedRequest) adapterView.getItemAtPosition(i);
            onSelectedRequest requestClick = (onSelectedRequest) activity;
            requestClick.showSelectRequestDetail(loadedRequest);
        }
    }

    public List<LoadedRequest> readRequests(JSONObject json) throws JSONException {
        List<LoadedRequest> requestsList = new ArrayList<LoadedRequest>();
        JSONArray jsonResquestsArray = json.getJSONArray("solicitacoes");

        for (int i = 0; i < jsonResquestsArray.length(); i++) {
            JSONObject jsonRequest = jsonResquestsArray.getJSONObject(i);
            LoadedRequest loadedRequest = new LoadedRequest();


            if (jsonRequest.has("id") && !jsonRequest.isNull("id")) {
                loadedRequest.setId(jsonRequest.getInt("id"));
            }

            if (jsonRequest.has("dataHoraSolicitacao") && !jsonRequest.isNull("dataHoraSolicitacao")) {
                loadedRequest.setDataHoraSolicitacao(jsonRequest.getString("dataHoraSolicitacao"));
            }

            if (jsonRequest.has("tipo") && !jsonRequest.isNull("tipo")) {
                loadedRequest.setTipo(jsonRequest.getString("tipo"));
            }

            if (jsonRequest.has("quantidade") && !jsonRequest.isNull("quantidade")) {
                loadedRequest.setQuantidade(jsonRequest.getInt("quantidade"));
            }

            if (jsonRequest.has("peso") && !jsonRequest.isNull("peso"))
                loadedRequest.setPeso(jsonRequest.getDouble("peso"));

            if (jsonRequest.has("tamanho") && !jsonRequest.isNull("tamanho"))
                loadedRequest.setTamanho(jsonRequest.getDouble("tamanho"));

            if (jsonRequest.has("localColeta") && !jsonRequest.isNull("localColeta")) {
                JSONObject localColeta = jsonRequest.getJSONObject("localColeta");

                if (jsonRequest.has("latitude") && !jsonRequest.isNull("latitude")) {
                    loadedRequest.setLatitude_Coleta(localColeta.getInt("latitude"));
                }

                if (jsonRequest.has("longitude") && !jsonRequest.isNull("longitude")) {
                    loadedRequest.setLongitude_Coleta(localColeta.getInt("longitude"));
                }
                if (jsonRequest.has("longitude") && !jsonRequest.isNull("longitude")) {
                    loadedRequest.setLongitude_Coleta(localColeta.getInt("longitude"));
                }

                if (jsonRequest.has("complemento") && !jsonRequest.isNull("complemento")) {
                    loadedRequest.setComplemento_Coleta(localColeta.getString("complemento"));
                }
                if (jsonRequest.has("numero") && !jsonRequest.isNull("numero")) {
                    loadedRequest.setNumero_Coleta(localColeta.getInt("numero"));
                }

                if (jsonRequest.has("enderecoGPS") && !jsonRequest.isNull("enderecoGPS")) {
                    loadedRequest.setEnderecoGPS_Coleta(localColeta.getString("enderecoGPS"));
                }
            }

            JSONObject localEntrega = jsonRequest.getJSONObject("localEntrega");
            if (jsonRequest.has("localEntrega") && !jsonRequest.isNull("localEntrega")) {

                if (jsonRequest.has("latitude") && !jsonRequest.isNull("latitude")) {
                    loadedRequest.setLatitude_Entrega(localEntrega.getInt("latitude"));
                }


                if (jsonRequest.has("longitude") && !jsonRequest.isNull("longitude")) {
                    loadedRequest.setLongitude_Entrega(localEntrega.getInt("longitude"));

                }

                if (jsonRequest.has("complemento") && !jsonRequest.isNull("complemento")) {
                    loadedRequest.setComplemento_Entrega(localEntrega.getString("complemento"));
                }

                if (jsonRequest.has("numero") && !jsonRequest.isNull("numero")) {
                    loadedRequest.setNumero_Entrega(localEntrega.getInt("numero"));
                }

                if (jsonRequest.has("enderecoGPS") && !jsonRequest.isNull("enderecoGPS")) {
                    loadedRequest.setEnderecoGPS_Entrega(localEntrega.getString("enderecoGPS"));
                }
            }

            requestsList.add(loadedRequest);

        }

        return requestsList;
    }

}
