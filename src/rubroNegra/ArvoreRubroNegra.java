package rubroNegra;

public class ArvoreRubroNegra<T extends Comparable<T>> {
    private No<T> raiz;
    private final No<T> TNULL;

    public ArvoreRubroNegra() {
        TNULL = new No<>();
        TNULL.cor = Cor.Negra;
        TNULL.esquerda = null;
        TNULL.direita = null;
        raiz = TNULL;
    }

    private void impressaoPreOrdem(No<T> no) {
        if (no != TNULL) {
            if (no.pai == null){
                System.out.println("[" + no.data + "] -> COR: " + no.cor + " -> PAI: Null");
            }else {
                System.out.println("[" + no.data + "] -> COR: " + no.cor + " -> PAI: " + no.pai.data);
            }
            impressaoPreOrdem(no.esquerda);
            impressaoPreOrdem(no.direita);
        }
    }

    public void impressaopreOrdem() {
        System.out.println(">>>>>>RUBRO-NEGRA<<<<<<");
        impressaoPreOrdem(this.raiz);
        System.out.println();
    }

    private void rotacaoEsquerda(No<T> x) {
        No<T> y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != TNULL) {
            y.esquerda.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            this.raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        y.esquerda = x;
        x.pai = y;
    }

    private void rotacaoDireita(No<T> x) {
        No<T> y = x.esquerda;
        x.esquerda = y.direita;
        if (y.direita != TNULL) {
            y.direita.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            this.raiz = y;
        } else if (x == x.pai.direita) {
            x.pai.direita = y;
        } else {
            x.pai.esquerda = y;
        }
        y.direita = x;
        x.pai = y;
    }

    public void inserir(T chave) {
        No<T> no = new No<>();
        no.pai = null;
        no.data = chave;
        no.esquerda = TNULL;
        no.direita = TNULL;
            no.cor = Cor.Rubro;
        No<T> y = null;
        No<T> x = this.raiz;

        while (x != TNULL) {
            y = x;
            if (no.data.compareTo(x.data) < 0) {
                x = x.esquerda;
            } else {
                x = x.direita;
            }
        }
        no.pai = y;
        if (y == null) {
            raiz = no;
        } else if (no.data.compareTo(y.data) < 0) {
            y.esquerda = no;
        } else {
            y.direita = no;
        }
        if (no.pai == null) {
            no.cor = Cor.Negra;
            return;
        }
        if (no.pai.pai == null) {
            return;
        }
        corrigirInsercao(no);
    }

    private void corrigirInsercao(No<T> k) {
        No<T> tio;
        while (k.pai.cor == Cor.Rubro) {
            if (k.pai == k.pai.pai.direita) {
                tio = k.pai.pai.esquerda;
                if (tio.cor == Cor.Rubro) {
                    tio.cor = Cor.Negra;
                    k.pai.cor = Cor.Negra;
                    k.pai.pai.cor = Cor.Rubro;
                    k = k.pai.pai;
                } else {
                    if (k == k.pai.esquerda) {
                        k = k.pai;
                        rotacaoDireita(k);
                    }
                    k.pai.cor = Cor.Negra;
                    k.pai.pai.cor = Cor.Rubro;
                    rotacaoEsquerda(k.pai.pai);
                }
            } else {
                tio = k.pai.pai.direita;
                if (tio.cor == Cor.Rubro) {
                    tio.cor = Cor.Negra;
                    k.pai.cor = Cor.Negra;
                    k.pai.pai.cor = Cor.Rubro;
                    k = k.pai.pai;
                } else {
                    if (k == k.pai.direita) {
                        k = k.pai;
                        rotacaoEsquerda(k);
                    }
                    k.pai.cor = Cor.Negra;
                    k.pai.pai.cor = Cor.Rubro;
                    rotacaoDireita(k.pai.pai);
                }
            }
            if (k == raiz) {
                break;
            }
        }
        raiz.cor = Cor.Negra;
    }
    private No<T> procurar(No<T> no, T chave) {
        if (no == null || chave.equals(no.data)) {
            return no;
        }

        if (chave.compareTo(no.data) < 0) {
            return procurar(no.esquerda, chave);
        }

        return procurar(no.direita, chave);
    }
    public void remover(T chave) {
        No<T> z = procurar(raiz, chave);
        if (z == null) {
            return;
        }

        No<T> y = z;
        Cor corOriginalY = y.cor;
        No<T> x;

        if (z.esquerda == TNULL) {
            x = z.direita;
            transplantar(z, z.direita);
        } else if (z.direita == TNULL) {
            x = z.esquerda;
            transplantar(z, z.esquerda);
        } else {
            y = maximo(z.esquerda); // Encontre o nó com o maior valor na subárvore esquerda
            corOriginalY = y.cor;
            x = y.esquerda;
            if (y.pai == z) {
                x.pai = y;
            } else {
                transplantar(y, y.esquerda);
                y.esquerda = z.esquerda;
                y.esquerda.pai = y;
            }
            transplantar(z, y);
            y.direita = z.direita;
            y.direita.pai = y;
            y.cor = z.cor;
        }

        if (corOriginalY == Cor.Negra) {
            corrigirRemocao(x);
        }
    }
    private void transplantar(No<T> u, No<T> v) {
        if (u.pai == null) {
            raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }
        v.pai = u.pai;
    }
    private void corrigirRemocao(No<T> x) {
        while (x != raiz && x.cor == Cor.Negra) {
            if (x == x.pai.esquerda) {
                No<T> w = x.pai.direita;
                if (w.cor == Cor.Rubro) {
                    w.cor = Cor.Negra;
                    x.pai.cor = Cor.Rubro;
                    rotacaoEsquerda(x.pai);
                    w = x.pai.direita;
                }
                if (w.esquerda.cor == Cor.Negra && w.direita.cor == Cor.Negra) {
                    w.cor = Cor.Rubro;
                    x = x.pai;
                } else {
                    if (w.direita.cor == Cor.Negra) {
                        w.esquerda.cor = Cor.Negra;
                        w.cor = Cor.Rubro;
                        rotacaoDireita(w);
                        w = x.pai.direita;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = Cor.Negra;
                    w.direita.cor = Cor.Negra;
                    rotacaoEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                No<T> w = x.pai.esquerda;
                if (w.cor == Cor.Rubro) {
                    w.cor = Cor.Negra;
                    x.pai.cor = Cor.Rubro;
                    rotacaoDireita(x.pai);
                    w = x.pai.esquerda;
                }
                if (w.direita.cor == Cor.Negra && w.esquerda.cor == Cor.Negra) {
                    w.cor = Cor.Rubro;
                    x = x.pai;
                } else {
                    if (w.esquerda.cor == Cor.Negra) {
                        w.direita.cor = Cor.Negra;
                        w.cor = Cor.Rubro;
                        rotacaoEsquerda(w);
                        w = x.pai.esquerda;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = Cor.Negra;
                    w.esquerda.cor = Cor.Negra;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = Cor.Negra;
    }
    private No<T> maximo(No<T> no) {
        while (no.direita != TNULL) {
            no = no.direita;
        }
        return no;
    }
}