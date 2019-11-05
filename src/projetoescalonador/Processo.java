package projetoescalonador;

import java.util.Arrays;

/**
 *
 * @author Danilo, Guilherme, Giovanni, Gustavo e Victor
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
    public double fim;
    public int duracaoTotal;

    public Processo(int id, int chegada, int duracao, int prioridade, int[] io) {
        this.id = id;
        this.chegada = chegada;
        this.duracao = duracao;
        this.prioridade = prioridade;
        this.io = io;
        this.espera = -1;
        this.turnaround = -1;
        this.inicio = -1;
        this.fim = -1;
        this.duracaoTotal = duracao;
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
        System.out.println("\n-----Processo " + this.id + "-----");
        System.out.println("Chegada: " + this.chegada);
        System.out.println("Duração: " + this.duracao);
        System.out.println("Prioridade: " + this.prioridade);
        if (this.io != null) {
            System.out.println("IO: " + Arrays.toString(this.io));
        }
        System.out.println("Espera: " + this.espera);
        System.out.println("Turnaround: " + this.turnaround);
        System.out.println("Inicio: " + this.inicio);
        System.out.println("Fim: " + this.fim);

    }

    @Override
    public String toString() {
        return "Processo{" + "id=" + id + ", chegada=" + chegada + ", duracao=" + duracao + ", prioridade=" + prioridade + ", io=" + io + '}';
    }
    
    

}
