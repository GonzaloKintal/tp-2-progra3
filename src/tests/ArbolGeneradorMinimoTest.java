package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import logica.GrafoDeProvincias;
import utils.Tupla;

public class ArbolGeneradorMinimoTest {

	
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
	
}
