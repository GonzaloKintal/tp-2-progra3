package utils;

import java.awt.Color;

public class Config {
	public static PaisType PAIS = ArchivoJSON.leerArchivo("argentina.json", PaisType.class);	
	
	public static Color COLOR_NODO = new Color(106, 226, 246);
	public static Color COLOR_ARISTA = new Color(23, 90, 115);
}
