package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.GrafoDeProvincias;

public class EdicionDeAristasTest
{
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(23, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 23);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 2);
	}

	@Test
	public void aristaExistenteTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 3);
		assertTrue( grafo.existeArista(2, 3) );
	}

	@Test
	public void aristaOpuestaTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 3);
		assertTrue( grafo.existeArista(3, 2) );
	}

	@Test
	public void aristaInexistenteTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 3);
		assertFalse( grafo.existeArista(1, 20));
	}

	@Test
	public void agregarAristaDosVecesTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 3);
		grafo.agregarArista(2, 3);

		assertTrue( grafo.existeArista(2, 3) );
	}
	
	@Test
	public void eliminarAristaExistenteTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 4);
		
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}

	@Test
	public void eliminarAristaInexistenteTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}
	
	@Test
	public void eliminarAristaDosVecesTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(2, 4);
		
		grafo.eliminarArista(2, 4);
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}
}
