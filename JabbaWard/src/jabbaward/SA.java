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
public class SA {

    ArrayList<Symbol> symbols = new ArrayList<>();
    ArrayList<Symbol> symbChunk = new ArrayList<>();
    ArrayList<ArrayList> erroresTotal = new ArrayList<>();
    ArrayList<Symbol> errorCurrent = new ArrayList<>();
    int niv = 0; //nivel en el arbol
    int id = 0;
    int dad = 0; //nodo dad
    int nivOrig;
    int pos; // pos
    ArrayList<Nodo> tree = new ArrayList<>();
    ArrayList<Nodo> currentTree = new ArrayList<>();
    Nodo inicial = new Nodo(id, niv, "S");
    Nodo nodo;
    boolean mainExist = false;
    boolean linesExist = false;
    boolean isFirstBracket = true;
    boolean e = true;

    public SA(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        tree.add(inicial);
    }

    public ArrayList<Nodo> compare() {
        for (int i = 0; i <= symbols.size(); i++) {

            if (symbols.get(i).getValor().equals("{") || symbols.get(i).getCategoria().equals("EOL")) {
//                if (symbols.get(i).getCategoria().equals("EOL")) {
//                    symbChunk.add(symbols.get(i));
//                } else if (isFirstBracket) {
//                    isFirstBracket = false;
//                    symbChunk.add(symbols.get(i));
//                }
                if (mainExist == false) {
                    id++;
                    niv++;
                    main();
                } else {
                    rules();
                }
                symbChunk.add(symbols.get(i));
                pos = 0;
//                System.out.println("TAMANO DEL CHUNK " + symbChunk.size());
                rules();
                if (errorCurrent.size() > 0) {
                    mostrarErrores();
                } else {
                    tree.addAll(currentTree);
                }
//                erroresTotal.add(errorCurrent);
                errorCurrent.clear();
                symbChunk.clear();
                currentTree.clear();
            } else if (symbols.get(i).getCategoria().equals("EOF")) {
                pos = 0;
                //
//                for (int j = 0; j < symbChunk.size(); j++) {
//                    System.out.println("ESTE " + symbChunk.size());
//                }
                System.out.println("ESTE " + symbChunk.size());
                if (symbChunk.size() > 1) {
                    rules();
//                    tree.addAll(currentTree);
                    //erroresTotal.add(errorCurrent);
                    //errorCurrent.clear();

                    lastBracket();
                    tree.addAll(currentTree);
                } else if (symbChunk.size() == 1) {
                    lastBracket();
                    tree.addAll(currentTree);
                    //erroresTotal.add(errorCurrent);

                }

                symbChunk.clear();
                currentTree.clear();

                errorCurrent.clear();
                break;
            } else {
                symbChunk.add(symbols.get(i));
            }

//            if (!errorCurrent.isEmpty()) {
//                erroresTotal.add(errorCurrent);
//            }
        }

//        if (erroresTotal.size() > 0) {
//            mostrarErrores();
//        }
        return tree;
    }

    void lastBracket() {
        down();
        addCurrent(id, 2, "agrupador", 1);
        down();
        addCurrent(id, 3, "}", 2);
        up();
        up();
    }

    void mostrarErrores() {
        if (e) {
            System.out.println("Se encontraron errores en la(s) siguiente(s) linea(s):");
            e = false;
        }

        for (int i = 0; i < errorCurrent.size(); i++) {
            System.out.print(errorCurrent.get(i).getValor() + " ");
        }
        System.out.print("\n");
    }

    void addCurrent(int idC, int nivC, String textoC, int dadC) {
        nodo = new Nodo(idC, nivC, textoC, dadC);
        currentTree.add(nodo);
    }

    void tokenError(Symbol tok) {
        System.out.println("ERROR! UNEXPECTED TOKEN: " + tok.getValor());
        System.out.println("ERROR! UNEXPECTED TOKEN: " + pos);
        errorCurrent.add(tok);
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
        niv++;
        dad++;
    }

    void up() {
        niv--;

        dad--;
    }

    void lr() {
        id++;
        pos++;

    }

    void rules() {
        {
            lines();

            if (isV("}")) {
                while (isV("}") && pos + 1 < symbChunk.size()) {
                    System.out.println("tamanio" + symbChunk.size());
                    System.out.println("pos" + pos);
                    closeBracket();
                    lr();
                }
            } else {
                down();
                addCurrent(id, niv, "line", dad);
                down();
                line();
            }

        }
    }

    void lines() {
        if (linesExist == false) {
            linesExist = true;
            down();
            addCurrent(id, niv, "lines", dad);
        }
    }

