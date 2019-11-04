package projetoescalonador;

/**
 *
 * @author Danilo, Guilherme e Gustavo
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
        No novoNo = new No(novoProc);
        if (isEmpty()) {
            this.inicio = this.fim = novoNo;
        } else {
            this.fim.proximo = novoNo;
            this.fim = novoNo;
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

    public void roundRobin(int quantum) {
        int tempo = processosAuxiliar.getFirst().chegada;
        int contadorMedias = 0;

    }

    public void addAuxiliar(Processo processo) {
        No temp = processosAuxiliar.inicio;
        if (temp == null) {
            processosAuxiliar.add(processo);
        } else {
            while (temp != null) {
                processosAuxiliar.add(processo);
                temp = temp.proximo;

            }
        }

    }

    public boolean verificaIO(int tempoAtual, Processo processo) {
        for (int i = 0; i < processo.io.length; i++) {
            if (processo.io[i] == tempoAtual) {
                return true;
            }
        }
        return false;
    }

    public boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                return true;
            }
        }
        return false;
    }

    public String imprimir() {
        StringBuilder valores = new StringBuilder();
        valores.append('(');
        No auxiliar = inicio;
        while (auxiliar != null) {
            valores.append(auxiliar.processo.id);
            if (auxiliar != fim) {
                valores.append(", ");
            }
            auxiliar = auxiliar.proximo;

        }
        valores.append(")");
        return valores.toString();
    }

}
