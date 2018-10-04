/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;
import java.util.regex.*;
/**
 *
 * @author Felipe
 */
public class CompareRegex {
    CategoriaLexica comentario = new CategoriaLexica("(<\\(-_-\\)>.*<\\(-_-\\)>|<\\(-_-\\)>)", "comentario");
    CategoriaLexica comparacionArtimetica = new CategoriaLexica("(>|<|YouWereLikeMyBrother|IHateYou)","comparacion aritmetica");
    CategoriaLexica comparacionLogica = new CategoriaLexica("(AND|OR)","operador logico");
    CategoriaLexica acceso = new CategoriaLexica("(sith|jedi)","acceso");
    CategoriaLexica palabraReservada = new CategoriaLexica("(_iAmTheSenate|returnOfTheJedi|youngling|doit|fett|jango|do|doNot|snoke|helloThere)","palabra reservada");
    CategoriaLexica tiposDeDatos = new CategoriaLexica("(clones|ewok|wookie|lightsaber)","tipo de dato");
    CategoriaLexica lightsaber = new CategoriaLexica("(on|off)", "lightsaber");
    CategoriaLexica asignacion = new CategoriaLexica("=", "asignacion");
    CategoriaLexica EOL = new CategoriaLexica("~endor","EOL");
    CategoriaLexica ewok = new CategoriaLexica("'.'","ewok");
    CategoriaLexica wookie = new CategoriaLexica("(\".*\"||\")","wookie");
    CategoriaLexica aritmetico = new CategoriaLexica("(\\+|\\-|\\*|\\^|\\/)","operador aritmetico");
    CategoriaLexica agrupador = new CategoriaLexica("(\\(|\\)|\\{|\\})","agrupador");
    CategoriaLexica clones = new CategoriaLexica("(((\\d*|\\d*\\.\\d*)E(\\+|\\-)\\d+)|\\d*\\.\\d+|\\d+)","clones");
    CategoriaLexica incremento = new CategoriaLexica("_\\w+\\+\\+", "incremento");
    CategoriaLexica id = new CategoriaLexica("_\\w+","id");
    

    
    public String Comparator(String original){
        if(compare(original,comentario)){return comentario.name;}
        if(compare(original,comparacionArtimetica)){return comparacionArtimetica.name;}
        if(compare(original,comparacionLogica)){return comparacionLogica.name;}
        if(compare(original,acceso)){return acceso.name;}
        if(compare(original,palabraReservada)){return palabraReservada.name;}
        if(compare(original,tiposDeDatos)){return tiposDeDatos.name;}
        if(compare(original,lightsaber)){return lightsaber.name;}
        if(compare(original,asignacion)){return asignacion.name;}
        if(compare(original,EOL)){return EOL.name;}
        if(compare(original,ewok)){return ewok.name;}
        if(compare(original,wookie)){return wookie.name;}
        if(compare(original,aritmetico)){return aritmetico.name;}
        if(compare(original,agrupador)){return agrupador.name;}
        if(compare(original,clones)){return clones.name;}
        if(compare(original,incremento)){return incremento.name;}        
        if(compare(original,id)){return id.name;}
        else{return "NOT FOUND";}
    }
    
    public boolean compare(String original, CategoriaLexica categoria){
        Pattern p = Pattern.compile(categoria.regex);
        Matcher m = p.matcher(original);
        if (m.matches()) { return true;} 
        else {return false;}
    }
}
