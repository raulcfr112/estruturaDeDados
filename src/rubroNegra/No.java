package rubroNegra;

public class No<T extends Comparable<T>> {
    T data;
    No<T> pai;
    No<T> esquerda;
    No<T> direita;
    Cor cor;
}
