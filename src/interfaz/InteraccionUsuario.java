package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

import logica.Pais;
import logica.Provincia;
import utils.Config;
import utils.MapUtil;
import static utils.MapUtil.esUnNumeroPositivo;

public class InteraccionUsuario {

	private JPanel panelInteractivo;
	private Pais pais;
	private JMapViewer mapa;

	private HashMap<String, JButton> listaBotonesSimilaridad;
	private JButton asignarSimilaridades;
	private JButton botonGenerarAGM;
	private JButton botonComponentesConexas;
	private JButton botonVerInfoRegiones;
	private JButton botonReiniciarMapa;
	private JButton verInfo;
	private InformacionSimilaridades windowInfo;

	public InteraccionUsuario(Pais pais, JMapViewer mapa) {
		this.panelInteractivo = new JPanel();
		this.pais = pais;
		this.mapa = mapa;
		this.windowInfo = new InformacionSimilaridades(pais.obtenerInformacionSimilaridad());
	}

	public JPanel obtenerPanelInteractivo() {
		configurarPanelInteractivo();
		agregarBotones();
		return this.panelInteractivo;
	}

	private void configurarPanelInteractivo() {
		panelInteractivo.setBackground(new Color(170, 211, 223));
		panelInteractivo.setPreferredSize(new Dimension(350, 700));
	}

	private void agregarBotones() {

		crearBotonesProvincias();

		crearBotonAsignarSimilaridades();

		crearBotonGenerarAGM();

		JTextField inputCantRegiones = crearInputRegiones();

		crearBotonComponentesConexas();

		crearBotonVerInfoRegiones();

		crearBotonReiniciarMapa();

		escucharBotones(inputCantRegiones);

		crearBotonVerInfo();

		escucharBotonVerInfo();

	}

	private void crearBotonesProvincias() {
		listaBotonesSimilaridad = new HashMap<String, JButton>();
		Provincia[] provincias = pais.obtenerProvincias();
		int cantProvincias = provincias.length;
		int posY = 10;
		int posX = 20;
		for (int i = 0; i < cantProvincias; i++) {
			String nombreProvincia = provincias[i].getNombre();
			JButton botonAbrirProvincia = crearBotonAbrirProvincia(posY, posX, nombreProvincia);

			// Condición para agregar los botones de las provincias en 2 columnas
			posY += 30;
			if (i == 11) {
				posY = 10;
				posX += 160;
			}

			listaBotonesSimilaridad.put(nombreProvincia, botonAbrirProvincia);

			// Crear una clase interna para el ActionListener que tenga acceso al índice i
			final int indiceProvincia = i; // Declarar final para que pueda ser accedido dentro de la clase interna

			escucharBotonesProvincia(botonAbrirProvincia, indiceProvincia);
			panelInteractivo.add(botonAbrirProvincia);

		}
	}

	private JButton crearBotonAbrirProvincia(int posY, int posX, String nombreProvincia) {
		JButton botonAbrirProvincia = new JButton(nombreProvincia);
		botonAbrirProvincia.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonAbrirProvincia.setBounds(posX, posY, 155, 23);
		botonAbrirProvincia.setBackground(new Color(52, 148, 58));
		botonAbrirProvincia.setForeground(Color.WHITE);
		return botonAbrirProvincia;
	}

	private void crearBotonAsignarSimilaridades() {
		asignarSimilaridades = new JButton("Asignar aleatoriamente");
		asignarSimilaridades.setBackground(new Color(106, 226, 246));
		asignarSimilaridades.setFont(new Font("Arial", Font.BOLD, 14));
		asignarSimilaridades.setCursor(new Cursor(Cursor.HAND_CURSOR));

		asignarSimilaridades.setBounds(60, 380, 230, 40);
		panelInteractivo.add(asignarSimilaridades);
	}

	private void crearBotonGenerarAGM() {
		botonGenerarAGM = new JButton("Generar árbol mínimo");
		botonGenerarAGM.setBackground(new Color(106, 226, 246));
		botonGenerarAGM.setFont(new Font("Arial", Font.BOLD, 14));
		botonGenerarAGM.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonGenerarAGM.setBounds(60, 430, 230, 40);
		panelInteractivo.add(botonGenerarAGM);
	}

	private JTextField crearInputRegiones() {
		JLabel textoCantRegiones = new JLabel("¿Cuántas regiones quiere ver?");
		textoCantRegiones.setBounds(60, 475, 230, 40);
		panelInteractivo.add(textoCantRegiones);

		JTextField inputCantRegiones = new JTextField();
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		inputCantRegiones.setBorder(border);
		inputCantRegiones.setBounds(240, 483, 50, 25);
		panelInteractivo.add(inputCantRegiones);
		return inputCantRegiones;
	}

