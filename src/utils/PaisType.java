package utils;

import logica.Provincia;

public class PaisType {
	private String nombre;
    private double latitud;
    private double longitud;
    private Provincia[] provincias;
    
    public String getNombre() {
		return nombre;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public Provincia[] getProvincias() {
		return provincias;
	}
	
}
