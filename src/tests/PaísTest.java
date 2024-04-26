package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Grafo;
import logica.Pais;

public class PaísTest {

	
	@Test
	public void obtenerCoordenadasLimitrofesTrueTest() {
		Pais país = new Pais();
		
		Set<Coordinate> coordenadaSantaCruz = new HashSet<Coordinate>();
		coordenadaSantaCruz.add(new Coordinate(-48.784325,-70.058595));
		 
		assertEquals(coordenadaSantaCruz, país.obtenerCoordenadasLimitrofes(9));
	}
	

}
