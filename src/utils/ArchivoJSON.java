package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ArchivoJSON {
	
	public static <T> T leerArchivo(String archivo, Class<T> clase) {
        Gson gson = new Gson();
        T datos = null;

        try {
            File archivoJSON = new File("src/data/" + archivo);
            BufferedReader reader = new BufferedReader(new FileReader(archivoJSON));
            Type tipoDatos = TypeToken.getParameterized(clase).getType();
            datos = gson.fromJson(reader, tipoDatos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datos;
    }
}