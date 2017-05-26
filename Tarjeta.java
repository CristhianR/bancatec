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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Tarjeta extends AppCompatActivity {

    TextView tv1;
    String info,id,m,saldo,com,mon,fecha,res,nom1,ap1,ced,idCuenta,fi,ff;
    String [] tarCredito,ids;
    Button pago,lista;
    EditText monto,f1,f2;
    int sal,n,l1,l2;
    List<String> compras = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        monto = (EditText) findViewById(R.id.editText6);
        f1 = (EditText) findViewById(R.id.editText8);
        f2 = (EditText) findViewById(R.id.editText9);
        pago = (Button) findViewById(R.id.button11);
        lista = (Button) findViewById(R.id.button12);
        id = getIntent().getStringExtra("ID");
        saldo = getIntent().getStringExtra("Saldo");
        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tarCredito = getIntent().getStringArrayExtra("Tarjetas");
        ids = getIntent().getStringArrayExtra("IDTar");
        fi = f1.getText().toString();
        ff = f2.getText().toString();




        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);

        pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = monto.getText().toString();

                Log.d("ESTO ES M: ", m);

                if(m.length() > 0) {
                    sal = Integer.parseInt(saldo);
                    n = Integer.parseInt(m);
                    if(sal >= n) {
                        msj.setText("¡Pago realizado exitosamente!");
                        msj.show();
                        sendPost post = new sendPost();
                        post.execute();
                    }else{
                        msj.setText("¡Usted no tiene suficientes fondos para pagar!");
                        msj.show();
                    }
                }else{
                    msj.setText("Ingrese el monto a cancelar");
                    msj.show();
                }
            }
        });

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                l1 = f1.length();
                l2 = f2.length();
                fi = f1.getText().toString();
                ff = f2.getText().toString();

                Conexion get = new Conexion();
                get.execute();

                String[] listado = new String[compras.size()];
                listado = compras.toArray(listado);

                if(listado.length > 0) {
                    Intent next = new Intent(Tarjeta.this, Listado.class);
                    next.putExtra("Nombre1", nom1);
                    next.putExtra("Apellido1", ap1);
                    next.putExtra("Cedula", ced);
                    next.putExtra("IDTarjeta", id);
                    next.putExtra("Compras", listado);
                    next.putExtra("Saldo",saldo);
                    next.putExtra("Tarjetas",tarCredito);
                    next.putExtra("IDTar",ids);
                    next.putExtra("IDCuenta",idCuenta);
                    compras.removeAll(compras);
                    startActivity(next);
                }else{
                    msj.setText("No hay compras realizadas con esta tarjeta");
                    msj.show();
                }
            }
        });
    }


    private class Conexion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;
            URL url = null;

            try {
                //Se especifica el URL
                if(l1 > 0 && l2 > 0) {
                    url = new URL("http://40.71.191.83/BancaTec/compra" + "?numtarjeta=" + id +
                            "&fechainicial=" + fi + "&fechafinal=" + ff);
                }else{
                    url = new URL("http://40.71.191.83/BancaTec/compra");
                }

                // se especifica el request
                Log.d("URL",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

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

            String ID = "";
            String PW = "";

            try {
                InputStream is = new ByteArrayInputStream(s.getBytes());

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);

                Element element = doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("Compra");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        ID = getValue("NumTarjeta",element2);
                        if(ID.equals(id)){

                            com = getValue("Comercio",element2);
                            mon = getValue("Monto",element2);
                            fecha = getValue("Fecha",element2);

                            res = "Comercio: " + com + "\n" +
                                    "Monto: " + mon + "\n" +
                                    "Fecha: " + fecha;
                            compras.add(res);

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String getValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }

    }

    private class sendPost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //String data = "nombre=Cristhian&segundonombre=Esteban&priapellido=Rojas&segapellido=Fuentes&telefono=84096782&direccion=Cartago&cedula=115930941&tipo=Fisico&contrasena=amon&ingreso=2000000&moneda=Colones";
            String data ="numtarjeta=" + id + "&monto=" + m;
            try {
                String fileString = new String(data.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String forecastJsonStr = null;

            try {
                //Se especifica el URL
                URL url = new URL("http://40.71.191.83/BancaTec/cancelartarjeta");

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
            Intent next = new Intent(Tarjeta.this, Tarjetas.class);
            next.putExtra("Nombre1", nom1);
            next.putExtra("Apellido1", ap1);
            next.putExtra("Cedula", ced);
            next.putExtra("Tarjetas",tarCredito);
            next.putExtra("IDTar",ids);
            next.putExtra("IDCuenta",idCuenta);
            next.putExtra("Saldo",saldo);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
