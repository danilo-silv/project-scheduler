package projetoescalonador;

/**
 *
 * @author Danilo, Guilherme e Gustavo
 */

public class No {

    public Processo processo;
    public No proximo;

    public No(Processo processo) {
        this.processo = processo;
        this.proximo = null;
    }

}
