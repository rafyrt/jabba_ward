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
        boolean b = isLines();
        b = b && isEOF();
        return  b ;
    }
    boolean isEOF(){
        return symbols.get(posicion).getCategoria().equals("EOF");
    }
    boolean isEmpty(){
        return symbols.get(posicion).getCategoria().equals("EOL")||symbols.get(posicion).getValor().equals("}")||isEOF();
    }
    boolean isLines(){
        if (isLine()) {
            return isLines();
        }else if(isEmpty())
            return true;
        return false;
    }
    boolean isLine(){
        return isDecl()|| isAsign();
    }
    boolean isIf(){
        if (symbols.get(posicion).getValor().equals("do")) {
            if (symbols.get(posicion + 1).getValor().equals("(")) {
                if (isLogic()) {
                    if (symbols.get(posicion +2).getValor().equals(")")) {

                    }
                }
            }
        }
        return false;
    }
    boolean isLogic(){
        return false;
    }
    public boolean isDecl(){
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
            posicion++;
                if (isID()) {
                    posicion++;
                    if(symbols.get(posicion).getCategoria().equals("EOL")){
                        posicion++;
                        return true;                                 
                    }
                    return false;
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
                if(isCalc()){
                    if(symbols.get(posicion).getCategoria().equals("EOL")) {
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
    boolean isVar( ){
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
