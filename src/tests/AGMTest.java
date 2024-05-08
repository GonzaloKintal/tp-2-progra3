package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.AGM;
import logica.BFS;
import logica.Grafo;

public class AGMTest {
	
	Grafo grafo;
	
	@Before
	public void init() {
		this.grafo = new Grafo(8);

		grafo.agregarArista(0, 1, 5);
		grafo.agregarArista(0, 4, 5);

		grafo.agregarArista(1, 2, 5);
		grafo.agregarArista(1, 4, 5);

		grafo.agregarArista(2, 3, 5);
		grafo.agregarArista(2, 4, 5);

		grafo.agregarArista(3, 4, 5);
		grafo.agregarArista(3, 1, 5);

		grafo.agregarArista(4, 1, 5);
		grafo.agregarArista(4, 5, 5);
		
		grafo.agregarArista(5, 6, 5);
		
		grafo.agregarArista(6, 7, 5);
	}

	@Test
	public void generarArbolMinimoCantidadAristasTest() {
		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);

		assertEquals(grafo.tamano() - 1, ArbolGeneradorMinimo.obtenerCantidadDeAristas());
	}

	@Test
	public void generarArbolMinimoSinAristaEspecíficaTest() {
		grafo.agregarPeso(0, 1, 10);

		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);

		assertFalse(ArbolGeneradorMinimo.existeArista(0, 1));
	}

	@Test
	public void generarArbolMinimoSigueSiendoConexoTest() {
		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);

		assertTrue(BFS.esConexo(ArbolGeneradorMinimo));
	}

	@Test
	public void grafoNoConexoDespuesDeEliminarAristasTest() {
		grafo = AGM.generarArbolMinimo(grafo);
		
		// Estas aristas sobreviven porque hay un unico camino
		grafo.agregarPeso(5, 6, 10);
		grafo.agregarPeso(6, 7, 10);
		
		// Borro 2 aristas
		AGM.generarRegionesConexas(grafo, 3);

		assertFalse(BFS.esConexo(grafo));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void generarArbolMinimoDeUnGrafoNoConexoTest() {
		Grafo g = new Grafo(4);
		AGM.generarArbolMinimo(g);
	}
	
	
	@Test
	public void generarRegionesConexasTest() {
		grafo = AGM.generarArbolMinimo(grafo);
		AGM.generarRegionesConexas(grafo, 3);
		// Tenía 8 vértices, 7 aristas. Generé 3 regiones, eliminé 2 aristas, quedan 5.
		assertEquals(5, grafo.obtenerCantidadDeAristas());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void generarRegionesConexasGrafoNullTest() {
		AGM.generarRegionesConexas(null, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void generarRegionesConexasCantidadExcedidoTest() {
		grafo = AGM.generarArbolMinimo(grafo);
		AGM.generarRegionesConexas(grafo, grafo.tamano()+1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void generarRegionesConexasCantidadCeroTest() {
		grafo = AGM.generarArbolMinimo(grafo);
		AGM.generarRegionesConexas(grafo, 0);
	}
	

}
