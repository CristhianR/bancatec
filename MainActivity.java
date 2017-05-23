package com.example.cristhian.bancatec;

//https://www.dotcom-tools.com/web-server-performance-test.aspx
//http://www.hermosaprogramacion.com/2015/01/android-httpurlconnection/

import android.content.Context;
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
    TextView tv1;
    private final String USER_AGENT = "Mozilla/5.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.editText);
        pw = (EditText) findViewById(R.id.editText2);
        in = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView10);


        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Conexion get = new Conexion();
                    get.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //ConexionPost con = new ConexionPost();
                //con.execute();

                //Intent next = new Intent(MainActivity.this, Cuentas.class);
                //startActivity(next);
            }
        });
    }

    private class Conexion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {


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
            //tv1.setText(s);
            String busqueda = "";

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

                        busqueda = getValue("Nombre",element2);
                        

                        if(Objects.equals(busqueda, "Cristhian")) {
                            tv1.setText(tv1.getText() + "\nNombre: " + getValue("Nombre", element2));
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
                            tv1.setText(tv1.getText() + "-----------------------");
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
            String data = "nombre=Cristhian&segundonombre=Esteban&priapellido=Rojas&segapellido=Fuentes&telefono=84096782&direccion=Cartago&cedula=115930941&tipo=Fisico&contrasena=amon&ingreso=2000000&moneda=Colones";
            try {
                String fileString = new String(data.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String forecastJsonStr = null;

            try {
                //Se especifica el URL
                URL url = new URL("http://13.82.28.191/BancaTec/cliente");

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


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }


}//cierra todo







