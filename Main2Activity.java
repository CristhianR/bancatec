package com.example.cristhian.bancatec;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

public class Main2Activity extends AppCompatActivity {

    Button cuenta;
    Button tarjetas;
    Button prestamos;
    String nom1,inte,ap1,ced,cedA,monPres,saldo,tipoCuenta,monCuenta,idCuenta,secc,
            salOrig,salActual,fechaExp,cs,res,idTar,idPres;
    String r = "";
    TextView tv1, tv2;
    List<String> tarCredito = new ArrayList<String>();
    List<String> idtarCredito = new ArrayList<String>();
    List<String> listpres = new ArrayList<String>();
    List<String> idlistpres = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cuenta = (Button) findViewById(R.id.button2);
        tarjetas = (Button) findViewById(R.id.button3);
        prestamos = (Button) findViewById(R.id.button4);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView8);

        nom1 = getIntent().getStringExtra("Nombre1");
        //nom2 = getIntent().getStringExtra("Nombre2");
        ap1 = getIntent().getStringExtra("Apellido1");
        //ap2 = getIntent().getStringExtra("Apellido2");
        ced = getIntent().getStringExtra("Cedula");
        //tipo = getIntent().getStringExtra("Tipo");
        //tel = getIntent().getStringExtra("Telefono");
        //dir = getIntent().getStringExtra("Direccion");
        //ing = getIntent().getStringExtra("Ingreso");
        //monIngreso = getIntent().getStringExtra("Moneda");
        //con = getIntent().getStringExtra("Contra");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        tv1.setText("Bienvenido a BancaTec señor(a) " + nom1 + " " + ap1);
        tv2.setText("Su número de cuenta es: " + idCuenta + "\n" + "Tipo de cuenta: " + tipoCuenta
        +"\n" + "Saldo Actual de cuenta: " + saldo + "   " + monCuenta);

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Main2Activity.this,Cuenta.class);
                next.putExtra("Nombre1",nom1);
                next.putExtra("Apellido1",ap1);
                next.putExtra("Cedula",ced);
                next.putExtra("Saldo",saldo);
                next.putExtra("TipoCuenta",tipoCuenta);
                next.putExtra("Coin",monCuenta);
                next.putExtra("IDCuenta",idCuenta);
                startActivity(next);
            }
        });
        tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secc = "tarjeta";
                String[] tarjetas = new String[tarCredito.size()];
                tarjetas = tarCredito.toArray(tarjetas);
                String[] IDS = new String[idtarCredito.size()];
                IDS = idtarCredito.toArray(IDS);

                try {
                    Conexion get = new Conexion();
                    get.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(r.equals(("nice"))) {
                    r="";
                    Intent next = new Intent(Main2Activity.this, Tarjetas.class);
                    next.putExtra("Nombre1", nom1);
                    next.putExtra("Apellido1", ap1);
                    next.putExtra("Cedula", ced);
                    next.putExtra("IDCuenta", idCuenta);
                    next.putExtra("Tarjetas", tarjetas);
                    next.putExtra("IDTar", IDS);
                    next.putExtra("Saldo", saldo);
                    tarCredito.removeAll(tarCredito);
                    idtarCredito.removeAll(idtarCredito);
                    secc="";
                    startActivity(next);
                }
            }
        });


        prestamos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            secc = "prestamo";
            String[] pres = new String[listpres.size()];
            pres = listpres.toArray(pres);
            String[] idspres = new String[idlistpres.size()];
            idspres = idlistpres.toArray(idspres);

            Conexion get = new Conexion();
            get.execute();

            if(r.equals(("nice"))) {
                r = "";
                Intent next = new Intent(Main2Activity.this, Prestamos.class);
                next.putExtra("Nombre1", nom1);
                next.putExtra("Apellido1", ap1);
                next.putExtra("Cedula", ced);
                next.putExtra("Saldo", saldo);
                next.putExtra("TipoCuenta", tipoCuenta);
                next.putExtra("Coin", monCuenta);
                next.putExtra("IDCuenta", idCuenta);
                next.putExtra("Prestamos", pres);
                next.putExtra("IDSPres", idspres);
                listpres.removeAll(listpres);
                idlistpres.removeAll(idlistpres);
                secc = "";
                startActivity(next);
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
                URL url = new URL("http://40.71.191.83/BancaTec/" + secc);

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

                if(secc.equals("tarjeta")){

                NodeList nList = doc.getElementsByTagName("Tarjeta");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        ID = getValue("NumCuenta", element2);
                        tipoCuenta = getValue("Tipo", element2);

                        if (Objects.equals(ID, idCuenta)) {
                            if (Objects.equals(tipoCuenta, "Credito")) {
                                r = "nice";

                                fechaExp = getValue("FechaExp", element2);
                                cs = getValue("CodigoSeg", element2);
                                salActual = getValue("SaldoActual", element2);
                                salOrig = getValue("SaldoOrig", element2);
                                idTar = getValue("Numero", element2);
                                res = "Código de Seguridad: " + cs + "\n" +
                                        "FechaExp: " + fechaExp + "\n" +
                                        "Saldo Original: " + salOrig + "\n" +
                                        "Saldo Actual: " + salActual + "\n" +
                                        "ID de Tarjeta: " + idTar;

                                tarCredito.add(res);
                                idtarCredito.add(idTar);
                                res = "";
                            }
                        }
                    }
                }
                }else {
                    if (secc.length() > 0) {
                        NodeList nList = doc.getElementsByTagName("Prestamo");

                        for (int i = 0; i < nList.getLength(); i++) {

                            Node node = nList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node;

                                ID = getValue("CedCliente", element2);
                                tipoCuenta = getValue("Tipo", element2);

                                if (Objects.equals(ID, ced)) {


                                    inte = getValue("Interes", element2);
                                    cedA = getValue("CedAsesor", element2);
                                    salActual = getValue("SaldoActual", element2);
                                    salOrig = getValue("SaldoOrig", element2);
                                    idPres = getValue("Numero", element2);
                                    monPres = getValue("Moneda", element2);
                                    res = "Interés: " + inte + "\n" +
                                            "Moneda: " + monPres + "\n" +
                                            "Saldo Original: " + salOrig + "\n" +
                                            "Saldo Actual: " + salActual + "\n" +
                                            "Cédula de Acesor:" + cedA;

                                    listpres.add(res);
                                    idlistpres.add(idPres);
                                    res = "";

                                }
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
            Intent next = new Intent(Main2Activity.this, Cuentas.class);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
