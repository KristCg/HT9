package workspace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lector {

    public static String llerArchivoTexto(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        if (contenido.length() > 0) {
            contenido.deleteCharAt(contenido.length() - 1);
        }
        return contenido.toString();
    }

    public static Map<Character, Integer> contarFrecuencias(String texto) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }
        return frecuencias;
    }

    public static Map<Character, Integer> leerFrecuenciasDesdeArchivo(String rutaArchivo) throws IOException {
        String texto = llerArchivoTexto(rutaArchivo);
        return contarFrecuencias(texto);
    }
    
}
