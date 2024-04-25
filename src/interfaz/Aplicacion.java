package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import logica.AGM;
import logica.Grafo;
import logica.Provincia;
import utils.Config;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Aplicacion {

	private JFrame frame;
	private JMapViewer mapa;
	private Grafo grafo;
	private Grafo grafoBackUp;

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
		this.grafo = new Grafo();
		this.grafoBackUp = grafo;
		grafo.asignarAristasLimitrofesPorDefecto();
		grafo.prueba();
		// Asignacion de pesos por arista
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

		crearMapa();

		dibujarMapa();

		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.setPreferredSize(new Dimension(300, 600));

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				grafo = AGM.generarArbolMinimo(grafoBackUp);

				dibujarMapa();
			}
		});
		btnNewButton.setBounds(104, 86, 89, 23);
		panelIzquierdo.add(btnNewButton);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelMapa);
		panelIzquierdo.setLayout(null);

		JButton botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				AGM.generarRegionesConexas(grafo, 3);

				dibujarMapa();
			}
		});
		botonComponentesConexas.setBounds(23, 164, 256, 23);
		panelIzquierdo.add(botonComponentesConexas);

		splitPane.setResizeWeight(0);
		splitPane.setDividerSize(0);
		panelMapa.setLayout(null);

		panelMapa.add(mapa);

		frame.getContentPane().add(splitPane);
	}

	private void crearMapa() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 300, 600);
		mapa.setZoomControlsVisible(false);

		Coordinate posicion = new Coordinate(-42.3944, -64.425);
		mapa.setDisplayPosition(posicion, 4);

		fijarMapa(posicion);
	}

	private void dibujarMapa() {
		for (int i = 0; i < grafo.tamano(); i++) {
			Provincia provincia = grafo.obtenerProvincias()[i];
			Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());

			MapMarker vertice = new MapMarkerDot("", coordenada);
			vertice.getStyle().setBackColor(Config.COLOR_NODO);
			mapa.addMapMarker(vertice);

			Set<Coordinate> vecinos = grafo.obtenerCoordenadasLimitrofes(i);

			for (Coordinate coordenadaVecino : vecinos) {
				Coordinate cdVecino = new Coordinate(coordenadaVecino.getLat(), coordenadaVecino.getLon());
				List<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(cdVecino, coordenada, coordenada));
				MapPolygonImpl aristas = new MapPolygonImpl(route);
				aristas.getStyle().setColor(Config.COLOR_ARISTA);
				mapa.addMapPolygon(aristas);
			}
		}
	}

	private void fijarMapa(Coordinate posicion) {
		mapa.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				mapa.setDisplayPosition(posicion, 4);
			}
		});

		mapa.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mapa.setDisplayPosition(posicion, 4);
			}
		});

		mapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.setDisplayPosition(posicion, 4);
			}
		});
	}
}
