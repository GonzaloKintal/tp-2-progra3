package interfaz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputWindow extends JFrame {

	public InputWindow(ArrayList<String> provinciasLimitrofes) {
		// Configuración de la ventana
		int cantProvincias = provinciasLimitrofes.size();
		setTitle("ASIGNACION SIMILIARIDAD");
		setSize(350, 500);
		setLocationRelativeTo(null);

		// Layout de la ventana
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JTextField[] pesosLimitrofes = new JTextField[cantProvincias];
		int x = 0;
		int y = 0;
		int width = 100;
		int heigth = 100;
		for (int i = 0; i < cantProvincias; i++) {
			JLabel label = new JLabel(provinciasLimitrofes.get(i));
			JTextField pesoLimitrofe = new JTextField();
			label.setBounds(20, y, width, heigth);
			pesoLimitrofe.setBounds(100, y + 35, 200, 30);
			y += 40;
			panel.add(label);
			panel.add(pesoLimitrofe);
			pesosLimitrofes[i] = pesoLimitrofe;

		}

		JButton botonTerminar = new JButton("Listorti José María");
		botonTerminar.setBounds(50, 300, 200, 100);
		panel.add(botonTerminar);

		// Agregar panel a la ventana
		getContentPane().add(panel);

		botonTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int botonTerminarCont = 0;
				for (int i = 0; i < cantProvincias; i++) {
					String pesoProvincia = pesosLimitrofes[i].getText();
					if (!pesoProvincia.isEmpty() && esUnNumero(pesoProvincia)) {
						pesosLimitrofes[i].setBackground(Color.gray);
						pesosLimitrofes[i].setEnabled(false);
						botonTerminarCont++;
					} else {
						JOptionPane.showMessageDialog(panel,
								"El peso de " + provinciasLimitrofes.get(i) + " debe ser un numero", "ATENCIÓN",
								JOptionPane.WARNING_MESSAGE);

					}

				}
				if (botonTerminarCont == pesosLimitrofes.length) {
					botonTerminar.setEnabled(false);

				}
			}

			private boolean esUnNumero(String pesoProvincia) {
				return pesoProvincia.matches("\\d+");
			}
		});

	}
}