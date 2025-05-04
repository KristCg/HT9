package workspace;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Comprimir {
    
    // Metodo que construye el árbol de Huffman a partir de las frecuencias
    public static NodoHuffman construirArbolHuffman(Map<Character, Integer> frecuencias) {
        PriorityQueue<NodoHuffman> colaPrioridad = new PriorityQueue<>();
        
        // Crear nodos hoja para cada carácter y añadirlos a la cola de prioridad
        for (Map.Entry<Character, Integer> entry : frecuencias.entrySet()) {
            colaPrioridad.add(new NodoHuffman(entry.getKey(), entry.getValue()));
        }
        
        // Construir el árbol combinando los nodos con menor frecuencia
        while (colaPrioridad.size() > 1) {
            NodoHuffman izquierda = colaPrioridad.poll();
            NodoHuffman derecha = colaPrioridad.poll();
            
            NodoHuffman nodoPadre = new NodoHuffman(izquierda, derecha);
            colaPrioridad.add(nodoPadre);
        }
        
        return colaPrioridad.poll();
    }
    
    // Metodo que va a generar los códigos Huffman para cada carácter recursivamente
    public static void generarCodigos(NodoHuffman nodo, String codigo, Map<Character, String> codigos) {
        if (nodo == null) return;
        
        if (nodo.esHoja()) {
            codigos.put(nodo.getCaracter(), codigo.isEmpty() ? "0" : codigo);
        }
        
        generarCodigos(nodo.getIzquierda(), codigo + "0", codigos);
        generarCodigos(nodo.getDerecha(), codigo + "1", codigos);
    }
    
    // metodo que codifica el texto usando los códigos Huffman
    public static String codificarTexto(String texto, Map<Character, String> codigos) {
        StringBuilder textoCodificado = new StringBuilder();
        
        for (char c : texto.toCharArray()) {
            textoCodificado.append(codigos.get(c));
        }
        
        return textoCodificado.toString();
    }
    
    // Metdo que convierte una cadena de bits a un array de bytes
    private static byte[] bitsABytes(String bits) {
        int length = bits.length();
        int byteLength = (length + 7) / 8;
        byte[] bytes = new byte[byteLength];
        
        for (int i = 0; i < length; i++) {
            if (bits.charAt(i) == '1') {
                bytes[i / 8] |= 1 << (7 - i % 8);
            }
        }
        
        return bytes;
    }
    
    // Metodo que serializar(convertir a secuencia de bits) el árbol de Huffman
    private static void serializarArbol(NodoHuffman nodo, StringBuilder sb) {
        if (nodo == null) return;
        
        if (nodo.esHoja()) {
            sb.append('1').append(nodo.getCaracter());
        } else {
            sb.append('0');
            serializarArbol(nodo.getIzquierda(), sb);
            serializarArbol(nodo.getDerecha(), sb);
        }
    }
    
    // Metodo que va a guardar el árbol de Huffman en un archivo
    private static void guardarArbol(NodoHuffman arbol, String rutaArchivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        serializarArbol(arbol, sb);
        
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo);
            DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeUTF(sb.toString());
        }
    }
    
    // Metodo que va a guardar el texto codificado en un archivo binario
    private static void guardarTextoComprimido(String textoCodificado, String rutaArchivo) throws IOException {
        byte[] bytes = bitsABytes(textoCodificado);
        
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo);
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(bytes);
        }
    }
    
    // Método principal que va a comprimir el archivo
    public static void comprimirArchivo(String rutaEntrada, String rutaSalidaHuff, String rutaSalidaTree) throws IOException {
        Map<Character, Integer> frecuencias = Lector.leerFrecuenciasDesdeArchivo(rutaEntrada);
        NodoHuffman arbol = construirArbolHuffman(frecuencias);
        Map<Character, String> codigos = new HashMap<>();
        generarCodigos(arbol, "", codigos);
        String textoOriginal = Lector.llerArchivoTexto(rutaEntrada);
        String textoCodificado = codificarTexto(textoOriginal, codigos);
        guardarArbol(arbol, rutaSalidaTree);
        guardarTextoComprimido(textoCodificado, rutaSalidaHuff);
    }
}