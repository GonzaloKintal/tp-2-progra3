package interfaz;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import logica.Pais;
import utils.Config;
import utils.MapUtil;

import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.MouseWheelEvent;
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
//				try {
//					Aplicacion window = new Aplicacion();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Aplicacion() {
		this.pais = new Pais(Config.PAIS);
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		crearFrame();

		JPanel panelMapa = new JPanel();

		crearMapa();

		MapUtil.dibujarMapa(this.pais, this.mapa);

		
		InteraccionUsuario interaccionUsuario = new InteraccionUsuario(this.pais, this.mapa);
		JPanel panelInteractivo = interaccionUsuario.obtenerPanelInteractivo();


		JSplitPane splitPane = dividirPantalla(panelMapa, panelInteractivo);

		splitPane.setResizeWeight(0);
		splitPane.setDividerSize(0);
		panelMapa.setLayout(null);

		panelMapa.add(mapa);

		frame.getContentPane().add(splitPane);

		agregarLogoGithub();

		agregarImagenMalvinas();
		

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


	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(350, 30, Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
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
