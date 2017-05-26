package com.example.cristhian.bancatec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class prueba extends AppCompatActivity {

    String id;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        tv1 = (TextView) findViewById(R.id.textView17);
        id = getIntent().getStringExtra("ID");

        tv1.setText("OK: " + id);
    }
}
