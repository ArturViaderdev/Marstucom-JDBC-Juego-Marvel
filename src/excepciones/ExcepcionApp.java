/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que controla las excepciones.
 * @author alu2017363
 */
public class ExcepcionApp extends Exception{
    //Lista de excepciones y sus mensajes
    public static final int NUMPARAMETROS = 0;
    public static final int COMANDOINCORRECTO = 1;
    public static final int USUARIOEXISTE = 2;
    public static final int SUPERHEROENOEXISTE = 3;
    public static final int LOGININCORRECTO = 4;
    public static final int NOLOGIN = 5;
    public static final int DIRECCIONINCORRECTA = 6; 
    public static final int NOSEENCUENTRAGEMA = 7;
    public static final int JUEGOTERMINADO = 8;
    public static final int NOENEMIGOESTENOMBRE = 9;
    public static final int PASSDELETEWRONG = 10;
    public static final int LOGGED = 11;
    public static final int VALORNOPERMITIDO = 12;

    private int code;
    
     private final List<String> messages = Arrays.asList(
       "Wrong number of arguments."    ,
             " Wrong command ",
       "User already exists",
       "There isn't a superhero with that name",
       "Username or password is incorrect",
       "You are not logged in",
       "You can't move in that direction",
       "Here there is no gem with that name",
       "You already finish your game",
       "Here there is no enemy with that name",
       "Delete aborted. Your password is wrong",
       "You are logged, command not usable.",
       "Not allowed value"
     );

     /**
      * Constructor
      * @param code Código de excepción 
      */
    public ExcepcionApp(int code) {
        this.code = code;
    }

    /**
     * Devuelve el mensaje de la excepción correspondiente al código
     * @return Mensaje
     */
    @Override
    public String getMessage() {
        return messages.get(code);
    }
}
