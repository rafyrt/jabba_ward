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
    ArrayList<Nodo> currentTree = new ArrayList<>();
    int id = 0;
    int nivel = 0;
    int padre = 0;
    int currentPadre;
    int hijo, nuevaPosicion;
    int currentNivel = 0;
    int nivelOrig, idOrig;
    String texto;
    Nodo nodo;
//    Nodo nodito = new Nodo (1, 1, "x", 1);
    ArrayList<Symbol> symbolsFinal = new ArrayList<>();
    int posicion;
    ArrayList<Symbol> symbols = new ArrayList<>();
//    tree.add(nodito);
    Nodo inicial = new Nodo(id, nivel, "S");
    
    public  Parser(ArrayList<Symbol> symbols){
        this.symbolsFinal = symbols;
        
//        compare();
//        return tree;
    }
    //symbols.get(i)
    
    public ArrayList<Nodo> compare(){
        tree.add(inicial);
        //currentTree.add(inicial);
        //nivel++;
        for (int i = 0; i < symbolsFinal.size(); i++) {
       // System.out.println(i);
            if(symbolsFinal.get(i).getValor().equals("{")  || symbolsFinal.get(i).getCategoria().equals("EOL")){
                posicion = 0;
               // System.out.println("Entra al IFFF");
                if (isMain()){
                    //System.out.println("YEHAW SI ES MAIN");
                    tree.addAll(currentTree);
                    i=posicion;
                    symbols.clear();
                    //System.out.println(symbolsFinal.get(i).getValor());
                    
                }
                else if(isLines()){
                    System.out.println("YEHAW SI LINES");
                }
                
                if (symbolsFinal.get(i).getValor().equals("{")){
                    System.out.println("OPEN BRACKET");
                    id++; 
                    nodo = new Nodo(id, nivel, symbolsFinal.get(i).getCategoria(), id-1);
                    tree.add(nodo);
                    id++; nivel++;
                    nodo = new Nodo(id, nivel, symbolsFinal.get(i).getValor(), id-1);
                    tree.add(nodo);
                    i++;
                    
                } else if (symbolsFinal.get(i).getCategoria().equals("EOL")){
                    id++;
                    nodo = new Nodo(id, nivel, symbolsFinal.get(i).getCategoria(), id-1);
                    tree.add(nodo);
                    id++; nivel++;
                    nodo = new Nodo(id, nivel, symbolsFinal.get(i).getValor(), id-1);
                    tree.add(nodo);
                    
                    i++;

                }
            } else {
            symbols.add(symbolsFinal.get(i));
            //System.out.println(symbolsFinal.get(i).getValor());
            }
        }
        //isCalc();
        return tree;
    }
    
