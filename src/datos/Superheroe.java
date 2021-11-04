/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Define el tipo de dato superheroe
 * @author alu2017363
 */
public class Superheroe {
    //Nombre del superheroe
    private String name;
    //Superpoder del superheroe
    private String superpower;

    /**
     * Obtiene el nombre del superheroes
     * @return Nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre del superheroe
     * @param name Nuevo nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el superpoder del superheroe
     * @return Superpoder
     */
    public String getSuperpower() {
        return superpower;
    }

    /**
     * Cambia el superpoder del superheroe
     * @param superpower Nuevo superpoder
     */
    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    /**
     * Constructor
     * @param name Nombre del superheroe
     * @param superpower Superpoder del superheroe
     */
    public Superheroe(String name, String superpower) {
        this.name = name;
        this.superpower = superpower;
    } 

    /**
     * Conversión a string
     * @return Clase a string
     */
    @Override
    public String toString() {
        return "Superheroe{" + "name=" + name + ", superpower=" + superpower + '}';
    }
    
}
