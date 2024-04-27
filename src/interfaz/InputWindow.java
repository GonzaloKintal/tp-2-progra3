package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputWindow extends JFrame {

    public InputWindow(ArrayList<String> provinciasLimitrofes) {
        // Configuración de la ventana
        setTitle("Ventana de Entrada");
        setSize(350, 500);
        setLocationRelativeTo(null);

        // Layout de la ventana
        JPanel panel = new JPanel();
        panel.setLayout(null);

        int x = 0;
        int y = 0;
        int width = 100;
        int heigth = 100;
    	for (String limitrofe: provinciasLimitrofes) {
    		JLabel label = new JLabel(limitrofe);
    		JTextField textField = new JTextField();
    		label.setBounds(20, y, width, heigth);
    		textField.setBounds(100, y+35, 200, 30);
    		y+=40;
    		panel.add(label);
    		panel.add(textField);
    	}
    	
    	JButton botonTerminar = new JButton("Listorti José María");
    	botonTerminar.setBounds(50, 300, 200, 100);
    	panel.add(botonTerminar);
        
    	// Agregar panel a la ventana
    	getContentPane().add(panel);
    	
    }
}