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
import Classes.Customer;
import Classes.JsonRequest;
import Classes.LoadedRequest;
import Interfaces.onModifyFragment;
import Interfaces.onRequestClick;
import com.example.dell.fichacadastral.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 05/08/2017.
 */

public class Deliveries_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private static final String EXTRA_CUSTOMER = "customer"; // tuple{Key,value}
    private Customer customer;
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
    private RequestTask task;
    private ProgressBar progressBar;


    public static Deliveries_Fragment newInstance(Customer costumer) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_CUSTOMER, costumer);
        Deliveries_Fragment fragment = new Deliveries_Fragment();
        fragment.setArguments(parametros);
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

        if (customer != null) {
            txt_Name.setText(String.format("Nome: %s", customer.getNome()));
            txt_Nivel.setText(String.format("%s%s", "Nivel: ".toUpperCase(), customer.getNome()));
            txt_Xp.setText(String.format("XP: %s", customer.getNome()));
            txt_Feed.setText(String.format("%s%s", "FeedBack: ".toUpperCase(), customer.getNome()));
            txt_Mean.setText(String.format("%s%s", "Média: ".toUpperCase(), customer.getNome()));
            toggleButton.setChecked(customer.isAvailable());

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
                if (customer != null) {
                    customer.setAvailable(false);
                    Toast.makeText(getActivity(), "Estou indisponível para realizar Entregas",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                toggleButton.setChecked(true);
                //TODO get current location

                if (customer != null) {
                    customer.setAvailable(true);
                    Toast.makeText(getActivity(), "Estou disponível para realizar Entregas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor", Toast.LENGTH_SHORT).show();
                }

            }
            onModifyFragment listener = (onModifyFragment) activity;
            listener.saveAllModifications(customer);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (loadedRequestList == null) {
            loadedRequestList = new ArrayList<LoadedRequest>();
        }
        requestArrayAdapter = new ArrayAdapter<LoadedRequest>(getActivity()
                ,android.R.layout.simple_list_item_1);

        listView.setAdapter(requestArrayAdapter);

        if(task == null) {
            if (JsonRequest.hasConnection(getActivity())) {
                textView.setText("Sem conexão");
            }
        }else if (task.getStatus() == AsyncTask.Status.RUNNING) {
            exhibitPogress(true);
        }
    }
    private void exhibitPogress(boolean exhibit) {
        if (exhibit) {
            textView.setText("Carregando as requisiçoes disponíveis...");
        }
        textView.setVisibility(exhibit ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(exhibit ? View.VISIBLE : View.GONE);
    }
    public void initiateDownload() {
        if (task == null ||  task.getStatus() != AsyncTask.Status.RUNNING) {
            task = new RequestTask();
            task.execute();
        }
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


    class RequestTask extends AsyncTask<Void, Void, List<LoadedRequest>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exhibitPogress(true);
        }
        @Override
        protected List<LoadedRequest> doInBackground(Void... strings) {
            return JsonRequest.loadJsonRequest("POR_ALGO_AQUI");

        }

        @Override
        protected void onPostExecute(List<LoadedRequest> loadedRequest) {
            super.onPostExecute(loadedRequest);
            exhibitPogress(false);
            if (loadedRequest != null) {
                loadedRequestList.clear();
                loadedRequestList.addAll(loadedRequest);
                requestArrayAdapter.notifyDataSetChanged();
            } else {
                textView.setText("Falha ao obter requisições");
            }
        }
    }


}
