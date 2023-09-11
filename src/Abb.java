import java.util.Scanner;

public class Abb<T extends Comparable<T>> {
    protected Node<T> raiz = new Node<>(null);

    boolean insercao(T valor) {
        Node<T> no = raiz;
        while (no.info != null) {
            if (no.info.compareTo(valor) < 0) {
                no = no.direito;
            } else if (no.info.compareTo(valor) > 0) {
                no = no.esquerdo;
            } else {
                return false;
            }
        }
        no.info = valor;
        no.esquerdo = new Node<>(null);
        no.direito = new Node<>(null);
        return true;
    }

    T busca(T valor) {
        Node<T> no = raiz;
        while (no != null) {
            if (valor.compareTo(no.info) > 0) {
                no = no.direito;
            } else if (valor.compareTo(no.info) < 0) {
                no = no.esquerdo;
            } else {
                return no.info;
            }
        }
        return null;
    }

    Object removerMaiorEsq(Node<T> no) {
        no = no.esquerdo;
        while (no.direito.info != null) {
            no = no.direito;
        }
        Object aux = no.info;
        no.info = no.esquerdo.info;
        no.direito = no.esquerdo.direito;
        no.esquerdo = no.esquerdo.esquerdo;
        return aux;
    }

    Object remocao(T valor) {
        Node<T> no = raiz;
        Object aux = null;
        while (no.info != null) {
            if (valor.compareTo(no.info) > 0) {
                no = no.direito;
            } else if (valor.compareTo(no.info) < 0) {
                no = no.esquerdo;
            } else {
                aux = no.info;
                if (no.direito.info != null && no.esquerdo.info != null) {
                    no.info = (T) removerMaiorEsq(no);
                } else if (no.direito.info == null) {
                    no.info = no.esquerdo.info;
                    no.direito = no.esquerdo.direito;
                    no.esquerdo = no.esquerdo.esquerdo;
                } else {
                    no.info = no.direito.info;
                    no.esquerdo = no.direito.esquerdo;
                    no.direito = no.direito.direito;
                }
            }
        }
        return aux;
    }

    void impressao(Node<T> raiz) {
        if (raiz != null) {
            if (raiz.info != null) {
                System.out.print(raiz.info + " ");
            }
            impressao(raiz.esquerdo);
            impressao(raiz.direito);
        }
    }

    public static void main(String[] args) {
        Scanner n = new Scanner(System.in);
        Abb<Integer> abb = new Abb<>();
        String[] teste;
        int valor = 0;
        int op = 0;

        System.out.println("Operações:\n" +
                "1: Inserção\n" +
                "2: Remoção\n" +
                "3: Impressão em pré-ordem");
        while (true) {
            System.out.println("\nInforme a operação e o valor:");
            teste = n.nextLine().split(" ");
            if (Integer.parseInt(teste[0]) == 0 || Integer.parseInt(teste[0]) > 3) {
                System.exit(0);
            }
            op = Integer.parseInt(teste[0]);
            if (op < 3) {
                valor = Integer.parseInt(teste[1]);
            }
            switch (op) {
                case 1:
                    abb.insercao(valor);
                    break;
                case 2:
                    abb.remocao(valor);
                    break;
                case 3:
                    abb.impressao(abb.raiz);
                    break;
            }
        }
    }
}