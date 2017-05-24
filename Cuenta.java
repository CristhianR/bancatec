package com.example.cristhian.bancatec;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class Cuenta extends AppCompatActivity {

    Button mov, tarAsoc, trans;
    String nom1,ap1,ced,saldo,tipoCuenta,monCuenta,idCuenta,sec,monto,fecha,tipoTrans,res,salOrig,salActual,fechaExp,cs;
    List<String> transacciones = new ArrayList<String>();
    List<String> tarDebito = new ArrayList<String>();
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        mov = (Button) findViewById(R.id.button6);
        tarAsoc = (Button) findViewById(R.id.button7);
        trans = (Button) findViewById(R.id.button8);

        tv2 = (TextView) findViewById(R.id.textView14);
        tv2.setAlpha(0.0f);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = "movimiento";
                String[] movimientos = new String[transacciones.size()];
                movimientos = transacciones.toArray(movimientos);

                try {
                    Conexion get = new Conexion();
                    get.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(tv2.getText() == "ok") {
                    Intent next = new Intent(Cuenta.this, Movimientos.class);
                    next.putExtra("Nombre1", nom1);
                    next.putExtra("Apellido1", ap1);
                    next.putExtra("Cedula", ced);
                    next.putExtra("IDCuenta", idCuenta);
                    next.putExtra("Movimientos", movimientos);
                    startActivity(next);
                }

            }
        });

        tarAsoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = "tarjeta";
                String[] tarjetas = new String[tarDebito.size()];
                tarjetas = tarDebito.toArray(tarjetas);

                try {
                    Conexion get = new Conexion();
                    get.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if(tv2.getText() == "nice") {
                    Intent next = new Intent(Cuenta.this, TarjetasAsociadas.class);
                    next.putExtra("Nombre1", nom1);
                    next.putExtra("Apellido1", ap1);
                    next.putExtra("Cedula", ced);
                    next.putExtra("IDCuenta", idCuenta);
                    next.putExtra("Tarjetas", tarjetas);
                    startActivity(next);
                }

            }
        });

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Cuenta.this, Transacciones.class);

                next.putExtra("Cedula", ced);
                next.putExtra("IDCuenta", idCuenta);
                next.putExtra("Saldo", saldo);
                next.putExtra("Moneda", monCuenta);
                next.putExtra("TipoCuenta", tipoCuenta);
                startActivity(next);
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

                Log.d("URL",sec);
                URL url = new URL("http://13.82.28.191/BancaTec/" + sec);

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

                if(Objects.equals(sec, "movimiento")) {

                    NodeList nList = doc.getElementsByTagName("Movimiento");

                    for (int i = 0; i < nList.getLength(); i++) {

                        Node node = nList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) node;

                            ID = getValue("NumCuenta", element2);

                            if (Objects.equals(ID, idCuenta)) {

                                tv2.setText("ok");
                                tipoTrans = getValue("Tipo", element2);
                                fecha = getValue("Fecha", element2);
                                monto = getValue("Monto", element2);
                                res = "Transacción: " + tipoTrans + "\n" +
                                        "Fecha: " + fecha + "\n" +
                                        "Monto: " + monto;
                                transacciones.add(res);
                                res = "";
                            }
                        }
                    }
                }else{
                    Log.d("Ok","ok");
                    if(Objects.equals(sec, "tarjeta")){

                        NodeList nList = doc.getElementsByTagName("Tarjeta");

                        for (int i = 0; i < nList.getLength(); i++) {

                            Node node = nList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node;

                                ID = getValue("NumCuenta", element2);
                                tipoCuenta = getValue("Tipo", element2);

                                if (Objects.equals(ID, idCuenta)) {
                                    if (Objects.equals(tipoCuenta, "Debito")) {
                                        tv2.setText("nice");

                                        fechaExp = getValue("FechaExp", element2);
                                        cs = getValue("CodigoSeg", element2);
                                        salActual = getValue("SaldoActual", element2);
                                        salOrig = getValue("SaldoOrig", element2);
                                        res = "Código de Seguridad: " + cs + "\n" +
                                                "FechaExp: " + fechaExp + "\n" +
                                                "Saldo Original: " + salOrig + "\n" +
                                                "Saldo Actual: " + salActual;
                                        tarDebito.add(res);
                                        res = "";
                                    }
                                }
                            }
                        }

                    } //aca lo otro!
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
}
