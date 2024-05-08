package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Pais;
import utils.Config;
import utils.Tupla;

public class PaísTest {

	
	private Pais país;
	
	
	@Before
	public void init() {
		país = new Pais(Config.PAIS);
	}
	
	
	@After
	public void restart() {
		país.reestablecerConexionEntreLimitrofes();
	}
	
	
	@Test
	public void obtenerCoordenadasLimitrofesTrueTest() {
		Set<Coordinate> coordenadaSantaCruz = new HashSet<Coordinate>();
		coordenadaSantaCruz.add(new Coordinate(-48.784325,-70.058595));
		 
		assertEquals(coordenadaSantaCruz, país.obtenerCoordenadasLimitrofes(9));
	}
	
	
	@Test
	public void obtenerAristasLimitrofesTest() {
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
		país.obtenerAristasLimitrofes("");
	}
	

	@Test
	public void indiceDeTest() {
		assertEquals(1, país.indiceDe("Córdoba"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void indiceDeVacioTest() {
		país.indiceDe("");
	}
	
	
	@Test
	public void dameLimitrofesDeTest() {
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
		país.dameLimitrofesDe("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void dameLimitrofesDeProvinciaInexistenteTest() {
		país.dameLimitrofesDe("Río de Janeiro");
	}
	
	
	@Test
	public void esArbolTest() {
		país.generarCaminoÚnico();
		assertTrue(país.esArbol());
	}
	
	
	@Test
	public void esArbolFalseTest() {
		assertFalse(país.esArbol());
	}
	
	
	@Test
	public void obtenerMinimoIndiceSimilaridadTest() {
		país.asignarPesosAleatoriamente();
		
		Set<Integer> region = new HashSet<>();
		for (String limitrofe: país.dameLimitrofesDe("Buenos Aires")) {
			region.add(país.indiceDe(limitrofe));
		}
		region.add(país.indiceDe("Buenos Aires"));
		
		país.actualizarSimilaridad(0, 1, 0);
		
		assertEquals(0, país.obtenerMinimoIndiceDeSimilaridad(region));
	}
	
	@Test
	public void obtenerMaximoIndiceSimilaridadTest() {
		país.asignarPesosAleatoriamente();
		
		Set<Integer> region = new HashSet<>();
		for (String limitrofe: país.dameLimitrofesDe("Buenos Aires")) {
			region.add(país.indiceDe(limitrofe));
		}
		region.add(país.indiceDe("Buenos Aires"));
		
		país.actualizarSimilaridad(0, 1, 500);
		
		assertEquals(500, país.obtenerMaximoIndiceDeSimilaridad(region));
	}
	
	
	@Test
	public void obtenerRegionesTest() {
		país.asignarPesosAleatoriamente();
		país.generarCaminoÚnico();
		país.dividirRegiones(2);
		
		assertEquals(2, país.obtenerRegiones().size());
	}
	
	
}
