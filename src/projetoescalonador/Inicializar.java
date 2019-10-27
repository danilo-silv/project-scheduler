package projetoescalonador;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *
 * @author Guilherme And Danilo
 */
public class Inicializar {

    Scanner input = new Scanner(System.in);
    Lista listaProcessos = new Lista();     //Lista dos processos prontos para a execução
    ArrayList<Processo> processos = new ArrayList<>();
    String chegada, duracao, prioridade, str;
    int cont = 1;
    int[] io;
    char opc = 'n';

    public void entradaDeProcessos() {
        System.out.println("--------------------------------------");
        System.out.println("|           ESCALONADOR              |");
        System.out.println("--------------------------------------");
        construcaoProcesso();
        Collections.sort(processos);//organizando o array por onder de chegada 
        processos.forEach((processo) -> listaProcessos.add(processo));//adcionando os processos na lista após eles serem organizados
//        processos.forEach((processo) -> {
//            processo.imprimir();
//        });
        listaProcessos.roundRobin(5);
    }

    public void construcaoProcesso() {
        do {    //Inserção dos dados do processo
            System.out.println("\n<       Adicionar processo      >");

            System.out.println("Informe os DADOS do " + this.cont + "° processo.");
            System.out.print("Chegada: ");
            chegada = input.nextLine();
            System.out.print("Duração: ");
            duracao = input.nextLine();
            System.out.print("Prioridade: ");
            prioridade = input.nextLine();

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
            processos.add(new Processo(cont, Integer.parseInt(chegada), Integer.parseInt(duracao), Integer.parseInt(prioridade), io));
            cont++;

            do { //Validação de continuidade
                System.out.println("Deseja adicionar outro processo? [s/n]: ");
                opc = input.nextLine().charAt(0);
                opc = Character.toLowerCase(opc);
                if (opc != 's' && opc != 'n') {
                    System.err.println("Formato inválido!");
                }
            } while (opc != 's' && opc != 'n');

        } while (opc == 's');
    }

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
                            execucao.add(temporario);
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

    static boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                return true;
            }
        }
        return false;
    }

}