	private void crearBotonComponentesConexas() {
		botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.setFont(new Font("Arial", Font.BOLD, 14));
		botonComponentesConexas.setBackground(new Color(106, 226, 246));
		botonComponentesConexas.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonComponentesConexas.setBounds(60, 510, 230, 40);
		panelInteractivo.add(botonComponentesConexas);
	}

	private void crearBotonVerInfoRegiones() {
		botonVerInfoRegiones = new JButton("Ver información de las regiones");
		botonVerInfoRegiones.setFont(new Font("Arial", Font.BOLD, 12));
		botonVerInfoRegiones.setBackground(new Color(250, 255, 110));
		botonVerInfoRegiones.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonVerInfoRegiones.setBounds(60, 560, 230, 30);
		panelInteractivo.add(botonVerInfoRegiones);
	}

	private void crearBotonReiniciarMapa() {
		botonReiniciarMapa = new JButton("Reiniciar mapa");
		botonReiniciarMapa.setFont(new Font("Arial", Font.BOLD, 14));
		botonReiniciarMapa.setBackground(new Color(247, 77, 64));
		botonReiniciarMapa.setForeground(Color.WHITE);
		botonReiniciarMapa.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonReiniciarMapa.setBounds(60, 600, 230, 40);
		panelInteractivo.add(botonReiniciarMapa);
	}

	private void escucharBotones(JTextField inputCantRegiones) {
		escucharBotonAsignarSimilaridades();

		escucharBotonGenerarAGM();

		escucharBotonComponentesConexas(inputCantRegiones);

		escucharBotonVerInfoRegiones();

		escucharBotonReiniciarMapa(inputCantRegiones);

	}

	private void escucharBotonesProvincia(JButton botonAbrirProvincia, final int indiceProvincia) {
		botonAbrirProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputWindow inputWindow = new InputWindow(botonAbrirProvincia.getText(), pais, listaBotonesSimilaridad,
						windowInfo);
				inputWindow.setVisible(true);
			}
		});
	}

	private void escucharBotonAsignarSimilaridades() {
		asignarSimilaridades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pais.asignarPesosAleatoriamente();
				for (JButton boton : listaBotonesSimilaridad.values()) {
					boton.setEnabled(false);
					boton.setBackground(new Color(230, 230, 230));
				}
				windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
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
				pais.generarCaminoÚnico();
				MapUtil.dibujarMapa(pais, mapa);
				botonGenerarAGM.setEnabled(false);
				botonGenerarAGM.setBackground(new Color(230, 230, 230));
				windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
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

				if (!esUnNumeroPositivo(valorIngresado)) {
					JOptionPane.showMessageDialog(null, Config.MSJ_ERROR_SOLO_NUMERO, "ATENCIÓN",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int cantidadRegiones = Integer.parseInt(valorIngresado);

				if (cantidadRegiones <= 0 || cantidadRegiones > pais.obtenerCantProvincias()) {
					JOptionPane.showMessageDialog(null,
							Config.MSJ_ERROR_CANT_REGIONES_INVALIDO + pais.obtenerCantProvincias(), "ATENCIÓN",
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
				MapUtil.dibujarMapa(pais, mapa);
				windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
			}

		});
	}

	private void escucharBotonVerInfoRegiones() {
		botonVerInfoRegiones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InformacionRegiones infoRegiones = new InformacionRegiones(pais.obtenerInformacionRegiones());
				infoRegiones.setVisible(true);
			}
		});
	}

	private void escucharBotonReiniciarMapa(JTextField inputCantRegiones) {
		botonReiniciarMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pais.reestablecerConexionEntreLimitrofes();
				MapUtil.dibujarMapa(pais, mapa);
				for (JButton boton : listaBotonesSimilaridad.values()) {
					boton.setEnabled(true);
					boton.setBackground(new Color(52, 148, 58));
					inputCantRegiones.setText("");
				}
				botonGenerarAGM.setEnabled(true);
				botonGenerarAGM.setBackground(new Color(106, 226, 246));
				windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
			}
		});
	}

	private void crearBotonVerInfo() {
		verInfo = new JButton("Ver info");
		verInfo.setBackground(Color.ORANGE);
		verInfo.setFont(new Font("Arial", Font.BOLD, 14));
		verInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		verInfo.setBounds(180, 340, 155, 23);
		panelInteractivo.add(verInfo);

	}

	private void escucharBotonVerInfo() {
		verInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
				windowInfo.setVisible(true);
			}
		});

	}

}
