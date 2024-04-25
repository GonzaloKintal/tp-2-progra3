package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.BFS;
import logica.GrafoDeProvincias;
import utils.Tupla;

public class GrafoDeProvinciasTest {

	
	@Test
	public void obtenerPosiblesAristasTrueTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		ArrayList<Integer> verticesMarcados = new ArrayList<>();
		verticesMarcados.add(1);
		verticesMarcados.add(2);
		
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		
		HashMap<Tupla<Integer, Integer>, Integer> posiblesAristas = grafo.obtenerAristasHaciaVerticesNoMarcados(0, verticesMarcados);
		Tupla<Integer, Integer> aristaEsperada = new Tupla<>(0, 3);
		
		assertTrue(posiblesAristas.containsKey(aristaEsperada));
	}
	
	@Test
	public void obtenerPosiblesAristasFalseTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		ArrayList<Integer> verticesMarcados = new ArrayList<>();
		verticesMarcados.add(1);
		verticesMarcados.add(2);
		
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		
		HashMap<Tupla<Integer, Integer>, Integer> posiblesAristas = grafo.obtenerAristasHaciaVerticesNoMarcados(0, verticesMarcados);
		Tupla<Integer, Integer> aristaEsperada = new Tupla<>(0, 2);
		
		assertFalse(posiblesAristas.containsKey(aristaEsperada));
	}
	
	@Test
	public void obtenerPosiblesAristasVacioTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		
		assertEquals(0, grafo.obtenerAristasHaciaVerticesNoMarcados(0, new ArrayList<Integer>()).size());
	}
	
	
	@Test
	public void elegirAristaConMenorPesoTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		HashMap<Tupla<Integer, Integer>, Integer> aristas = new HashMap<>();
		
		aristas.put(new Tupla<>(0, 1), 20);
		aristas.put(new Tupla<>(0, 2), 15);
		aristas.put(new Tupla<>(0, 3), 25);
		
		Tupla<Integer, Integer> aristaEsperada = new Tupla<>(0, 2);
		
		assertEquals(aristaEsperada, grafo.elegirAristaConMenorPeso(aristas));
	}

	@Test(expected = IllegalArgumentException.class)
	public void elegirAristaConMenorPesoAristasVacioTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		HashMap<Tupla<Integer, Integer>, Integer> aristas = new HashMap<>();
		
		grafo.elegirAristaConMenorPeso(aristas);
	}
	
	
	@Test
	public void generarArbolMinimoCantidadAristasTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		
		GrafoDeProvincias ArbolGeneradorMinimo = grafo.generarArbolMinimo();
		
		assertEquals(22, ArbolGeneradorMinimo.obtenerCantidadDeAristas());
	}
	
	@Test
	public void generarArbolMinimoSinAristaEspecíficaTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo.agregarPeso(0, 1, 10);
		
		GrafoDeProvincias ArbolGeneradorMinimo = grafo.generarArbolMinimo();
		
		assertFalse(ArbolGeneradorMinimo.existeArista(0, 1));
	}
	
	@Test
	public void generarArbolMinimoSigueSiendoConexoTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		
		GrafoDeProvincias ArbolGeneradorMinimo = grafo.generarArbolMinimo();
		
		assertTrue(BFS.esConexo(ArbolGeneradorMinimo));
	}
	
	
	@Test
	public void obtenerCoordenadasLimitrofesTrueTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		
		grafo.agregarArista(9, 8);	// Agrego arista entre Tierra del Fuego y Santa Cruz
		
		Set<Coordinate> coordenadaSantaCruz = new HashSet<Coordinate>();
		coordenadaSantaCruz.add(new Coordinate(-48.784325,-70.058595));
		 
		assertEquals(coordenadaSantaCruz, grafo.obtenerCoordenadasLimitrofes(9));
	}
	
	@Test
	public void grafoNoConexoDespuesDeEliminarAristasTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo = grafo.generarArbolMinimo();
		
		// Agrego peso desde Tierra del fuego - Santa Cruz
		grafo.agregarPeso(9, 8, 10);
		
		// Agrego peso desde Santa Cruz - Chubut
		grafo.agregarPeso(8, 7, 10);
		
		// Borro 2 aristas
		grafo.generarRegionesConexas(3);
		
		assertFalse(BFS.esConexo(grafo));
	}
	
	@Test(expected = RuntimeException.class)
	public void obtenerAristaDeMayorPesoAristasTienenPesoCeroTest() {
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo.obtenerAristaDeMayorPeso();
	}
	
}
