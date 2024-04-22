package logica;

import java.util.HashSet;
import java.util.Set;

import utils.Config;

public class GrafoDeProvincias {

	private final Provincia[] provincias;
	private Arista[][] matrizAdyacente;

	public GrafoDeProvincias() {
		Config config = new Config();
		provincias = config.PROVINCIAS;
		int cantProvincias = provincias.length;

		matrizAdyacente = new Arista[cantProvincias][cantProvincias];
		instanciarAristas();
		asignarAristasPorDefecto();
	}

	private int tamañoMatriz() {
		return this.matrizAdyacente.length;
	}
	
	private void instanciarAristas() {
		for (int i = 0; i < tamañoMatriz(); i++) {
			for (int j = 0; j < tamañoMatriz(); j++) {
				matrizAdyacente[i][j] = new Arista();
			}
		}
	}

	private void asignarAristasPorDefecto() {
		for (int i = 0; i < tamañoMatriz(); i++) {
			for (int j = 0; j < tamañoMatriz(); j++) {
				if (provincias[i].limitrofes.contains(provincias[j].nombre)) {
					agregarArista(i, j);
				}				
			}
		}
	}

	// Getters y setters de aristas

	public void agregarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		matrizAdyacente[i][j].agregarArista();
		matrizAdyacente[j][i].agregarArista();
	}

	public void eliminarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		matrizAdyacente[i][j].eliminarArista();
		matrizAdyacente[j][i].eliminarArista();
	}

	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);

		return matrizAdyacente[i][j].existeArista;
	}

	public Set<Integer> vecinos(int v) {
		verificarVertice(v);

		Set<Integer> ret = new HashSet<Integer>();

		for (int i = 0; i < this.tamano(); i++) {
			if (this.existeArista(v, i)) {
				ret.add(i);
			}
		}

		return ret;
	}

	public int tamano() {
		return matrizAdyacente.length;
	}

	// Verifica que sea un vértice válido
	private void verificarVertice(int v) {
		if (v < 0) {
			throw new IllegalArgumentException("El vértice no puede ser negativo: " + v);
		}

		if (v >= matrizAdyacente.length) {
			throw new IllegalArgumentException("Los vértices deben estar entre 0 y |V|-1");
		}
	}

	// Verifica que i y j sean distintos
	private void verificarDistintos(int i, int j) {
		if (i == j) {
			throw new IllegalArgumentException("No se permiten loops!");
		}
	}

}
