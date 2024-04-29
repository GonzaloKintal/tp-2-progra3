package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import utils.PaisType;
import utils.Tupla;

public class Pais {

	private final Provincia[] provincias;
	private String nombre;
	private int zoom;
	public double latitud;
	public double longitud;
	private Grafo grafo;

	public Pais(PaisType info) {
		this.provincias = info.getProvincias();
		this.grafo = new Grafo(provincias.length);
		this.latitud = info.getLatitud();
		this.longitud = info.getLongitud();
		this.zoom = info.getZoom();
		this.nombre = info.getNombre();

		asignarAristasLimitrofesPorDefecto();
	}

	public Provincia[] obtenerProvincias() {
		return this.provincias;
	}
	
	public String obtenerNombrePorIndice(int idxProvincia) {
		return this.provincias[idxProvincia].nombre;
	}

	public void asignarAristasLimitrofesPorDefecto() {
		for (int i = 0; i < grafo.tamano(); i++) {
			for (int j = 0; j < grafo.tamano(); j++) {
				if (provincias[i].limitrofes.contains(provincias[j].nombre)) {
					grafo.agregarArista(i, j);
					grafo.agregarPeso(i, j, 0);
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

	public ArrayList<Tupla<String, Integer>> obtenerAristasLimitrofes(String nombreProvincia) {
		ArrayList<Tupla<String, Integer>> ret = new ArrayList<>();

		ArrayList<String> limitrofes = dameLimitrofesDe(nombreProvincia);

		int indiceProvincia = indiceDe(nombreProvincia);

		for (String limit : limitrofes) {

			int peso = this.grafo.consultarPeso(indiceProvincia, indiceDe(limit));
			ret.add(new Tupla<>(limit, peso));
		}

		return ret;
	}

	public int indiceDe(String nombre) {
		int idx = 0;

		for (int i = 0; i < this.provincias.length; i++) {
			if (provincias[i].nombre.equals(nombre)) {
				idx = i;
			}
		}

		return idx;
	}

	private ArrayList<String> dameLimitrofesDe(String nombre) {
		ArrayList<String> limitrofes = this.provincias[indiceDe(nombre)].limitrofes;

		return limitrofes;
	}
	
	public boolean esPosibleDividirRegiones(int cantRegiones) {
		return this.grafo.esPosibleDesconexar(cantRegiones);
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

	public void asignarPesosAleatoriamente() {
		grafo.prueba();

	}

	public void actualizarSimilaridad(int indiceProvincia1, int indiceProvincia2, int similaridad) {
		this.grafo.agregarPeso(indiceProvincia1, indiceProvincia2, similaridad);
	}
}
