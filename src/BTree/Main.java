package BTree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BTree arvore = new BTree(3);
        Scanner n = new Scanner(System.in);
        String[] teste;
        int valor = 0;
        int op;

        System.out.println("""
                Operações:
                1: Inserção
                2: Remoção
                3: Impressão em pré-ordem
                Informe a operação e o valor:""");
        while (true) {
            teste = n.nextLine().split(" ");
            if (Integer.parseInt(teste[0]) == 0 || Integer.parseInt(teste[0]) > 3) {
                System.exit(0);
            }
            op = Integer.parseInt(teste[0]);
            if (op < 3) {
                valor = Integer.parseInt(teste[1]);
            }
            switch (op) {
                case 1 -> arvore.inserir(valor);
                case 2 -> arvore.remover(valor);
                case 3 -> arvore.imprimirArvorePorPagina();
            }
        }
    }
}