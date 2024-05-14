package utils;

import java.awt.Color;

public class Config {
  public static PaisType PAIS = ArchivoJSON.leerArchivo("argentina.json", PaisType.class);
  public static int ZOOM = 4;

  public static Color COLOR_NODO = new Color(106, 226, 246);
  public static Color COLOR_ARISTA = new Color(23, 90, 115);
  public static Color COLOR_BOTON_DESHABILITADO = new Color(230, 230, 230);
  public static Color COLOR_BOTON_PROVINCIAS = new Color(52, 148, 58);
  public static Color COLOR_BOTON_SALIDA = new Color(247, 77, 64);
  public static Color COLOR_BOTON_INFORMACION = new Color(250, 255, 110);

  public static int FRAME_WIDTH = 700;
  public static int FRAME_HEIGHT = 700;

  public static String MSJ_ERROR_DESCONEXAR = "Pesos insuficientes para dividir el país. Agregue mas pesos por favor";
  public static String MSJ_ERROR_REGIONES_VACIO = "Indique por favor la cantidad de regiones conexas";
  public static String MSJ_ERROR_SOLO_NUMERO = "Disculpe la molestia, pero solo aceptamos números positivos enteros por el momento";
  public static String MSJ_ERROR_CANT_REGIONES_INVALIDO = "La cantidad de regiones conexas debe ser entre 1 a ";
}
