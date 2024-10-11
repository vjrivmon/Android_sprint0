package com.example.biometria3a;

public class Medidas {
    private int medicion;
    private int tipoSensor;
    private double latitud;

    private double longitud;


    // Getters y Setters

    // -> getMedicion -> N
    public int getMedicion() {
        return medicion;
    }

    // N -> setMedicion -> 
    public void setMedicion(int medicion) {
        this.medicion = medicion;
    }

    // constructor
    // N, N, R, R -> Medidas
    public Medidas(int medicion, int tipoSensor, double latitud, double longitud) {
        this.medicion = medicion;
        this.tipoSensor = tipoSensor;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    // -> getTipoSensor -> N
    public int getTipoSensor() {
        return tipoSensor;
    }

    // N -> setTipoSensor ->
    public void setTipoSensor(int tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    // -> getLatitud -> R
    public double getLatitud() {
        return latitud;
    }

    // R -> setLatitud ->
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }


    // -> getLongitud -> R
    public double getLongitud() {
        return longitud;
    }

    // R -> setLongitud ->
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


}
