package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Pais;
import utils.Config;
import utils.Tupla;

public class PaísTest {

	
	@Test
	public void obtenerCoordenadasLimitrofesTrueTest() {
		Pais país = new Pais(Config.PAIS);
		
		Set<Coordinate> coordenadaSantaCruz = new HashSet<Coordinate>();
		coordenadaSantaCruz.add(new Coordinate(-48.784325,-70.058595));
		 
		assertEquals(coordenadaSantaCruz, país.obtenerCoordenadasLimitrofes(9));
	}
	
	
	@Test
	public void obtenerAristasLimitrofesTest() {
		Pais país = new Pais(Config.PAIS);
		país.asignarAristasLimitrofesPorDefecto();
		país.actualizarSimilaridad(país.indiceDe("Formosa"), país.indiceDe("Salta"), 10);
		país.actualizarSimilaridad(país.indiceDe("Formosa"), país.indiceDe("Chaco"), 20);
		
		ArrayList<Tupla<String, Integer>> esperado = new ArrayList<>();
		esperado.add(new Tupla("Salta", 10));
		esperado.add(new Tupla("Chaco", 20));
		
		assertEquals(esperado, país.obtenerAristasLimitrofes("Formosa"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void obtenerAristasLimitrofesVacioTest() {
		Pais país = new Pais(Config.PAIS);
		país.obtenerAristasLimitrofes("");
	}
	

	@Test
	public void indiceDeTest() {
		Pais país = new Pais(Config.PAIS);
		assertEquals(1, país.indiceDe("Córdoba"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void indiceDeVacioTest() {
		Pais país = new Pais(Config.PAIS);
		país.indiceDe("");
	}
	
	
	@Test
	public void dameLimitrofesDeTest() {
		Pais país = new Pais(Config.PAIS);
		ArrayList<String> esperado = new ArrayList<>();
		esperado.add("Córdoba");
		esperado.add("Santa Fé");
		esperado.add("Entre Ríos");
		esperado.add("La Pampa");
		esperado.add("Río Negro");
		
		assertEquals(esperado, país.dameLimitrofesDe("Buenos Aires"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void dameLimitrofesDeVacioTest() {
		Pais país = new Pais(Config.PAIS);
		país.dameLimitrofesDe("");
	}
	
}
