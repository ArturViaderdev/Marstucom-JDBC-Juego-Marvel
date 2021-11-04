/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Clase que define el tipo de dato gema.
 * @author alu2017363
 */
public class Gem {
    //Nombre de la gema
    private String name;
    //Usuario de la gema
    private String user;
    //Poseedor de la gema
    private String owner;
    //Lugar de la gema
    private String place;
    
    /**
     * Obtiene el nombre de la gema
     * @return Nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre de la gema
     * @param name Nombre de la gema
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el usuario de la gema
     * @return Usuario
     */
    public String getUser() {
        return user;
    }

    /**
     * Cambia el usuario de la gema
     * @param user Nuevo usuario
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Obtiene el propietario de la gema
     * @return Propietario
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Cambia el propietario de la gema
     * @param owner Nuevo propietario
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Obtiene el lugar de la gema
     * @return Lugar
     */
    public String getPlace() {
        return place;
    }

    /**
     * Cambia el lugar de la gema
     * @param place Nuevo lugar
     */
    public void setPlace(String place) {
        this.place = place;
    }
 
    /**
     * Constructor
     * @param name Nombre de la gema
     * @param user Usuario de la gema
     * @param owner Poseedor de la gema
     * @param place Lugar de la gema
     */
    public Gem(String name, String user, String owner, String place) {
        this.name = name;
        this.user = user;
        this.owner = owner;
        this.place = place;
    }
    
    /**
     * Conversión a string
     * @return Clase a string
     */
     @Override
    public String toString() {
        return "Gem{" + "name=" + name + ", user=" + user + ", owner=" + owner + ", place=" + place + '}';
    }
}
