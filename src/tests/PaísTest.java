package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Pais;
import utils.Config;

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
  public void obtenerLimitrofesDeTest() {
	  ArrayList<String> limitrofesSantaCruz = new ArrayList<>();
	  limitrofesSantaCruz.add("Tierra del Fuego");
	  limitrofesSantaCruz.add("Chubut");
	  assertEquals(limitrofesSantaCruz, país.obtenerLimitrofesDe(18));
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void obtenerLimitrofesDeIndiceNegativoTest() {
	  país.obtenerLimitrofesDe(-5);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void obtenerLimitrofesDeIndiceExcedidoTest() {
	  país.obtenerLimitrofesDe(23);
  }

  @Test
  public void obtenerCoordenadasLimitrofesTrueTest() {
    Set<Coordinate> coordenadaSantaCruz = new HashSet<Coordinate>();
    coordenadaSantaCruz.add(new Coordinate(-48.784325, -70.058595));

    assertEquals(coordenadaSantaCruz, país.obtenerCoordenadasLimitrofes(21));	// La provincia 21 es Tierra del Fuego
  }

  @Test
  public void obtenerAristasLimitrofesTest() {
    país.asignarAristasLimitrofesPorDefecto();
    país.actualizarSimilaridad(país.indiceDe("Formosa"), país.indiceDe("Salta"), 10);
    país.actualizarSimilaridad(país.indiceDe("Formosa"), país.indiceDe("Chaco"), 20);

    HashMap<String, Integer> esperado = país.obtenerAristasLimitrofes("Formosa");

    assertEquals((Integer) 10, esperado.get("Salta"));
    assertEquals((Integer) 20, esperado.get("Chaco"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void obtenerAristasLimitrofesVacioTest() {
    país.obtenerAristasLimitrofes("");
  }

  @Test
  public void indiceDeTest() {
    assertEquals(5, país.indiceDe("Córdoba"));
  }

  @Test(expected = IllegalArgumentException.class)
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

  @Test(expected = IllegalArgumentException.class)
  public void dameLimitrofesDeVacioTest() {
    país.dameLimitrofesDe("");
  }

  @Test(expected = IllegalArgumentException.class)
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
    for (String limitrofe : país.dameLimitrofesDe("Buenos Aires")) {
      region.add(país.indiceDe(limitrofe));
    }
    region.add(país.indiceDe("Buenos Aires"));

    país.actualizarSimilaridad(0, 5, 0);

    assertEquals(0, país.obtenerMinimoIndiceDeSimilaridad(region));
  }

  @Test
  public void obtenerMaximoIndiceSimilaridadTest() {
    país.asignarPesosAleatoriamente();

    Set<Integer> region = new HashSet<>();
    for (String limitrofe : país.dameLimitrofesDe("Buenos Aires")) {
      region.add(país.indiceDe(limitrofe));
    }
    region.add(país.indiceDe("Buenos Aires"));

    país.actualizarSimilaridad(0, 5, 500);

    assertEquals(500, país.obtenerMaximoIndiceDeSimilaridad(region));
  }
  
  @Test
  public void obtenerIndicePromedioDeSimilaridadTest() {
	  país.actualizarSimilaridad(3, 18, 10);	// 3: Chubut		18: Santa Cruz
	  país.actualizarSimilaridad(18, 21, 20);	// 18: Santa Cruz	21: Tierra del Fuego
	  
	  Set<Integer> region = new HashSet<>();
	  region.add(3);
	  region.add(18);
	  region.add(21);
	  
	  double esperado = 15.0;
	  double actual = país.obtenerIndicePromedioDeSimilaridad(region);
	  
	  assertEquals(esperado, actual, 0.0001);	// El '0.0001' es el delta. Es el margen de error permitido en la comparación
  }
  
  @Test
  public void obtenerIndicePromedioDeSimilaridadRegionSinAristasTest() {
	  Set<Integer> region = new HashSet<>();
	  region.add(3);
	  
	  assertEquals(0, país.obtenerIndicePromedioDeSimilaridad(region), 0.0001);
  }
  
  @Test (expected = NullPointerException.class)
  public void obtenerIndicePromedioDeSimilaridadRegionNulaTest() {
	  país.obtenerIndicePromedioDeSimilaridad(null);
  }
 
 
  @Test
  public void obtenerRegionesTest() {
    país.asignarPesosAleatoriamente();
    país.generarCaminoÚnico();
    país.dividirRegiones(2);

		assertEquals(2, país.obtenerRegiones().size());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void obtenerRegionesFueraDeRangoPositivoTest() {
		país.asignarPesosAleatoriamente();
		país.generarCaminoÚnico();
		país.dividirRegiones(25);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void obtenerRegionesFueraDeRangoNegativoTest() {
		país.asignarPesosAleatoriamente();
		país.generarCaminoÚnico();
		país.dividirRegiones(-2);
	}
	
	
  }

