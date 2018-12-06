/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

/**
 *
 * @author cesar
 */
public class Symbol{
    int entrada;
    String categoria;
    String valor;
    
    public Symbol(int entrada, String categoria, String valor){
        this.entrada = entrada;
        this.categoria = categoria;
        this.valor = valor;
    }
    public int getEntrada(){
        return entrada;
    }
    public String getCategoria(){
        return categoria;
    }

//    public getSym(int num){
//        return Symbol.
//    }
    
    public String getValor(){
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
