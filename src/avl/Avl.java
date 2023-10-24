package avl;

public class Avl<T extends Comparable<T>> {
    protected No<T> raiz;

    void insercao(T valor) {
        raiz = insercao(raiz, valor);
    }

    private No<T> insercao(No<T> no, T valor) {
        if (no == null) return new No<>(valor);
        if (valor.compareTo(no.info) > 0) {
            no.direito = insercao(no.direito, valor);
        } else if (valor.compareTo(no.info) < 0) {
            no.esquerdo = insercao(no.esquerdo, valor);
        } else return no;
        return balancear(no, valor);
    }

    void remocao(T valor) {
        raiz = remocao(valor, raiz);
    }

    private No<T> remocao(T valor, No<T> no) {
        if (no == null) return null;
        if (valor.compareTo(no.info) < 0) {
            no.esquerdo = remocao(valor, no.esquerdo);
        } else if (valor.compareTo(no.info) > 0) {
            no.direito = remocao(valor, no.direito);
        } else {
            if (no.esquerdo == null) return no.direito;
            else if (no.direito == null) {
                return no.esquerdo;
            }
            No<T> sucessor = buscarMaior(no.esquerdo);
            no.info = sucessor.info;
            no.esquerdo = remocao(sucessor.info, no.esquerdo);
        }
        return balancearRemocao(no);
    }


    private No<T> rotacionaEsquerda(No<T> y) {
        No<T> x = y.direito;
        No<T> T2 = x.esquerdo;
        x.esquerdo = y;
        y.direito = T2;

        novaAltura(y);
        novaAltura(x);

        return x;
    }

    private No<T> rotacionaDireita(No<T> y) {
        No<T> x = y.esquerdo;
        No<T> T2 = x.direito;

        x.direito = y;
        y.esquerdo = T2;

        novaAltura(y);
        novaAltura(x);

        return x;
    }

    private int fatorDeBalanceamento(No<T> no) {
        if (no == null) {
            return 0;
        }
        return getAltura(no.esquerdo) - getAltura(no.direito);
    }

    private No<T> balancear(No<T> no, T valor) {
        novaAltura(no);
        int bal = fatorDeBalanceamento(no);
        if (bal > 1) {
            if (valor.compareTo(no.esquerdo.info) >= 0) {
                no.esquerdo = rotacionaEsquerda(no.esquerdo);
            }
            return rotacionaDireita(no);
        }
        if (bal < -1) {
            if (valor.compareTo(no.direito.info) <= 0) {
                no.direito = rotacionaDireita(no.direito);
            }
            return rotacionaEsquerda(no);
        }
        return no;
    }

    private No<T> balancearRemocao(No<T> no) {
        novaAltura(no);
        int bal = fatorDeBalanceamento(no);
        if (bal > 1) {
            if (fatorDeBalanceamento(no.esquerdo) < 0) {
                no.esquerdo = rotacionaEsquerda(no.esquerdo);
            }
            return rotacionaDireita(no);
        }
        if (bal < -1) {
            if (fatorDeBalanceamento(no.direito) > 0) {
                no.direito = rotacionaDireita(no.direito);
            }
            return rotacionaEsquerda(no);
        }
        return no;
    }

    private void novaAltura(No<T> no) {
        if (no != null) {
            no.altura = Math.max(getAltura(no.esquerdo), getAltura(no.direito)) + 1;
        }
    }


    private No<T> buscarMaior(No<T> no) {
        while (no.direito != null) {
            no = no.direito;
        }
        return no;
    }

    void impressaoPreOrdem() {
        System.out.println(">>>>>>AVL<<<<<<");
        impressaoPreOrdem(raiz);
    }

    private void impressaoPreOrdem(No<T> no) {
        if (no != null) {
            if (no.info != null) {
                System.out.println(no.info + " -> altura:" + no.altura);
            }
            impressaoPreOrdem(no.esquerdo);
            impressaoPreOrdem(no.direito);
        }
    }

    private int getAltura(No<T> no) {
        if (no == null) return 0;
        return no.altura;
    }
}
