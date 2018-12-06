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
public class Tuplas {
    String operador;
    String operando1;
    String operando2;
    String resultado;
    String L;
    int posNodos;
    int posSimbolos;
    
    public Tuplas(String operador, String operando1, String operando2, String resultado){
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
    }
    
    public Tuplas(String operador, String operando1, String resultado){
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = " ";
        this.resultado = resultado;
    }
    
        public Tuplas(String operador, String resultado){
        this.operador = operador;
        this.operando1 = " ";
        this.operando2 = " ";
        this.resultado = resultado;
    }
    
    
    public Tuplas(String L, int posNodos, int posSim){
        this.L = L;
        this.posNodos = posNodos;
        this.posSimbolos = posSim;
    }
    
    public String getL(){
        return this.L;
    }
    
    public String getTuplas(){
        if (this.L==null){
        return "(" + this.operador + ", " + this.operando1 + ", " + this.operando2 +  ", " + this.resultado + ")";
        }
        return this.L + ":";
        
    }
}