//    public int reglas(){
//    Symbol currentSymbol = symbols.get(posicion);
//    String currentValue = currentSymbol.getValor();
//    String currentCategory = currentSymbol.getCategoria();
//    
//        if (currentValue.equals("youngling")) {
//            
//            return posicion;            
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else if (true) {
//            return posicion;            
//        } else if (true) {
//            return posicion;
//        } else {
//        
//            return posicion;    
//        }
//        
//        
//    }
    
    
//    public boolean isS(){
//        posicion = 0;
//        return isCalc();
//    }
    public boolean isS(){
        posicion = 0;
        boolean b = isMain();
        b = b && isEOF();
        return  b ;
    }
    boolean isEOF(){
        return symbols.get(posicion).getCategoria().equals("EOF");
    }
    boolean isEOL(){
        return symbols.get(posicion).getCategoria().equals("EOL");
    }
    boolean isEmpty(){
        return isEOL()||symbols.get(posicion).getValor().equals("}")||isEOF();
    }
    
    void addCurrent(int idC, int nivelC, String textoC, int padreC){
        nodo = new Nodo(idC, nivelC, textoC, padreC);
        currentTree.add(nodo);
    }
    
    void isFalse(){
        id= idOrig; 
    }
    
    boolean isMain(){
        id++; nivel++;
        nivelOrig = nivel;
        addCurrent(id, nivel, "Main", id-1);
        if (symbols.get(posicion).getValor().equals("youngling")) {
            id++; nivel++;
            addCurrent(id, nivel, symbols.get(posicion).getCategoria(), id-1);
            id++;
            addCurrent(id, nivel+1, symbols.get(posicion).getValor(), id);
            posicion++;
            if(symbols.get(posicion).getValor().equals("iAmTheSenate")){
                System.out.println("senado");
                id++;
                addCurrent(id, nivel, symbols.get(posicion).getCategoria(), id-1);
                id++;
                addCurrent(id, nivel+1, symbols.get(posicion).getValor(), id);
                posicion++;
                if(symbols.get(posicion).getValor().equals("(")){
                    id++;
                    addCurrent(id, nivel, symbols.get(posicion).getCategoria(), id-1);
                    id++;
                    addCurrent(id, nivel+1, symbols.get(posicion).getValor(), id);
                    posicion++;
                    if(symbols.get(posicion).getValor().equals(")")){
                        id++;
                        addCurrent(id, nivel, symbols.get(posicion).getCategoria(), id-1);
                        id++;
                        addCurrent(id, nivel+1, symbols.get(posicion).getValor(), id);
                        posicion++;
                        nivel = nivelOrig+1;
                        return true;
                       // if(symbols.get(posicion).getValor().equals("{")){
                         //   posicion++;
                            //return true;
//                            if(isLines()){
//                                if(symbols.get(posicion).getValor().equals("}")){
//                                    posicion++;
//                                    return true;
//                                }
//                            }
                        //}
                    }
                }
            }
        }
        return false;
    }
    boolean isLines(){
        id++; nivel++;
        nivelOrig = nivel;
        addCurrent(id, nivel, "Lines", id-1);
        if (isLine()) {

            return isLines();
        }else if(isEmpty())
            return true;
        return false;
    }
    boolean isLine(){
        id++; nivel++;
        nivelOrig = nivel;
        addCurrent(id, nivel, "Line", id-1);
        return isDecl()|| isAsign()||isIf()||isPrint();
    }
    boolean isIf(){
        if (symbols.get(posicion).getValor().equals("do")) {
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                posicion++;
                if (isComp()) {
                    if (symbols.get(posicion).getValor().equals(")")) {
                        posicion++;
                        if (symbols.get(posicion).getValor().equals("{")) {
                            posicion++;
                            if(isLines()){
                                if (symbols.get(posicion).getValor().equals("}")) {
                                    posicion++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean isLogic(){
        if (isComp()) { 
            return true;            
        }
        return false;
    }
    public boolean isDecl(){
        idOrig = id;
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
            id++; nivel++;
            nivelOrig = nivel;
            addCurrent(id, nivel, "decl", id-1);
            posicion++;
                if (isID()) {
                    posicion++;
                    if(isEOL()){
                        posicion++;
                        return true;                                 
                    }
            }
            posicion--;
            return isAsign();
        } else {
            isFalse();
            return false;
        }

    }
    
    boolean isAsign(){
        if (isID()) {
            posicion++;
            if (symbols.get(posicion).getCategoria().equals("asign")) {
                posicion++;
                if(isComp()){
                    //if(isEOL()) {
                      //  posicion++;
                        return true;}
                   // } 
                    return false;
                
            }
        }
        return false;
    }
    boolean isCalc(){
        if (isVar()){
            posicion++;
            if (symbols.get(posicion).getCategoria().equals("operador aritmetico")) {
                posicion++;
                return isCalc();
            }
            return true;
        }
        return false;
    }
    boolean isComp(){
        if (isCalc()){
            if (symbols.get(posicion).getCategoria().equals("comparacion aritmetica")) {
                posicion++;
                return isCalc();
            }
            return true;
        }
        return false;
    }
    boolean isPrint(){
        if (symbols.get(posicion).getValor().equals("helloThere")) {
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                posicion++;
                if (isVar()) {
                    posicion++;
                    if (symbols.get(posicion).getValor().equals(")")) {
                        posicion++;
                        if (isEOL()) {
                            posicion++;
                            return true;
                        }
                    }
                }
            }            
        }
        return false;
    }
    boolean isVar(){
        return isID( ) || isConstant( );
    }
    boolean isConstant(){
        return symbols.get(posicion).getCategoria().equals("clones") || symbols.get(posicion).getCategoria().equals("lightsaber")
                || symbols.get(posicion).getCategoria().equals("ewok") || symbols.get(posicion).getCategoria().equals("wookie");
    }
    boolean isID(){
        return symbols.get(posicion).getCategoria().equals("id");
    }
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////////////
    //OLD
    ///////////////////////////////////////////////////////////////////////////////////
//    public int compare2(ArrayList<Symbol> symbols){
//        //Nodo nodito = null;
//        
//        if (symbols.get(posicion).getCategoria().equals("tipo de dato")  && symbols.get(posicion+1).getCategoria().equals("id") 
//                && symbols.get(posicion+2).getCategoria().equals("asignacion")
//                && 
//                (symbols.get(posicion+3).getCategoria().equals("clones") || symbols.get(posicion+3).getCategoria().equals("lightsaber")
//                || symbols.get(posicion+3).getCategoria().equals("ewok") || symbols.get(posicion+3).getCategoria().equals("wookie")) 
//                && symbols.get(posicion+4).getCategoria().equals("EOL")){
//            return nuevaPosicion = decl(symbols, posicion, posicion+4);
//        }
//        return posicion;
//    }
//    
//   
//    public int decl(ArrayList<Symbol> symbols, int min, int max){
//        int padreAnterior = padre;
//        int nivelAnterior = nivel;
//        id++;
//        nivel++;
//        nodo = new Nodo(id, nivel, "decl", padre);
//        tree.add(nodo);
//        padre++;
//        nivel++;
//        int newPadre;
//        for (int i = min; i < max+1; i++) {
//            id++;
//            nodo = new Nodo(id, nivel, symbols.get(i).getCategoria(), padre);
//            tree.add(nodo);
//            tree.get(padre).setHijo(id);
//            newPadre = id;
//            id++;
//            nodo = new Nodo(id, nivel+1, symbols.get(i).getValor(), newPadre);
//            tree.add(nodo);
//            tree.get(newPadre).setHijo(id);
//        }
//        padre = padreAnterior;
//        nivel = nivelAnterior;
//        return max;
//    }
//    
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
    
    

