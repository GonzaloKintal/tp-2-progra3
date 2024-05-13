package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Config;

public class InformacionSimilaridades extends JFrame {
	JList<String> infoJList;
	JPanel panel;
	JScrollPane scrollPane;
	JButton botonSalir;

	public InformacionSimilaridades(String similaridades) {
		configurarframe();

		crearPanel();

		crearJList();

		crearScrollPane();

		crearBotonSalir();

		actualizarInfo(similaridades);

		escucharBotonSalir();

	}

	private void configurarframe() {
		setTitle("Informacion de similaridades");
		setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		setResizable(false);
		setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());
		setBounds(0, 0, 700, 700);
		setLocationRelativeTo(null);

	}

	private void crearPanel() {
		this.panel = new JPanel();
		panel.setLayout(null);
		add(panel);

	}

	private void crearJList() {
		infoJList = new JList<String>();
		infoJList.setBounds(0, 0, 700, 700);
		panel.add(infoJList);
	}

	private void crearScrollPane() {
		scrollPane = new JScrollPane(infoJList);
		scrollPane.setBounds(10, 10, 675, 560);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(null);
		panel.add(scrollPane);
	}

	private void crearBotonSalir() {
		botonSalir = new JButton("Salir");
		botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonSalir.setBounds(245, 580, 200, 60);
		botonSalir.setBackground(new Color(227, 0, 0));
		botonSalir.setForeground(Color.WHITE);
		botonSalir.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(botonSalir);
	}

	private void escucharBotonSalir() {
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void actualizarInfo(String similaridades) {
		String[] regiones = similaridades.split("\n");
		infoJList.setListData(regiones);
	}

}