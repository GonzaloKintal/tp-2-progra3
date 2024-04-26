package logica;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Tupla;

public class AGM {


	public static Grafo generarArbolMinimo(Grafo grafo) {
		if(!BFS.esConexo(grafo)) {
			throw new IllegalArgumentException("No se puede generar un AGM de un grafo no conexo");
		}
		
		ArrayList<Integer> verticesMarcados = new ArrayList<>();
		Grafo arbolGeneradorMinimo = new Grafo(grafo.tamano());
		int contador = 0;
		
		// Empezamos desde un vertice elegido arbitrariamente
		verticesMarcados.add(0);

		while (contador < grafo.tamano() - 1) {
			HashMap<Tupla<Integer, Integer>, Integer> aristasTotales = new HashMap<>();

			for (Integer vertice : verticesMarcados) {
				HashMap<Tupla<Integer, Integer>, Integer> aristasDelVerticeActual = grafo.obtenerAristasHaciaVerticesNoMarcados(vertice, verticesMarcados);
				aristasTotales.putAll(aristasDelVerticeActual);
			}

			Tupla<Integer, Integer> aristaMinima = grafo.elegirAristaConMenorPeso(aristasTotales);
			int verticeMarcado = aristaMinima.getPrimero();
			int verticeNoMarcado = aristaMinima.getSegundo();
			int peso= grafo.consultarPeso(verticeMarcado, verticeNoMarcado);
			
			arbolGeneradorMinimo.agregarArista(verticeMarcado, verticeNoMarcado);
			arbolGeneradorMinimo.agregarPeso(verticeMarcado, verticeNoMarcado, peso);
			verticesMarcados.add(verticeNoMarcado);
			contador++;

		}
		return arbolGeneradorMinimo;
	}
	
	
	public static void generarRegionesConexas(Grafo grafo, int k) {
		int aristasAEliminar = k - 1;
		
		while (aristasAEliminar > 0) {
			grafo.eliminarAristaDeMayorPeso();
			aristasAEliminar--;
		}
	}
	
	
}