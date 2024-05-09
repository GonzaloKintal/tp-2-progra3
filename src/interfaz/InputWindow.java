package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class InputWindow extends JFrame {

  HashMap<String, JButton> listaBotonesSimilaridad;
  JTextField[] inputPesosLimitrofes;
  JButton botonCompletarCampos;
  JButton botonConfirmar;
  String nombreProvincia;
  JPanel panel;
  Pais pais;

  public InputWindow(String nombreProvincia, Pais pais, HashMap<String, JButton> listaBotonesSimilaridad) {

    this.nombreProvincia = nombreProvincia;
    this.pais = pais;
    this.listaBotonesSimilaridad = listaBotonesSimilaridad;

    panel = new JPanel();
    panel.setLayout(null);

    configurarVentana();

    agregarInputPorProvincia();

    crearBotonConfirmar(panel);
    escucharBotonConfirmar();

    crearBotonCompletarCampos();
    escucharBotonCompletarCampos();

    getContentPane().add(panel);

  }

  private void escucharBotonCompletarCampos() {
    botonCompletarCampos.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for (JTextField textField : inputPesosLimitrofes) {
          if (!textField.isEditable()) {
            continue;
          }
          int randomNumber = (int) (Math.random() * 101 + 1);
          textField.setText(Integer.toString(randomNumber));
        }

      }
    });
  }

  private JButton crearBotonCompletarCampos() {
    this.botonCompletarCampos = new JButton("Llenar campos aleatoriamente");
    botonCompletarCampos.setCursor(new Cursor(Cursor.HAND_CURSOR));
    botonCompletarCampos.setBounds(42, 329, 248, 30);
    botonCompletarCampos.setBackground(new Color(106, 226, 246));
    botonCompletarCampos.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(botonCompletarCampos);
    return botonCompletarCampos;
  }

  private void escucharBotonConfirmar() {
    this.botonConfirmar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int botonTerminarCont = 0;
        int contadorProvincia = 0;
        HashMap<String, Integer> provinciasLimitrofes = pais.obtenerAristasLimitrofes(nombreProvincia);

        for (String provincia : provinciasLimitrofes.keySet()) {
          String pesoProvincia = inputPesosLimitrofes[contadorProvincia].getText();
          int pesoProvinciaInt = Integer.parseInt(pesoProvincia);

          int indiceSegundaProvincia = pais.indiceDe(provincia);

          if (!pesoProvincia.isEmpty() && esUnNumero(pesoProvincia) && pesoProvinciaInt > 0) {
            pais.actualizarSimilaridad(pais.indiceDe(nombreProvincia), indiceSegundaProvincia, pesoProvinciaInt);
            inputPesosLimitrofes[contadorProvincia].setBackground(Color.gray);
            inputPesosLimitrofes[contadorProvincia].setEnabled(false);
            botonTerminarCont++;
          }
        }

        if (botonTerminarCont < provinciasLimitrofes.size())
          JOptionPane.showMessageDialog(panel, "Por favor, asigne similaridad positiva a las provincias",
              "ATENCIÓN", JOptionPane.WARNING_MESSAGE);

        if (botonTerminarCont == inputPesosLimitrofes.length) {
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
          if (pais.tieneAsignadaSimilaridad(nombreProvincia)) {
            botonProvincia.setEnabled(false);
            botonProvincia.setBackground(new Color(230, 230, 230));
          }
        }
      }

    });
  }

  private JButton crearBotonConfirmar(JPanel panel) {
    this.botonConfirmar = new JButton("Confirmar");
    botonConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    botonConfirmar.setBounds(66, 370, 200, 80);
    botonConfirmar.setBackground(new Color(29, 245, 87));
    botonConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(botonConfirmar);
    return botonConfirmar;
  }

  private void agregarInputPorProvincia() {
    HashMap<String, Integer> provinciasLimitrofes = pais.obtenerAristasLimitrofes(nombreProvincia);
    int cantProvinciasLimitrofes = provinciasLimitrofes.size();
    int indiceProvincia = 0;
    inputPesosLimitrofes = new JTextField[cantProvinciasLimitrofes];

    int posicion_Y = 0;
    int heigth = 100;

    for (String provincia : provinciasLimitrofes.keySet()) {
      JLabel label = new JLabel(provincia);
      JTextField inputPesoLimitrofe = new JTextField();
      int peso = provinciasLimitrofes.get(provincia);

      if (peso > 0) {
        deshabilitarCampo(inputPesoLimitrofe);
      }

      inputPesoLimitrofe.setText(Integer.toString(peso));
      label.setBounds(20, posicion_Y, 120, heigth);
      inputPesoLimitrofe.setBounds(150, posicion_Y + 35, 150, 30);
      posicion_Y += 40;
      panel.add(label);
      panel.add(inputPesoLimitrofe);

      inputPesosLimitrofes[indiceProvincia++] = inputPesoLimitrofe;
    }
  }

  private void deshabilitarCampo(JTextField campo) {
    campo.setEditable(false);
    campo.setBackground(new Color(130, 130, 130));
    campo.setForeground(Color.WHITE);
    campo.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
  }

  private void configurarVentana() {
    setTitle("Similaridad " + this.nombreProvincia);
    setSize(350, 500);
    setLocationRelativeTo(null);
    setResizable(false);
    setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());
  }

}