package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.Grafo;

public class EdicionDeAristasTest
{
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(23, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 23);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarPesoNegativoTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(1, 3);
		grafo.agregarPeso(1, 3, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarPesoEntreDosNoLimitrofesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarPeso(2, 5, 10);
	}

	@Test
	public void aristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 3);
		assertTrue( grafo.existeArista(2, 3) );
	}

	@Test
	public void aristaOpuestaTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 3);
		assertTrue( grafo.existeArista(3, 2) );
	}

	@Test
	public void aristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 3);
		assertFalse( grafo.existeArista(1, 20));
	}

	@Test
	public void agregarAristaDosVecesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 3);
		grafo.agregarArista(2, 3);

		assertTrue( grafo.existeArista(2, 3) );
	}
	
	@Test
	public void eliminarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 4);
		
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}

	@Test
	public void eliminarAristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}
	
	@Test
	public void eliminarAristaDosVecesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 4);
		
		grafo.eliminarArista(2, 4);
		grafo.eliminarArista(2, 4);
		assertFalse( grafo.existeArista(2, 4) );
	}
	
	@Test
	public void agregarPesoEntreDosLimitrofesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 4);
		grafo.agregarPeso(2, 4, 10);
	
		assertEquals(10, grafo.consultarPeso(2, 4));
		assertEquals(10, grafo.consultarPeso(4, 2));
	}
	
	@Test
	public void actualizarPesoEntreLimitrofes()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista(2, 4);
		grafo.agregarPeso(2, 4, 10);
		grafo.agregarPeso(2, 4, 5);
		
		assertEquals(5, grafo.consultarPeso(2, 4));
		assertEquals(5, grafo.consultarPeso(4, 2));
	}
}
