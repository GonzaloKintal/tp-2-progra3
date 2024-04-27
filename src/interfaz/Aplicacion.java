package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import logica.Provincia;
import logica.Pais;
import utils.Config;

import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;

public class Aplicacion {

	private JFrame frame;
	private JMapViewer mapa;
	private Pais pais;

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
		this.pais = new Pais(Config.PAIS);
		// Asignacion de pesos por arista
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		crearFrame();

		JPanel panelMapa = new JPanel();

		crearMapa();

		dibujarMapa();

		JPanel panelIzquierdo = crearPanelIzquierdo();

		agregarBotones(panelIzquierdo);

		JSplitPane splitPane = dividirPantalla(panelMapa, panelIzquierdo);

		splitPane.setResizeWeight(0);
		splitPane.setDividerSize(0);
		panelMapa.setLayout(null);

		panelMapa.add(mapa);

		frame.getContentPane().add(splitPane);
	}

	private void agregarBotones(JPanel panelIzquierdo) {
		JButton botonGenerarAGM = new JButton("Generar árbol mínimo");
		botonGenerarAGM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				pais.actualizarSimililaridades();

				dibujarMapa();
			}
		});
		botonGenerarAGM.setBounds(87, 505, 158, 23);
		panelIzquierdo.add(botonGenerarAGM);

		JButton botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				pais.dividirRegiones(3);

				dibujarMapa();
			}
		});
		botonComponentesConexas.setBounds(42, 539, 256, 23);
		panelIzquierdo.add(botonComponentesConexas);

		JButton botonReiniciarMapa = new JButton("Reiniciar mapa");
		botonReiniciarMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pais.reestablecerConexionEntreLimitrofes();
				dibujarMapa();
			}
		});
		botonReiniciarMapa.setBounds(100, 573, 135, 23);
		panelIzquierdo.add(botonReiniciarMapa);

		// Crear botón
		JButton btnAbrirVentana = new JButton("Abrir Ventana");
		btnAbrirVentana.setBounds(100, 250, 135, 23);
		btnAbrirVentana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crear e mostrar la nueva ventana cuando se hace clic en el botón
				InputWindow inputWindow = new InputWindow(pais.obtenerLimitrofesDe(5));
				inputWindow.setVisible(true);
			}
		});

		// Agregar botón a la ventana principal
		panelIzquierdo.add(btnAbrirVentana);
	}

	private JSplitPane dividirPantalla(JPanel panelMapa, JPanel panelIzquierdo) {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelMapa);
		panelIzquierdo.setLayout(null);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			int lastLocation = splitPane.getDividerLocation();

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				splitPane.setDividerLocation(lastLocation);
			}
		});
		return splitPane;
	}

	private JPanel crearPanelIzquierdo() {
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.setPreferredSize(new Dimension(350, 700));

		return panelIzquierdo;
	}

	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(300, 70, 700, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JMapViewer");
	}

	private void crearMapa() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 350, 700);
		mapa.setZoomControlsVisible(false);

		Coordinate posicion = new Coordinate(pais.latitud, pais.longitud);
		mapa.setDisplayPosition(posicion, pais.getZoom());

		fijarMapa(posicion);
	}

	private void dibujarMapa() {
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

	private void fijarMapa(Coordinate posicion) {
		mapa.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				mapa.setDisplayPosition(posicion, pais.getZoom());
			}
		});

		mapa.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mapa.setDisplayPosition(posicion, pais.getZoom());
			}
		});

		mapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.setDisplayPosition(posicion, pais.getZoom());
			}
		});
	}
}
