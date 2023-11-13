package BTree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int n; // Número de chaves no nó
    List<Integer> chaves; // Lista para armazenar as chaves
    List<Node> filhos; // Lista para armazenar os nós filhos
    boolean folha; // Se o nó é uma folha

    Node(int grauMinimo, boolean folha) {
        this.n = 0;
        this.folha = folha;
        this.chaves = new ArrayList<>(2 * grauMinimo - 1);
        this.filhos = new ArrayList<>(2 * grauMinimo);
    }
}

