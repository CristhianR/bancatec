package com.example.cristhian.bancatec;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tarjetas extends AppCompatActivity {


    TextView tv1;
    ListView list;
    String[] tarCredito;
    String[] ids;
    String nom1,ap1,ced,idCuenta,saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        tv1 = (TextView) findViewById(R.id.textView7);
        list = (ListView)findViewById(R.id.listview3);

        tarCredito = null;

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tarCredito = getIntent().getStringArrayExtra("Tarjetas");
        ids = getIntent().getStringArrayExtra("IDTar");
        saldo = getIntent().getStringExtra("Saldo");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tarCredito);

        tv1.setText("Tarjetas de crédito asociandos a la cuenta: " + idCuenta +"\n"
                + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        if(tarCredito!=null && tarCredito.length>0){
            list.setAdapter(adaptador);
        }else{
            Toast msj = Toast.makeText(getApplicationContext(),"Vacío!", Toast.LENGTH_SHORT);
            msj.show();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(Tarjetas.this,Tarjeta.class);
                next.putExtra("info",tarCredito[position]);
                next.putExtra("ID",ids[position]);
                next.putExtra("Saldo",saldo);
                next.putExtra("Nombre1",nom1);
                next.putExtra("Apellido1",ap1);
                next.putExtra("Cedula",ced);
                next.putExtra("Tarjetas",tarCredito);
                next.putExtra("IDTar",ids);
                next.putExtra("IDCuenta",idCuenta);
                startActivity(next);

            }

        });
    }

}
