package BTree;

class BTree {
    private Node raiz;
    private final int grauMinimo;

    public BTree(int grauMinimo) {
        this.raiz = null;
        this.grauMinimo = grauMinimo;
    }

    public void inserir(int chave) {
        if (raiz == null) {
            raiz = new Node(grauMinimo, true);
            raiz.chaves.add(chave);
            raiz.n = 1;
        } else {
            if (raiz.n == 2 * grauMinimo - 1) {
                Node novaRaiz = new Node(grauMinimo, false);
                novaRaiz.filhos.add(raiz);
                dividirFilho(novaRaiz, 0, raiz);
                int i = 0;
                if (novaRaiz.chaves.get(0) < chave) {
                    i++;
                }
                inserirNaoCheio(novaRaiz.filhos.get(i), chave);
                raiz = novaRaiz;
            } else {
                inserirNaoCheio(raiz, chave);
            }
        }
    }

    private void inserirNaoCheio(Node no, int chave) {
        int i = no.n - 1;

        if (no.folha) {
            while (i >= 0 && chave < no.chaves.get(i)) {
                i--;
            }

            // Incrementar i para corrigir a posição da inserção
            i++;

            no.chaves.add(i, chave);
            no.n = no.n + 1;
        } else {
            while (i >= 0 && chave < no.chaves.get(i)) {
                i--;
            }

            // Incrementar i para corrigir a posição do filho
            i++;

            Node filho = no.filhos.get(i);
            if (filho.n == 2 * grauMinimo - 1) {
                dividirFilho(no, i, filho);
                if (chave > no.chaves.get(i)) {
                    i++;
                }
            }
            inserirNaoCheio(no.filhos.get(i), chave);
        }
    }

    private void dividirFilho(Node pai, int i, Node filho) {
        Node novoFilho = new Node(grauMinimo, filho.folha);
        novoFilho.n = grauMinimo - 1;

        // Transferir as chaves para o novo filho
        for (int j = 0; j < grauMinimo - 1; j++) {
            novoFilho.chaves.add(j, filho.chaves.get(j + grauMinimo));
        }

        // Se não for uma folha, transferir os filhos também
        if (!filho.folha) {
            for (int j = 0; j < grauMinimo; j++) {
                novoFilho.filhos.add(j, filho.filhos.get(j + grauMinimo));
            }
        }

        // Atualizar o número de chaves no filho original
        filho.n = grauMinimo - 1;

        // Inserir o novo filho na posição correta
        pai.filhos.add(i + 1, novoFilho);

        // Mover a chave do meio para o nó pai
        pai.chaves.add(i, filho.chaves.get(grauMinimo - 1));

        // Atualizar o número de chaves no pai
        pai.n = pai.n + 1;
    }

    public void remover(int chave) {
        if (raiz == null) {
            System.out.println("A árvore está vazia");
            return;
        }

        remover(raiz, chave);

        if (raiz.n == 0) {
            if (raiz.folha) {
                raiz = null;
            } else {
                raiz = raiz.filhos.get(0);
            }
        }
    }

    private void remover(Node no, int chave) {
        int index = no.chaves.indexOf(chave);

        if (index != -1) {
            if (no.folha) {
                no.chaves.remove(index);
                no.n--;
            } else {
                Node antecessor = (index < no.filhos.size()) ? no.filhos.get(index) : null;
                Node sucessor = (index + 1 < no.filhos.size()) ? no.filhos.get(index + 1) : null;

                if (antecessor != null && antecessor.n >= grauMinimo) {
                    int antecessorChave = obterAntecessor(antecessor);
                    no.chaves.set(index, antecessorChave);
                    remover(antecessor, antecessorChave);
                } else if (sucessor != null && sucessor.n >= grauMinimo) {
                    int sucessorChave = obterSucessor(sucessor);
                    no.chaves.set(index, sucessorChave);
                    remover(sucessor, sucessorChave);
                } else {
                    assert antecessor != null;
                    assert sucessor != null;
                    fundir(no, index, antecessor, sucessor);
                    remover(antecessor, chave);
                }
            }
        } else {
            int i = 0;
            while (i < no.n && chave > no.chaves.get(i)) {
                i++;
            }

            boolean ultimoFilho = (i == no.n);

            if (i < no.filhos.size() && no.filhos.get(i).n < grauMinimo) {
                preencher(no, i);
            }

            if (ultimoFilho && i < no.n) {
                remover(no.filhos.get(i), chave);
            } else if (i < no.filhos.size()) {
                remover(no.filhos.get(i), chave);
            }
        }
    }

