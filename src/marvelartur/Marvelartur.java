/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marvelartur;

import codigo.Aplicacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal que inicia la aplicación
 * @author alu2017363
 */
public class Marvelartur {

    /**
     * Método principal
     * @param args the command line arguments Lista de comandos
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Aplicacion aplicacion;
        String [] parametros;
        try {
            //Se instancia la clase de la aplicación que interpretará los comandos.
            aplicacion = new Aplicacion();
            String leido = "";
            parametros = new String[1];
            parametros[0] = " ";
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            do {
                try {
                    //El usuario introduce una lista de comandos
                    leido = lector.readLine();
                    //Se separan los comandos por espacios
                    parametros = leido.split(" ");
                    //Se procesan los comandos
                    aplicacion.procesa(parametros);
                } catch (IOException ex) {
                    Logger.getLogger(Marvelartur.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!parametros[0].equals("x") && !parametros[0].equals("X"));
            //Se van solicitando comandos al usuario hasta que no introduce x como primer parámetro.
        } catch (SQLException ex) {
            Logger.getLogger(Marvelartur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
