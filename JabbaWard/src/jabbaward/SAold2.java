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
public class SAold2 {
    
    ArrayList<Symbol> symbols = new ArrayList<>();
    ArrayList<Symbol> symbChunk = new ArrayList<>();
    int nivel = 0;
    int id = 0;
    int padre = 0;
    int nivelOrig;
    int posicion;
    ArrayList<Nodo> tree = new ArrayList<>();
    ArrayList<Nodo> currentTree = new ArrayList<>();
    Nodo inicial = new Nodo(id, nivel, "S");
    Nodo nodo;
    boolean mainExist = false;

    public SAold2(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        tree.add(inicial);
    }

    public ArrayList<Nodo> compare() {
        for (int i = 0; i <= symbols.size(); i++) {

            if (symbols.get(i).getValor().equals("{") || symbols.get(i).getCategoria().equals("EOL")) {
                symbChunk.add(symbols.get(i));
                posicion = 0;
                System.out.println("TAMANO DEL CHUNK " + symbChunk.size());
                rules();
                tree.addAll(currentTree);
                symbChunk.clear();
                currentTree.clear();
            } else if (symbols.get(i).getCategoria().equals("EOF")) {
                posicion = 0;
                //
                for (int j = 0; j < symbChunk.size(); j++) {
                    System.out.println("ESTE " + symbChunk.size());
                }
                System.out.println("chetos");
                if (symbChunk.size() > 1) {
                    rules();
                    tree.addAll(currentTree);
                }

                symbChunk.clear();
                currentTree.clear();

                break;
            } else {
                symbChunk.add(symbols.get(i));
            }
        }
        return tree;
    }

    void addCurrent(int idC, int nivelC, String textoC, int padreC) {
        nodo = new Nodo(idC, nivelC, textoC, padreC);
        currentTree.add(nodo);
    }

    void tokenError(Symbol tok) {
        System.out.println("ERROR! UNEXPECTED TOKEN: " + tok.getValor());
        System.out.println("ERROR! UNEXPECTED TOKEN: " + posicion);
    }

    Symbol getS(int pos) {
        return symbChunk.get(pos);
    }

    String getV(int pos) {
        return symbChunk.get(pos).getValor();
    }

    String getC(int pos) {
        return symbChunk.get(pos).getCategoria();
    }

    void down() {
        id++;
        nivel++;
        padre++;
    }

    void up() {
        nivel--;

        padre--;
    }

    void lr() {
        id++;
        posicion++;

    }

    void in() {
        id++;
        posicion++;

    }

    void rules() {

        if (mainExist == false) {
            id++;
            nivel++;

            //nivelOrig = nivel;
            main();
            //nivel = nivelOrig;
            //System.out.println("tamanio" + currentTree.size());
        } else {
            line();
        }
    }

    void line() {
        //System.out.println(getV(posicion));
        if (getC(posicion).equals("tipo de dato")) {
            addCurrent(id, nivel, "decl", padre);
            //System.out.println("NIVEL: ------------ " + nivel);
            tipodedato();

            line();
        } else if (getC(posicion).equals("id")) {
            if (getC(posicion).equals("asignacion")) {
                addCurrent(id, nivel, "asig", padre);
                asig();
            } else {
                sentence();
            }
        } else if (getV(posicion).equals("do")) {
            addCurrent(id, nivel, "condicional", padre);
            lr();
            condicional();
        } else if (getV(posicion).equals("helloThere")) {
            addCurrent(id, nivel, "helloThere", padre);
            lr();
            call();
        } else if (getV(posicion).equals("EOF")) {
            //end
        } else if (getV(posicion).equals("}")) {
            closingBrack();

        }
    }

