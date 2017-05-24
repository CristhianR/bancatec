package com.example.cristhian.bancatec;

//https://www.dotcom-tools.com/web-server-performance-test.aspx
//http://www.hermosaprogramacion.com/2015/01/android-httpurlconnection/

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    Button in;
    TextView tv1, tv2;
    String nom, segNom, priAp, segAp, ced, tel, mon, tipo, dir, ingr, contra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.editText);
        pw = (EditText) findViewById(R.id.editText2);
        in = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView10);
        tv2 = (TextView) findViewById(R.id.textView12);
        tv2.setAlpha(0.0f);
        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Conexion get = new Conexion();
                    get.execute(id.getText().toString(),pw.getText().toString());
                    //sendPost post = new sendPost();
                    //post.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //ConexionPost con = new ConexionPost();
                //con.execute();

                if(tv2.getText().equals("OK")) {

                    msj.setText("¡Cuenta correcta, ingresando al sistema!");
                    msj.show();
                    //tv1.setText(nom+segNom+priAp+segAp+ced+tipo+dir+tel+ingr+mon+contra);
                    Intent next = new Intent(MainActivity.this, Cuentas.class);

                    next.putExtra("Nombre1",nom);
                    next.putExtra("Nombre2",segNom);
                    next.putExtra("Apellido1",priAp);
                    next.putExtra("Apellido2",segAp);
                    next.putExtra("Cedula",ced);
                    next.putExtra("Tipo",tipo);
                    next.putExtra("Telefono",tel);
                    next.putExtra("Direccion",dir);
                    next.putExtra("Ingreso",ingr);
                    next.putExtra("Moneda",mon);
                    next.putExtra("Contra",contra);

                    startActivity(next);

                }else{
                    if(tv2.getText().equals("0")){
                        msj.setText("Cuenta incorrecta o inexistente.");
                        msj.show();
                    }else{
                        if(tv2.getText().equals("1")){
                            msj.setText("Contraseña incorrecta.");
                            msj.show();
                        }else{
                            msj.setText("Introzuca los datos por favor.");
                            msj.show();
                        }
                    }
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

            try {
                //Se especifica el URL
                URL url = new URL("http://13.82.28.191/BancaTec/cliente");

                // se especifica el request
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

                NodeList nList = doc.getElementsByTagName("Cliente");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        ID = getValue("Cedula",element2);
                        PW = getValue("Contrasena",element2);

                        if(Objects.equals(ID, id.getText().toString())) {
                            if(Objects.equals(PW, pw.getText().toString())){
                                tv2.setText("OK");
                                nom = getValue("Nombre",element2);
                                segNom = getValue("SegundoNombre",element2);
                                priAp = getValue("PriApellido",element2);
                                segAp = getValue("SegApellido",element2);
                                ced = getValue("Cedula",element2);
                                tipo = getValue("Tipo",element2);
                                tel = getValue("Telefono",element2);
                                dir = getValue("Direccion",element2);
                                ingr = getValue("Ingreso",element2);
                                mon = getValue("Moneda",element2);
                                contra = getValue("Contrasena",element2);
                                break;
                            }else{
                                tv2.setText("1");
                            }
                        }else{
                            tv2.setText("0");
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

//data = "Nombre=" + URLEncoder.encode("Marco", "UTF-8") +
//                      "&Descripcion=" + URLEncoder.encode("EsoProfe", "UTF-8");
    //int responseCode = urlConnection.getResponseCode();

    private class sendPost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //String data = "nombre=Cristhian&segundonombre=Esteban&priapellido=Rojas&segapellido=Fuentes&telefono=84096782&direccion=Cartago&cedula=115930941&tipo=Fisico&contrasena=amon&ingreso=2000000&moneda=Colones";
            String data ="codigoseg=100&fechaexp=2025-10-10T00:00:00&tipo=Debito&numcuenta=5&saldoorig=500000000";
            try {
                String fileString = new String(data.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String forecastJsonStr = null;

            try {
                //Se especifica el URL
                URL url = new URL("http://13.82.28.191/BancaTec/tarjeta");

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

}//cierra todo


                                /*tv1.setText(tv1.getText() + "\nNombre: " + getValue("Nombre", element2));
                                tv1.setText(tv1.getText() + "SegNombre: " + getValue("SegundoNombre", element2));
                                tv1.setText(tv1.getText() + "PriApellido: " + getValue("PriApellido", element2));
                                tv1.setText(tv1.getText() + "SegApellido: " + getValue("SegApellido", element2));
                                tv1.setText(tv1.getText() + "Cedula: " + getValue("Cedula", element2));
                                tv1.setText(tv1.getText() + "Tipo: " + getValue("Tipo", element2));
                                tv1.setText(tv1.getText() + "Telefono: " + getValue("Telefono", element2));
                                tv1.setText(tv1.getText() + "Direccion: " + getValue("Direccion", element2));
                                tv1.setText(tv1.getText() + "Ingreso: " + getValue("Ingreso", element2));
                                tv1.setText(tv1.getText() + "Moneda: " + getValue("Moneda", element2));
                                tv1.setText(tv1.getText() + "Contraseña: " + getValue("Contrasena", element2));
                                tv1.setText(tv1.getText() + "-----------------------");*/





