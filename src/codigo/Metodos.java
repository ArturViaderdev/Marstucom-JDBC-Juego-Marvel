/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import datos.Enemy;
import datos.Gem;
import datos.Place;
import datos.Ranking;
import datos.Superheroe;
import datos.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Clase que contiene métodos útiles para la aplicación.
 * @author alu2017363
 */
public class Metodos {
    
    /**
     * Quita un lugar de una lista de lugares.
     * @param lugares Lista de lugares
     * @param nombrelugar Nombre del lugar a eliminar de la lista
     */
    public static void quitalugar(ArrayList<Place> lugares, String nombrelugar)
    {
        boolean sal = false;
        boolean encontrado = false;
        int cont =0;
        //Se recorre la lista y cuando se encuentra el lugar buscado se elimina de esta.
        do
        {
            if(cont<lugares.size())
            {
                if(lugares.get(cont).getName().equals(nombrelugar))
                {
                    encontrado = true;
                    sal = true;
                }
                else
                {
                    cont++;
                }
            }
            else
            {
                sal = true;
            }
        }while(!sal);  
        if(encontrado)
        {
            lugares.remove(cont);
        } 
    }
    
    /**
     * Devuelve un ataque aleatorio entre piedra,papel, tijera, lagarto, spock
     * @return  El ataque
     */
    public static String obtenataque()
    {
        String[] ataques = {"rock", "paper", "scissors", "lizard", "spock"};
        Random aleatorio = new Random();
        return ataques[aleatorio.nextInt(ataques.length)];  
    }
    
