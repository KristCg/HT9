package workspace;

public class NodoHuffman implements Comparable<NodoHuffman> {
    private char caracter;
    private int frecuencia;
    private NodoHuffman izquierda, derecha;

    // Getters y Setters 
    public char getCaracter() {
        return caracter;
    }
    public int getFrecuencia() {
        return frecuencia;
    }
    public NodoHuffman getIzquierda() {
        return izquierda;
    }
    public NodoHuffman getDerecha() {
        return derecha;
    }
    public void setIzquierda(NodoHuffman izquierda) {
        this.izquierda = izquierda;
    }
    public void setDerecha(NodoHuffman derecha) {
        this.derecha = derecha;
    }
    

    // Constructor para los nodos que seran de tipo hoja 
    public NodoHuffman(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.izquierda = null;
        this.derecha = null;
    }

    // Constructor para los nodos que seran de tipo interno
    public NodoHuffman(NodoHuffman izquierda, NodoHuffman derecha) {
        this.caracter = '\0'; 
        this.frecuencia = izquierda.frecuencia + derecha.frecuencia;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    //Metodos
    public boolean esHoja() {
        return izquierda == null && derecha == null;
    }
    @Override
    public int compareTo(NodoHuffman otro) {
        return Integer.compare(this.frecuencia, otro.frecuencia);
    }

    @Override
    public String toString(){
        if(esHoja()){
            return "Joja ['" + caracter + "', " + frecuencia + "]";
        } else{
            return "Nodo [" + frecuencia + "]";
        }
    }
}
