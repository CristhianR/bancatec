package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Movimientos extends AppCompatActivity {

    TextView tv1;
    String nom1,ap1,ced,idCuenta;
    ListView list;
    String[] movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        tv1 = (TextView) findViewById(R.id.textView13);
        list = (ListView)findViewById(R.id.listview);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        movimientos = getIntent().getStringArrayExtra("Movimientos");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movimientos);

        tv1.setText("Movimientos asociandos a la cuenta: " + idCuenta +"\n"
        + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        if(movimientos!=null && movimientos.length>0){
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
                Intent next = new Intent(Movimientos.this,prueba.class);
                next.putExtra("ID",movimientos[position]);
                startActivity(next);

            }

        });
    }
}
