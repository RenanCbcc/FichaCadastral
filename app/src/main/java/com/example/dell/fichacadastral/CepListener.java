package com.example.dell.fichacadastral;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Dell on 23/07/2017.
 */

public class CepListener implements TextWatcher {
    private Context context;
    public CepListener( Context context ){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if( zipCode.length() == 8 ){
            new AddressTask( context ).execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
