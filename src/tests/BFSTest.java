package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import tests.Assert;
import logica.BFS;
import logica.GrafoDeProvincias;

public class BFSTest 
{
	@Test(expected=IllegalArgumentException.class)
	public void grafoNullTest() {
		BFS.esConexo(null);
	}

	@Test
	public void grafoDosVerticesAisladosTest() {
		assertFalse(BFS.esConexo(new GrafoDeProvincias()));
	}
	
	@Test
	public void grafoInconexoTest() {
		GrafoDeProvincias g = inicializarGrafoInconexo();
		
		assertFalse(BFS.esConexo(g));
	}
	
	@Test
	public void alcanzablesGrafoCompletoTest() {
		GrafoDeProvincias g = inicializarGrafoCompleto();
		
		int[] esperado = {0, 1, 2, 3};
		Assert.iguales(esperado, BFS.alcanzables(g, 0));
	}

	
	@Test
	public void alcanzablesInconexoTest() {
		GrafoDeProvincias g = inicializarGrafoInconexo();
		
		int[] esperado = {0, 1, 2, 3, 4};
		Assert.iguales(esperado, BFS.alcanzables(g, 0));
	}
	
	private GrafoDeProvincias inicializarGrafoInconexo() {
		GrafoDeProvincias g = new GrafoDeProvincias();
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(1, 2);
		g.agregarArista(1, 3);
		g.agregarArista(2, 4);
		g.agregarArista(3, 4);
		g.agregarArista(5, 6);
		
		return g;		
	}
	
	private GrafoDeProvincias inicializarGrafoCompleto() {
		GrafoDeProvincias g = new GrafoDeProvincias();
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);
		g.agregarArista(2, 3);
		g.agregarArista(0, 3);
		g.agregarArista(0, 2);
		g.agregarArista(1, 3);
		
		return g;		
	}
}
