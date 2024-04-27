package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import utils.PaisType;

public class Pais {
	
	private final Provincia[] provincias;
	private String nombre;
	private int zoom;
	public double latitud;
	public double longitud;
	private Grafo grafo;
	
//	public Pais() {
//		this.provincias = Config.PROVINCIAS;
//		this.grafo = new Grafo(provincias.length);
//		asignarAristasLimitrofesPorDefecto();
//		this.grafo.prueba();
//	}
	
	public Pais(PaisType info) {
		this.provincias = info.getProvincias();
		this.grafo = new Grafo(provincias.length);
		this.latitud = info.getLatitud();
		this.longitud = info.getLongitud();
		this.zoom = info.getZoom();
		this.nombre = info.getNombre();
		
		asignarAristasLimitrofesPorDefecto();
		this.grafo.prueba();
	}

	public Provincia[] obtenerProvincias() {
		return this.provincias;
	}
	
	public void asignarAristasLimitrofesPorDefecto() {
		for (int i = 0; i < grafo.tamano(); i++) {
			for (int j = 0; j < grafo.tamano(); j++) {
				if (provincias[i].limitrofes.contains(provincias[j].nombre)) {
					grafo.agregarArista(i, j);
				}
			}
		}
	}
	
	
	public Set<Coordinate> obtenerCoordenadasLimitrofes(int v) {
		Set<Coordinate> vecinos = new HashSet<Coordinate>();

		for (int i = 0; i < grafo.tamano(); i++) {
			if (grafo.existeArista(v, i)) {
				Provincia provincia = this.provincias[i];
				vecinos.add(new Coordinate(provincia.getLatitud(), provincia.getLongitud()));
			}
		}
		return vecinos;
	}
	
	public Grafo obtenerGrafo() {
		return this.grafo;
	}
	
	public void actualizarSimililaridades() {
		this.grafo = AGM.generarArbolMinimo(grafo);
	}

	public void dividirRegiones(int i) {
		AGM.generarRegionesConexas(this.grafo, i);
	}

	public void reestablecerConexionEntreLimitrofes() {
		this.asignarAristasLimitrofesPorDefecto();
	}

	public int getZoom() {
		return zoom;
	}
	
	public String getNombre() {
		return nombre;
	}

	public ArrayList<String> obtenerLimitrofesDe(int indiceProvincia) {
		return this.provincias[indiceProvincia].limitrofes;
	}
}
