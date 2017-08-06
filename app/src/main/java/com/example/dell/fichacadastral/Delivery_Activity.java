package com.example.dell.fichacadastral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Dell on 05/08/2017.
 */

public class Delivery_Activity extends Fragment implements View.OnClickListener {
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.delivery_layout, container, false);
        button = (Button) view.findViewById(R.id.btn_diasSemana);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
