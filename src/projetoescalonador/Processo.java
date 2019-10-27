package projetoescalonador;

import java.util.Arrays;

/**
 *
 * @author Danilo, Guilherme e Gustavo
 */
public class Processo implements Comparable<Processo> {

    public int id;
    public int chegada;
    public int duracao;
    public int prioridade;
    public int[] io;
    public int espera;
    public int turnaround;
    public int inicio;
    public int fim;

    public Processo(int id, int chegada, int duração, int prioridade, int[] io) {
        this.id = id;
        this.chegada = chegada;
        this.duracao = duração;
        this.prioridade = prioridade;
        this.io = io;
        this.espera = 0;
        this.turnaround = 0;
        this.inicio = -1;
        this.fim = 0;
    }

    public int getChegada() {
        return chegada;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Processo outroProcesso) {
        if (this.chegada < outroProcesso.chegada) {
            return -1;
        }

        if (this.chegada > outroProcesso.chegada) {
            return 1;
        }
        return 0;
    }

    public void imprimir() {
        System.out.println("-----Processo " + this.id + "-----");
        System.out.println("Chegada: " + this.chegada);
        System.out.println("Duração: " + this.duracao);
        System.out.println("Prioridade: " + this.prioridade);
        System.out.println("IO: " + Arrays.toString(this.io));
        System.out.println("Espera: " + this.espera);
        System.out.println("Turnaround: " + this.turnaround);
        System.out.println("Inicio: " + this.inicio);
        System.out.println("Fim: " + this.fim + "\n");
    }

    @Override
    public String toString() {
        return "Processo{" + "id=" + id + ", chegada=" + chegada + ", duracao=" + duracao + ", prioridade=" + prioridade + ", io=" + io + ", espera=" + espera + ", turnaround=" + turnaround + ", inicio=" + inicio + ", fim=" + fim + '}';
    }

}
