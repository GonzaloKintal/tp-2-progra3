package utils;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Provincia;

public class Config {
	public Provincia[] PROVINCIAS;
	
	public Config() {
		this.PROVINCIAS = new Provincia[23];
		
		ArrayList<String> limitrofes_bsas = new ArrayList<>();
		limitrofes_bsas.add("Cordoba");
		limitrofes_bsas.add("Santa Fe");
		limitrofes_bsas.add("Entre Rios");
		limitrofes_bsas.add("La Pampa");
		limitrofes_bsas.add("Rio Negro");
		limitrofes_bsas.add("Ciudad Autonom de Buenos Aires");
		PROVINCIAS[0] = new Provincia("Buenos Aires", new Coordinate(-36.410246, -60.441624), limitrofes_bsas);
		
		ArrayList<String> limitrofes_cordoba = new ArrayList<>();
		limitrofes_bsas.add("Buenos Aires");
		limitrofes_bsas.add("Santa Fe");
		limitrofes_bsas.add("San Luis");
		limitrofes_bsas.add("La Pampa");
		limitrofes_bsas.add("Santiago Del Estero");
		PROVINCIAS[1] = new Provincia("Córdoba", new Coordinate(-31.407339, -64.191297), limitrofes_cordoba);
	}
}
