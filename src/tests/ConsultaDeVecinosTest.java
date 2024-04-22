package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.GrafoDeProvincias;

public class ConsultaDeVecinosTest
{
	@Test(expected = IllegalArgumentException.class)
	public void verticeNegativoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.vecinos(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeExcedidoTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.vecinos(23);
	}

	@Test
	
	public void unSoloVecino()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		assertEquals(1, grafo.vecinos(9).size());
	}
	
	@Test
	public void verticeNormalTest()
	{
		GrafoDeProvincias grafo = new GrafoDeProvincias();
		grafo.agregarArista(1, 3);
		grafo.agregarArista(2, 3);
		grafo.agregarArista(2, 4);
		
		int[] esperados = {1, 2};
		Assert.iguales(esperados, grafo.vecinos(3));
	}
}


