/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

/**
 *
 * @author Felipe
 */
public class Nodo {
    int id;
    int simbolo;
    int nivel;
    String texto;
    int padre;
    int hijo;
    
    public Nodo (int id, int nivel, String texto, int padre){
        this.id = id;
        this.nivel = nivel;
        this.texto = texto;
        this.padre = padre;
        this.simbolo = -1;
    }
    public Nodo (int id, int nivel, String texto, int padre, int simbolo){
        this.id = id;
        this.nivel = nivel;
        this.texto = texto;
        this.padre = padre;
        this.simbolo = simbolo;
    }
    public String print(){
        return "id: " +this.id+ ", nivel: "+this.nivel+ ", texto: "+this.texto+ ", padre: "+this.padre;
    }
    
    public Nodo (int id, int nivel, String texto){
        this.id = id;
        this.nivel = nivel;
        this.texto = texto;
        this.padre = -1;
        this.simbolo = -1;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getSimbolo(){
        return this.simbolo;
    }
    
    public int getNivel(){
        return this.nivel;
    }
    
    public String getTexto(){
        return this.texto;
    }
    
    public int getPadre(){
        return this.padre;
    }
    
    public int getHijo(){
        return this.hijo;
    }
    
    
    public void setHijo(int hijo){
        this.hijo = hijo;
    }
}
