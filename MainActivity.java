package com.example.cristhian.bancatec;

//Bibliotecas a utilizar en esta sección.
import android.content.Intent;
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
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
Clase MainActivity se encarga de realizar el Login de un cliente a la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    //Se de declaran las variables a utilizar en este activity.
    EditText id,pw;
    Button in;
    TextView tv1;
    String nom, segNom, priAp, segAp, ced, tel, mon, tipo, dir, ingr, contra;
    String est = "";


    @Override
    //Se instancian las variables a utilizar.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.editText);
        pw = (EditText) findViewById(R.id.editText2);
        in = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView10);
        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);

        //Al precionar el botón se realiza la conexión al servidor para la obtención de datos (GET) en este caso.
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se establce la conexión con la clase que hereda del paquete android.os.AsyncTask.
                Conexion get = new Conexion();
                get.execute();
                //sendPost post = new sendPost();
                //post.execute();

                if(est.equals("OK")) {

                    //Si la validación es correcta, se pasan los parámetros necesarios al siguiente activity.
                    msj.setText("¡Cuenta correcta, ingresando al sistema!");
                    msj.show();
                    Intent next = new Intent(MainActivity.this, Cuentas.class); //Con esta clase se crea un nuevo activity.
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
                    startActivity(next); //Con este método se pasa al siguiente activity.

                }else{
                    if(est.equals("0")){
                        msj.setText("Cuenta incorrecta o inexistente.");
                        msj.show();
                    }else{
                        if(est.equals("1")){
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

    /*
    Clase encargada de establecer la comunicación con el servidor.
    En ella se definen los métodos POST y GET.
    Necesita que se especifíque la dirección URL en ambos métodos.
    El método POST necesita además que se especifique la data a envíar.
     */
    private class Conexion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            try {
                //Se especifica el URL
                URL url = new URL("http://40.71.191.83/BancaTec/cliente");

                // se especifica el request
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                //Se declara la variable de entrada, para saber el response del servidor.
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
        //Una vez realizada la conexión, el método GET devuleve un XML con la información de interés.
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

                //Apartir de acá se buscan los datos que requieran mediante condiciones y parseo del XML...
                //...con la ayuda de una lista.
                NodeList nList = doc.getElementsByTagName("Cliente");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        ID = getValue("Cedula",element2);
                        PW = getValue("Contrasena",element2);

                        if(Objects.equals(ID, id.getText().toString())) {
                            if(Objects.equals(PW, pw.getText().toString())){
                                //Valores de interés por recuperar.
                                est = "OK";
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
                                est= "1";
                            }
                        }else{
                            est = "0";
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
            String data ="cuenta=9&numprestamo=18&extra=500&moneda=Colones";
            try {
                String fileString = new String(data.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String forecastJsonStr = null;

            try {
                //Se especifica el URL
                URL url = new URL("http://40.71.191.83/BancaTec/pago");

                // se especifica el request
                urlConnection = (HttpURLConnection) url.openConnection();
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





