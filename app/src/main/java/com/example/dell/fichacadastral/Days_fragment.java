package com.example.dell.fichacadastral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Dell on 21/07/2017.
 */

public class Days_fragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.string_array_dias, android.R.layout.simple_list_item_multiple_choice);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.days_layout, container, false);
        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        String prompt =
                "clicked item: " + getListView().getItemAtPosition(position).toString() + "\n\n";

        prompt += "selected items: \n";
        int count = getListView().getCount();
        SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();
        for (int i = 0; i < count; i++) {
            if (sparseBooleanArray.get(i)) {
                prompt += getListView().getItemAtPosition(i).toString() + "\n";
            }
        }

        Toast.makeText(
                getActivity(),
                prompt,
                Toast.LENGTH_LONG).show();
    }

}
