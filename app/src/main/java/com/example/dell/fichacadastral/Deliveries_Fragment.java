package com.example.dell.fichacadastral;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Dell on 05/08/2017.
 */

public class Deliveries_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    private static final String EXTRA_CUSTOMER = "customer"; // tuple{Key,value}
    private Customer customer;
    private TextView txt_Name;
    private TextView txt_Nivel;
    private TextView txt_Xp;
    private TextView txt_Feed;
    private TextView txt_Mean;
    private ToggleButton toggleButton;
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Activity activity = getActivity();
        if (activity instanceof onModifyFragment) {
            if (toggleButton.getText().toString().equals("Disponivel")) {
                toggleButton.setChecked(false);
                if (customer != null) {
                    customer.setAvailable(false);
                    Toast.makeText(getActivity(), "Estou indisponível para realizar Entregas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor", Toast.LENGTH_SHORT).show();
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



    public interface onModifyFragment {
        void saveAllModifications(Customer customer);
    }

    @Override
    public void onStart() {
        super.onStart();

    }





}
