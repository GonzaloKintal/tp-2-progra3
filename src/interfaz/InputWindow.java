package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import logica.Pais;
import utils.Tupla;

public class InputWindow extends JFrame {
	
	JTextField[] pesosLimitrofes;

	public InputWindow(JButton botonProvincia, Pais pais,HashMap<String,JButton> listaBotonesSimilaridad) {
		String nombreProvincia = botonProvincia.getText();
		int indiceProvincia = pais.indiceDe(nombreProvincia);
		ArrayList<Tupla<String, Integer>> provinciasLimitrofes = pais.obtenerAristasLimitrofes(nombreProvincia);

		// Configuración de la ventana
		int cantProvinciasLimitrofes = provinciasLimitrofes.size();
		setTitle("Similaridad " + nombreProvincia);
		setSize(350, 500);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());

		// Layout de la ventana
		JPanel panel = new JPanel();
		panel.setLayout(null);
		pesosLimitrofes = new JTextField[cantProvinciasLimitrofes];
		int y = 0;
		int heigth = 100;
		for (int i = 0; i < cantProvinciasLimitrofes; i++) {
			JLabel label = new JLabel(provinciasLimitrofes.get(i).getPrimero());
			JTextField pesoLimitrofe = new JTextField();
			int peso = provinciasLimitrofes.get(i).getSegundo();
			if (peso > 0) {
				pesoLimitrofe.setEditable(false);
				pesoLimitrofe.setBackground(new Color(130, 130, 130));
				pesoLimitrofe.setForeground(Color.WHITE);
				pesoLimitrofe.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
			}
			pesoLimitrofe.setText(Integer.toString(peso));

			label.setBounds(20, y, 120, heigth);
			pesoLimitrofe.setBounds(150, y + 35, 150, 30);
			y += 40;
			panel.add(label);
			panel.add(pesoLimitrofe);
			
			pesosLimitrofes[i] = pesoLimitrofe;
		}

		JButton botonConfirmar = new JButton("Confirmar");
		botonConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonConfirmar.setBounds(66, 330, 200, 100);
		botonConfirmar.setBackground(new Color(29, 245, 87));
		botonConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(botonConfirmar);

		// Agregar panel a la ventana
		getContentPane().add(panel);

		botonConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int botonTerminarCont = 0;
				for (int i = 0; i < cantProvinciasLimitrofes; i++) {
					String pesoProvincia = pesosLimitrofes[i].getText();
					int pesoProvinciaInt = Integer.parseInt(pesoProvincia);

					int indiceSegundaProvincia = pais.indiceDe(provinciasLimitrofes.get(i).getPrimero());

					if (!pesoProvincia.isEmpty() && esUnNumero(pesoProvincia) && pesoProvinciaInt > 0) {
						pais.actualizarSimilaridad(indiceProvincia, indiceSegundaProvincia, pesoProvinciaInt);
						pesosLimitrofes[i].setBackground(Color.gray);
						pesosLimitrofes[i].setEnabled(false);
						botonTerminarCont++;
					}

				}
				if (botonTerminarCont < cantProvinciasLimitrofes)
					JOptionPane.showMessageDialog(panel, "Por favor, asigne similaridad positiva a las provincias",
							"ATENCIÓN", JOptionPane.WARNING_MESSAGE);

				if (botonTerminarCont == pesosLimitrofes.length) {
					dispose();
				}
				
				verificarBotones();
			}

			private boolean esUnNumero(String pesoProvincia) {
				return pesoProvincia.matches("\\d+");
			}
			private void verificarBotones() {
			    for (Entry<String, JButton> entry : listaBotonesSimilaridad.entrySet()) {
			        String nombreProvincia = entry.getKey();
			        JButton botonProvincia = entry.getValue();
			        if(pais.tieneAsignadaSimilaridad(nombreProvincia)) {
			        	botonProvincia.setEnabled(false);
			        	botonProvincia.setBackground(new Color(230, 230, 230));
			        }
			    }
			}
			
			
		});

		JButton botonCompletarCampos = new JButton("Llenar campos aleatoriamente");
		botonCompletarCampos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonCompletarCampos.setBounds(42, 289, 248, 30);
		botonCompletarCampos.setBackground(new Color(106, 226, 246));
		botonCompletarCampos.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(botonCompletarCampos);

		botonCompletarCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JTextField textField : pesosLimitrofes) {
					if(!textField.isEditable()) {
						continue;
					}
					int randomNumber = (int) (Math.random() * 101 + 1);
					textField.setText(Integer.toString(randomNumber));
				}
			
			}
			
			
		});

		
	}
	
	

}