    void closingBrack() {
        addCurrent(id, nivel, getC(posicion), padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        if (posicion + 1 < symbChunk.size()) {

            if (getV(posicion).equals("EOF")) {
                line();
            } else if (getV(posicion).equals("}")) {
                lr();
                closingBrack();
            }
        }
        

    }

    void call() {
        if (getC(posicion).equals("agrupador")) {

            addCurrent(id, nivel, getC(posicion), padre);
            down();

            if (getV(posicion).equals("(")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                call();

            } else if (getV(posicion).equals(")")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                call();

            }
        } else if (getC(posicion).equals("id")) {
            id();
            call();

        } else if (getC(posicion).equals("EOL")) {
            eol();

        } else {
            tokenError(getS(posicion));
        }
    }

    void condicional() {

        addCurrent(id, nivel, "if", padre);
        down();
        addCurrent(id, nivel, "do", padre);
        up();
        sentence();

    }

    void sentence() {
        //System.out.println(getV(posicion));
        if (getC(posicion).equals("agrupador")) {
            addCurrent(id, nivel, getC(posicion), padre);
            down();

            if (getV(posicion).equals("(")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                sentence();
            }
        } else if (getC(posicion).equals("id")) {
            id();
            //down();
            sentence();
        } else if (getC(posicion).equals("clones") || getC(posicion).equals("lightsaber") || getC(posicion).equals("ewok") || getC(posicion).equals("wookie")) {
            cte();
            sentence();
        } else if (getC(posicion).equals("comparacion aritmetica")) {
            comp_arit();
            sentence();
        } else if (getC(posicion).equals("agrupador")) {
            addCurrent(id, nivel, getC(posicion), padre);
            down();

            if (getV(posicion).equals(")")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
            }
        } else if (getC(posicion).equals("EOL")) {
            eol();
        } else {
            System.out.println("THIS THIS THIS");
            tokenError(getS(posicion));
        }
    }

    void tipodedato() {
        String tipo = getV(posicion);
        //System.out.println("EL TIPO DE DATO ES   " + tipo);
        if (tipo.equals("clones") || tipo.equals("wookie") || tipo.equals("ewok") || tipo.equals("lightsaber")) {
            down();
            addCurrent(id, nivel, "type", padre);
            down();
            addCurrent(id, nivel, getV(posicion), padre);
            up();
            up();
            lr();
        } else {
            tokenError(getS(posicion));
        }
    }

    void comp_arit() {
        down();
        addCurrent(id, nivel, "id", padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        up();
        lr();
    }

    void id() {
        down();
        addCurrent(id, nivel, "id", padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        up();
        lr();
    }

    void eol() {
        down();
        addCurrent(id, nivel, "EOL", padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        up();
        lr();
    }

    void asig() {
        System.out.println("token actual: " + getV(posicion) + "    pos actual" + posicion);
        if (getC(posicion).equals("id")) {
            id();
            asig();
        } else if (getC(posicion).equals("asignacion")) {
            down();
            addCurrent(id, nivel, "asignacion", padre);
            down();
            //System.out.println("WEWEWE   " + getV(posicion));
            addCurrent(id, nivel, getV(posicion), padre);
            up();
            up();
            lr();
            asig();
        } else if (getC(posicion).equals("operador aritmetico")) {
            op_arit();
            asig();
        } else if (getV(posicion).equals("doit") || getV(posicion).equals("jango")) {
            addCurrent(id, nivel, "loop", padre);
            down();
            loop(getV(posicion));
        } else if (getC(posicion).equals("clones") || getC(posicion).equals("lightsaber") || getC(posicion).equals("ewok") || getC(posicion).equals("wookie")) {
            cte();
            System.out.println("WOW WOW CONSTANTE");
            asig();
        } else if (getC(posicion).equals("EOL")) {
            eol();
        } else {

            tokenError(getS(posicion));
        }

    }

    void loop(String loopType) {
        if (loopType.equals("doit")) {
            addCurrent(id, nivel, "doit", padre);
            up();
            doit();
        } else if (loopType.equals("jango")) {
            addCurrent(id, nivel, "jango", padre);
            up();
            jango();
        }
    }

    void doit() {
        System.out.println("WHERE ARE YOU GOD");
        lr();
        if (getV(posicion).equals("(")) {
            addCurrent(id, nivel, getC(posicion), padre);
            down();
            addCurrent(id, nivel, getV(posicion), padre);
            up();
            lr();
            sentence();
        }
    }

    void jango() {

    }

    void cte() {
        down();
        addCurrent(id, nivel, "cte", padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        up();
        lr();
    }

    void op_arit() {
        down();
        addCurrent(id, nivel, "op_arit", padre);
        down();
        addCurrent(id, nivel, getV(posicion), padre);
        up();
        up();
        lr();
    }

    void main() {

        if (mainExist == false) {
            addCurrent(id, nivel, "Main", padre);
            down();
        }
        mainExist = true;
//        System.out.println(getV(posicion));
        if (getC(posicion).equals("palabra reservada")) {

            padre = padre + 1;
            addCurrent(id, nivel, getC(posicion), padre);
            down();
            // main();
            if (getV(posicion).equals("youngling")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                main();
            } else if (getV(posicion).equals("iAmTheSenate")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                main();
            }
        } else if (getC(posicion).equals("agrupador")) {

            addCurrent(id, nivel, getC(posicion), padre);
            down();

            if (getV(posicion).equals("(")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                main();
//        } else if (getC(posicion).equals("agrupador")) {
//
//            addCurrent(id, nivel, getC(posicion), padre);
//            down();main();
            } else if (getV(posicion).equals(")")) {

                addCurrent(id, nivel, getV(posicion), padre);
                up();
                lr();
                main();

            } else if (getV(posicion).equals("{")) {

                addCurrent(id, nivel, getV(posicion), padre);
                lr();
                up();
                //mainExist = true;
            }
            // System.out.println(nivel);
        } else {
            tokenError(getS(posicion));
        }
    }


}
