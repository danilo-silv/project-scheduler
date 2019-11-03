/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoescalonador;

import javax.swing.WindowConstants;
import projetoescalonador.AppHome;

/**
 *
 * @author Desktop
 */
public class ApsEscalonadorUAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       AppHome app = new AppHome();
       
       app.setLocationRelativeTo(null);
       app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       app.setVisible(true);
       
        
    }
    
}
