package projetoescalonador;

import projetoescalonador.AppHome;
/**
 *
 * @author Guilherme And Danilo
 */
public class ProjetoEscalonador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppHome app = new AppHome();
        app.setVisible(true);
        Inicializar inicializar = new Inicializar();
        inicializar.entradaDeProcessos();
        
    }
    
}
