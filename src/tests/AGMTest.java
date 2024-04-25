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
		this.grafo = new Grafo(23);
	}
	

	@Test
	public void generarArbolMinimoCantidadAristasTest() {
		grafo.asignarAristasLimitrofesPorDefecto();
		
		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);
		
		assertEquals(22, ArbolGeneradorMinimo.obtenerCantidadDeAristas());
	}
	
	@Test
	public void generarArbolMinimoSinAristaEspecíficaTest() {
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo.agregarPeso(0, 1, 10);
		
		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);
		
		assertFalse(ArbolGeneradorMinimo.existeArista(0, 1));
	}
	
	@Test
	public void generarArbolMinimoSigueSiendoConexoTest() {
		grafo.asignarAristasLimitrofesPorDefecto();
		
		Grafo ArbolGeneradorMinimo = AGM.generarArbolMinimo(grafo);
		
		assertTrue(BFS.esConexo(ArbolGeneradorMinimo));
	}
	
	@Test
	public void grafoNoConexoDespuesDeEliminarAristasTest() {
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo = AGM.generarArbolMinimo(grafo);
		
		// Agrego peso desde Tierra del fuego - Santa Cruz
		grafo.agregarPeso(9, 8, 10);
		
		// Agrego peso desde Santa Cruz - Chubut
		grafo.agregarPeso(8, 7, 10);
		
		// Borro 2 aristas
		AGM.generarRegionesConexas(grafo, 3);
		
		assertFalse(BFS.esConexo(grafo));
	}
	

}
