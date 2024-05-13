package logica;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Tupla;

public class AGM {

  public static Grafo generarArbolMinimo(Grafo grafo) {
    verificarConexo(grafo);

    ArrayList<Integer> verticesMarcados = new ArrayList<>();
    Grafo arbolGeneradorMinimo = new Grafo(grafo.tamano());
    int contador = 0;

    verticesMarcados.add(0);

    while (hayVertices(grafo, contador)) {
      HashMap<Tupla<Integer, Integer>, Integer> aristasTotales = filtrarAristasDeNoMarcados(grafo, verticesMarcados);

      Tupla<Integer, Integer> aristaMinima = grafo.elegirAristaConMenorPeso(aristasTotales);
      agregarAristaMinimaAlAGM(grafo, arbolGeneradorMinimo, aristaMinima);
      int verticeNoMarcado = aristaMinima.getSegundo();
      verticesMarcados.add(verticeNoMarcado);
      contador++;

    }
    return arbolGeneradorMinimo;
  }

  private static boolean hayVertices(Grafo grafo, int contador) {
	return contador < grafo.tamano() - 1;
  }

  private static void agregarAristaMinimaAlAGM(Grafo grafo, Grafo arbolGeneradorMinimo,
    Tupla<Integer, Integer> aristaMinima) {
    int indiceVerticeMarcado = aristaMinima.getPrimero();
    int indiceVerticeNoMarcado = aristaMinima.getSegundo();
    int peso = grafo.consultarPeso(indiceVerticeMarcado, indiceVerticeNoMarcado);

    arbolGeneradorMinimo.agregarArista(indiceVerticeMarcado, indiceVerticeNoMarcado, peso);
  }

  private static HashMap<Tupla<Integer, Integer>, Integer> filtrarAristasDeNoMarcados(Grafo grafo,
      ArrayList<Integer> verticesMarcados) {
    HashMap<Tupla<Integer, Integer>, Integer> aristasTotales = new HashMap<>();

    for (Integer vertice : verticesMarcados) {
      HashMap<Tupla<Integer, Integer>, Integer> aristasDelVerticeActual = grafo
          .obtenerAristasHaciaVerticesNoMarcados(vertice, verticesMarcados);
      aristasTotales.putAll(aristasDelVerticeActual);
    }
    return aristasTotales;
  }

  private static void verificarConexo(Grafo grafo) {
    if (!BFS.esConexo(grafo)) {
      throw new IllegalArgumentException("No se puede generar un AGM de un grafo no conexo.");
    }
  }

  public static void generarRegionesConexas(Grafo grafo, int k) {
    verificarGrafoNulo(grafo);

    verificarRangoCantidadRegiones(grafo, k);

    int aristasAEliminar = k - 1;

    while (aristasAEliminar > 0) {
      grafo.eliminarAristaDeMayorPeso();
      aristasAEliminar--;
    }
  }

  private static void verificarRangoCantidadRegiones(Grafo grafo, int k) {
	  if (k <= 0 || k > grafo.tamano()) {
        throw new IllegalArgumentException("La cantidad de regiones conexas debe estar entre 1 y 23.");
      }
  }

  private static void verificarGrafoNulo(Grafo grafo) {
	  if (grafo == null) {
        throw new IllegalArgumentException("El grafo es nulo.");
      }
  }

}