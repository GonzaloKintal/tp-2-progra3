package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class Aplicacion {

	private JFrame frame;

	private JMapViewer mapa;

	private HashMap<String, Coordinate> provincias;

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
		provincias = new HashMap<>();
		provincias.put("Buenos Aires", new Coordinate(-36.410246, -60.441624));
		provincias.put("Córdoba", new Coordinate(-31.407339, -64.191297));
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

		for (Coordinate provincia : provincias.values()) {
			MapMarker p = new MapMarkerDot("", provincia);
			p.getStyle().setBackColor(Color.BLUE);
			mapa.addMapMarker(p);
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
