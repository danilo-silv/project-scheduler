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
    Lista lista = new Lista();     //Lista dos processos prontos para a execução
    Lista processo = new Lista(lista);
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
        processos.forEach((processo) -> lista.add(processo));//adcionando os processos na lista após eles serem organizados
//        processos.forEach((processo) -> {
//            processo.imprimir();
//        });

        System.out.println("\nID dos processos cadastrados: \n" + processo.imprimirId());
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

    static boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                return true;
            }
        }
        return false;
    }

}
