package projetoescalonador;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *
 * @author Danilo, Guilherme e Gustavo
 */
public class Inicializar {

    Scanner input = new Scanner(System.in);
    Scanner inputIo = new Scanner(System.in);
    ArrayList<Processo> processos = new ArrayList<>(); //Lista que recebe e ordena os processos
    Lista listaProcessos = new Lista();     //Lista dos processos e ordenados por chegada
    String chegada, duracao, prioridade, str;
    int cont = 1, io[];
    char opc = 'n', opcIo = 'n';

    public void entradaDeProcessos() {
        System.out.println("--------------------------------------------------");
        System.out.println("|           ESCALONADOR DE PROCESSOS             |");
        System.out.println("--------------------------------------------------");
        construcaoProcesso();
        Collections.sort(processos);
        processos.forEach((processo) -> listaProcessos.add(processo));
//        processos.forEach((processo) -> {
//            processo.imprimir();
//        });
        //  prioridadePreemptivo(listaProcessos);

        roundRobin(listaProcessos, 4);
    }

    public void construcaoProcesso() {
        do {
            System.out.println("\n<       Adicionar processo      >");
            System.out.println("Informe os DADOS do " + this.cont + "° processo.");
            System.out.println("Informe chegada, duração e prioridade: ");
            chegada = input.nextLine();
            duracao = input.nextLine();
            prioridade = input.nextLine();
//            System.out.print("Chegada: ");
//            chegada = input.nextLine();
//            System.out.print("Duração: ");
//            duracao = input.nextLine();
//            System.out.print("Prioridade: ");
//            prioridade = input.nextLine();

            do {
                do { //Verifica se possui I/O
                    System.out.print("Processo possui I/O? [s/n]: ");
                    opcIo = inputIo.nextLine().charAt(0);
                    opcIo = Character.toLowerCase(opcIo);
                    if (opcIo != 's' && opcIo != 'n') {
                        System.err.println("Formato inválido!");
                    }
                } while (opcIo != 's' && opcIo != 'n');

                if (opcIo == 's') { //Entrada do I/O caso exista
                    do {
                        System.out.print("I/O - (Ex: 2, 4, 3): ");
                        str = input.nextLine().replace(",", "");

                        io = new int[str.length()];
                        for (int i = 0; i < io.length; i++) {
                            io[i] = Integer.parseInt(str.substring(i, i + 1));
                        }

                        if (compareHealingAndIo(Integer.parseInt(duracao), io)) {
                            System.err.println("O tempo que o processo fara I/O deve ser menor que o tempo de duração do processo!");
                        }

                    } while (compareHealingAndIo(Integer.parseInt(duracao), io));
                    opcIo = 'n';
                } else {
                    io = null;
                }
            } while (opcIo == 's');

            processos.add(new Processo(cont, Integer.parseInt(chegada), Integer.parseInt(duracao), Integer.parseInt(prioridade), io));
            cont++;

            do {
                System.out.print("Deseja adicionar outro processo? [s/n]: ");
                opc = input.nextLine().charAt(0);
                opc = Character.toLowerCase(opc);
                if (opc != 's' && opc != 'n') {
                    System.err.println("Formato inválido!");
                }
            } while (opc != 's' && opc != 'n');

        } while (opc == 's');
    }

    //Lógica do Prioridade Preemptivo
    static void prioridadePreemptivo(Lista processosOrdenados) {
        No execucao = processosOrdenados.inicio;
        int tempo = execucao.processo.chegada;

        while (execucao != null) {
            while (execucao.processo.duracao > 0) {
                No ref = execucao.proximo;
                while (ref != null) {
                    if (ref.processo.chegada <= tempo && ref.processo.prioridade > execucao.processo.prioridade && ref.processo.duracao > 0) {
                        processosOrdenados.add(execucao.processo);
                        processosOrdenados.inicio = ref;
                        execucao = ref;
                        tempo--;
                        execucao.processo.duracao++;
                    }
                    ref = ref.proximo;
                }

                if (execucao.processo.io != null) {
                    checarIo(tempo, processosOrdenados);
                }

                if (execucao.processo.inicio == -1) {
                    execucao.processo.inicio = tempo;
                }

                execucao.processo.duracao--;
                tempo++;

                System.out.println("\nProcesso executado: " + execucao.processo.id);
                System.out.println("Duração restante: " + execucao.processo.duracao);
                System.out.println("Tempo de execução: " + tempo);

                if (execucao.processo.duracao == 0) {
                    execucao.processo.fim = tempo;
                }

            }
            execucao = execucao.proximo;
        }

        No contaEspera = processosOrdenados.inicio;

        while (contaEspera != null) {
            contaEspera.processo.turnaround = contaEspera.processo.fim - contaEspera.processo.chegada;
            contaEspera.processo.espera = contaEspera.processo.turnaround - contaEspera.processo.duracaoTotal;
            contaEspera.processo.imprimir();
            contaEspera = contaEspera.proximo;
        }

    }

    //Lógica do RoundRobin
    static void roundRobin(Lista processosOrdenados, int quantum) {
        No execucao = processosOrdenados.inicio;
        int tempo = execucao.processo.chegada, teste = 0;

        while (execucao != null) {
            while (execucao.processo.duracao > 0) {
                No ref = execucao.proximo;
                if (execucao.processo.duracao == 0 || teste == quantum) {
                    while (ref != null) {
                        if (ref.processo.duracao > 0) {
                            processosOrdenados.add(execucao.processo);
                            processosOrdenados.inicio = ref;
                            execucao = ref;
                            tempo--;
                            execucao.processo.duracao++;
                        }
                        ref = ref.proximo;
                    }
                }

                if (execucao.processo.io != null) {
                    checarIo(tempo, processosOrdenados);
                }

                if (execucao.processo.inicio == -1) {
                    execucao.processo.inicio = tempo;
                }

                execucao.processo.duracao--;
                if (execucao.processo.duracao < 0) {
                    execucao.processo.duracao = 0;
                }

                tempo++;
                if (tempo - 1 == quantum) {
                    if (teste == 0) {
                        teste = quantum;
                    } else {
                        teste = quantum * 2;
                    }

                }
                System.out.println("\nProcesso executado: " + execucao.processo.id);
                System.out.println("Duração restante: " + execucao.processo.duracao);
                System.out.println("Tempo de execução: " + tempo);

                if (execucao.processo.duracao == 0) {
                    execucao.processo.fim = tempo;
                }

            }
            execucao = execucao.proximo;
        }
        No contaEspera = processosOrdenados.inicio;

        while (contaEspera != null) {
            contaEspera.processo.turnaround = contaEspera.processo.fim - contaEspera.processo.chegada;
            contaEspera.processo.espera = contaEspera.processo.turnaround - contaEspera.processo.duracaoTotal;
            contaEspera.processo.imprimir();
            contaEspera = contaEspera.proximo;
        }

    }

    public static void checarIo(int tempo, Lista execucao) {
        for (int i = 0; i < execucao.inicio.processo.io.length; i++) {
            if (tempo == execucao.inicio.processo.io[i]) {
                execucao.add(execucao.inicio.processo);
                execucao.inicio = execucao.inicio.proximo;
                //quebrar e voltar pro segundo while
                return;
            }
        }
    }

    static boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                return true;
            }
        }
        return false;
    }

}
