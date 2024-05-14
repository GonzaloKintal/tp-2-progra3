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

@SuppressWarnings("serial")
public class InformacionRegiones extends JFrame {

  JPanel panel;
  JList<String> informacionRegiones;
  JScrollPane scrollPane;
  String[] regiones;
  JButton botonSalir;

  public InformacionRegiones(String info) {
    configurarFrame();

    crearPanel();

    crearJList();

    crearScrollPane();
    panel.add(scrollPane);

    agregarInformacion(info);

    crearBotonSalir();
    escucharBotonSalir();
    panel.add(botonSalir);

    getContentPane().add(panel);
  }

  private void configurarFrame() {
    setTitle("Estadísticas de las regiones");
    setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
    setBounds(0, 0, 700, 700);
    setLocationRelativeTo(null);
    setIconImage(new ImageIcon(getClass().getResource("/icono-app.png")).getImage());
  }

  private void crearPanel() {
    this.panel = new JPanel();
    panel.setLayout(null);
  }

  private void crearJList() {
    informacionRegiones = new JList<>();
    informacionRegiones.setBackground(Config.COLOR_SECUNDARIO);
    informacionRegiones.setForeground(Color.WHITE);
    informacionRegiones.setFocusable(false);
    setResizable(false);
  }

  private void crearScrollPane() {
    scrollPane = new JScrollPane(informacionRegiones);
    scrollPane.setBounds(10, 10, 675, 560);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setBorder(null);
  }

  private void agregarInformacion(String info) {
    String[] regiones = info.split("\n");
    informacionRegiones.setListData(regiones);
  }

  private void escucharBotonSalir() {
    botonSalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
  }

  private void crearBotonSalir() {
    botonSalir = new JButton("Salir");
    botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
    botonSalir.setBounds(245, 585, 200, 60);
    botonSalir.setBackground(Config.COLOR_BOTON_SALIDA);
    botonSalir.setForeground(Color.WHITE);
    botonSalir.setFont(new Font("Arial", Font.BOLD, 18));
  }

}