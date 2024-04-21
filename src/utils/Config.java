package utils;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Provincia;

public class Config {
	public Provincia[] PROVINCIAS;
	
	public Config() {
		this.PROVINCIAS = new Provincia[23];
		
		ArrayList<String> limitrofes_bsas = new ArrayList<>();
		limitrofes_bsas.add("Córdoba");
		limitrofes_bsas.add("Santa Fé");
		limitrofes_bsas.add("Entre Rios");
		limitrofes_bsas.add("La Pampa");
		limitrofes_bsas.add("Río Negro");
		PROVINCIAS[0] = new Provincia("Buenos Aires", new Coordinate(-36.410246, -60.441624), limitrofes_bsas);
		
		ArrayList<String> limitrofes_cordoba = new ArrayList<>();
		limitrofes_bsas.add("Buenos Aires");
		limitrofes_bsas.add("Santa Fé");
		limitrofes_bsas.add("San Luis");
		limitrofes_bsas.add("La Pampa");
		limitrofes_bsas.add("Santiago del Estero");
		PROVINCIAS[1] = new Provincia("Córdoba", new Coordinate(-31.407339, -64.191297), limitrofes_cordoba);
		
		ArrayList<String> limitrofes_catamarca = new ArrayList<>();
		limitrofes_catamarca.add("Salta");
		limitrofes_catamarca.add("Tucumán");
		limitrofes_catamarca.add("Santiago del Estero");
		limitrofes_catamarca.add("Córdoba");
		limitrofes_catamarca.add("La Rioja");
		PROVINCIAS[2] = new Provincia("Catamarca", new Coordinate(-28.458453, -65.783749), limitrofes_catamarca);
		
		ArrayList<String> limitrofes_entreRios = new ArrayList<>();
		limitrofes_catamarca.add("Buenos Aires");
		limitrofes_catamarca.add("Santa Fé");
		limitrofes_catamarca.add("Corrientes");
		PROVINCIAS[3] = new Provincia("Entre Rios", new Coordinate(-32.020103, -59.300656), limitrofes_entreRios);
		
		ArrayList<String> limitrofes_santaFe = new ArrayList<>();
		limitrofes_catamarca.add("Buenos Aires");
		limitrofes_catamarca.add("Córdoba");
		limitrofes_catamarca.add("Santiago del Estero");
		limitrofes_catamarca.add("Chaco");
		limitrofes_catamarca.add("Corrientes");
		limitrofes_catamarca.add("Entre Rios");
		PROVINCIAS[4] = new Provincia("Santa Fé", new Coordinate(-30.734059, -61.045393), limitrofes_santaFe);
	
		ArrayList<String> limitrofes_laPampa = new ArrayList<>();
		limitrofes_catamarca.add("Buenos Aires");
		limitrofes_catamarca.add("Río Negro");
		limitrofes_catamarca.add("Neuquén");
		limitrofes_catamarca.add("Mendoza");
		limitrofes_catamarca.add("San Luis");
		limitrofes_catamarca.add("Córdoba");
		PROVINCIAS[5] = new Provincia("La Pampa", new Coordinate(-37.150575, -65.638422), limitrofes_laPampa);
		
		ArrayList<String> limitrofes_rioNegro = new ArrayList<>();
		limitrofes_rioNegro.add("Chubut");
		limitrofes_rioNegro.add("Neuquén");
		limitrofes_rioNegro.add("La Pampa");
		limitrofes_rioNegro.add("Buenos Aires");
		PROVINCIAS[6] = new Provincia("Río Negro", new Coordinate(-40.272863, -67.371468), limitrofes_rioNegro);
		
		ArrayList<String> limitrofes_chubut = new ArrayList<>();
		limitrofes_chubut.add("Santa Cruz");
		limitrofes_chubut.add("Río Negro");
		PROVINCIAS[7] = new Provincia("Chubut", new Coordinate(-43.885722, -68.748194), limitrofes_chubut);
		
		ArrayList<String> limitrofes_santaCruz = new ArrayList<>();
		limitrofes_santaCruz.add("Tierra del Fuego");
		limitrofes_santaCruz.add("Chubut");
		PROVINCIAS[8] = new Provincia("Santa Cruz", new Coordinate(-48.784325, -70.058595), limitrofes_santaCruz);
		
		ArrayList<String> limitrofes_tierraDelFuego = new ArrayList<>();
		limitrofes_tierraDelFuego.add("Santa Cruz");
		PROVINCIAS[9] = new Provincia("Tierra del Fuego", new Coordinate(-54.357735, -67.768914), limitrofes_tierraDelFuego);
		
		ArrayList<String> limitrofes_neuquen = new ArrayList<>();
		limitrofes_neuquen.add("Mendoza");
		limitrofes_neuquen.add("La Pampa");
		limitrofes_neuquen.add("Río Negro");
		PROVINCIAS[10] = new Provincia("Neuquén", new Coordinate(-38.574205, -69.924229), limitrofes_neuquen);
		
		ArrayList<String> limitrofes_mendoza = new ArrayList<>();
		limitrofes_mendoza.add("San Juan");
		limitrofes_mendoza.add("San Luis");
		limitrofes_mendoza.add("La Pampa");
		limitrofes_mendoza.add("Neuquén");
		PROVINCIAS[11] = new Provincia("Mendoza", new Coordinate(-34.561651, -68.546747), limitrofes_mendoza);
		
		ArrayList<String> limitrofes_sanJuan = new ArrayList<>();
		limitrofes_sanJuan.add("La Rioja");
		limitrofes_sanJuan.add("San Luis");
		limitrofes_sanJuan.add("Mendoza");
		PROVINCIAS[12] = new Provincia("San Juan", new Coordinate(-30.906648, -69.015093), limitrofes_sanJuan);
		
		ArrayList<String> limitrofes_laRioja = new ArrayList<>();
		limitrofes_laRioja.add("Catamarca");
		limitrofes_laRioja.add("Córdoba");
		limitrofes_laRioja.add("San Luis");
		limitrofes_laRioja.add("San Juan");
		PROVINCIAS[13] = new Provincia("La Rioja", new Coordinate(-29.616979, -67.097551), limitrofes_laRioja);
		
		ArrayList<String> limitrofes_salta = new ArrayList<>();
		limitrofes_salta.add("Jujuy");
		limitrofes_salta.add("Formosa");
		limitrofes_salta.add("Chaco");
		limitrofes_salta.add("Santiago del Estero");
		limitrofes_salta.add("Tucumán");
		limitrofes_salta.add("Catamarca");
		PROVINCIAS[14] = new Provincia("Salta", new Coordinate(-25.267636, -65.103732), limitrofes_salta);
		
		ArrayList<String> limitrofes_jujuy = new ArrayList<>();
		limitrofes_jujuy.add("Salta");
		PROVINCIAS[15] = new Provincia("Jujuy", new Coordinate(-22.987009, -66.063212), limitrofes_jujuy);
		
		ArrayList<String> limitrofes_formosa = new ArrayList<>();
		limitrofes_formosa.add("Salta");
		limitrofes_formosa.add("Chaco");
		PROVINCIAS[16] = new Provincia("Formosa", new Coordinate(-24.713873, -60.208776), limitrofes_formosa);
		
		ArrayList<String> limitrofes_chaco = new ArrayList<>();
		limitrofes_chaco.add("Formosa");
		limitrofes_chaco.add("Corrientes");
		limitrofes_chaco.add("Santa Fé");
		limitrofes_chaco.add("Santiago del Estero");
		limitrofes_chaco.add("Salta");
		PROVINCIAS[17] = new Provincia("Chaco", new Coordinate(-26.636121, -60.421103), limitrofes_chaco);
		
		ArrayList<String> limitrofes_corrientes = new ArrayList<>();
		limitrofes_corrientes.add("Misiones");
		limitrofes_corrientes.add("Entre Ríos");
		limitrofes_corrientes.add("Santa Fé");
		limitrofes_corrientes.add("Chaco");
		PROVINCIAS[18] = new Provincia("Corrientes", new Coordinate(-28.731096, -57.885219), limitrofes_corrientes);
		
		ArrayList<String> limitrofes_misiones = new ArrayList<>();
		limitrofes_misiones.add("Corrientes");
		PROVINCIAS[19] = new Provincia("Misiones", new Coordinate(-27.042709, -54.684721), limitrofes_misiones);
		
		ArrayList<String> limitrofes_tucuman = new ArrayList<>();
		limitrofes_tucuman.add("Salta");
		limitrofes_tucuman.add("Santiago del Estero");
		limitrofes_tucuman.add("Catamarca");
		PROVINCIAS[20] = new Provincia("Tucumán", new Coordinate(-26.933428, -65.388273), limitrofes_tucuman);
		
		ArrayList<String> limitrofes_sanLuis = new ArrayList<>();
		limitrofes_sanLuis.add("La Rioja");
		limitrofes_sanLuis.add("Córdoba");
		limitrofes_sanLuis.add("La Pampa");
		limitrofes_sanLuis.add("Mendoza");
		limitrofes_sanLuis.add("San Juan");
		PROVINCIAS[21] = new Provincia("San Luis", new Coordinate(-33.705925, -66.026088), limitrofes_sanLuis);
		
		ArrayList<String> limitrofes_santiagoDelEstero = new ArrayList<>();
		limitrofes_santiagoDelEstero.add("Chaco");
		limitrofes_santiagoDelEstero.add("Santa Fé");
		limitrofes_santiagoDelEstero.add("Córdoba");
		limitrofes_santiagoDelEstero.add("Catamarca");
		limitrofes_santiagoDelEstero.add("Tucumán");
		limitrofes_santiagoDelEstero.add("Salta");
		PROVINCIAS[22] = new Provincia("Santiago del Estero", new Coordinate(-27.729467, -63.406912), limitrofes_santiagoDelEstero);
	}
}
