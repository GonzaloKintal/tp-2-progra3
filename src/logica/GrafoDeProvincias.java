package logica;

import java.util.HashSet;
import java.util.Set;

import utils.Config;

public class GrafoDeProvincias {

	private Provincia[] provincias;
	private Arista[][] _adj;

	public GrafoDeProvincias() {
		provincias = new Config().PROVINCIAS;
		Integer cantProvincias = provincias.length;

		_adj = new Arista[cantProvincias][cantProvincias];
		for (int i = 0; i < cantProvincias; i++) {
			for (int j = 0; j < cantProvincias; j++) {
				_adj[i][j] = new Arista();

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

		_adj[i][j].agregarArista();
		_adj[j][i].agregarArista();
	}

	public void eliminarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		_adj[i][j].eliminarArista();
		_adj[j][i].eliminarArista();

	}

	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);

		return _adj[i][j].existeArista;
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
		return _adj.length;
	}

	// Verifica que sea un vértice válido
	private void verificarVertice(int v) {
		if (v < 0) {
			throw new IllegalArgumentException("El vértice no puede ser negativo: " + v);
		}

		if (v >= _adj.length) {
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
