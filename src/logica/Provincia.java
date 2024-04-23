package logica;

import java.util.ArrayList;

public class Provincia {
	public String nombre;
	public double latitud;
	public double longitud;
	public ArrayList<String> limitrofes;

	public Provincia(String nombre, double latitud, double longitud, ArrayList<String> limitrofes) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.limitrofes = limitrofes;
	}

	@Override
	public String toString() {
		return "Provincia [nombre=" + nombre + ", latitud=" + latitud + ", longitud=" + longitud + ", limitrofes="
				+ limitrofes + "]";
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<String> getLimitrofes() {
		return limitrofes;
	}
}
