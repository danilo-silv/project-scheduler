package projetoescalonador;

/**
 *
 * @author Danilo, Guilherme, Giovanni, Gustavo e Victor
 */
public class Lista {

    public No inicio;
    public No fim;
    public int quantidade;
    Lista processosAuxiliar;

    public Lista() {
        this.inicio = this.fim = null;
        this.quantidade = 0;
    }

    public Lista(Lista auxiliar) {
        this.inicio = this.fim = null;
        this.quantidade = 0;
        this.processosAuxiliar = auxiliar;
    }

    public Processo escolherExecutar(Lista lista) {
        No auxiliar = lista.inicio;
        Lista listaPrioridade = new Lista();
        listaPrioridade.add(auxiliar.processo);

        while (auxiliar.proximo != null) {
            if (auxiliar.processo.prioridade < auxiliar.proximo.processo.prioridade) {
                listaPrioridade.inicio = listaPrioridade.fim = null; //limpa a lista
                listaPrioridade.add(auxiliar.processo);
            }

            if (auxiliar.processo.prioridade == auxiliar.proximo.processo.prioridade) {
                listaPrioridade.add(auxiliar.proximo.processo);
            }

            auxiliar = auxiliar.proximo;
        }

        return auxiliar.processo;
    }

    public int size() {
        return quantidade;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(Processo novoProc, int posicao) {
        if (posicao > size() || posicao < 0) {
            return false;
        }
        No novoNo = new No(novoProc);
        if (posicao == 0 && isEmpty()) {
            this.inicio = fim = novoNo;
        } else {
            if (posicao == 0) {
                novoNo.proximo = this.inicio;
                this.inicio.proximo = novoNo;
            } else {
                if (posicao == size()) {
                    this.fim.proximo = novoNo;
                    this.fim = novoNo;
                } else {
                    int contador = 0;
                    No temporario = this.inicio;
                    while (contador < posicao - 1) {
                        temporario = temporario.proximo;
                        contador++;
                    }
                    novoNo.proximo = temporario.proximo;
                    temporario.proximo = novoNo;
                }
            }
        }
        quantidade++;
        return true;
    }

    public void add(Processo novoProc) {
        No novo = new No(novoProc);
        if (isEmpty()) {
            inicio = fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        quantidade++;
    }

    public Processo remove(int posicao) {
        if (isEmpty() || posicao > size() || posicao < 0) {
            new Error("Inválido");
        } else {
            Processo temporario;

            if (posicao == 0 && size() == 1) {
                temporario = inicio.processo;
                inicio = fim = null;
                quantidade--;
                return temporario;
            } else {
                if (posicao == 0) {
                    temporario = inicio.processo;
                    inicio = inicio.proximo;
                } else {
                    int contador = 0;
                    No auxiliar = inicio;
                    while (contador < posicao - 1) {
                        contador++;
                        auxiliar = auxiliar.proximo;
                    }

                    No removido = auxiliar.proximo;
                    temporario = removido.processo;
                    auxiliar.proximo = removido.proximo;
                }

                quantidade--;
                return temporario;
            }
        }

        return null;
    }

    public Processo get(int posicao) {
        if (posicao < size() && !isEmpty() && posicao >= 0) {
            int contador = 0;
            No auxiliar = inicio;
            while (contador < posicao) {
                contador++;
                auxiliar = auxiliar.proximo;
            }

            return auxiliar.processo;
        }
        return null;
    }

    public Processo getFirst() {
        return inicio.processo;
    }

    public void addAuxiliar(Processo processo) {
        processosAuxiliar.add(processo);

    }

}
