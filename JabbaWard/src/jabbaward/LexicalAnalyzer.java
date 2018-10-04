/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.util.ArrayList;

/**
 *
 * @author Rafael, Felipe & Cesar
 */

public class LexicalAnalyzer{

    static ArrayList<Symbol> Analyse(String[] list) {
        ArrayList<Symbol> tabla = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            Symbol entry = new Symbol(i, "taco", list[i]);
            tabla.add(entry);
        }
        return tabla;
    }

    String[] list;
    ArrayList<Symbol> tablaSimbolos = new ArrayList<>();
}