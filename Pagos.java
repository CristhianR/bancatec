package com.example.cristhian.bancatec;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Este activity realiza los POST para los pagos, tanto ordinarios como extraordinarios de un préstamo en específico.
 */
public class Pagos extends AppCompatActivity {

    Button po,pex;
    EditText mon;
    TextView tv1;

    String id,saldo,monto,data,idCuenta,monpres,nom1,ap1,ced,monCuenta,tipoCuenta;
    String[] prestamos,idpres,monpres2;
    String forecastJsonStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        po = (Button) findViewById(R.id.button9);
        pex = (Button) findViewById(R.id.button10);
        mon = (EditText) findViewById(R.id.editText4);
        tv1 = (TextView) findViewById(R.id.textView18);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");
        prestamos = getIntent().getStringArrayExtra("Prestamos");
        idpres = getIntent().getStringArrayExtra("IDPres");
        monpres2 = getIntent().getStringArrayExtra("MonPres2");

        id = getIntent().getStringExtra("ID");
        saldo = getIntent().getStringExtra("Saldo");
        idCuenta= getIntent().getStringExtra("IDCuenta");
        monpres = getIntent().getStringExtra("MonPres");

        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);
        tv1.setText("Es esta sección podrá realizar los pagos asociados a su cuenta");

        po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data ="cuenta=" + idCuenta + "&numprestamo=" + id;;
                sendPost post = new sendPost();
                post.execute();
                msj.setText(forecastJsonStr);
                msj.show();
            }
        });

        pex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monto = mon.getText().toString();
                data ="cuenta=" + idCuenta + "&numprestamo=" + id + "&extra=" + monto + "&moneda=" + monpres;;
                sendPost post = new sendPost();
                post.execute();
                msj.setText(forecastJsonStr);
                msj.show();
            }
        });
    }



    private class sendPost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //String data = "nombre=Cristhian&segundonombre=Esteban&priapellido=Rojas&segapellido=Fuentes&telefono=84096782&direccion=Cartago&cedula=115930941&tipo=Fisico&contrasena=amon&ingreso=2000000&moneda=Colones";
            try {
                String fileString = new String(data.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            try {
                //Se especifica el URL
                URL url = new URL("http://40.71.191.83/BancaTec/pago");
                Log.d("URL",url.toString());
                Log.d("DATA",data);

                // se especifica el request
                urlConnection = (HttpURLConnection) url.openConnection();
                //String responseCode = Integer.toString(urlConnection.getResponseCode());

                urlConnection.setRequestMethod("POST");
                int length = data.getBytes().length;
                urlConnection.setFixedLengthStreamingMode(length);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", Integer.toString(length));


                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                out.write(data.getBytes());
                out.flush();
                out.close();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    Log.d("llego:", inputStream.toString());
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.d("Response: ",forecastJsonStr);
                return forecastJsonStr;
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Pagos.this, Prestamos.class);
            next.putExtra("Nombre1", nom1);
            next.putExtra("Apellido1", ap1);
            next.putExtra("Cedula", ced);
            next.putExtra("Saldo", saldo);
            next.putExtra("Coin", monCuenta);
            next.putExtra("TipoCuenta", tipoCuenta);
            next.putExtra("MonPres2",monpres2);
            next.putExtra("IDPres",idpres);
            next.putExtra("Prestamos",prestamos);
            next.putExtra("IDCuenta", idCuenta);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
