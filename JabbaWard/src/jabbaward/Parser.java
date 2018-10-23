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
public class Parser {
    ArrayList<Nodo> tree = new ArrayList<>();

    int id = 0;
    int nivel = 0;
    int padre = 0;
    int hijo, nuevaPosicion;
    String texto;
    Nodo nodo;
//    Nodo nodito = new Nodo (1, 1, "x", 1);

    int posicion = 0;
//    tree.add(nodito);
    Nodo inicial = new Nodo(id, nivel, "S");
    
    public ArrayList getParsed(ArrayList<Symbol> symbols){
        System.out.println("tamaño: " + symbols.size());
        tree.add(inicial);
        for (posicion = 0; posicion < symbols.size(); posicion++) {
            System.out.println(posicion);
            posicion = compare(symbols, posicion);
//            tree.add(nodo);
            padre=0;
        }
        return tree;
    }
    //symbols.get(i)
    
    public int compare(ArrayList<Symbol> symbols, int numero){
        //Nodo nodito = null;
        
        if (symbols.get(numero).getCategoria().equals("tipo de dato")  && symbols.get(numero+1).getCategoria().equals("id") 
                && symbols.get(numero+2).getCategoria().equals("asignacion")
                && 
                (symbols.get(numero+3).getCategoria().equals("clones") || symbols.get(numero+3).getCategoria().equals("lightsaber")
                || symbols.get(numero+3).getCategoria().equals("ewok") || symbols.get(numero+3).getCategoria().equals("wookie")) 
                && symbols.get(numero+4).getCategoria().equals("EOL")){
            return nuevaPosicion = decl(symbols, numero, numero+4);
        }
        return numero;
    }
    
    public int decl(ArrayList<Symbol> symbols, int min, int max){
        int padreAnterior = padre;
        int nivelAnterior = nivel;
        id++;
        nivel++;
        nodo = new Nodo(id, nivel, "decl", padre);
        tree.add(nodo);
        padre++;
        nivel++;
        int newPadre;
        for (int i = min; i < max+1; i++) {
            id++;
            nodo = new Nodo(id, nivel, symbols.get(i).getCategoria(), padre);
            tree.add(nodo);
            tree.get(padre).setHijo(id);
            newPadre = id;
            id++;
            nodo = new Nodo(id, nivel+1, symbols.get(i).getValor(), newPadre);
            tree.add(nodo);
            tree.get(newPadre).setHijo(id);
        }
        padre = padreAnterior;
        nivel = nivelAnterior;
        return max;
    }
    
//    public 
//  public void parse(LinkedList<Symbol> tokens){
//    this.tokens = (LinkedList<Symbol>) tokens.clone();
//    lookahead = this.tokens.getFirst();
//
//    expression();
//
//    
//  }
//  
//    private void nextToken(){
//    tokens.pop();
//    // at the end of input we return an epsilon token
//    if (tokens.isEmpty())
//      return;
//    else
//      lookahead = tokens.getFirst();
//  }
    
    
}
