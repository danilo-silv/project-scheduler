package projetoescalonador;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *
 * @author Danilo, Guilherme, Giovanni, Gustavo e Victor
 */
public class Inicializar {

    Scanner input = new Scanner(System.in);
    Scanner inputIo = new Scanner(System.in);
    ArrayList<Processo> processos = new ArrayList<>();
    Lista auxiliar = new Lista();
    Lista listaProcessos = new Lista(auxiliar);
    Lista listaExecutados = new Lista();
    String chegada, duracao, prioridade, str, quantum, escalonador = "1";
    int cont = 1, io[];
    char opc = 'n', opcIo = 'n';

    public void entradaDeProcessos() {
        System.out.println("--------------------------------------------------");
        System.out.println("|           ESCALONADOR DE PROCESSOS             |");
        System.out.println("--------------------------------------------------");
        construcaoProcesso();
        Collections.sort(processos);

        processos.forEach((processo) -> {
            listaProcessos.addAuxiliar(processo);
        });
        if (Integer.parseInt(escalonador) == 1) {
            listaProcessos.roundRobin(Integer.parseInt(quantum));
            System.out.println(Integer.parseInt(quantum));
        } else {
            listaProcessos.prioridadePreemptivo();
        }

    }

    public void construcaoProcesso() {

        System.out.println("\n<       Escolha do escalonador      >");
        System.out.println("1 - Round Robin");
        System.out.println("2 - Prioridade Preemptivo");
        escalonador = input.nextLine();
        System.out.println(escalonador);
        if (Integer.parseInt(escalonador) == 1) {
            System.out.println("Informe o valor do Quantum: ");
            quantum = input.nextLine();
            System.out.println(quantum);
        }

        do {

            System.out.println("\n<       Adicionar processo      >");

            System.out.println("Informe os DADOS do " + this.cont + "° processo.");
            System.out.print("Chegada: ");
            chegada = input.nextLine();
            System.out.print("Duração: ");
            duracao = input.nextLine();
            System.out.print("Prioridade: ");
            prioridade = input.nextLine();

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
                        str = input.nextLine();
                        String vetString[] = str.split(",");
                        io = new int[vetString.length];

                        for (int i = 0; i < io.length; i++) {
                            io[i] = Integer.parseInt(vetString[i]);
                        }
                        if (compareHealingAndIo(Integer.parseInt(duracao), io)) {
                            System.err.println("O tempo que o processo fara I/O deve ser menor que o tempo de duração do processo!");
                        }

                    } while (compareHealingAndIo(Integer.parseInt(duracao), io));

                    opcIo = 'n';
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

    public boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                return true;
            }
        }
        return false;
    }
}