    /**
     * Obtiene el ganador partiendo de dos ataques
     * @param juegoa Ataque a
     * @param juegob Ataque b
     * @return El ganador 0 cuando gana el primero, 1 cuando gana el segundo y 2 cuando empatan
     */
    public static int obtenganador(String juegoa, String juegob)
    {
        
        int ganador=0;
        if(juegoa.equals("rock") && juegob.equals("rock"))
        {
            ganador=2;
        }
        else if(juegoa.equals("rock") && juegob.equals("paper"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("rock") && juegob.equals("scissors"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("rock") && juegob.equals("lizard"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("rock") && juegob.equals("spock"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("paper") && juegob.equals("rock"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("paper") && juegob.equals("paper"))
        {
            ganador = 2;
        }
        else if(juegoa.equals("paper") && juegob.equals("scisssors"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("paper") && juegob.equals("lizard"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("paper") && juegob.equals("spock"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("scissors") && juegob.equals("rock"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("scissors") && juegob.equals("paper"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("scissors") && juegob.equals("scissors"))
        {
            ganador = 2;
        }
        else if(juegoa.equals("scissors") && juegob.equals("lizard"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("scissors") && juegob.equals("spock"))
        {
            ganador = 1;
        }
        
        else if(juegoa.equals("lizard") && juegob.equals("rock"))
        {
            ganador = 1;
        }
         else if(juegoa.equals("lizard") && juegob.equals("paper"))
        {
            ganador = 0;
        }
         else if(juegoa.equals("lizard") && juegob.equals("scissors"))
        {
            ganador = 1;
        }
         else if(juegoa.equals("lizard") && juegob.equals("lizard"))
        {
            ganador = 2;
        }
         else if(juegoa.equals("lizard") && juegob.equals("spock"))
        {
            ganador = 1;
        }
        else if(juegoa.equals("spock") && juegob.equals("rock"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("spock") && juegob.equals("paper"))
        {
            ganador = 0;
        }
         else if(juegoa.equals("spock") && juegob.equals("scissors"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("spock") && juegob.equals("rock"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("spock") && juegob.equals("lizard"))
        {
            ganador = 0;
        }
        else if(juegoa.equals("spock") && juegob.equals("spock"))
        {
            ganador = 2;
        }
        return ganador;
        
    }
 
    /**
     * Detecta si es posible coger una gema
     * @param gema Gema que se intenta coger
     * @param usuario Usuario que quiere coger la gema
     * @param villanos Lista de villanos
     * @return Si se puede coger o no. Se puede si no pertenece al propio usuario ni a ningún villano.
     */
   // public static boolean puedocogergema(Gem gema, User usuario, ArrayList<Enemy> villanos)
    public static boolean puedocogergema(Gem gema)
    {
        
       // if(gema.getOwner().equals("null"))
        
        if(gema.getOwner()==null)
        {
            
            
            return true;
        }
        else
        {
            
            return false;
        }
        
       /* boolean encontrado = false;
        boolean sal = false;
        int cont = -1;*/
        //Se recorre la lista de villanos pero empezando primero por el usuario
        
        /*
        do
        {
        
            if(cont<villanos.size())
            {
                if(cont==-1)
                {
                    //Se comprueba que la gema no pertenezca al propio usuario
                    if(gema.getOwner().equals(usuario.getUsername()))
                    {
                  
                        encontrado = true;
                        sal = true;
                    }
                }
                else
                {
                   //Se comprueba que la gema no pertenzca a ningún villano
                    if(gema.getOwner().equals(villanos.get(cont).getName()))
                    {
                        encontrado = true;
                        sal = true;
                    } 
                }
                if(!encontrado)
                {
                    cont++;
                }
            }
            else
            {
                sal = true;
            }
        }while(!sal);*/
        
       //return !encontrado;  
    }
    
    /**
     * Muestra las direcciones a las que se puede ir desde un lugar.
     * @param lugar Lugar especificado
     */
    public static void muestradirecciones(Place lugar)
    {
        ArrayList<String> direcciones = new ArrayList<String>();
        
       //Se añaden a una lista las direcciones posibles. Las que no son null en el lugar.
       if(lugar.getNorth()!=null)
       {
           
           direcciones.add("North");
       }
       
       if(lugar.getSouth()!=null)
       {
           
           direcciones.add("South");
       }
       
       if(lugar.getEast()!=null)
       {
           
           direcciones.add("East");
       }
       
       if(lugar.getWest()!=null)
       {
           
           direcciones.add("West");
       }
       
       //Se muestra la lista de direcciones.
        System.out.println("Direcciones posibles:");
       for (String direccion : direcciones)
       {
           System.out.println(direccion);
       }
    }   
    
    /**
     * Muestra una lista de villanos
     * @param enemigos Villanos a mostrar.
     */
    public static void muestravillanos(ArrayList<Enemy> enemigos)
    {
        System.out.println("Hay " + enemigos.size() + " enemigos.");
        for(Enemy enemigo : enemigos)
        {
            System.out.println(enemigo.toString());
        }
        
    }
    
    /**
     * Muestra el ranking
     * @param ranking Ranking calculado
     */
    public static void muestraranking(ArrayList<Ranking> ranking)
    {
        Collections.sort(ranking);
        for(Ranking rankeo : ranking)
        {
            System.out.println(rankeo.toString());
        }
    }
    
    /**
     * Muestra una lista de gemas
     * @param gemas Lista de gemas
     */
    public static void muestragemas(ArrayList<Gem> gemas)
    {
        System.out.println("Hay " + gemas.size() + " gemas.");
        for(Gem gema : gemas)
        {
            System.out.println(gema.toString());
        }
    }
    
    /**
     * Crea un usuario en memoria con los argumentos.
     * @param argumentos Datos del nuevo usuario
     * @return Devuelve el usuario con todos sus datos
     */
    public static User creausuario(String[] argumentos)
    {
        User usuario = new User(argumentos[0],argumentos[1],argumentos[2]);
        return usuario;
    }
    
    /**
     * Muestra una lista de superheroes
     * @param superheroes Lista de superheroes
     */
    public static void muestrasuperheroes(ArrayList<Superheroe> superheroes)
    {
        for(Superheroe perso : superheroes)
        {
            System.out.println(perso.toString());
        }
    }            
            
    /**
     * Crea un superheroe en memoria partiendo de una lectura de la base de datos.
     * @param rs Lectura de superheroe de la base de datos
     * @return El superheroe leido.
     * @throws SQLException 
     */
    public static Superheroe creasuperheroe(ResultSet rs) throws SQLException
    {
        Superheroe heroe = new Superheroe(rs.getString("name"),rs.getString("superpower"));
        return heroe;
    }
}