    private int obterAntecessor(Node no) {
        while (!no.folha) {
            no = no.filhos.get(no.n);
        }
        return no.chaves.get(no.n - 1);
    }

    private int obterSucessor(Node no) {
        while (!no.folha) {
            no = no.filhos.get(0);
        }
        return no.chaves.get(0);
    }

    private void fundir(Node pai, int index, Node antecessor, Node sucessor) {
        antecessor.chaves.add(pai.chaves.get(index));
        for (int i = 0; i < sucessor.n; i++) {
            antecessor.chaves.add(sucessor.chaves.get(i));
        }

        // Se não for uma folha, adicionar todos os filhos do sucessor como filhos do antecessor
        if (!antecessor.folha) {
            for (int i = 0; i <= sucessor.n; i++) {
                antecessor.filhos.add(sucessor.filhos.get(i));
            }
        }

        // Remover a chave do pai que foi transferida
        pai.chaves.remove(index);

        // Remover o sucessor
        pai.filhos.remove(index + 1);

        // Atualizar os valores de n
        antecessor.n = antecessor.n + sucessor.n + 1;
        pai.n--;
    }


    private void preencher(Node no, int index) {
        Node filho = no.filhos.get(index);
        Node irmaoEsquerdo = (index == 0) ? null : no.filhos.get(index - 1);
        Node irmaoDireito = (index == no.n) ? null : no.filhos.get(index + 1);

        if (irmaoEsquerdo != null && irmaoEsquerdo.n >= grauMinimo) {
            emprestarDoAnterior(no, index);
        } else if (irmaoDireito != null && irmaoDireito.n >= grauMinimo) {
            emprestarDoProximo(no, index);
        } else {
            if (irmaoDireito != null) {
                fundir(no, index, filho, irmaoDireito);
            } else if (irmaoEsquerdo != null) {
                fundir(no, index - 1, irmaoEsquerdo, filho);
            }
        }
    }

    private void emprestarDoAnterior(Node no, int index) {
        Node filho = no.filhos.get(index);
        Node irmao = no.filhos.get(index - 1);

        filho.chaves.add(0, no.chaves.get(index - 1));

        if (!filho.folha) {
            filho.filhos.add(0, irmao.filhos.get(irmao.n));
        }

        no.chaves.set(index - 1, irmao.chaves.get(irmao.n - 1));

        irmao.chaves.remove(irmao.n - 1);

        if (!filho.folha) {
            irmao.filhos.remove(irmao.n);
        }

        filho.n++;
        irmao.n--;
    }

    private void emprestarDoProximo(Node no, int index) {
        Node filho = no.filhos.get(index);
        Node irmao = no.filhos.get(index + 1);

        filho.chaves.add(no.chaves.get(index));

        if (!filho.folha) {
            filho.filhos.add(irmao.filhos.get(0));
        }

        no.chaves.set(index, irmao.chaves.get(0));

        irmao.chaves.remove(0);

        if (!irmao.folha) {
            irmao.filhos.remove(0);
        }

        filho.n++;
        irmao.n--;
    }

    public void imprimirArvorePorPagina() {
        if (raiz != null) {
            imprimirArvorePorPagina(raiz, 0, 1);
        }
    }

    private void imprimirArvorePorPagina(Node no, int nivel, int pagina) {
        System.out.print("<< ");
        for (int i = 0; i < no.n; i++) {
            System.out.print(no.chaves.get(i) + " ");
        }
        System.out.println(">>");

        if (!no.folha) {
            for (int i = 0; i <= no.n; i++) {
                imprimirArvorePorPagina(no.filhos.get(i), nivel + 1, pagina * (i + 1));
            }
        }
    }
}