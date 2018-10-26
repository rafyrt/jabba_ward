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
        String oneCW = "";
        String current;
        String space = " ";
        String c = "comentario";
        String w = "wookie";
        String l = "library";
        int num = 0;
        boolean notInCW = true;
        for (int i = 0; i < list.length; i++) {
            Symbol entry;
            String categoria;
            String newString;
            current = list[i];
            CompareRegex compareTest = new CompareRegex();
            categoria = compareTest.Comparator(current);
            if((c.equals(categoria)||w.equals(categoria)) && notInCW == true){

                oneCW+=current;
                notInCW = false;
            }

            
            else if((c.equals(categoria)||w.equals(categoria)) && notInCW == false){
                newString = " " + current;
                oneCW+=newString;
                notInCW = true;
                current = oneCW;
                oneCW = "";
                num++;
                entry = new Symbol(num, categoria, current);
                tabla.add(entry);
            }
            
            else if (notInCW == false){
                newString = " " + current;
                oneCW+=newString;
            }
            else{
            num++;
            entry = new Symbol(num, categoria, current);
            tabla.add(entry);
            }
        }
        tabla.add(new Symbol(tabla.size()+1, "EOF", "EOF"));
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