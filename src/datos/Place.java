/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Clase que define el tipo de dato lugar
 * @author alu2017363
 */
public class Place {
    //Nombre del lugar
    private String name;
    //Descripción del lugar
    private String description;
    //Nombre del lugar situado al norte.
    private String north;
    //Nombre del lugar situado al sur.
    private String south;
    //Nombre del lugar situado al oeste.
    private String west;
    //Nombre del lugar situado al este.
    private String east;
    
    /**
     * Constructor
     * @param name Nombre del lugar
     * @param description Descripción del lugar
     * @param north Nombre del lugar situado al norte
     * @param south Nombre del lugar situado al sur
     * @param west Nombre del lugar situado el oeste
     * @param east Nombre del lugar situado al este
     */
    public Place(String name, String description, String north, String south, String west, String east) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }
 
    /**
     * Devuelve el nombre del lugar
     * @return Nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre del lugar
     * @param name Nuevo nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción del lugar
     * @return Descripción
     */
    public String getDescription() {
        return description;
    }

    /**
     * Cambia la descripción del lugar
     * @param description Nueva descripción
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene el nombre del lugar situado al norte
     * @return Nombre de Lugar norte
     */
    public String getNorth() {
        return north;
    }

    /**
     * Cambia el lugar situado al norte.
     * @param north  Nombre del nuevo lugar norte.
     */
    public void setNorth(String north) {
        this.north = north;
    }

    /**
     * Obtiene el nombre del lugar situado al sur.
     * @return Nombre lugar sur
     */
    public String getSouth() {
        return south;
    }

    /**
     * Cambia el lugar situado al sur
     * @param south Nombre de lugar
     */
    public void setSouth(String south) {
        this.south = south;
    }

    /**
     * Obtiene el nombre del lugar situado al oeste
     * @return Nombre de lugar oeste
     */
    public String getWest() {
        return west;
    }

    /**
     * Cambia el lugar situado al oeste
     * @param west  Nombre del nuevo lugar
     */
    public void setWest(String west) {
        this.west = west;
    }

    /**
     * Obtiene el nombre del lugar situado al este
     * @return Nombre de lugar este
     */
    public String getEast() {
        return east;
    }

    /**
     * Cambia el lugar situado al este
     * @param east Nuevo lugar
     */
    public void setEast(String east) {
        this.east = east;
    }
 
    /**
     * Conversión a string
     * @return Clase a string
     */
    @Override
    public String toString() {
        return "Place{" + "name=" + name + ", description=" + description + ", north=" + north + ", south=" + south + ", west=" + west + ", east=" + east + '}';
    }
}
