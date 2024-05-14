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
import utils.Config;

import static utils.MapUtil.esUnNumeroPositivo;

@SuppressWarnings("serial")
public class InputWindow extends JFrame {

  HashMap<String, JButton> listaBotonesSimilaridad;
  JTextField[] inputPesosLimitrofes;
  JButton botonCompletarCampos;
  JButton botonConfirmar;
  String nombreProvincia;
  JPanel panel;
  Pais pais;
  InformacionSimilaridades windowInfo;

  public InputWindow(String nombreProvincia, Pais pais, HashMap<String, JButton> listaBotonesSimilaridad,
      InformacionSimilaridades windowInfo) {

    this.nombreProvincia = nombreProvincia;
    this.pais = pais;
    this.listaBotonesSimilaridad = listaBotonesSimilaridad;
    this.windowInfo = windowInfo;

    panel = new JPanel();
    panel.setLayout(null);

    configurarVentana();

    agregarInputPorProvincia();

    crearBotonCompletarCampos();
    escucharBotonCompletarCampos();

    crearBotonConfirmar(panel);
    escucharBotonConfirmar();

    getContentPane().add(panel);

  }

  private void configurarVentana() {
    setTitle("Similaridad " + this.nombreProvincia);
    setSize(350, 500);
    setLocationRelativeTo(null);
    setResizable(false);
    setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());
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
    botonCompletarCampos.setBackground(Config.COLOR_NODO);
    botonCompletarCampos.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(botonCompletarCampos);
    return botonCompletarCampos;
  }

  private JButton crearBotonConfirmar(JPanel panel) {
    this.botonConfirmar = new JButton("Confirmar");
    botonConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    botonConfirmar.setBounds(66, 370, 200, 80);
    botonConfirmar.setBackground(Config.COLOR_BOTON_PROVINCIAS);
    botonConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(botonConfirmar);
    return botonConfirmar;
  }

  private void escucharBotonConfirmar() {
    this.botonConfirmar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int botonTerminarCont = 0;
        int indiceInput = 0;
        HashMap<String, Integer> provinciasLimitrofes = pais.obtenerAristasLimitrofes(nombreProvincia);

        for (String provincia : provinciasLimitrofes.keySet()) {
          String pesoProvincia = inputPesosLimitrofes[indiceInput].getText();

          if (!esUnNumeroPositivo(pesoProvincia)) {
            JOptionPane.showMessageDialog(null, Config.MSJ_ERROR_SOLO_NUMERO, "ATENCIÓN",
                JOptionPane.WARNING_MESSAGE);
            return;
          }

          int pesoProvinciaInt = Integer.parseInt(pesoProvincia);

          int indiceSegundaProvincia = pais.indiceDe(provincia);

          if (campoEsValidoParaAsignarPeso(pesoProvincia)) {
            deshabilitarInput(indiceInput, pesoProvinciaInt, indiceSegundaProvincia);
            botonTerminarCont++;
          }
          indiceInput++;
        }

        if (botonTerminarCont < provinciasLimitrofes.size())
          JOptionPane.showMessageDialog(panel, "Por favor, asigne similaridad positiva a las provincias",
              "ATENCIÓN", JOptionPane.WARNING_MESSAGE);

        if (botonTerminarCont == inputPesosLimitrofes.length) {
          dispose();
        }

        verificarBotones();
        windowInfo.actualizarInfo(pais.obtenerInformacionSimilaridad());
      }

      private void deshabilitarInput(int indiceInput, int pesoProvinciaInt, int indiceSegundaProvincia) {
        pais.actualizarSimilaridad(pais.indiceDe(nombreProvincia), indiceSegundaProvincia, pesoProvinciaInt);
        inputPesosLimitrofes[indiceInput].setBackground(Color.gray);
        inputPesosLimitrofes[indiceInput].setEditable(false);
      }
    });
  }

  private void verificarBotones() {
    for (Entry<String, JButton> entry : listaBotonesSimilaridad.entrySet()) {
      String nombreProvincia = entry.getKey();
      JButton botonProvincia = entry.getValue();
      if (pais.todasSusLimitrofesTienenSimilaridad(nombreProvincia)) {
        botonProvincia.setEnabled(false);
        botonProvincia.setBackground(Config.COLOR_BOTON_DESHABILITADO);
      }
    }
  }

  private void deshabilitarCampo(JTextField campo) {
    campo.setEditable(false);
    campo.setBackground(Config.COLOR_BOTON_DESHABILITADO_2);
    campo.setForeground(Color.WHITE);
    campo.setBorder(BorderFactory.createLineBorder(Config.COLOR_BOTON_DESHABILITADO));
  }

  private boolean campoEsValidoParaAsignarPeso(String pesoProvincia) {
    return !pesoProvincia.isEmpty() && Integer.parseInt(pesoProvincia) > 0;
  }

}