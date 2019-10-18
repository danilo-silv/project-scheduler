package projetoescalonador;

/**
 *
 * @author Guilherme
 */
public class Processo {

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

    
}
