public class Node<T> {
    T info;
    Node<T> direito;
    Node<T> esquerdo;

    public Node(T info) {
        this.info = info;
        this.direito = null;
        this.esquerdo = null;
    }
}
