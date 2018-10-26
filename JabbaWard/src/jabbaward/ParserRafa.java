/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class ParserRafa {
    ArrayList<Symbol> symbols;
    public ArrayList<Nodo> tree;
    int posicion;
    int id = -1;
    int nivel = 0;
    int padre = 0;
    int hijo, nuevaPosicion;
    Nodo inicial = new Nodo(0, 0, "S");
    Nodo nodo;
    public ParserRafa(ArrayList<Symbol> symbols){
        this.symbols = symbols;
    }
    public boolean isS(){
        tree = new ArrayList<>();
        posicion = 0;
        nivel = 0;
        tree.add(new Nodo(++id, nivel, "S"));
        padre = id;
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
    boolean isMain(){
        if(isEOF()) return true;
        nivel++;
        
        nodo = new Nodo(++id,nivel,"MAIN",padre);
        tree.add(nodo);
        padre= id;
        if (symbols.get(posicion).getValor().equals("youngling")) {
            nodo = new Nodo(++id,nivel,"void",padre);
            tree.add(nodo);
            posicion++;
            if(symbols.get(posicion).getValor().equals("iAmTheSenate")){
                nodo = new Nodo(++id,nivel,"main",padre);
            tree.add(nodo);
                posicion++;
                if(symbols.get(posicion).getValor().equals("(")){
                    nodo = new Nodo(++id,nivel,"(",padre);
            tree.add(nodo);
                    posicion++;
                    if(symbols.get(posicion).getValor().equals(")")){
                        nodo = new Nodo(++id,nivel,")",padre);
            tree.add(nodo);
                        posicion++;
                        if(symbols.get(posicion).getValor().equals("{")){
                            nodo = new Nodo(++id,nivel,"{",padre);
            tree.add(nodo);
                            posicion++;
                            if(isLines(padre)){
                                if(symbols.get(posicion).getValor().equals("}")){
                                    nodo = new Nodo(++id,nivel,"}",padre);
            tree.add(nodo);
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
    boolean isLines(int padreL){
        if (isEmpty()) {
            return true;
        }
        nivel++;
        nodo = new Nodo(++id,nivel,"lines",padreL);
        tree.add(nodo);
        padre = id;
        if (isLine()) {
            nivel--;
            return isLines(padreL);
        }
        return false;
    }
    boolean isLine(){
        nivel++;
        nodo = new Nodo(++id,nivel,"line",padre);
        tree.add(nodo);
        padre = id;
        int tempP = posicion;
        int tempSize = tree.size();
        if (isDecl()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if(isAsign())
        {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isIf()){
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if(isPrint()){
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        return false;
    }
    boolean isIf(){
        nivel++;
        nodo = new Nodo(++id,nivel,"IF",padre,posicion);
        tree.add(nodo);
        padre = id;
        if (symbols.get(posicion).getValor().equals("do")) {
            nodo = new Nodo(++id,nivel,"do",padre,posicion);
            tree.add(nodo);
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id,nivel,"(",padre,posicion);
                tree.add(nodo);
                posicion++;
                if (isComp()) {
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id,nivel,")",padre,posicion);
                        tree.add(nodo);
                        posicion++;
                        if (symbols.get(posicion).getValor().equals("{")) {
                            nodo = new Nodo(++id,nivel,"{",padre,posicion);
                            tree.add(nodo);
                            posicion++;
                            if(isLines(padre)){
                                if (symbols.get(posicion).getValor().equals("}")) {
                                    nodo = new Nodo(++id,nivel,"}",padre,posicion);
                                    tree.add(nodo);
                                    posicion++;
                                    nivel--;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        nivel--;
        return false;
    }
    boolean isLogic(){
        if (isComp()) { 
            return true;            
        }
        return false;
    }
    public boolean isDecl(){
        nivel++;
        nodo = new Nodo(++id,nivel,"decl",padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
            nodo = new Nodo(++id,nivel,"tipo",padre);
            tree.add(nodo);
            posicion++;
            int tempP = posicion;
            int tempSize = tree.size();
            if (isAsign()) {
                return true;
            }
            removeNodes(tempSize);
            posicion = tempP;
            padre = temp;
                if (isID()) {
                    if(isEOL()) {
                        tree.add(new Nodo(++id,nivel,"EOL",padre));
                        posicion++;
                        nivel--;
                        return true;
                    }
                    tree.add(new Nodo(++id,nivel,"missing EOL",padre));
                        nivel--;
                    posicion++;
            }
            posicion--;
        }
        return false;
    }
    
    boolean isAsign(){
        nivel++;
        nodo = new Nodo(++id,nivel,"asign",padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (isID()) {
            if (symbols.get(posicion).getCategoria().equals("asignacion")) {
                nodo = new Nodo(++id,nivel+1,"=",padre);
                tree.add(nodo);
                posicion++;
                if(isComp()){
                    padre = temp;
                    if(isEOL()) {
                        tree.add(new Nodo(++id,nivel,"EOL",padre));
                        posicion++;
                        nivel--;
                        return true;
                    }
                    tree.add(new Nodo(++id,nivel,"missing EOL",padre));
                    posicion++;
                        nivel--;
                    return false;
                }
            }
        }
        return false;
    }
    boolean isCalc(){
        nivel++;
        nodo = new Nodo(++id,nivel,"calc",padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (isVar()){
            padre = temp;
            if (symbols.get(posicion).getCategoria().equals("operador aritmetico")) {
                nodo = new Nodo(++id,nivel+1,"op_arit",padre);
                tree.add(nodo);
                posicion++;
                if(isCalc()){
                    nivel--;
                    return true;
                }
                nivel--;
                return false;
            }
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }
    boolean isComp(){
        nivel++;
            nodo = new Nodo(++id,nivel,"comp",padre);
            tree.add(nodo);
            padre = id;
        int temp = padre;
        if (isCalc()){
        padre = temp;
            if (symbols.get(posicion).getCategoria().equals("comparacion aritmetica")) {
                nodo = new Nodo(++id,nivel,"comp_arit",padre);
                tree.add(nodo);
                posicion++;
                nivel--;
                return isCalc();
            }
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }
    boolean isPrint(){
        nivel++;
        nodo = new Nodo(++id,nivel,"PRINT",padre);
            tree.add(nodo);
            padre = id;
        if (symbols.get(posicion).getValor().equals("helloThere")) {
            nivel++;
            posicion++;nodo = new Nodo(++id,nivel,"hlloThere",padre);
            tree.add(nodo);
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id,nivel,"(",padre);
            tree.add(nodo);
                posicion++;
                nivel--;
                if (isVar()) {
                    nivel++;
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id,nivel,")",padre);
            tree.add(nodo);
                        posicion++;
                        if (isEOL()) {
                             tree.add(new Nodo(++id,nivel,"EOL",padre));
                            posicion++;
                            nivel--;
                            return true;
                        }
                    }
                }
            }            
        }
        return false;
    }
    boolean isVar(){
        nivel++;
        nodo = new Nodo(++id,nivel,"var",padre);
        tree.add(nodo);
            padre = id;
        if (isID()||isConstant())
        { 
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }
    boolean isConstant(){
        if(symbols.get(posicion).getCategoria().equals("clones") || symbols.get(posicion).getCategoria().equals("lightsaber")
                || symbols.get(posicion).getCategoria().equals("ewok") || symbols.get(posicion).getCategoria().equals("wookie")){
            nodo = new Nodo(++id,nivel+1,"Constant",padre,posicion);
            tree.add(nodo);
            posicion++;
            return true;
        }
        return false;
    }
    boolean isID(){
        if(symbols.get(posicion).getCategoria().equals("id")){
            nodo = new Nodo(++id,nivel+1,"ID",padre,posicion);
            tree.add(nodo);
            posicion++;
            return true;
        }
        return false;
    }
    void removeNodes(int tempS){
        while(tempS<tree.size()){
            id--;
            tree.remove(tree.size()-1);
        }
    }
    
}
