package logica;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import utils.PaisType;

public class Pais {

  private final Provincia[] provincias;
  private String nombre;
  public double latitud;
  public double longitud;
  private Grafo grafo;

  public Pais(PaisType info) {
    this.provincias = info.getProvincias();
    this.grafo = new Grafo(provincias.length);
    this.latitud = info.getLatitud();
    this.longitud = info.getLongitud();
    this.nombre = info.getNombre();

    asignarAristasLimitrofesPorDefecto();
  }

  public Provincia[] obtenerProvincias() {
    return this.provincias.clone();
  }

  public void asignarAristasLimitrofesPorDefecto() {
    for (int i = 0; i < grafo.tamano(); i++) {
      for (int j = 0; j < grafo.tamano(); j++) {
        if (provincias[i].limitrofes.contains(provincias[j].nombre)) {
          grafo.agregarArista(i, j);
          grafo.agregarPeso(i, j, 0);
        }
      }
    }
  }

  public Set<Coordinate> obtenerCoordenadasLimitrofes(int v) {
    Set<Coordinate> vecinos = new HashSet<Coordinate>();

    for (int i = 0; i < grafo.tamano(); i++) {
      if (grafo.existeArista(v, i)) {
        Provincia provincia = this.provincias[i];
        vecinos.add(new Coordinate(provincia.getLatitud(), provincia.getLongitud()));
      }
    }
    return vecinos;
  }

  public HashMap<String, Integer> obtenerAristasLimitrofes(String nombreProvincia) {
    if (nombreProvincia.isEmpty()) {
      throw new IllegalArgumentException("El nombre de la provincia no puede ser vacío.");
    }

    HashMap<String, Integer> ret = new HashMap<>();
    ArrayList<String> limitrofes = dameLimitrofesDe(nombreProvincia);
    int indiceProvincia = indiceDe(nombreProvincia);

    for (String limitrofe : limitrofes) {
      int peso = this.grafo.consultarPeso(indiceProvincia, indiceDe(limitrofe));
      ret.put(limitrofe, peso);
    }

    return ret;
  }

  public int indiceDe(String nombre) {
    if (nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre de la provincia no puede ser vacío.");
    }

    int idx = 0;

    for (int i = 0; i < this.provincias.length; i++) {
      if (provincias[i].nombre.equals(nombre)) {
        idx = i;
      }
    }

    return idx;
  }

  public String nombreDe(int indice) {
    return this.provincias[indice].nombre;
  }

  public ArrayList<String> dameLimitrofesDe(String nombre) {
    verificarNombreDeProvinciaValido(nombre);

    return this.provincias[indiceDe(nombre)].limitrofes;
  }

  private void verificarNombreDeProvinciaValido(String nombre) {
    if (nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre de la provincia no puede ser vacío.");
    }

    if (!existeProvincia(nombre)) {
      throw new IllegalArgumentException("La provincia es inexistente.");
    }
  }

  public boolean existeProvincia(String nombre) {
    for (int i = 0; i < provincias.length; i++) {
      if (nombreDe(i).equals(nombre)) {
        return true;
      }
    }
    return false;
  }

  public void generarCaminoÚnico() {
    this.grafo = AGM.generarArbolMinimo(grafo);
  }

  public void dividirRegiones(int i) {
    AGM.generarRegionesConexas(this.grafo, i);
  }

  public void reestablecerConexionEntreLimitrofes() {
    this.asignarAristasLimitrofesPorDefecto();
  }

  public String getNombre() {
    return nombre;
  }

  public ArrayList<String> obtenerLimitrofesDe(int indiceProvincia) {
    verificarIndiceProvinciaValido(indiceProvincia);
    return this.provincias[indiceProvincia].limitrofes;
  }

  private void verificarIndiceProvinciaValido(int indiceProvincia) {
    if (indiceProvincia < 0 || indiceProvincia >= grafo.tamano()) {
      throw new IllegalArgumentException("El índice debe ser entre 0 y " + grafo.tamano());
    }
  }

  public void asignarSimilaridadesAleatoriamente() {
    grafo.asignarPesosRandom();

  }

  public void actualizarSimilaridad(int indiceProvincia1, int indiceProvincia2, int similaridad) {
    this.grafo.agregarPeso(indiceProvincia1, indiceProvincia2, similaridad);
  }

  public boolean todasLasProvinciasTienenSimilaridad() {
    return this.grafo.todasLasAristasTienenPeso();
  }

  public boolean estaTodoConectado() {
    return BFS.esConexo(grafo);
  }

  public boolean esArbol() {
    return estaTodoConectado() && this.provincias.length == this.grafo.obtenerCantidadDeAristas() + 1;
  }

  public int obtenerCantProvincias() {
    return this.provincias.length;
  }

