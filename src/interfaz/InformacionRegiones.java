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

public class InformacionRegiones extends JFrame {

	public InformacionRegiones(String info) {
		setTitle("Estadísticas de las regiones");
		setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		setBounds(0, 0, 700, 700);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());

		// Layout de la ventana
		JPanel panel = new JPanel();
		panel.setBackground(new Color(170, 211, 223));
		panel.setLayout(null);

		JList<String> list = new JList<>();
		list.setBackground(new Color(9, 48, 67));
		list.setForeground(Color.WHITE);
		list.setFocusable(false);
		setResizable(false);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 10, 675, 560);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(null);

		panel.add(scrollPane);

		String[] regiones = info.split("\n");
		list.setListData(regiones);

		JButton botonSalir = new JButton("Salir");
		botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonSalir.setBounds(245, 580, 200, 60);
		botonSalir.setBackground(new Color(227, 0, 0));
		botonSalir.setForeground(Color.WHITE);
		botonSalir.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(botonSalir);

		// Agregar panel a la ventana
		getContentPane().add(panel);

		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
}