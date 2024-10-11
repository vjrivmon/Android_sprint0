package com.example.biometria3a;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeticionarioREST extends AsyncTask<Void, Void, Boolean> {

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------

    // la interfaz RespuestaREST es para que el PeticionarioREST pueda llamar a un método de la clase que lo invoca
    // y así devolverle la respuesta que reciba del servidor REST
    public interface RespuestaREST {
        void callback (int codigo, String cuerpo);  // método que se llamará cuando el servidor REST responda
    }

    // --------------------------------------------------------------------
    // --------------------------------------------------------------------
    private String elMetodo;
    private String urlDestino;
    private String elCuerpo = null;
    private RespuestaREST laRespuesta;

    private int codigoRespuesta;
    private String cuerpoRespuesta = "";

    // --------------------------------------------------------------
    /*
     * Método para hacer la petición REST
     *
     * @param {String} metodo. Le pasamos el metodo
     * @param {String} urlDestino. Le pasamos la url de destino
     * @param {String} cuerpo. Le pasamos el cuerpo
     * @param RespuestaREST laRespuesta. Le pasamos la respuesta de la interfaz de RespuestaREST
     *
     * @return No devuelve nada
     */
    // --------------------------------------------------------------

    // este método es el que se llama desde la clase que quiera hacer una petición REST
    // string, string, string, RespuestaREST -> hacerPeticonREST -> 
    public void hacerPeticionREST (String metodo, String urlDestino, String cuerpo, RespuestaREST  laRespuesta) {
        this.elMetodo = metodo;
        this.urlDestino = urlDestino;
        this.elCuerpo = cuerpo;
        this.laRespuesta = laRespuesta;

        this.execute(); // otro thread ejecutará doInBackground()
    }

    /*
     * Método para PeticionarioREST
     *
     * @param No le pasamos nada
     *
     * @return No devuelve nada
     */

    // constructor
    public PeticionarioREST() {
        Log.d("clienterestandroid", "constructor()");
    }

    /*
     * Método para hacerlo en el Background
     *
     * @param {Void}
     *
     * @return bool: ToF
     */


    //Este es el verdadero cerebro de PeticionarioREST() en el que nos conectamos con la url,
    // vemos si es un post (¡GET) y hacemos la transferencia

    @Override

    // Void -> doInBackground -> T/F
    // sirve para hacer la petición REST en un thread distinto al principal (el de la UI)
    protected Boolean doInBackground(Void... params) {
        Log.d("clienterestandroid", "doInBackground()");

        try {

            // envio la peticion

            Log.d("clienterestandroid", "doInBackground() me conecto a >" + urlDestino + "<");

            URL url = new URL(urlDestino);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();   // abro la conexión con el servidor
            connection.setRequestProperty( "Content-Type", "application/json; charset-utf-8" );   // indico que el cuerpo de la petición es JSON
            connection.setRequestMethod(this.elMetodo);  // GET o POST
            // connection.setRequestProperty("Accept", "*/*);

            // connection.setUseCaches(false);
            connection.setDoInput(true);  // siempre true. es para leer la respuesta

            if ( ! this.elMetodo.equals("GET") && this.elCuerpo != null ) {  // si no es GET, pongo el cuerpo que me den en la petición
                Log.d("clienterestandroid", "doInBackground(): no es get, pongo cuerpo");
                connection.setDoOutput(true);  // para poder enviar el cuerpo
                connection.getOutputStream().write(this.elCuerpo.getBytes());  // escribo el cuerpo en la petición que se enviará al servidor
                // si no es GET, pongo el cuerpo que me den en la peticion
                //DataOutputStream dos = new DataOutputStream (connection.getOutputStream());
                //dos.writeBytes(this.elCuerpo);
                //dos.flush();
                //dos.close();
                connection.getOutputStream().flush(); // vacio el buffer de salida
                connection.getOutputStream().close(); // cierro el stream de salida

            }

            // ya he enviado la peticion
            Log.d("clienterestandroid", "doInBackground(): peticion enviada ");

            // ahora obtengo la respuesta

            int rc = connection.getResponseCode();  // código de respuesta 
            String rm = connection.getResponseMessage();  // mensaje de respuesta
            String respuesta = "" + rc + " : " + rm;  // respuesta = "200 : OK" o "404 : Not Found"
            Log.d("clienterestandroid", "doInBackground() recibo respuesta = " + respuesta);//OK o not found
            this.codigoRespuesta = rc;

            try {  // intento leer el cuerpo de la respuesta

                InputStream is = connection.getInputStream(); // obtengo el cuerpo de la respuesta (si lo hay)
                BufferedReader br = new BufferedReader(new InputStreamReader(is));  // lo leo línea a línea

                Log.d("clienterestandroid", "leyendo cuerpo");
                StringBuilder acumulador = new StringBuilder ();
                String linea;
                while ( (linea = br.readLine()) != null) {  // leo línea a línea
                    Log.d("clienterestandroid", linea);
                    acumulador.append(linea);  // acumulo las líneas en un StringBuilder
                }
                Log.d("clienterestandroid", "FIN leyendo cuerpo");

                this.cuerpoRespuesta = acumulador.toString();
                Log.d("clienterestandroid", "cuerpo recibido=" + this.cuerpoRespuesta);

                connection.disconnect();  // cierro la conexión

            } catch (IOException ex) { // dispara excepción cuando la respuesta REST no tiene cuerpo y yo intento getInputStream()
                // dispara excepcin cuando la respuesta REST no tiene cuerpo y yo intento getInputStream()
                Log.d("clienterestandroid", "doInBackground() : parece que no hay cuerpo en la respuesta");
            }

            return true; // doInBackground() termina bien

        } catch (Exception ex) {
            Log.d("clienterestandroid", "doInBackground(): ocurrio alguna otra excepcion: " + ex.getMessage());
        }

        return false; // doInBackground() NO termina bien
    } // ()

    /*
     * Método para el post ejecutado
     *
     * @param {Boolean} comoFue
     *
     * @return No devuelve nada
     */

    // este método se llama tras doInBackground()
    // T/F -> onPostExecute -> 
    protected void onPostExecute(Boolean comoFue) {
        // llamado tras doInBackground()
        Log.d("clienterestandroid", "onPostExecute() comoFue = " + comoFue);
        this.laRespuesta.callback(this.codigoRespuesta, this.cuerpoRespuesta);
    }

} // class


