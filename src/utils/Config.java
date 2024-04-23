package utils;

import java.awt.Color;

import logica.Provincia;

public class Config {
	private static String NOMBRE_ARCHIVO = "provincias.json";
	public static Provincia[] PROVINCIAS = ArchivoJSON.leerJSON(NOMBRE_ARCHIVO);
	
	public static Color COLOR_NODO = new Color(106, 226, 246);
	public static Color COLOR_ARISTA = new Color(23, 90, 115);
}
