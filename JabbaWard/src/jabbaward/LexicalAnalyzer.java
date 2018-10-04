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
            Symbol entry;
            String categoria;
            CompareRegex compareTest = new CompareRegex();
            categoria = compareTest.Comparator(list[i]);
            entry = new Symbol(i, categoria, list[i]);
            tabla.add(entry);
        }
        return tabla;
    }
//        private static String defineCategory(String valor){
//        String categoria;
//        //regex stuff goes here
//        switch (valor){
//            case "_\\w+": 
//                categoria = "id";
//                break;
//            case "~endor":
//                categoria = "EOL";
//                break;
//            case "=":
//                categoria = "asignacion";
//                break;
//            case "(((\\d*|\\d*\\.\\d*)E(\\+|\\-)\\d+)|\\d*\\.\\d+|\\d+)":
//                categoria = "clones";
//                break;
//            default:
//                throw new ArithmeticException("Rip, not found");
//        }
//        return categoria;
//    }
}