    void main() {

        if (mainExist == false) {
            addCurrent(id, niv, "Main", dad);
            down();
        }
        mainExist = true;
//        System.out.println(getV(pos));
        if (getC(pos).equals("palabra reservada")) {

            dad = dad + 1;
            addCurrent(id, niv, getC(pos), dad);
            down();
            // main();
            if (getV(pos).equals("youngling")) {

                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                main();
            } else if (getV(pos).equals("iAmTheSenate")) {

                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                main();
            }
        } else if (getC(pos).equals("agrupador")) {

            addCurrent(id, niv, getC(pos), dad);
            down();

            if (getV(pos).equals("(")) {

                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                main();
            } else if (getV(pos).equals(")")) {

                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                main();

            } else if (getV(pos).equals("{")) {

                addCurrent(id, niv, getV(pos), dad);
                //lr();
                up();
                up();
                //mainExist = true;
            }
            // System.out.println(niv);
        } else {
            tokenError(getS(pos));
        }
    }

    boolean isC(String t) {
        if (getC(pos).equals(t)) {
            return true;
        } else {
            return false;
        }
    }

    boolean isV(String t) {
        if (getV(pos).equals(t)) {
            return true;
        } else {
            return false;
        }
    }

    void openBracket() {
        down();
        addCurrent(id, niv, "agrupador", dad);
        down();
        addCurrent(id, niv, "{", dad);
        up();
        up();
        //lr();
    }

    void closeBracket() {
        down();
        addCurrent(id, niv, "agrupador", dad);
        down();
        addCurrent(id, niv, "}", dad);
        up();
        up();
        //lr();
    }

    //getC(pos).equals("tipo de dato")
    void line() {
        if (isV("{")) {
            openBracket();
        }

        if (isC("tipo de dato")) {
            declOrAsig();
        } else if (isV("do")) {
            addCurrent(id, niv, "if", dad);
            down();
            addCurrent(id, niv, getC(pos), dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            down();
            up();
            up();
            lr();
            isIf();
        } else if (isV("helloThere")) {
            isPrint();
        }
    }

    void declOrAsig() {
        if (getC(pos + 1).equals("id") && getC(pos + 2).equals("EOL")) {
            addCurrent(id, niv, "decl", dad);
            down();
            decl();

        } else {
            addCurrent(id, niv, "asig", dad);
            down();
            asig();
        }
    }

    void decl() {

        if (isC("tipo de dato")) {
            addCurrent(id, niv, "type", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
//            up();
            lr();
            if (isC("id")) {
                addCurrent(id, niv, "id", dad);
                down();
                addCurrent(id, niv, getV(pos), dad);
                up();
//                up();
                lr();
                if (isC("EOL")) {
                    isEOL();
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    void asig() {

        if (isC("tipo de dato")) {
            addCurrent(id, niv, "type", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
//            up();
            lr();
            if (isC("id")) {
                addCurrent(id, niv, "id", dad);
                down();
                addCurrent(id, niv, getV(pos), dad);
                up();
//                up();
                lr();
                if (isC("asignacion")) {
                    addCurrent(id, niv, "asignacion", dad);
                    down();
                    addCurrent(id, niv, getV(pos), dad);
                    up();
//                    up();
                    lr();
                    isCalc();
//                    if (isCalc()) {
//                        //isEOL();
//                    }
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    void isCalc() {
        if (boolVal()) {
            isVal();
        } else if (isC("EOL")) {
            isEOL();
        } else {
            error();
        }
    }

    boolean boolVal() {
        if (isC("clones") || isC("lightsaber") || isC("ewok") || isC("wookie") || isC("id")) {
            return true;
        } else {
            return false;
        }
    }

    void opArit() {
        if (isC("operador aritmetico")) {
            addCurrent(id, niv, "op_arit", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            lr();
            isVal();
        } else if (isC("EOL")) {
            isEOL();
        } else if (isC("comparacion aritmetica")) {
            addCurrent(id, niv, "comp_arit", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            lr();
            isVal();
        } else if (isV(")")) {
            addCurrent(id, niv, getC(pos), dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            lr();
            if (isV("{")) {
                openBracket();
            }

        } else {
            error();
        }
    }

    void isVal() {
        if (isC("clones") || isC("lightsaber") || isC("ewok") || isC("wookie")) {
            addCurrent(id, niv, "val", dad);
            down();
            addCurrent(id, niv, "cte", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            up();
            lr();
            opArit();
        } else if (isC("id")) {
            addCurrent(id, niv, "val", dad);
            down();
            addCurrent(id, niv, "id", dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            up();
            lr();
            opArit();

        } else {
            error();
        }

    }

//    void eol() {
//        down();
//        addCurrent(id, niv, "EOL", dad);
//        down();
//        addCurrent(id, niv, getV(pos), dad);
//        up();
//        up();
//        //lr();
//    }
    void isEOL() {
//        down();
        addCurrent(id, niv, "EOL", dad);
        down();
        addCurrent(id, niv, getV(pos), dad);
        up();
        up();
//        up();
    }

    void error() {
        errorCurrent = symbChunk;

    }

    void isIf() {
        if (isV("(")) {
            down();
            addCurrent(id, niv, getC(pos), dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up();
            up();
            lr();
            isVal();
        } else {
            error();
        }
    }

    void isPrint() {
//
    }
}
