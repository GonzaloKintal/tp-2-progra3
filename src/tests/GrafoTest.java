package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import logica.Grafo;
import utils.Tupla;

public class GrafoTest {

	
	@Test
	public void instanciarAristasTest() {
		Grafo grafo = new Grafo(2);
		grafo.instanciarAristas();
		assertNotNull(grafo.obtenerArista(0, 1));
	}
	
	
	@Test
	public void obtenerPosiblesAristasTrueTest() {
		Grafo grafo = new Grafo(23);
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
		Grafo grafo = new Grafo(23);
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
		Grafo grafo = new Grafo(23);
		
		assertEquals(0, grafo.obtenerAristasHaciaVerticesNoMarcados(0, new ArrayList<Integer>()).size());
	}
	
	
	@Test
	public void elegirAristaConMenorPesoTest() {
		Grafo grafo = new Grafo(23);
		HashMap<Tupla<Integer, Integer>, Integer> aristas = new HashMap<>();
		
		aristas.put(new Tupla<>(0, 1), 20);
		aristas.put(new Tupla<>(0, 2), 15);
		aristas.put(new Tupla<>(0, 3), 25);
		
		Tupla<Integer, Integer> aristaEsperada = new Tupla<>(0, 2);
		
		assertEquals(aristaEsperada, grafo.elegirAristaConMenorPeso(aristas));
	}

	@Test(expected = IllegalArgumentException.class)
	public void elegirAristaConMenorPesoAristasVacioTest() {
		Grafo grafo = new Grafo(23);
		HashMap<Tupla<Integer, Integer>, Integer> aristas = new HashMap<>();
		
		grafo.elegirAristaConMenorPeso(aristas);
	}
	
	@Test(expected = RuntimeException.class)
	public void obtenerAristaDeMayorPesoAristasTienenPesoCeroTest() {
		Grafo grafo = new Grafo(23);
		grafo.obtenerAristaDeMayorPeso();
	}
	
	@Test
	public void cantidadDeAristasIgualTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		
		assertEquals(3, grafo.obtenerCantidadDeAristas());
	}
	
	
	@Test
	public void esPosibleDesconexarTest() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		
		assertTrue(grafo.esPosibleDesconexarEnRegiones(2));
	}
	
	@Test
	public void esPosibleDesconexarFalseTest() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		
		assertFalse(grafo.esPosibleDesconexarEnRegiones(4));
	}
	
	
	@Test
	public void todasLasAristasTienenPesoTest() {
		Grafo grafo = new Grafo(5);
		grafo.asignarPesosRandom();
		
		assertTrue(grafo.todasLasAristasTienenPeso());
	}
	
	@Test
	public void todasLasAristasTienenPesoFalseTest() {
		Grafo grafo = new Grafo(5);
		grafo.instanciarAristas();
		assertTrue(grafo.todasLasAristasTienenPeso());
	}
	
	
}
