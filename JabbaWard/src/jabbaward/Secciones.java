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
public class Secciones {

    String L;
    int posNodos;
    int posSimbolos;
    
    public Secciones(String L, int posNodos, int posSimbolos ){
        this.L = L;
        this.posNodos = posNodos;
        this.posSimbolos = posSimbolos;
    }
    
    public int getNodo(){
        return this.posNodos;
    }
    
    public int getSimbolo(){
        return this.posSimbolos;
    }
}
