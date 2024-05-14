package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Config;

public class Welcome {

	private JFrame frame;
	private JPanel panelWelcome;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Welcome() {
		initialize();
	}

	private void initialize() {
		crearFrame();

		crearPanel();

		crearBotonIniciar();

		crearBotonSalir();

		agregarImagenFondo();

		frame.getContentPane().add(panelWelcome);
	}

	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(280, 30, Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Regiones de la Argentina");
		frame.setResizable(false);

		frame.setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());
	}

	private void crearPanel() {
		panelWelcome = new JPanel();
		panelWelcome.setLayout(null);
	}

	private void crearBotonIniciar() {
		JButton botonIniciar = new JButton("INICIAR");
		botonIniciar.setBounds(140, 410, 400, 70);
		botonIniciar.setFont(new Font("Verdana", Font.BOLD, 30));
		botonIniciar.setForeground(Color.WHITE);
		botonIniciar.setBackground(new Color(93, 189, 72));
		botonIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new Aplicacion();
			}
		});
		panelWelcome.add(botonIniciar);
	}

	private void crearBotonSalir() {
		JButton botonSalir = new JButton("SALIR");
		botonSalir.setBounds(140, 520, 400, 70);
		botonSalir.setFont(new Font("Verdana", Font.BOLD, 30));
		botonSalir.setForeground(Color.WHITE);
		botonSalir.setBackground(new Color(194, 10, 10));
		botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

		botonSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		panelWelcome.add(botonSalir);
	}

	private void agregarImagenFondo() {
		Image backgroundImage = new ImageIcon(this.getClass().getResource("/Welcome.png")).getImage();
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 690, 665);
		backgroundLabel.setIcon(new ImageIcon(backgroundImage));
		panelWelcome.add(backgroundLabel);
	}

}
