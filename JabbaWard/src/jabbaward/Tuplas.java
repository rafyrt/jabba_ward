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
    String trueFalse;

    public Tuplas(String operador, String operando1, String operando2, String resultado) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
    }

    public Tuplas() {

    }

    public Tuplas(String operador, String operando1, String operando2, String resultado, boolean trueFalse) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
        this.trueFalse = "{true|false}";
        this.L = null;
    }

    public Tuplas(String operador, String operando1, String resultado) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = " ";
        this.resultado = resultado;
        this.L = null;
    }

    public Tuplas(String operador, String resultado) {
        this.operador = operador;
        this.operando1 = " ";
        this.operando2 = " ";
        this.resultado = resultado;
        this.L = null;
    }

    public Tuplas(String L) {
        this.L = L;
        //this.posNodos = posNodos;
        //this.posSimbolos = posSim;
    }

    public String getL() {
        return this.L;
    }

    public String getOp() {
        return this.operador;
    }

    public String getOp1() {
        return this.operando1;
    }

    public String getOp2() {
        return this.operando2;
    }

    public String getR() {
        return this.resultado;
    }

    public String getTuplas() {
        if (this.L == null) {
            if (this.trueFalse == null) {
                return "(" + this.operador + ", " + this.operando1 + ", " + this.operando2 + ", " + this.resultado + ")";
            } else {
                return "(" + this.operador + ", " + this.operando1 + ", " + this.operando2 + ", " + this.resultado + ")" + this.trueFalse;
            }
        } else {
            return this.L;
        }

    }
}
