package com.example.biometria3a;

import android.util.Log;


// La clase Logica es la encargada de gestionar la lógica de la aplicación. 
public class Logica {
    private Object Medicion;  // Objeto de la clase Medicion

    public void guardarMedicion(Medidas medicion) {

        Log.d("test", "entra a guardar medicion");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();  // Objeto de la clase PeticionarioREST

        // Este es el JSON que se envía al servidor para insertar la medición en la base de datos
        String textoJSON = "{\"Medicion\":\"" + medicion.getMedicion() + "\", \"TipoSensor\":\"" + medicion.getTipoSensor() + "\", \"Latitud\":\"" + medicion.getLatitud() + "\", \"Longitud\":\"" + medicion.getLongitud() + "\"}";
        Log.d("JSON", textoJSON);
        elPeticionario.hacerPeticionREST("POST", "http://172.20.10.2/src/api/v1.0/index.php", textoJSON,   // Se envía la petición al servidor con la dirección que sale tras http://
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {  // Esto es lo que se ejecuta cuando se recibe la respuesta del servidor
                        Log.d("test", "Se ha insertado correctamente");
                    }
                }
        );


    }
}
