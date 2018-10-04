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
        String twoSome = "";
        String current;
        String space = " ";
        String c = "comentario";
        String w = "wookie";
        int num = 0;
        boolean first = true;
        for (int i = 0; i < list.length; i++) {
            Symbol entry;
            String categoria;
            String newString;
            current = list[i];
            CompareRegex compareTest = new CompareRegex();
            categoria = compareTest.Comparator(current);
            if((c.equals(categoria)||w.equals(categoria)) && first == true){
                
                twoSome+=current;
                first = false;
            }
            else if ("NOT FOUND".equals(categoria) && first == false){
                newString = " " + current;
                twoSome+=newString;
                newString = null;
            }
            
            else if((c.equals(categoria)||w.equals(categoria)) && first == false){
                newString = " " + current;
                twoSome+=newString;
                first = true;
                current = twoSome;
                twoSome = "";
                num++;
                entry = new Symbol(num, categoria, current);
                tabla.add(entry);
                
            }
            else{
            num++;
            entry = new Symbol(num, categoria, current);
            tabla.add(entry);
            }
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