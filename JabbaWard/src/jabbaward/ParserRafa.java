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
    
    public ParserRafa(ArrayList<Symbol> symbols){
        this.symbols = symbols;
    }
    boolean isLines( int posicion){
        if (isLine(posicion)) {
            
            
        }
        return false;
    }
    boolean isLine( int posicion){
        return isDecl(posicion);
    }
    public boolean isDecl( int posicion){
        int offset = 0;
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
            if (isID(posicion + 1) && symbols.get(posicion+2).getCategoria().equals("EOL")) {
                    return true;                                 
            }
            return isAsign( posicion + 1);
        }
        return false;
    }
    
    boolean isAsign( int posicion){
        if (isID( posicion)) {
            if (symbols.get(posicion+1).getCategoria().equals("asignacion")) {
                return isCalc(posicion +2);
            }
        }
        return false;
    }
    boolean isCalc( int posicion){
        if (isVar( posicion)){
            if (symbols.get(posicion + 1).getCategoria().equals("operador aritmetico")) {
                return isCalc( posicion + 2);
            }
            return true;
        }
        return false;
    }
    boolean isVar( int posicion){
        return isID( posicion) || isConstant( posicion);
    }
    boolean isConstant( int posicion){
        return symbols.get(posicion).getCategoria().equals("clones") || symbols.get(posicion).getCategoria().equals("lightsaber")
                || symbols.get(posicion).getCategoria().equals("ewok") || symbols.get(posicion).getCategoria().equals("wookie");
    }
    boolean isID( int posicion){
        return symbols.get(posicion).getCategoria().equals("id");
    }
    
}
