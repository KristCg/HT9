package workspace;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    
    private static final String ARCHIVO_ORIGINAL = "texto.txt";
    private static final String ARCHIVO_COMPRIMIDO = "texto.huff";
    private static final String ARBOL_HUFFMAN = "texto.hufftree";
    private static final String ARCHIVO_DESCOMPRIMIDO = "texto_descomprimido.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Compresor Huffman");
        System.out.println("1. Comprimir archivo ");
        System.out.println("2. Descomprimir archivo");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        
        try {
            switch (opcion) {
                case 1:
                    System.out.println("Comprimiendo " + ARCHIVO_ORIGINAL + "...");
                    Comprimir.comprimirArchivo(ARCHIVO_ORIGINAL, ARCHIVO_COMPRIMIDO, ARBOL_HUFFMAN);
                    System.out.println("Compresión completada. Archivos creados:");
                    System.out.println("- " + ARCHIVO_COMPRIMIDO);
                    System.out.println("- " + ARBOL_HUFFMAN);
                    break;
                    
                case 2:
                    System.out.println("Descomprimiendo " + ARCHIVO_COMPRIMIDO + "...");
                    Descomprimir.descomprimirArchivo(ARCHIVO_COMPRIMIDO, ARBOL_HUFFMAN, ARCHIVO_DESCOMPRIMIDO);
                    System.out.println("Descompresión completada. Archivo creado:");
                    System.out.println("- " + ARCHIVO_DESCOMPRIMIDO);
                    break;
                    
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Archivo no encontrado.");
        } finally {
            scanner.close();
        }
    }
}
