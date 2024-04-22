package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import logica.GrafoDeProvincias;
import logica.Provincia;

public class Aplicacion {

	private JFrame frame;
	private JMapViewer mapa;
	private GrafoDeProvincias grafo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplicacion window = new Aplicacion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Aplicacion() {
		this.grafo = new GrafoDeProvincias();
		grafo.asignarAristasLimitrofesPorDefecto();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JMapViewer");

		JPanel panelMapa = new JPanel();
		panelMapa.setLayout(null);

		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 300, 600);
		mapa.setZoomControlsVisible(false);

		Coordinate posicion = new Coordinate(-42.3944, -64.425);
		mapa.setDisplayPosition(posicion, 4);

		for (int i = 0; i < grafo.tamano(); i++) {
			Provincia provincia = grafo.obtenerProvincias()[i];
			Coordinate coordenada = provincia.coordenada;
			
			MapMarker vertice = new MapMarkerDot("", coordenada);
			vertice.getStyle().setBackColor(Color.BLUE);
			mapa.addMapMarker(vertice);
			
			Set<Coordinate> vecinos = grafo.obtenerCoordenadasLimitrofes(i);
			
			for(Coordinate coordenadaVecino: vecinos) {
				List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(coordenadaVecino, coordenada, coordenada));
				mapa.addMapPolygon(new MapPolygonImpl(route));
			}
		}

		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.setPreferredSize(new Dimension(300, 600));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelMapa);
		splitPane.setResizeWeight(0);
		splitPane.setDividerSize(0);

		panelMapa.add(mapa);
		frame.getContentPane().add(splitPane);
	}

}
