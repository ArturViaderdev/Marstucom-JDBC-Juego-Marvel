/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Clase que define el tipo de dato villano
 * @author alu2017363
 */
public class Enemy {
    //Nombre del villano
    private String name;
    //Debilidad del villano
    private String debility;
    //Nivel del villano
    private int level;
    //Lugar del villano
    private String place;

    /**
     * Devuelve el nombre del villano
     * @return Nombre del villano
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre del villano
     * @param name Nuevo nombre para el villano
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la debilidad del villano
     * @return Debilidad
     */
    public String getDebility() {
        return debility;
    }

    /**
     * Cambia la debilidad del villano
     * @param debility Debilidad
     */
    public void setDebility(String debility) {
        this.debility = debility;
    }

    /**
     * Devuelve el nivel del villano
     * @return Nivel del villano
     */
    public int getLevel() {
        return level;
    }

    /**
     * Cambia el nivel del villano
     * @param level Nuevo nivel del villano
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Devuelve el lugar del villano
     * @return Lugar del villano
     */
    public String getPlace() {
        return place;
    }

    /**
     * Cambia el lugar del villano
     * @param place Nuevo lugar
     */
    public void setPlace(String place) {
        this.place = place;
    }
    
    /**
     * Constructor
     * @param name Nombre del villano
     * @param debility Debilidad del villano
     * @param level Nivel del villano
     * @param place Lugar del villano
     */
    public Enemy(String name, String debility, int level, String place) {
        this.name = name;
        this.debility = debility;
        this.level = level;
        this.place = place;
    }
    
    /**
     * Conversión a string
     * @return Clase a string
     */
    @Override
    public String toString() {
        return "Enemy{" + "name=" + name + ", debility=" + debility + ", level=" + level + ", place=" + place + '}';
    }
}
