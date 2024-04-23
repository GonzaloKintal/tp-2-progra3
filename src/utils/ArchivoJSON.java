package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logica.Provincia;

public class ArchivoJSON {
	
	public static Provincia[] leerJSON(String archivo) {
		Gson gson = new Gson();
		Provincia[] ret = null;
		
		try {
			File directoryPath = new File("src/data/" + archivo);
			BufferedReader reader = new BufferedReader(new FileReader(directoryPath));
			Type tipoListaArchivoJSON = new TypeToken<Provincia[]>(){}.getType();
			ret = gson.fromJson(reader, tipoListaArchivoJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}