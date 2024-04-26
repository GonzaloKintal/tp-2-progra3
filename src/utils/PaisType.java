package utils;

import logica.Provincia;

public class PaisType {
	private String nombre;
    private double latitud;
    private double longitud;
    private int zoom;
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

	public int getZoom() {
		return zoom;
	}

	public Provincia[] getProvincias() {
		return provincias;
	}
	
}
