package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

	private ArrayList<JButton> listaBotonesSimilaridad;
	private JButton asignarSimilaridades;
	private JButton botonGenerarAGM;
	private JButton botonComponentesConexas;
	private JButton botonVerInfoRegiones;
	private JButton botonReiniciarMapa;

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

		agregarLogoGithub();

		agregarImagenMalvinas();

	}

	private void agregarBotones(JPanel panelIzquierdo) {

		// *Botones similaridad**//
		crearBotonesProvincias(panelIzquierdo);

		crearBotonAsignarSimilaridades(panelIzquierdo);

		crearBotonGenerarAGM(panelIzquierdo);

		JTextField inputCantRegiones = crearInputRegiones(panelIzquierdo);

		crearBotonComponentesConexas(panelIzquierdo);

		crearBotonVerInfoRegiones(panelIzquierdo);

		crearBotonReiniciarMapa(panelIzquierdo);

		escucharBotones(inputCantRegiones);

	}

	private void agregarLogoGithub() {
		Image githubImage = new ImageIcon(this.getClass().getResource("/github-logo.png")).getImage();
		JLabel githubLabel = new JLabel();
		githubLabel.setIcon(new ImageIcon(githubImage));
		githubLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		githubLabel.setBounds(285, 610, 45, 45);
		githubLabel.setToolTipText("Ir al repositorio de GitHub");
		mapa.add(githubLabel);

		githubLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirEnlaceGitHub();
			}
		});
	}

	private void agregarImagenMalvinas() {
		Image islasImage = new ImageIcon(this.getClass().getResource("/malvinas_argentinas.png")).getImage();
		JLabel islasLabel = new JLabel();
		islasLabel.setIcon(new ImageIcon(islasImage));
		islasLabel.setBounds(-350, 5, 700, 650);
		mapa.add(islasLabel);
	}

	private void crearBotonesProvincias(JPanel panelIzquierdo) {
		listaBotonesSimilaridad = new ArrayList<>();
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

			escucharBotonesProvincia(botonAbrirProvincia, indiceProvincia);
			panelIzquierdo.add(botonAbrirProvincia);

		}
	}

	private void escucharBotonesProvincia(JButton botonAbrirProvincia, final int indiceProvincia) {
		botonAbrirProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputWindow inputWindow =new InputWindow(botonAbrirProvincia, pais);
				inputWindow.setVisible(true);
			}
		});
	}

	private void escucharBotones(JTextField inputCantRegiones) {
		escucharBotonAsignarSimilaridades();

		escucharBotonGenerarAGM();

		escucharBotonComponentesConexas(inputCantRegiones);

		escucharBotonVerInfoRegiones();

		escucharBotonReiniciarMapa(inputCantRegiones);
	}

	private void escucharBotonReiniciarMapa(JTextField inputCantRegiones) {
		botonReiniciarMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pais.reestablecerConexionEntreLimitrofes();
				dibujarMapa();
				for (JButton boton : listaBotonesSimilaridad) {
					boton.setEnabled(true);
					boton.setBackground(new Color(189, 242, 189));
					inputCantRegiones.setText("");
				}
				botonGenerarAGM.setEnabled(true);
				botonGenerarAGM.setBackground(new Color(106, 226, 246));

			}
		});
	}

	private void escucharBotonVerInfoRegiones() {
		botonVerInfoRegiones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Hay que mostrar la info");
			}
		});
	}

	private void escucharBotonComponentesConexas(JTextField inputCantRegiones) {
		botonComponentesConexas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String valorIngresado = inputCantRegiones.getText();

				if (valorIngresado.isEmpty()) {
					JOptionPane.showMessageDialog(null, Config.MSJ_ERROR_REGIONES_VACIO, "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
								
				if (!valorIngresado.matches("\\d+")) {
					JOptionPane.showMessageDialog(null, Config.MSJ_ERROR_SOLO_NUMERO, "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int cantidadRegiones = Integer.parseInt(valorIngresado);

				if (cantidadRegiones <= 0 || cantidadRegiones > 23) {
					JOptionPane.showMessageDialog(null, Config.MSJ_ERROR_CANT_REGIONES_INVALIDO, "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;

				}

				if (!pais.estaTodoConectado()) {
					return;
				}

				if (!pais.esArbol()) {
					JOptionPane.showMessageDialog(null, "Debe generar el árbol mínimo primero.", "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				pais.dividirRegiones(cantidadRegiones);
				dibujarMapa();
			}
		});
	}

	private void escucharBotonGenerarAGM() {
		botonGenerarAGM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!pais.todasLasProvinciasTienenSimilaridad()) {
					JOptionPane.showMessageDialog(null, "Por favor, asigne similaridad entre provincias", "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (!pais.estaTodoConectado()) {
					return;
				}
				pais.actualizarSimililaridades();
				dibujarMapa();
				botonGenerarAGM.setEnabled(false);
				botonGenerarAGM.setBackground(new Color(230, 230, 230));
			}
		});
	}

	private void escucharBotonAsignarSimilaridades() {
		asignarSimilaridades.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            pais = new Pais(Config.PAIS);
	            pais.asignarPesosAleatoriamente();
	            for (JButton boton : listaBotonesSimilaridad) {
	                boton.setEnabled(false);
	                boton.setBackground(new Color(230, 230, 230));
	            }
	        }
	    });
	}


	private void crearBotonReiniciarMapa(JPanel panelIzquierdo) {
		botonReiniciarMapa = new JButton("Reiniciar mapa");
		botonReiniciarMapa.setFont(new Font("Arial", Font.BOLD, 14));
		botonReiniciarMapa.setBackground(new Color(219, 101, 90));
		botonReiniciarMapa.setForeground(Color.WHITE);
		botonReiniciarMapa.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonReiniciarMapa.setBounds(60, 600, 230, 40);
		panelIzquierdo.add(botonReiniciarMapa);
	}

	private void crearBotonVerInfoRegiones(JPanel panelIzquierdo) {
		botonVerInfoRegiones = new JButton("Ver información de las regiones");
		botonVerInfoRegiones.setFont(new Font("Arial", Font.BOLD, 12));
		botonVerInfoRegiones.setBackground(new Color(250, 255, 110));
		botonVerInfoRegiones.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonVerInfoRegiones.setBounds(60, 560, 230, 30);
		panelIzquierdo.add(botonVerInfoRegiones);
	}

	private void crearBotonComponentesConexas(JPanel panelIzquierdo) {
		botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.setFont(new Font("Arial", Font.BOLD, 14));
		botonComponentesConexas.setBackground(new Color(106, 226, 246));
		botonComponentesConexas.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonComponentesConexas.setBounds(60, 510, 230, 40);
		panelIzquierdo.add(botonComponentesConexas);
	}

	private JTextField crearInputRegiones(JPanel panelIzquierdo) {
		JLabel textoCantRegiones = new JLabel("¿Cuántas regiones quiere ver?");
		textoCantRegiones.setBounds(60, 475, 230, 40);
		panelIzquierdo.add(textoCantRegiones);

		JTextField inputCantRegiones = new JTextField();
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		inputCantRegiones.setBorder(border);
		inputCantRegiones.setBounds(240, 483, 50, 25);
		panelIzquierdo.add(inputCantRegiones);
		return inputCantRegiones;
	}

	private void crearBotonGenerarAGM(JPanel panelIzquierdo) {
		botonGenerarAGM = new JButton("Generar árbol mínimo");
		botonGenerarAGM.setBackground(new Color(106, 226, 246));
		botonGenerarAGM.setFont(new Font("Arial", Font.BOLD, 14));
		botonGenerarAGM.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonGenerarAGM.setBounds(60, 430, 230, 40);
		panelIzquierdo.add(botonGenerarAGM);
	}

	private void crearBotonAsignarSimilaridades(JPanel panelIzquierdo) {
		asignarSimilaridades = new JButton("Asignar aleatoriamente");
		asignarSimilaridades.setBackground(new Color(106, 226, 246));
		asignarSimilaridades.setFont(new Font("Arial", Font.BOLD, 14));
		asignarSimilaridades.setCursor(new Cursor(Cursor.HAND_CURSOR));

		asignarSimilaridades.setBounds(60, 380, 230, 40);
		panelIzquierdo.add(asignarSimilaridades);
	}

//	private boolean todasLasProvinciasDeshabilitadas(List<JButton> listaBotonesSimilaridad) {
//		boolean ret = true;
//		for (JButton boton : listaBotonesSimilaridad) {
//			ret &= !boton.isEnabled();
//		}
//		System.out.println(ret);
//		return ret;
//	}

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
		panelIzquierdo.setBackground(new Color(170, 211, 223));
		panelIzquierdo.setPreferredSize(new Dimension(350, 700));

		return panelIzquierdo;
	}

	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(350, 30, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Regiones de la Argentina");
		frame.setResizable(false);
	}

	private void crearMapa() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 340, 700);
		mapa.setZoomControlsVisible(false);

		Coordinate posicion = new Coordinate(pais.latitud, pais.longitud);
		mapa.setDisplayPosition(posicion, pais.getZoom());

		fijarMapa(posicion);
	}

	private void dibujarMapa() {
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

	private void abrirEnlaceGitHub() {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/GonzaloKintal/tp-2-progra3"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
