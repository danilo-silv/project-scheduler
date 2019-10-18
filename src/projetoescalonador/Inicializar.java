package projetoescalonador;

import java.util.Scanner;

/**
 * 
 *
 * @author Guilherme And Danilo
 */
public class Inicializar {

public void entradaDeProcessos() {
        Scanner input = new Scanner(System.in);
        ListaDinamica list = new ListaDinamica();     //Lista dos processos prontos para a execução
        String chegada, duracao, prioridade, str;
        int cont = 1;
        int[] io;
        char opc = 'n';
        System.out.println("--------------------------------------");
        System.out.println("|           ESCALONADOR              |");
        System.out.println("--------------------------------------");
        do {    //Inserção dos dados do processo
            System.out.println("<       Adicionar processo      >\n");

            System.out.println("Informe os DADOS do " + cont + "° processo.");
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

            list.add(new Processo(cont, Integer.parseInt(chegada), Integer.parseInt(duracao), Integer.parseInt(prioridade), io));
            cont++;

            do { //Validação de continuidade
                System.out.println("Deseja adicionar outro processo? [s/n]: ");
                opc = input.nextLine().charAt(0);
                if (opc != 's' && opc != 'n') {
                    System.err.println("Formato inválido!");
                }
            } while (opc != 's' && opc != 'n');

        } while (opc == 's');

        System.out.println("\nID dos processos cadastrados: \n" + list.imprimir());

    }

    public boolean compareHealingAndIo(int duracao, int[] io) {
        for (int number : io) {
            if (number > duracao) {
                System.out.println("maior");
                return true;
            }
        }
        return false;
    }

}


