package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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

		// *Botones similaridad**//
		List<JButton> listaBotonesSimilaridad = new ArrayList<>();
		Provincia[] provincias = pais.obtenerProvincias();
		int cantProvincias = provincias.length;
		int y = 10;
		int x = 20;
		for (int i = 0; i < cantProvincias; i++) {
			JButton botonAbrirProvincia = new JButton(provincias[i].getNombre());
			botonAbrirProvincia.setCursor(new Cursor(Cursor.HAND_CURSOR));
			botonAbrirProvincia.setBounds(x, y, 155, 23);
			botonAbrirProvincia.setBackground(new Color(189, 242, 189));
			y += 30;
			if (i == 11) {
				y = 10;
				x += 160;
			}
			listaBotonesSimilaridad.add(botonAbrirProvincia);

			// Crear una clase interna para el ActionListener que tenga acceso al índice i
			final int indiceProvincia = i; // Declarar final para que pueda ser accedido dentro de la clase interna

			botonAbrirProvincia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String nombreProvincia = pais.obtenerProvincias()[indiceProvincia].getNombre();

					InputWindow inputWindow = new InputWindow(pais.obtenerLimitrofesDe(indiceProvincia),
							nombreProvincia, indiceProvincia);
					inputWindow.setVisible(true);
					botonAbrirProvincia.setEnabled(false);
					botonAbrirProvincia.setBackground(new Color(230, 230, 230));

				}
			});

			panelIzquierdo.add(botonAbrirProvincia);

		}

		JButton asignarSimilaridades = new JButton("Asignar aleatoriamente");
		asignarSimilaridades.setBackground(new Color(106, 226, 246));
		asignarSimilaridades.setFont(new Font("Arial", Font.BOLD, 14));
		asignarSimilaridades.setCursor(new Cursor(Cursor.HAND_CURSOR));
		asignarSimilaridades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JButton boton : listaBotonesSimilaridad) {
					boton.setEnabled(false);
					boton.setBackground(new Color(230, 230, 230));
				}
				pais.asignarPesosAleatoriamente();
				pais.actualizarSimililaridades();
			}
		});
		asignarSimilaridades.setBounds(60, 410, 230, 40);
		panelIzquierdo.add(asignarSimilaridades);

		JButton botonGenerarAGM = new JButton("Generar árbol mínimo");
		botonGenerarAGM.setFont(new Font("Arial", Font.BOLD, 14));
		botonGenerarAGM.setBackground(new Color(106, 226, 246));
		botonGenerarAGM.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonGenerarAGM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				pais.actualizarSimililaridades();

				dibujarMapa();
			}
		});
		botonGenerarAGM.setBounds(60, 458, 230, 40);
		panelIzquierdo.add(botonGenerarAGM);

		JButton botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.setFont(new Font("Arial", Font.BOLD, 14));
		botonComponentesConexas.setBackground(new Color(106, 226, 246));
		botonComponentesConexas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonComponentesConexas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mapa.removeAllMapPolygons();

				pais.dividirRegiones(3);

				dibujarMapa();
			}
		});
		botonComponentesConexas.setBounds(60, 504, 230, 40);
		panelIzquierdo.add(botonComponentesConexas);

		JButton botonReiniciarMapa = new JButton("Reiniciar mapa");
		botonReiniciarMapa.setFont(new Font("Arial", Font.BOLD, 14));
		botonReiniciarMapa.setBackground(new Color(106, 226, 246));
		botonReiniciarMapa.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonReiniciarMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pais.reestablecerConexionEntreLimitrofes();
				dibujarMapa();
				for (JButton boton : listaBotonesSimilaridad) {
					boton.setEnabled(true);
					boton.setBackground(new Color(189, 242, 189));
				}

			}
		});

		botonReiniciarMapa.setBounds(60, 551, 230, 40);
		panelIzquierdo.add(botonReiniciarMapa);
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
		frame.setBounds(350, 70, 700, 650);
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
