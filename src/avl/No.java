package avl;

public class No<T extends Comparable<T>> {
    T info;
    Integer altura;
    No<T> direito, esquerdo;

    public No(T info) {
        this.info = info;
        this.altura = 1;
        this.direito = null;
        this.esquerdo = null;
    }
}
