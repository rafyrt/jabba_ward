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
    int posicion;
    
    public ParserRafa(ArrayList<Symbol> symbols){
        this.symbols = symbols;
    }
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
    boolean isMain(){
        if (symbols.get(posicion).getValor().equals("youngling")) {
            posicion++;
            if(symbols.get(posicion).getValor().equals("iAmTheSenate")){
                posicion++;
                if(symbols.get(posicion).getValor().equals("(")){
                    posicion++;
                    if(symbols.get(posicion).getValor().equals(")")){
                        posicion++;
                        if(symbols.get(posicion).getValor().equals("{")){
                            posicion++;
                            if(isLines()){
                                if(symbols.get(posicion).getValor().equals("}")){
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
    boolean isLines(){
        if (isLine()) {
            return isLines();
        }else if(isEmpty())
            return true;
        return false;
    }
    boolean isLine(){
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
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
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
        }
        return false;
    }
    
    boolean isAsign(){
        if (isID()) {
            posicion++;
            if (symbols.get(posicion).getCategoria().equals("asignacion")) {
                posicion++;
                if(isComp()){
                    if(isEOL()) {
                        posicion++;
                        return true;
                    } 
                    return false;
                }
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