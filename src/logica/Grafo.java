package logica;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
	
	
	private int _vertices;
	private boolean[][] _adj;
	
	
	public Grafo(int n) {
		_vertices = n;
		_adj = new boolean[n][n];
	}
	
	
	// Getters y setters de aristas
	
	public void agregarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);
		
		_adj[i][j] = _adj[j][i] = true;
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
	
	
	public void eliminarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);
		
		_adj[i][j] = _adj[j][i] = false;
	}
	
	
	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		
		return _adj[i][j];
	}
	
	
	public Set<Integer> vecinos(int v) {
		verificarVertice(v);
		
		Set<Integer> ret = new HashSet<Integer>();
		
		for (int i=0; i < this.tamano(); i++) {
			if (this.existeArista(v, i)) {
				ret.add(i);
			}
		}
		
		return ret;
	}
	
	
	public int tamano() {
		return _adj.length;
	}
	
	

	public static void main(String[] args) {

	}

}
