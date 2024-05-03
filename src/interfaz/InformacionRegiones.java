package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InformacionRegiones extends JFrame {

	public InformacionRegiones() {
		setTitle("Estadísticas de las regiones");
		setSize(500, 500);
		setLocationRelativeTo(null);

		// Layout de la ventana
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JButton botonSalir = new JButton("Salir");
		botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonSalir.setBounds(140, 330, 200, 100);
		botonSalir.setBackground(new Color(29, 245, 87));
		botonSalir.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(botonSalir);

		// Agregar panel a la ventana
		getContentPane().add(panel);

		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

	}
}