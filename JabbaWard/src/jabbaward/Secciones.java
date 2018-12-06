/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class Secciones {

    String L;
    int posNodos;
    int posSimbolos;
    ArrayList<Tuplas> tupis;
    ArrayList<Tuplas> tupis2;

    public Secciones(String L, int posNodos, int posSimbolos) {
        this.L = L;
        this.posNodos = posNodos;
        this.posSimbolos = posSimbolos;
        this.tupis = new ArrayList<Tuplas>();
        this.tupis2 = new ArrayList<Tuplas>();
    }

    public int getPosNodo() {
        return this.posNodos;
    }

    public ArrayList<Tuplas> getTupis() {
        return this.tupis;
    }

    public ArrayList<Tuplas> getTupis2() {
        return this.tupis2;
    }

    public int getPosSimbolo() {
        return this.posSimbolos;
    }

    public void addTuplas(ArrayList<Tuplas> tupas) {
        this.tupis = tupas;
    }

    public void addTuplas(ArrayList<Tuplas> tupas, ArrayList<Tuplas> tupas2) {
        this.tupis = tupas;
        this.tupis2 = tupas2;
    }

    public String print() {
        return this.L + " \t N:" + this.posNodos + " \t S: " + this.posSimbolos;
    }
}
