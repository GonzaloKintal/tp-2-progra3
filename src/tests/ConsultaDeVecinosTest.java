package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.Grafo;

public class ConsultaDeVecinosTest
{
	@Test(expected = IllegalArgumentException.class)
	public void verticeNegativoTest()
	{
		Grafo grafo = new Grafo(1);
		grafo.vecinos(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeExcedidoTest()
	{
		Grafo grafo = new Grafo(3);
		grafo.vecinos(4);
	}

	@Test
	
	public void unSoloVecino()
	{
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(0, 1);
		
		assertEquals(1, grafo.vecinos(0).size());
	}
	
	@Test
	public void verticeNormalTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(1, 3);
		grafo.agregarArista(2, 3);
		grafo.agregarArista(2, 4);
		
		int[] esperados = {1, 2};
		Assert.iguales(esperados, grafo.vecinos(3));
	}
}


