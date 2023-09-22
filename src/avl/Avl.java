package avl;

public class Avl<T extends Comparable<T>> {
    protected No<T> raiz = new No<>(null);

    private int getAltura(No<T> no){
        if (no == null) return 0;
        return no.altura;
    }

    private int getBal(No<T> no){
        if (no == null) return 0;
        return no.altura;
    }

    void balancear(No<T> no, int bal){
        if (bal == 2){
            if (no.direito.esquerdo.altura > no.direito.direito.altura){
                rotacionaDireita(no.direito);
            }rotacionaEsquerda(no);
        }else {
            if (no.esquerdo.direito.altura > no.esquerdo.esquerdo.altura){
                rotacionaEsquerda(no.esquerdo);
            }rotacionaDireita(no);
        }
    }

    void rotacionaEsquerda(No<T> no) {
        No<T> filho = no.direito;
        No<T> neto = filho.esquerdo;

        T aux = no.info;
        no.info = filho.info;
        filho.info = aux;

        no.direito = filho.direito;
        filho.direito = neto;
        filho.esquerdo = no.esquerdo;
        no.esquerdo = filho;

        no.altura = Math.max(getAltura(no.esquerdo), getAltura(no.direito)) + 1;
        filho.altura = Math.max(getAltura(filho.esquerdo), getAltura(filho.direito)) + 1;
    }

    void rotacionaDireita(No<T> no) {
        No<T> filho = no.esquerdo;
        No<T> neto = filho.direito;

        T aux = no.info;
        no.info = filho.info;
        filho.info = aux;

        no.esquerdo = filho.esquerdo;
        filho.esquerdo = neto;
        filho.direito = no.direito;
        no.direito = filho;

        no.altura = Math.max(getAltura(no.esquerdo), getAltura(no.direito)) + 1;
        filho.altura = Math.max(getAltura(filho.esquerdo), getAltura(filho.direito)) + 1;
    }

    void insercao(T valor){
        insercao(valor, raiz);
    }

    private boolean insercao(T valor, No<T> no){
        boolean ret = true;
        int bal;
        if (no.info == null){
            new No<>(valor);
        }else {
            if (valor.compareTo(no.info) > 0){
                ret = insercao(valor, no.direito);
            } else if (valor.compareTo(no.info) < 0) {
                ret = insercao(valor, no.esquerdo);
            }else ret = false;
            if (ret){
                no.altura = 1 + Math.max(getAltura(no.esquerdo), getAltura(no.direito));
                bal = getBal(no);
                if (bal != 1){
                    balancear(no, bal);
                }
            }
        }
        return ret;
    }
    void remocao(T valor){
        remocao(valor,raiz);
    }

    private No<T> remocao(T valor, No<T> no) {
        No<T> ret = null;
        if (no.info != null) {
            if (valor.compareTo(no.info) > 0) ret = remocao(valor, no.direito);
            else if (valor.compareTo(no.info) < 0) ret = remocao(valor, no.esquerdo);
            else {
                ret = no;
                if (no.direito != null && no.esquerdo != null) {
                    T maiorDaEsquerda = retorneMaiorEsq(no);
                    remocao(maiorDaEsquerda, no);
                    no.info = maiorDaEsquerda;
                } else if (no.esquerdo != null) {
                    no = no.esquerdo;
                } else {
                    no = no.direito;
                }
                return ret;
            }
            if (ret != null) {
                no.altura = 1 + Math.max(getAltura(no.esquerdo), getAltura(no.direito));
                int bal = getBal(no);
                if (bal != 1) balancear(no, bal);
            }
        }
        return ret;
    }
    private T retorneMaiorEsq(No<T> no){
        if (no == null) return null;
        while (no.direito != null) no = no.direito;
        return no.info;
    }
    void impressaoPreOrdem(){
        impressaoPreOrdem(raiz);
    }
    private void impressaoPreOrdem(No<T> no){
        if (no != null) {
            if (no.info != null) {
                System.out.print(no.info + " ");
            }
            impressaoPreOrdem(no.esquerdo);
            impressaoPreOrdem(no.direito);
        }
    }

}
