package com.example.cristhian.bancatec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class TarjetasAsociadas extends AppCompatActivity {

    TextView tv1;
    ListView list;
    String nom1,ap1,ced,idCuenta,monto,fecha,tipoTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_asociadas);
        tv1 = (TextView) findViewById(R.id.textView16);
        list = (ListView)findViewById(R.id.listview2);
    }
}
