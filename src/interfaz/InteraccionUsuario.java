package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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

public class InteraccionUsuario {

	private JPanel panelInteractivo;
	private Pais pais;
	private JMapViewer mapa;

	private ArrayList<JButton> listaBotonesSimilaridad;
	private JButton asignarSimilaridades;
	private JButton botonGenerarAGM;
	private JButton botonComponentesConexas;
	private JButton botonVerInfoRegiones;
	private JButton botonReiniciarMapa;

	public InteraccionUsuario(Pais pais, JMapViewer mapa) {
		this.panelInteractivo = new JPanel();
		this.pais = pais;
		this.mapa = mapa;

	}

	public JPanel obtenerPanelInteractivo() {
		configurarPanelInteractivo();

		agregarBotones(panelInteractivo);

		return this.panelInteractivo;
	}

	private void configurarPanelInteractivo() {
		panelInteractivo.setBackground(new Color(170, 211, 223));
		panelInteractivo.setPreferredSize(new Dimension(350, 700));
	}

	private void agregarBotones(JPanel panelInteractivo) {

		// *Botones similaridad**//
		crearBotonesProvincias(panelInteractivo);

		crearBotonAsignarSimilaridades(panelInteractivo);

		crearBotonGenerarAGM(panelInteractivo);

		JTextField inputCantRegiones = crearInputRegiones(panelInteractivo);

		crearBotonComponentesConexas(panelInteractivo);

		crearBotonVerInfoRegiones(panelInteractivo);

		crearBotonReiniciarMapa(panelInteractivo);

		escucharBotones(inputCantRegiones);

	}

	private void crearBotonesProvincias(JPanel panelInteractivo) {
		listaBotonesSimilaridad = new ArrayList<>();
		Provincia[] provincias = pais.obtenerProvincias();
		int cantProvincias = provincias.length;
		int y = 10;
		int x = 20;
		for (int i = 0; i < cantProvincias; i++) {
			JButton botonAbrirProvincia = new JButton(provincias[i].getNombre());
			botonAbrirProvincia.setCursor(new Cursor(Cursor.HAND_CURSOR));
			botonAbrirProvincia.setBounds(x, y, 155, 23);
			botonAbrirProvincia.setBackground(new Color(52, 148, 58));
			botonAbrirProvincia.setForeground(Color.WHITE);

			y += 30;
			if (i == 11) {
				y = 10;
				x += 160;
			}
			listaBotonesSimilaridad.add(botonAbrirProvincia);

			// Crear una clase interna para el ActionListener que tenga acceso al índice i
			final int indiceProvincia = i; // Declarar final para que pueda ser accedido dentro de la clase interna

			escucharBotonesProvincia(botonAbrirProvincia, indiceProvincia);
			panelInteractivo.add(botonAbrirProvincia);

		}
	}

	private void crearBotonAsignarSimilaridades(JPanel panelIzquierdo) {
		asignarSimilaridades = new JButton("Asignar aleatoriamente");
		asignarSimilaridades.setBackground(new Color(106, 226, 246));
		asignarSimilaridades.setFont(new Font("Arial", Font.BOLD, 14));
		asignarSimilaridades.setCursor(new Cursor(Cursor.HAND_CURSOR));

		asignarSimilaridades.setBounds(60, 380, 230, 40);
		panelIzquierdo.add(asignarSimilaridades);
	}

	private void crearBotonGenerarAGM(JPanel panelIzquierdo) {
		botonGenerarAGM = new JButton("Generar árbol mínimo");
		botonGenerarAGM.setBackground(new Color(106, 226, 246));
		botonGenerarAGM.setFont(new Font("Arial", Font.BOLD, 14));
		botonGenerarAGM.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonGenerarAGM.setBounds(60, 430, 230, 40);
		panelIzquierdo.add(botonGenerarAGM);
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

	private void crearBotonComponentesConexas(JPanel panelIzquierdo) {
		botonComponentesConexas = new JButton("Generar regiones conexas");
		botonComponentesConexas.setFont(new Font("Arial", Font.BOLD, 14));
		botonComponentesConexas.setBackground(new Color(106, 226, 246));
		botonComponentesConexas.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonComponentesConexas.setBounds(60, 510, 230, 40);
		panelIzquierdo.add(botonComponentesConexas);
	}

	private void crearBotonVerInfoRegiones(JPanel panelIzquierdo) {
		botonVerInfoRegiones = new JButton("Ver información de las regiones");
		botonVerInfoRegiones.setFont(new Font("Arial", Font.BOLD, 12));
		botonVerInfoRegiones.setBackground(new Color(250, 255, 110));
		botonVerInfoRegiones.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonVerInfoRegiones.setBounds(60, 560, 230, 30);
		panelIzquierdo.add(botonVerInfoRegiones);
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
				InputWindow inputWindow = new InputWindow(botonAbrirProvincia, pais);
				inputWindow.setVisible(true);
			}
		});
	}

	private void escucharBotonAsignarSimilaridades() {
		asignarSimilaridades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pais.asignarPesosAleatoriamente();
				for (JButton boton : listaBotonesSimilaridad) {
					boton.setEnabled(false);
					boton.setBackground(new Color(230, 230, 230));
				}
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
				MapUtil.dibujarMapa(pais, mapa);
				botonGenerarAGM.setEnabled(false);
				botonGenerarAGM.setBackground(new Color(230, 230, 230));
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
				MapUtil.dibujarMapa(pais, mapa);
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
				for (JButton boton : listaBotonesSimilaridad) {
					boton.setEnabled(true);
					boton.setBackground(new Color(52, 148, 58));
					inputCantRegiones.setText("");
				}
				botonGenerarAGM.setEnabled(true);
				botonGenerarAGM.setBackground(new Color(106, 226, 246));

			}
		});
	}

}
