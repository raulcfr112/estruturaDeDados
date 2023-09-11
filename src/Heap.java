import java.util.Arrays;
import java.util.Scanner;

public class Heap {
    private int[] dados;
    private int tamanhoHeap;

    public Heap() {
        this.dados = new int[8];
        this.tamanhoHeap = 0;
    }

    void inserirElemento(int elem) {

        int posAtual = tamanhoHeap;
        dados[posAtual] = elem;
        tamanhoHeap++;

        while (posAtual > 0) {
            int posFilho = (posAtual - 1) / 2;
            if (dados[posAtual] > dados[posFilho]) {
                int aux = dados[posAtual];
                dados[posAtual] = dados[posFilho];
                dados[posFilho] = aux;

                posAtual = posFilho;
            } else {
                break;
            }
        }
    }

    void removerElemento() {

        dados[0] = dados[tamanhoHeap - 1];
        tamanhoHeap--;

        int posAtual = 0;
        while (true) {
            int filhoEsquerda = 2 * posAtual + 1;
            int filhoDireita = 2 * posAtual + 2;

            if (filhoEsquerda >= tamanhoHeap) {
                dados[tamanhoHeap] = 0;
                break; //acabou os filhos
            }

            int menorFilho = filhoEsquerda;
            if (filhoDireita < tamanhoHeap && dados[filhoDireita] > dados[filhoEsquerda]) {
                menorFilho = filhoDireita;
            }

            if (dados[posAtual] < dados[menorFilho]) {
                int aux = dados[posAtual];
                dados[posAtual] = dados[menorFilho];
                dados[menorFilho] = aux;

                posAtual = menorFilho;
            } else {
                dados[tamanhoHeap] = 0;
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner n = new Scanner(System.in);
        Heap heap = new Heap();
        while (heap.tamanhoHeap < 4) {
            int elem = n.nextInt();
            heap.inserirElemento(elem);
            System.out.println(Arrays.toString(heap.dados));
        }
        heap.removerElemento();
        System.out.println(Arrays.toString(heap.dados));
    }
}
