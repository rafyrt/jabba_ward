/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

/**
 *
 * @author Rafael, Felipe & Cesar
 */

import java.util.regex.*;
public class LexicalAnalyzer {
    String archivoFuente;
    String tablaSimbolos;
    public LexicalAnalyzer(String archivo){
        archivoFuente = archivo;
    }
    
    public void read(){
        
    }
}

class MDD{
    int posicionInicial, posicionActual;
    public MDD(){
        
    }
    /*
    9 comentario
    12 comparacion aritmetica
    10 comparacion logica
    11 palabra reservada
    0 asignacion
    1 EOL
    2 ewok
    3 wookie
    4 aritmetico
    5 agrupador
    7 drones
    8 clones
    6 id
        
    */
    public int categoriza(String code, int pIni){
        boolean encontro = false;
        while(!encontro){
            
        }
        
        return 0;
    }
    
}