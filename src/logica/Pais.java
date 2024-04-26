package logica;

import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import utils.Config;

public class Pais {
	
	private final Provincia[] provincias;
	private Grafo grafo;
	
	public Pais() {
		this.provincias = Config.PROVINCIAS;
		this.grafo = new Grafo(23);
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
		AGM.generarRegionesConexas(this.grafo, 3);
	}
	
}
