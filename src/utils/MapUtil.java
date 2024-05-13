package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import logica.Pais;
import logica.Provincia;

public class MapUtil {

	public static void dibujarMapa(Pais pais, JMapViewer mapa) {
		mapa.removeAllMapPolygons();

		Provincia[] arrProvincias = pais.obtenerProvincias();

		for (int i = 0; i < arrProvincias.length; i++) {
			Provincia provincia = arrProvincias[i];
			Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());

			MapMarker vertice = new MapMarkerDot("", coordenada);
			vertice.getStyle().setBackColor(Config.COLOR_NODO);
			mapa.addMapMarker(vertice);

			Set<Coordinate> vecinos = pais.obtenerCoordenadasLimitrofes(i);

			for (Coordinate coordenadaVecino : vecinos) {
				Coordinate cdVecino = new Coordinate(coordenadaVecino.getLat(), coordenadaVecino.getLon());
				List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(cdVecino, coordenada, coordenada));
				MapPolygonImpl aristas = new MapPolygonImpl(route);
				aristas.getStyle().setColor(Config.COLOR_ARISTA);
				mapa.addMapPolygon(aristas);
			}
		}
	}
	
	public static boolean esUnNumeroPositivo(String pesoProvincia) {
	      return pesoProvincia.matches("\\d+");
	}
}
