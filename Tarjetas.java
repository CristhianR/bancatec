package com.example.cristhian.bancatec;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
Similar al activity TarjetasAsociadas, presenta el listView de las tarjetas de crédito asociadas a la cuenta, pero
además cada item del listView(Tarjeta de Crédito) funciona como botón, permitiendo crear un nuevo activity, con los datos
específicos de cada tarjeta.
 */
public class Tarjetas extends AppCompatActivity {


    TextView tv1;
    ListView list;
    String[] tarCredito;
    String[] ids;
    String nom1,ap1,ced,idCuenta,saldo,monCuenta,tipoCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        tv1 = (TextView) findViewById(R.id.textView7);
        list = (ListView)findViewById(R.id.listview3);

        //tarCredito = null;

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tarCredito = getIntent().getStringArrayExtra("Tarjetas");
        ids = getIntent().getStringArrayExtra("IDTar");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

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
                if(ids.length > 0) {
                    Intent next = new Intent(Tarjetas.this, Tarjeta.class);
                    next.putExtra("info", tarCredito[position]);
                    next.putExtra("ID", ids[position]);
                    next.putExtra("Saldo", saldo);
                    next.putExtra("Nombre1", nom1);
                    next.putExtra("Apellido1", ap1);
                    next.putExtra("Tarjetas", tarCredito);
                    next.putExtra("IDTar", ids);
                    next.putExtra("Cedula", ced);
                    next.putExtra("IDCuenta", idCuenta);
                    next.putExtra("Coin", monCuenta);
                    next.putExtra("TipoCuenta", tipoCuenta);
                    startActivity(next);
                }

            }

        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Tarjetas.this, Main2Activity.class);
            next.putExtra("Nombre1", nom1);
            next.putExtra("Apellido1", ap1);
            next.putExtra("Cedula", ced);
            next.putExtra("Saldo", saldo);
            next.putExtra("Coin", monCuenta);
            next.putExtra("TipoCuenta", tipoCuenta);
            next.putExtra("IDCuenta", idCuenta);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
