package projetoescalonador;

/**
 *
 * @author Danilo
 */
public class Escalonador {

    //Lógica do Round Robin
    public void roundRobin(int quantum, Lista lista) {
        Processo execucao;
        int tempo = lista.processosAuxiliar.getFirst().chegada;
        double espera[] = new double[lista.processosAuxiliar.size()];
        double turnaround[] = new double[lista.processosAuxiliar.size()];
        int[] idProcesso = new int[lista.processosAuxiliar.size()];

        int contadorMedias = 0;

        while (!lista.processosAuxiliar.isEmpty() && lista.processosAuxiliar.getFirst().chegada == tempo) {
            lista.add(lista.processosAuxiliar.remove(0));
        }

        while (!(lista.isEmpty() && lista.processosAuxiliar.isEmpty())) {
            execucao = lista.remove(0);
            execucao.inicio = tempo;

            for (int i = 0; i < quantum; i++) {
                if (execucao.duracao > 0) {
                    execucao.duracao--;
                    execucao.tempoProcesso++;
                    while (!lista.processosAuxiliar.isEmpty() && lista.processosAuxiliar.getFirst().chegada == tempo) {
                        lista.add(lista.processosAuxiliar.remove(0));
                    }
                    tempo++;
                    if (iO(execucao.tempoProcesso, execucao)) {
                        i = quantum;
                    }
                } else {
                    i = quantum;
                }
            }
            if (execucao.duracao != 0) {
                lista.add(execucao);
            } else {
                execucao.turnaround = tempo - execucao.chegada;
                execucao.espera = execucao.turnaround - execucao.duracaoTotal;;
                espera[contadorMedias] = execucao.espera;
                turnaround[contadorMedias] = execucao.turnaround;
                idProcesso[contadorMedias] = execucao.id;
                contadorMedias++;
            }
            System.out.println("\n---------------STATUS--------------");
            System.out.println("Processo executado: " + execucao.id);
            System.out.println("Duração restante: " + execucao.duracao);
            System.out.println("Tempo de execução: " + tempo);
            System.out.println("-----------------------------------");
        }

        System.out.println("\n<-- Tempo de Espera--|--Turnaround -->");
        double mediaEspera = 0;
        double mediaTurnaround = 0;
        for (int i = 0; i < espera.length; i++) {
            System.out.println("P" + idProcesso[i] + ":             " + espera[i] + "  |  " + turnaround[i]);
            mediaEspera += espera[i];
            mediaTurnaround += turnaround[i];
        }
        mediaEspera = mediaEspera / espera.length;
        mediaTurnaround = mediaTurnaround / espera.length;

        System.out.println("\n--------------|MÉDIAS|--------------");
        System.out.println("Espera:          " + mediaEspera);
        System.out.println("Turnaround:      " + mediaTurnaround);

    }

    //Lógica do Prioridade Preemptivo
    public void prioridadePreemptivo(Lista lista) {
        No execucao = lista.processosAuxiliar.inicio;
        int tempo = execucao.processo.chegada;

        while (execucao != null) {
            while (execucao.processo.duracao > 0) {

                No ref = null;
                if (execucao.proximo != null) {
                    ref = execucao.proximo;
                }

                while (ref != null) {
                    if (ref.processo.chegada <= tempo && ref.processo.prioridade > execucao.processo.prioridade && ref.processo.duracao > 0) {
                        lista.processosAuxiliar.add(execucao.processo);
                        lista.processosAuxiliar.inicio = ref;
                        execucao = ref;
                        tempo--;
                        execucao.processo.duracao++;
                    }
                    ref = ref.proximo;
                }

                execucao.processo.duracao--;
                tempo++;

                System.out.println("\nProcesso executado: " + execucao.processo.id);
                System.out.println("Duração restante: " + execucao.processo.duracao);
                System.out.println("Tempo de execução: " + tempo);

            }
            if (execucao.processo.inicio == -1) {
                execucao.processo.inicio = tempo;
            }
            if (execucao.processo.duracao == 0) {
                execucao.processo.fim = tempo;
            }
            if (execucao.processo.duracao == 0) {

                System.out.println("\n---------------STATUS--------------");
                System.out.println("Processo executado: " + execucao.processo.id);
                System.out.println("Duração restante: " + execucao.processo.duracao);
                System.out.println("Tempo de execução: " + tempo);
                System.out.println("-----------------------------------");

                System.out.println("\n<-- Tempo de Turnaround --|-- Espera -->");
                System.out.println("P" + execucao.processo.id + ": " + (execucao.processo.turnaround = execucao.processo.fim - execucao.processo.chegada)
                        + " | " + (execucao.processo.espera = execucao.processo.turnaround - execucao.processo.duracaoTotal));
                execucao = execucao.proximo;
            }
        }

    }

    public boolean iO(int tempoAtual, Processo processo) {
        if (processo.io == null) {
            return false;
        } else {
            for (int i = 0; i < processo.io.length; i++) {
                if (processo.io[i] == tempoAtual) {
                    System.out.println("FEZ IO");
                    return true;
                }
            }
            return false;
        }

    }

}