  public String obtenerInformacionRegiones() {
    ArrayList<Set<Integer>> regiones = obtenerRegiones();
    StringBuilder informacion = new StringBuilder();
    int regionActual = 1;

    for (Set<Integer> region : regiones) {
      informacion.append("La región " + regionActual + " está compuesta por: \n");
      for (Integer indice : region) {
        informacion.append(nombreDe(indice));
        informacion.append("   |   ");
      }

      if (region.size() > 1) {
        agregarInfoDeRegion(informacion, region);
      }

      regionActual++;
      agregarEspacio(informacion);
    }
    return informacion.toString();
  }

  private void agregarInfoDeRegion(StringBuilder informacion, Set<Integer> region) {
    informacion.append("\n");
    informacion.append("El mínimo indice de similaridad es: " + obtenerMinimoIndiceDeSimilaridad(region));
    informacion.append("\n");
    informacion.append("El índice promedio de similaridad es: " + obtenerIndicePromedioDeSimilaridad(region));
    informacion.append("\n");
    informacion.append("El máximo indice de similaridad es: " + obtenerMaximoIndiceDeSimilaridad(region));
  }

  public int obtenerMinimoIndiceDeSimilaridad(Set<Integer> region) {
    int minimoActual = Integer.MAX_VALUE;

    if (region.size() == 1) {
      return 0;
    }

    for (Integer i : region)
      for (Integer j : region)
        if (existeConexión(i, j) && obtenerSimilaridad(i, j) < minimoActual) {
          minimoActual = obtenerSimilaridad(i, j);
        }

    return minimoActual;
  }

  public int obtenerMaximoIndiceDeSimilaridad(Set<Integer> region) {
    int maximoActual = 0;

    for (Integer i : region)
      for (Integer j : region)
        if (existeConexión(i, j) && obtenerSimilaridad(i, j) > maximoActual) {
          maximoActual = obtenerSimilaridad(i, j);
        }

    return maximoActual;
  }

  public double obtenerIndicePromedioDeSimilaridad(Set<Integer> region) {

    if (region.equals(null))
      throw new NullPointerException("La región es nula.");

    double totalSimilaridad = 0;
    int totalAristas = 0;

    for (Integer i : region) {
      for (Integer j : region) {
        if (i < j && existeConexión(i, j)) {
          totalSimilaridad += obtenerSimilaridad(i, j);
          totalAristas++;
        }
      }
    }

    if (totalAristas == 0) {
      return 0;
    }

    return retornarDecimalAcotado(totalSimilaridad, totalAristas);
  }

  private double retornarDecimalAcotado(double totalSimilaridad, int totalPares) {
    double promedio = totalSimilaridad / totalPares;

    DecimalFormat df = new DecimalFormat("#.####");
    String promedioFormateado = df.format(promedio);

    try {
      return df.parse(promedioFormateado).doubleValue();
    } catch (ParseException e) {
      e.printStackTrace();
      return 0.0;
    }
  }

  private int obtenerSimilaridad(int idxProvincia1, int idxProvincia2) {
    return grafo.consultarPeso(idxProvincia1, idxProvincia2);
  }

  private boolean existeConexión(int idxProvincia1, int idxProvincia2) {
    return grafo.existeArista(idxProvincia1, idxProvincia2);
  }

  public ArrayList<Set<Integer>> obtenerRegiones() {
    ArrayList<Set<Integer>> regiones = new ArrayList<>();
    Set<Integer> provinciasYaVisitadas = new HashSet<>();

    for (int i = 0; i < obtenerCantProvincias(); i++) {
      if (!provinciasYaVisitadas.contains(i)) {
        Set<Integer> region = BFS.alcanzables(grafo, i);
        regiones.add(region);
        agregarProvinciasDeLaMismaRegion(region, provinciasYaVisitadas);
      }
    }
    return regiones;
  }

  private void agregarProvinciasDeLaMismaRegion(Set<Integer> region, Set<Integer> provinciasYaVisitadas) {
    for (Integer provincia : region) {
      provinciasYaVisitadas.add(provincia);
    }
  }

  public boolean todasSusLimitrofesTienenSimilaridad(String nombreProvincia) {
    int indiceProvincia = indiceDe(nombreProvincia);
    return grafo.todasSusAristasTienenPeso(indiceProvincia);
  }

  private void agregarEspacio(StringBuilder informacion) {
	  for (int i=0; i<11; i++) {
	    	informacion.append("\n");
	  }
  }

  public String obtenerInformacionSimilaridad() {
    StringBuilder informacion = new StringBuilder();
    for (int i = 0; i < grafo.tamano(); i++) {
      for (int j = i; j < grafo.tamano(); j++) {
        if (grafo.existeArista(i, j)) {
          informacion.append(" ");
          informacion.append(nombreDe(i));
          informacion.append(" - ");
          informacion.append(nombreDe(j));
          informacion.append(" tienen indice: ");
          informacion.append(grafo.consultarPeso(i, j));
          informacion.append("\n");
          informacion.append("\n");
          informacion.append("\n");
          informacion.append("\n");
        }

      }
    }
    return informacion.toString();
  }

}
