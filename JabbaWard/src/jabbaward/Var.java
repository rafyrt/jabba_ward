/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

/**
 *
 * @author Felipe
 */
public class Var {

    int id;
    String name;
    String type;
    boolean val;

    public Var(int id, String name, String type, boolean val) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.val = val;
    }

    public String getVar(){
        return (this.id + " " + this.name + " "+ this.type + " "+ this.val);
    }
    
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public boolean hasValue(){
        return val;
    }
    
    public void setValue(boolean v){
        this.val = v;
        
        // o nada mas this.value = true;
    }
}
