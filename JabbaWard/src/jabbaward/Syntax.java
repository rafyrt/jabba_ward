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
public class Syntax {

    ArrayList<Symbol> symbols = new ArrayList<>();
    ArrayList<Symbol> symbChunk = new ArrayList<>();
    ArrayList<ArrayList> erroresTotal = new ArrayList<>();
    ArrayList<Symbol> errorCurrent = new ArrayList<>();
    int niv = 0; //nivel en el arbol
    int id = 0;
    int dad = 0; //nodo dad
    int nivOrig;
    int pos; // pos
    int brcount;
    ArrayList<Nodo> tree = new ArrayList<>();
    ArrayList<Nodo> currentTree = new ArrayList<>();
    Nodo inicial = new Nodo(id, niv, "S");
    Nodo nodo;
    boolean mainExist = false;
    boolean linesExist = false;
    boolean isFirstBracket = true;
    boolean e = true;

    public Syntax(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        tree.add(inicial);
    }

    public ArrayList<Nodo> compare() {
        for (int i = 0; i <= symbols.size(); i++) {
            if (mainExist == false && symbols.get(i).getValor().equals("{")) {
                symbChunk.add(symbols.get(i));
                id++;
                pos = 0;
                niv++;
                main();
                if (errorCurrent.size() > 0) {
                    mostrarErrores();
                } else {
                    tree.addAll(currentTree);
                }
                errorCurrent.clear();
                symbChunk.clear();
                currentTree.clear();

            } else if (symbols.get(i).getValor().equals("{") || symbols.get(i).getCategoria().equals("EOL")) {

                symbChunk.add(symbols.get(i));
                pos = 0;

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

    void lastBracket() {
//        brNum();
        down();
        addCurrent(id, 2, "agrupador", 1);
        down();
        addCurrent(id, 3, "}", 2);
        up();
        up();
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

    void addCurrent(int idC, int nivC, String textoC, int dadC) {
        nodo = new Nodo(idC, nivC, textoC, dadC);
        currentTree.add(nodo);
    }

    void error() {
        errorCurrent = symbChunk;

    }

    void main() {

        if (mainExist == false) {
            addCurrent(id, niv, "Main", dad);
            down();
        } else {
            error();
        }
        mainExist = true;

        if (getC(pos).equals("palabra reservada")) {

            dad = dad + 1;
            addCurrent(id, niv, getC(pos), dad);
            down();

            if (getV(pos).equals("youngling")) {

                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                if (getC(pos).equals("palabra reservada")) {

                    dad = dad + 1;
                    addCurrent(id, niv, getC(pos), dad);
                    down();
                    if (getV(pos).equals("iAmTheSenate")) {

                        addCurrent(id, niv, getV(pos), dad);
                        up();
                        lr();

                        if (getC(pos).equals("agrupador")) {

                            addCurrent(id, niv, getC(pos), dad);
                            down();

                            if (getV(pos).equals("(")) {

                                addCurrent(id, niv, getV(pos), dad);
                                up();
                                lr();
                                if (getC(pos).equals("agrupador")) {

                                    addCurrent(id, niv, getC(pos), dad);
                                    down();
                                    if (getV(pos).equals(")")) {

                                        addCurrent(id, niv, getV(pos), dad);
                                        up();
                                        lr();

                                        if (getC(pos).equals("agrupador")) {

                                            addCurrent(id, niv, getC(pos), dad);
                                            down();
                                            if (getV(pos).equals("{")) {
                                                addCurrent(id, niv, getV(pos), dad);
                                                up();
                                                brcount++;
                                            } else {
                                                error();
                                            }

                                        } else {
                                            error();
                                        }

                                    } else {
                                        error();
                                    }
                                } else {
                                    error();
                                }
                            } else {
                                error();
                            }
                        } else {
                            error();
                        }
                    } else {
                        error();
                    }
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

    void rules() {
        addCurrent(id, niv, "lines", dad);

        line();
        up();
    }

    void line() {
        down();
        addCurrent(id, niv, "line", dad);
        down();
        if (isC("tipo de dato")) {
            declOrAsig();
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
                lr();
                if (isC("EOL")) {
                    isEOL();
                    up();
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

    void isComp() {
        down();
        addCurrent(id, niv, "comp", dad);
        isCalc();
        if (isV("comparacion aritmetica")) {
            compArit();
        }
        up();

    }

    void compArit() {

    }

    void isCalc() {
        down();
        addCurrent(id, niv, "calc", dad);
        isVar();
        if (lrTrue()) {
            lr();
            if (isC("operador aritmetico")) {
                opArit();
                isCalc();
            }
        } else {
            error();
        }
        up();
    }

    void opArit() {
        down();
        addCurrent(id, niv, getC(pos), dad);
        down();
        addCurrent(id, niv, getV(pos), dad);
        up();
        up();
        lr();

    }

    boolean lrTrue() {
        if (pos + 1 == symbChunk.size()) {
            return false;
        } else {
            return true;
        }
    }

    void isVar() {
        if (isC("id") || isC("wookie") || isC("clones") || isC("ewok") || isC("lightsaber")) {
            down();
            addCurrent(id, niv, getC(pos), dad);
            down();
            addCurrent(id, niv, getV(pos), dad);
            up(); up();
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
            lr();
            if (isC("id")) {
                addCurrent(id, niv, "id", dad);
                down();
                addCurrent(id, niv, getV(pos), dad);
                up();
                lr();
                if (isC("asignacion")) {
                    addCurrent(id, niv, "asignacion", dad);
                    down();
                    addCurrent(id, niv, getV(pos), dad);
                    up();
                    up();
                    lr();
                    isComp();
                    if (isC("EOL")) {
                        down();
                        isEOL();
                        up();
                    } else {
                        error();
                    }
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

    void isEOL() {
        addCurrent(id, niv, "EOL", dad);
        down();
        addCurrent(id, niv, getV(pos), dad);
        up();
    }
}
