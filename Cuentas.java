package com.example.cristhian.bancatec;

import android.app.Activity;
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Cuentas extends AppCompatActivity {

    Button cuentas;
    TextView tv1, tv2;
    EditText numCuenta;
    String nom1,nom2,ap1,ap2,ced,tipo,tel,dir,ing,monIngreso,con,saldo,tipoCuenta,monCuenta,idCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        cuentas = (Button) findViewById(R.id.button5);
        tv1 = (TextView) findViewById(R.id.textView6);
        numCuenta = (EditText) findViewById(R.id.editText5);
        tv2 = (TextView) findViewById(R.id.textView11);
        tv2.setAlpha(0.0f);

        nom1 = getIntent().getStringExtra("Nombre1");
        nom2 = getIntent().getStringExtra("Nombre2");
        ap1 = getIntent().getStringExtra("Apellido1");
        ap2 = getIntent().getStringExtra("Apellido2");
        ced = getIntent().getStringExtra("Cedula");
        tipo = getIntent().getStringExtra("Tipo");
        tel = getIntent().getStringExtra("Telefono");
        dir = getIntent().getStringExtra("Direccion");
        ing = getIntent().getStringExtra("Ingreso");
        monIngreso = getIntent().getStringExtra("Moneda");
        con = getIntent().getStringExtra("Contra");

        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);


        tv1.setText("Bienvenido a BancaTec señor(a) " + nom1 + " " + ap1);
        cuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Conexion get = new Conexion();
                    get.execute(numCuenta.getText().toString());

                    if(Objects.equals(tv2.getText(), "OK")) {
                        msj.setText("Cuenta a consultar correcta.");
                        msj.show();
                        Intent next = new Intent(Cuentas.this, Main2Activity.class);
                        next.putExtra("Nombre1",nom1);
                        next.putExtra("Nombre2",nom2);
                        next.putExtra("Apellido1",ap1);
                        next.putExtra("Apellido2",ap2);
                        next.putExtra("Cedula",ced);
                        next.putExtra("Tipo",tipo);
                        next.putExtra("Telefono",tel);
                        next.putExtra("Direccion",dir);
                        next.putExtra("Ingreso",ing);
                        next.putExtra("Moneda",monIngreso);
                        next.putExtra("Contra",con);
                        next.putExtra("Saldo",saldo);
                        next.putExtra("TipoCuenta",tipoCuenta);
                        next.putExtra("Coin",monCuenta);
                        next.putExtra("IDCuenta",idCuenta);
                        startActivity(next);
                    }else{
                        msj.setText("Cuenta a consultar incorrecta o inexistente.");
                        msj.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                URL url = new URL("http://40.71.191.83/BancaTec/cuenta");

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
            String cedu = "";

            try {
                InputStream is = new ByteArrayInputStream(s.getBytes());

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);

                Element element = doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("Cuenta");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        idCuenta = getValue("NumCuenta",element2);
                        cedu = getValue("CedCliente",element2);

                        if(Objects.equals(idCuenta, numCuenta.getText().toString())){
                            if(Objects.equals(cedu, ced)){
                                tv2.setText("OK");
                                tipoCuenta = getValue("Tipo", element2);
                                monCuenta = getValue("Moneda", element2);
                                saldo = getValue("Saldo", element2);
                                break;
                            }else{
                               tv2.setText("OTRA");
                            }
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Cuentas.this, MainActivity.class);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
