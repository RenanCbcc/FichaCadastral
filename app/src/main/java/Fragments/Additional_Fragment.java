package Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.fichacadastral.R;

/**
 * Created by Dell on 20/07/2017.
 */

public class Additional_Fragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.additional_layout,container,false);
    }

    @Override
    public void onClick(View view) {

    }
}
