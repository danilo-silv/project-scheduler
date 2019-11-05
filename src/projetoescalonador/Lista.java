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
    Lista processosExecutados;

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

    public void roundRobin(int quantum) {
        Processo execucao;
        int tempo = processosAuxiliar.getFirst().chegada;
        double espera[] = new double[processosAuxiliar.size()];
        double turnaround[] = new double[processosAuxiliar.size()];
        int contadorMedias = 0;

        while (!processosAuxiliar.isEmpty() && processosAuxiliar.getFirst().chegada == tempo) {
            this.add(processosAuxiliar.remove(0));
        }

        while (!(this.isEmpty() && processosAuxiliar.isEmpty())) {
            execucao = this.remove(0);
            execucao.inicio = tempo;

            for (int i = 0; i < quantum; i++) {
                if (execucao.duracao > 0) {
                    execucao.duracao--;

                    while (!processosAuxiliar.isEmpty() && processosAuxiliar.getFirst().chegada == tempo) {
                        this.add(processosAuxiliar.remove(0));
                    }
                    tempo++;
                    if (iO(tempo, execucao)) {
                        i = quantum;
                    }
                }
            }
            if (execucao.duracao != 0) {
                this.add(execucao);
            } else {

                tempo++;
                execucao.turnaround = tempo - execucao.inicio;
                execucao.espera = execucao.turnaround - execucao.duracaoTotal;
                espera[contadorMedias] = execucao.espera;
                turnaround[contadorMedias] = execucao.turnaround;
                contadorMedias++;
                tempo--;
            }
            System.out.println("\n---------------STATUS--------------");
            System.out.println("Processo executado: " + execucao.id);
            System.out.println("Duração restante: " + execucao.duracao);
            System.out.println("Tempo de execução: " + tempo);
            System.out.println("---------------------------------------");
        }

        System.out.println("\nTempo de Espera     Turnaround");
        double mediaEspera = 0;
        double mediaTurnaround = 0;
        for (int i = 0; i < espera.length; i++) {
            System.out.println(espera[i] + "     " + turnaround[i]);
            mediaEspera += espera[i];
            mediaTurnaround += turnaround[i];
        }
        mediaEspera = mediaEspera / espera.length;
        mediaTurnaround = mediaTurnaround / espera.length;

        System.out.println("Media - Espera -> " + mediaEspera);
        System.out.println("Media - Turnaround -> " + mediaTurnaround);

    }

    public void imprimirListaExecutada() {
        No temp = this.inicio;
        while (temp != null) {
            temp.processo.imprimir();
            temp = temp.proximo;
        }
    }

    public boolean iO(int tempoAtual, Processo processo) {
        if (processo.io == null) {
            return false;
        } else {
            for (int i = 0; i < processo.io.length; i++) {
                if (processo.io[i] == tempoAtual) {
                    return true;
                }
            }
            return false;
        }

    }

    public void addAuxiliar(Processo processo) {
        processosAuxiliar.add(processo);

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
