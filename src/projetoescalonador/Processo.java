package projetoescalonador;

/**
 *
 * @author Guilherme
 */
public class Processo implements Comparable<Processo> {

    public int id;
    public int chegada;
    public int duração;
    public int prioridade;
    public int[] io;
    public int espera;
    public int turnaround;
    public int inicio;
    public int fim;

    public Processo(int id, int chegada, int duração, int prioridade, int[] io) {
        this.id = id;
        this.chegada = chegada;
        this.duração = duração;
        this.prioridade = prioridade;
        this.io = io;
        this.espera = 0;
        this.turnaround = 0;
        this.inicio = 0;
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
        if (this.chegada > outroProcesso.chegada) {
            return -1;
        }

        if (this.chegada < outroProcesso.chegada) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Processo[" + id + "]";
    }
}
