package avl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner n = new Scanner(System.in);
        Avl<Integer> avl = new Avl<>();
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
                case 1 -> avl.insercao(valor);
                case 2 -> avl.remocao(valor);
                case 3 -> avl.impressaoPreOrdem();
            }
        }
    }
}
