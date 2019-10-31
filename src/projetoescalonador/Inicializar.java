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
        Collections.sort(processos);//organizando o array por onder de chegada 
        processos.forEach((processo) -> listaProcessos.add(processo));//adcionando os processos na lista após eles serem organizados
//        processos.forEach((processo) -> {
//            processo.imprimir();
//        });
        prioridadePreemptivo(listaProcessos);
        //listaProcessos.roundRobin(5);
    }

    public void construcaoProcesso() {
        do {    //Inserção dos dados do processo
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

            do { //Validação de continuidade
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
        //insere o primeiro processo para a execucao na lista  -> ok 
        //iguala o tempo com a chegada do processo -> ok
        //verifica a cada segundo se chegou um novo processo (da lista original) -> ok
        //se chegou um novo processo, verificar sua prioridade -> ok
        //caso sua prioridade seja maior, passar o processo atual pro fim da fila e executar o novo -> ok
        //caso seja menor ou igual, jogar para o fim da fila -> ok
        //se não chegou processos, continuar executando -> ok
        //verificar i/o -> ok

        No execucao = processosOrdenados.inicio;
        int tempo = execucao.processo.chegada;

        while (execucao.proximo != null) {
            No ref = processosOrdenados.inicio.proximo;
            while (execucao.processo.duracao > 0) {
                System.out.println("primeiro: " + execucao.processo.id);
                while (ref != null) {
                    System.out.println("segundo: " + execucao.proximo.processo.id);
                    if (ref.processo.chegada <= tempo && ref.processo.prioridade > execucao.processo.prioridade && ref.processo.duracao > 0) {
                        processosOrdenados.add(execucao.processo);
                        processosOrdenados.inicio = ref;
                        execucao = ref;
                        if (ref.proximo != null) {
                            continue;
                        } else {
                            break;
                        }
                    }

                    ref = ref.proximo;
                }

                if (execucao.processo.io != null) //Verifica IO e retorna pro while caso tru
                {
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

                if (execucao.processo.duracao == 0) //Seta o fim
                {
                    execucao.processo.fim = tempo;
                }

            }
            execucao = ref;
        }

    }

    //Lógica do RoundRobin
    static void roundRobin(Lista processosOrdenados, int quantum) {
        int tempo = 0, espera;
        Lista execucao = processosOrdenados;
        //pega primeiro processo
        //subtrai o quantum da duração
        //manda esse processo pra fila(ultima posição)
        //pega o proximo

        while (execucao.inicio != null) {
            Processo temporario = execucao.inicio.processo;
            while (temporario.duracao > 0 && execucao.inicio.proximo.processo.chegada < tempo) {
                if (temporario.duracao > 0) {
                    if (temporario.duracao < quantum) {
                        tempo += temporario.duracao;
                        temporario.duracao = 0;
                        break;
                    }
                    temporario.duracao -= quantum;
                    tempo += quantum;

                    //verifica se o proximo objeto já chegou na lista
                    //objeto.chegada <= tempo
                    //se for menor adiciona o objeto executado no final
                    //se for maior adiciona o objeto executado antes dele
                    //se o rpoximo processo <= tempo de execução ai o processo que estiver executando vai proximo, se não ele é inserido antes
                    while (execucao.inicio != null) {
                        Processo temp = execucao.inicio.proximo.processo;//segundo objeto
                        if (temp.chegada <= tempo) {
//                            execucao.add(temporario);
                        }
                        execucao.inicio = execucao.inicio.proximo;
                    }

//                    execucao.add(temporario);
                }
            }

            execucao.inicio = execucao.inicio.proximo;
        }
        //4
        //3
        //3

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
