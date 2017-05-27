package com.example.cristhian.bancatec;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
Este activity presenta el listView de los préstamos asociados a la cuenta del cliente, además cada item tiene funcionalidad
de botón para especificar datos.
 */
public class Prestamos extends AppCompatActivity {

    TextView tv1;
    String nom1,ap1,ced,saldo,tipoCuenta,monCuenta,idCuenta;
    ListView list;
    String[] prestamos,idpres,monpres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);
        tv1 = (TextView) findViewById(R.id.textView4);
        list = (ListView)findViewById(R.id.listview5);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");
        prestamos = getIntent().getStringArrayExtra("Prestamos");
        idpres = getIntent().getStringArrayExtra("IDPres");
        monpres = getIntent().getStringArrayExtra("MonPres2");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prestamos);

        tv1.setText("Préstamos asociados al: " + "\n"
                + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        if(prestamos!=null && prestamos.length>0){
            list.setAdapter(adaptador);
        }else{
            Toast msj = Toast.makeText(getApplicationContext(),"Vacío!", Toast.LENGTH_SHORT);
            msj.show();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Intent next = new Intent(Prestamos.this, Pagos.class);
                next.putExtra("ID", idpres[position]);
                next.putExtra("Saldo",saldo);
                next.putExtra("Nombre1",nom1);
                next.putExtra("Apellido1",ap1);
                next.putExtra("TipoCuenta",tipoCuenta);
                next.putExtra("IDCuenta",idCuenta);
                next.putExtra("Coin",monCuenta);
                next.putExtra("IDCuenta",idCuenta);
                next.putExtra("MonPres",monpres[position]);
                next.putExtra("MonPres2",monpres);
                next.putExtra("IDPres",idpres);
                next.putExtra("Prestamos",prestamos);
                next.putExtra("Cedula",ced);
                startActivity(next);

            }
        });


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Prestamos.this, Main2Activity.class);
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
