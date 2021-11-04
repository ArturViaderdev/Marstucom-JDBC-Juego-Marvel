/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Clase que define el tipo de datos usuario
 * @author alu2017363
 */
public class User {
    //Nombre de usuario
    private String username;
    //Password
    private String password;
    //Nivel
    private int level;
    //Superheroe
    private String superhero;
    //Lugar
    private String place;
    //Puntos
    private int points;

    /**
     * Devuelve el nombre de usuario
     * @return Nombre
     */
    public String getUsername() {
        return username;
    }

    /**
     * Cambia el nombre de usuario
     * @param username Nuevo nombre
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el password No se usa porque no se guarda el password en memoria
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cambia el password
     * @param password Nuevo password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el nivel
     * @return Nivel
     */
    public int getLevel() {
        return level;
    }

    /**
     * Cambia el nivel
     * @param level Nuevo nivel
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Obtiene el superheroe
     * @return Superheroe
     */
    public String getSuperhero() {
        return superhero;
    }

    /**
     * Cambia el superheroe
     * @param superhero Nuevo superheroe
     */
    public void setSuperhero(String superhero) {
        this.superhero = superhero;
    }

    /**
     * Obtiene el lugar
     * @return Lugar
     */
    public String getPlace() {
        return place;
    }

    /**
     * Cambia el lugar
     * @param place Nuevo lugar
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Obtiene los puntos
     * @return puntos
     */
    public int getPoints() {
        return points;
    }

    /**
     * Cambia los puntos
     * @param points Nuevo puntos
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Constructor
     * @param username Nombre de usuario
     * @param password Password
     * @param superhero Superheroe
     */
    public User(String username, String password, String superhero) {
        this.username = username;
        this.password = password;
        this.superhero = superhero;
        this.level = 1;
        this.points = 0;
        this.place = "New York";       
    }  
    
    /**
     * Contructor sin el password
     * @param username Nombre de usuario
     * @param superhero Superheroe
     * @param level Nivel
     * @param place Lugar
     * @param points Puntos
     */
    public User(String username, String superhero, int level, String place, int points) {
        this.username = username;
        this.password = null;
        this.superhero = superhero;
        this.level = level;
        this.points = points;
        this.place = place;       
    }  
    
}
