package logica;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Provincia {
	public String nombre;
	public Coordinate coordenada;
	public ArrayList<String> limitrofes;

	public Provincia(String nombre, Coordinate coordenada, ArrayList<String> limitrofes) {
		this.nombre = nombre;
		this.coordenada = coordenada;
		this.limitrofes = limitrofes;
	}
}
