package com.example.dell.fichacadastral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.txt_singup);
        button = (Button) findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, Deliverer_Activity.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }


}
