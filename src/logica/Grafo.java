package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import utils.Tupla;

public class Grafo {

	private Arista[][] matrizAdyacente;

	public Grafo(int cantVertices) {
		matrizAdyacente = new Arista[cantVertices][cantVertices];
		instanciarAristas();
	}

	public void prueba() {
		for (int i = 0; i < tamano(); i++) {
			for (int j = 0; j < tamano(); j++) {
				if (existeArista(i, j)) {
					Random rd = new Random();
					agregarPeso(i, j, rd.nextInt(200));
				}
			}
		}
	}


	private void instanciarAristas() {
		for (int i = 0; i < this.tamano(); i++) {
			for (int j = 0; j < this.tamano(); j++) {
				matrizAdyacente[i][j] = new Arista();
			}
		}
	}


	public void agregarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		matrizAdyacente[i][j].agregarArista();
		matrizAdyacente[j][i].agregarArista();
	}

	public void agregarPeso(int i, int j, int peso) {
		if (!existeArista(i, j)) {
			throw new IllegalArgumentException("No existe una arista entre esas provincias");
		}

		if (peso < 0) {
			throw new IllegalArgumentException("El peso no puede ser negativo");
		}

		verificarDistintos(i, j);

		matrizAdyacente[i][j].setPeso(peso);
		matrizAdyacente[j][i].setPeso(peso);
	}

	public int consultarPeso(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		existeArista(i, j);

		return matrizAdyacente[i][j].obtenerPeso();
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

		return matrizAdyacente[i][j].existeArista();
	}

	public Set<Integer> vecinos(int v) {
		verificarVertice(v);

		Set<Integer> vecinos = new HashSet<Integer>();

		for (int i = 0; i < this.tamano(); i++) {
			if (this.existeArista(v, i)) {
				vecinos.add(i);
			}
		}

		return vecinos;
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

	public HashMap<Tupla<Integer, Integer>, Integer> obtenerAristasHaciaVerticesNoMarcados(int vertice, ArrayList<Integer> verticesMarcados) {
		HashMap<Tupla<Integer, Integer>, Integer> aristasHaciaNoMarcados = new HashMap<>();
		for (int i = 0; i < tamano(); i++) {
			if (!verticesMarcados.contains(i) && existeArista(vertice, i)) {
				Tupla<Integer, Integer> indice = new Tupla<>(vertice, i);
				int peso = matrizAdyacente[vertice][i].obtenerPeso();
				aristasHaciaNoMarcados.put(indice, peso);
				
			}
		}
		return aristasHaciaNoMarcados;
	}


	public Tupla<Integer, Integer> elegirAristaConMenorPeso(HashMap<Tupla<Integer, Integer>, Integer> aristasTotales) {
		if (aristasTotales.isEmpty()) {
			throw new IllegalArgumentException("El conjunto de aristas no puede estar vacío.");
		}
		
		Tupla<Integer, Integer> verticesAristaMinima = null;
		int pesoMinimo = Integer.MAX_VALUE;
		    
		for (Entry<Tupla<Integer, Integer>, Integer> entry : aristasTotales.entrySet()) {
			Tupla<Integer, Integer> verticesArista = entry.getKey();
			int peso = entry.getValue();
			if (peso < pesoMinimo) {
		    	pesoMinimo = peso;
		    	verticesAristaMinima = verticesArista;
			}
		}
		return verticesAristaMinima;
	}
	
	
	public int obtenerCantidadDeAristas() {
		int contador = 0;
		
		for (int i=0; i<tamano(); i++) {
			for (int j=0; j<tamano(); j++) {
				if (existeArista(i,j)) {
					contador++;
				}
			}
		}
		return contador/2;
	}
	
	
	public void eliminarAristaDeMayorPeso() {
		Tupla<Integer, Integer> aristaDeMayorPeso = obtenerAristaDeMayorPeso();
		eliminarArista(aristaDeMayorPeso.getPrimero(), aristaDeMayorPeso.getSegundo());
	}

	public Tupla<Integer, Integer> obtenerAristaDeMayorPeso() {
		Tupla<Integer, Integer> aristaDeMayorPeso = null;
		int mayorPesoEncontrado = 0;
		
		for (int i=0; i<tamano(); i++) {
			for (int j=0; j<tamano(); j++) {
				int pesoActual = matrizAdyacente[i][j].obtenerPeso();
				if (pesoActual > mayorPesoEncontrado) {
					aristaDeMayorPeso = new Tupla<>(i,j);
					mayorPesoEncontrado = pesoActual;
				}
			}
		}
		
		if(aristaDeMayorPeso == null) {
			throw new RuntimeException("Las aristas deben tener peso");
		}
		
		return aristaDeMayorPeso;
	}
	
	public void generarAristasEntreVertices() {
		
	}
	
}
