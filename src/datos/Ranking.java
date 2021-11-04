/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;



/**
 * Clase que define el tipo de dato del ranking.
 * Es ordenable.
 * @author djvat
 */
public class Ranking implements Comparable<Ranking>  {
    //Nombre de usuario
    private String usuario;
    //Superheroe
    private String superheroe;
    //Número de gemas
    private int gemas;
    //Nivel
    private int nivel;
    //Puntos
    private int puntos;
    
    /**
     * Conversión a string
     * @return Clase a string
     */
    @Override
    public String toString() {
        return "Ranking{" + "usuario=" + usuario + ", superheroe=" + superheroe + ", gemas=" + gemas + ", nivel=" + nivel + ", puntos=" + puntos + '}';
    }
    
    /**
     * Constructor
     * @param usuario Usuario
     * @param superheroe Superheroe
     * @param gemas Número de gemas
     * @param nivel Nivel
     * @param puntos Puntos
     */
    public Ranking(String usuario, String superheroe, int gemas, int nivel, int puntos) {
        this.usuario = usuario;
        this.superheroe = superheroe;
        this.gemas = gemas;
        this.nivel = nivel;
        this.puntos = puntos;
    }

    /**
     * Obtiene el usuario
     * @return Usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Cambia el usuario
     * @param usuario Nuevo usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el superheroe
     * @return Superheroe
     */
    public String getSuperheroe() {
        return superheroe;
    }

    /**
     * Cambia el superheroe
     * @param superheroe Nuevo superheroe
     */
    public void setSuperheroe(String superheroe) {
        this.superheroe = superheroe;
    }

    /**
     * Obtiene el número de gemas
     * @return Número de gemas
     */
    public int getGemas() {
        return gemas;
    }

    /**
     * Cambia el número de gemas
     * @param gemas Nuevo número de gemas
     */
    public void setGemas(int gemas) {
        this.gemas = gemas;
    }

    /**
     * Obtiene el nivel
     * @return Nivel
     */
    public int getNivel() {
        return nivel;
    }
    
    /**
     * Cambia el nivel
     * @param nivel Nuevo nivel
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene los puntos
     * @return Puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Cambia los puntos
     * @param puntos Puntos
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Compara para ordenar de forma descendente por gemas, nivel y puntos.
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Ranking t) {
        if(gemas==t.getGemas())
        {
            if(nivel==t.getNivel())
            {
                return (Integer.compare(t.getPuntos(), puntos));         
            }
            else
            {
                return (Integer.compare(t.getNivel(), nivel));
            }
        }
        else
        {
            return (Integer.compare(t.getGemas(), gemas));
        }     
    } 
}
