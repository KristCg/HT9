package workspace;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Descomprimir {
    
    public static NodoHuffman reconstruirArbol(String datosArbol) {
        if (datosArbol == null || datosArbol.isEmpty()) {
            return null;
        }
        
        int[] index = {0};
        return reconstruirArbolHelper(datosArbol, index);
    }
    
    private static NodoHuffman reconstruirArbolHelper(String datosArbol, int[] index) {
        if (index[0] >= datosArbol.length()) {
            return null;
        }
        
        char tipoNodo = datosArbol.charAt(index[0]++);
        
        if (tipoNodo == '1') {
            char caracter = datosArbol.charAt(index[0]++);
            return new NodoHuffman(caracter, 0); 
        } else {
            NodoHuffman izquierda = reconstruirArbolHelper(datosArbol, index);
            NodoHuffman derecha = reconstruirArbolHelper(datosArbol, index);
            return new NodoHuffman(izquierda, derecha);
        }
    }
    
    private static String leerArchivoArbol(String rutaArchivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             DataInputStream dis = new DataInputStream(fis)) {
            return dis.readUTF();
        }
    }
    
    private static byte[] leerArchivoComprimido(String rutaArchivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            return bis.readAllBytes();
        }
    }
    
    private static String bytesABits(byte[] bytes) {
        StringBuilder bits = new StringBuilder();
        
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                bits.append((b >> i) & 1);
            }
        }
        
        return bits.toString();
    }
    
    
    public static String decodificarTexto(String bits, NodoHuffman arbol) {
        StringBuilder textoDecodificado = new StringBuilder();
        NodoHuffman nodoActual = arbol;
        
        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);
            
            if (bit == '0') {
                nodoActual = nodoActual.getIzquierda();
            } else {
                nodoActual = nodoActual.getDerecha();
            }
            
            if (nodoActual.esHoja()) {
                textoDecodificado.append(nodoActual.getCaracter());
                nodoActual = arbol; 
            }
        }
        
        return textoDecodificado.toString();
    }
    
   
    public static void descomprimirArchivo(String rutaHuff, String rutaHuffTree, String rutaSalida) throws IOException {
        
        String datosArbol = leerArchivoArbol(rutaHuffTree);
        NodoHuffman arbol = reconstruirArbol(datosArbol);
        
        
        byte[] bytesComprimidos = leerArchivoComprimido(rutaHuff);
        String bits = bytesABits(bytesComprimidos);
        String textoDecodificado = decodificarTexto(bits, arbol);
        
        
        try (FileOutputStream fos = new FileOutputStream(rutaSalida)) {
            fos.write(textoDecodificado.getBytes());
        }
    }
}