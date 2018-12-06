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
    public ArrayList<Nodo> tree;
    int posicion;
    int id = -1;
    int nivel = 0;
    int padre = 0;
    int hijo, nuevaPosicion;
    Nodo inicial = new Nodo(0, 0, "S");
    Nodo nodo;

    public ParserRafa(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public void start() {
        for (int i = 0; i + 1 < symbols.size(); i++) {
            if (i == posicion) {
                System.out.print(ConsoleColors.RED + symbols.get(i).getValor() + ConsoleColors.RESET + " ");

            } else if (symbols.get(i).getValor().equals("{") || symbols.get(i).getValor().equals("~endor")) {
                System.out.println(symbols.get(i).getValor() + " ");
            } else {
                System.out.print(symbols.get(i).getValor() + " ");
            }
        }
        System.out.println();
    }

    public class ConsoleColors {
        // Reset

        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
    }

    public ArrayList<Tuplas> parser() {
        if (isS()) {
            System.out.println("---Syntax: Correct!!!---");
            //start();
            Semantic semantic = new Semantic(symbols, tree);
            return semantic.start();
        } else {
            System.out.println("---Syntax: ERROR!---");
            start();
            return null;
        }
    }

    public boolean isS() {
        tree = new ArrayList<>();
        posicion = 0;
        nivel = 0;
        tree.add(new Nodo(++id, nivel, "S"));
        padre = id;
        boolean b = isMain();
        b = b && isEOF();
        return b;
    }

    boolean isEOF() {
        return symbols.get(posicion).getCategoria().equals("EOF");
    }

    boolean isEOL() {
        return symbols.get(posicion).getCategoria().equals("EOL");
    }

    boolean isEmpty() {
        return isEOL() || symbols.get(posicion).getValor().equals("}") || isEOF();
    }

    boolean isMain() {
        if (isEOF()) {
            return true;
        }
        nivel++;

        nodo = new Nodo(++id, nivel, "MAIN", padre);
        tree.add(nodo);
        padre = id;
        nivel++;
        if (symbols.get(posicion).getValor().equals("youngling")) {
            nodo = new Nodo(++id, nivel, "void", padre);
            tree.add(nodo);
            posicion++;
            if (symbols.get(posicion).getValor().equals("iAmTheSenate")) {
                nodo = new Nodo(++id, nivel, "main", padre);
                tree.add(nodo);
                posicion++;
                if (symbols.get(posicion).getValor().equals("(")) {
                    nodo = new Nodo(++id, nivel, "(", padre);
                    tree.add(nodo);
                    posicion++;
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id, nivel, ")", padre);
                        tree.add(nodo);
                        posicion++;
                        if (symbols.get(posicion).getValor().equals("{")) {
                            nodo = new Nodo(++id, nivel, "{", padre);
                            tree.add(nodo);
                            posicion++;
                            if (isLines(padre)) {
                                if (symbols.get(posicion).getValor().equals("}")) {
                                    nodo = new Nodo(++id, nivel, "}", padre);
                                    tree.add(nodo);
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

    boolean isLines(int padreL) {
        if (isEmpty()) {
            return true;
        }
        nivel++;
        nodo = new Nodo(++id, nivel, "lines", padreL);
        tree.add(nodo);
        padre = id;
        if (isLine()) {
            nivel--;
            return isLines(padreL);
        }
        return false;
    }

    boolean isLine() {
        nivel++;
        nodo = new Nodo(++id, nivel, "line", padre);
        tree.add(nodo);
        padre = id;
        int tempP = posicion;
        int tempSize = tree.size();
        if (isDecl()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isAsign()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isIf()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isWhile()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isFor()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        if (isPrint()) {
            return true;
        }
        nivel--;
        posicion = tempP;
        removeNodes(tempSize);
        padre = id;
        return false;
    }

    boolean isIf() {
        nivel++;
        nodo = new Nodo(++id, nivel, "IF", padre, posicion);
        tree.add(nodo);
        padre = id;
        if (symbols.get(posicion).getValor().equals("do")) {
            nodo = new Nodo(++id, nivel, "do", padre, posicion);
            tree.add(nodo);
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id, nivel, "(", padre, posicion);
                tree.add(nodo);
                posicion++;
                if (isComp()) {
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id, nivel, ")", padre, posicion);
                        tree.add(nodo);
                        posicion++;
                        if (symbols.get(posicion).getValor().equals("{")) {
                            nodo = new Nodo(++id, nivel, "{", padre, posicion);
                            tree.add(nodo);
                            posicion++;
                            if (isLines(padre)) {
                                if (symbols.get(posicion).getValor().equals("}")) {
                                    nodo = new Nodo(++id, nivel, "}", padre, posicion);
                                    tree.add(nodo);
                                    posicion++;
                                    nivel--;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        nivel--;
        return false;
    }

    boolean isWhile() {
        nivel++;
        nodo = new Nodo(++id, nivel, "WHILE", padre, posicion);
        tree.add(nodo);
        padre = id;
        if (symbols.get(posicion).getValor().equals("fett")) {
            nodo = new Nodo(++id, nivel, "fett", padre, posicion);
            tree.add(nodo);
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id, nivel, "(", padre, posicion);
                tree.add(nodo);
                posicion++;
                if (isComp()) {
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id, nivel, ")", padre, posicion);
                        tree.add(nodo);
                        posicion++;
                        if (symbols.get(posicion).getValor().equals("{")) {
                            nodo = new Nodo(++id, nivel, "{", padre, posicion);
                            tree.add(nodo);
                            posicion++;
                            if (isLines(padre)) {
                                if (symbols.get(posicion).getValor().equals("}")) {
                                    nodo = new Nodo(++id, nivel, "}", padre, posicion);
                                    tree.add(nodo);
                                    posicion++;
                                    nivel--;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        nivel--;
        return false;
    }

    boolean isFor() {
        nivel++;
        nodo = new Nodo(++id, nivel, "FOR", padre, posicion);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (symbols.get(posicion).getValor().equals("doit")) {
            nivel++;
            nodo = new Nodo(++id, nivel, "doit", padre, posicion);
            tree.add(nodo);
            posicion++;
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id, nivel, "(", padre, posicion);
                tree.add(nodo);
                posicion++;
                if (isAsign()) {
                    if (isComp()) {
                        if (isEOL()) {

                            padre = temp;
                            tree.add(new Nodo(++id, nivel, "EOL", padre));
                            posicion++;
                            if (isAsign()) {
                                padre = temp;
                                if (symbols.get(posicion).getValor().equals(")")) {
                                    nodo = new Nodo(++id, nivel, ")", padre, posicion);
                                    tree.add(nodo);
                                    posicion++;
                                    if (symbols.get(posicion).getValor().equals("{")) {
                                        nodo = new Nodo(++id, nivel, "{", padre, posicion);
                                        tree.add(nodo);
                                        posicion++;
                                        if (isLines(padre)) {
                                            if (symbols.get(posicion).getValor().equals("}")) {
                                                nodo = new Nodo(++id, nivel, "}", padre, posicion);
                                                tree.add(nodo);
                                                posicion++;
                                                nivel--;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        nivel--;
        return false;
    }

    boolean isLogic() {
        if (isComp()) {
            return true;
        }
        return false;
    }

    public boolean isDecl() {
        nivel++;
        nodo = new Nodo(++id, nivel, "decl", padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (symbols.get(posicion).getCategoria().equals("tipo de dato")) {
            nodo = new Nodo(++id, nivel, "tipo", padre, posicion);
            tree.add(nodo);
            posicion++;
            int tempP = posicion;
            int tempSize = tree.size();
            if (isAsign()) {
                return true;
            }
            removeNodes(tempSize);
            posicion = tempP;
            padre = temp;
            if (isID()) {
                if (isEOL()) {
                    tree.add(new Nodo(++id, nivel, "EOL", padre));
                    posicion++;
                    nivel--;
                }
//                tree.add(new Nodo(++id, nivel, "missing EOL", padre));
//                nivel--;
//                posicion++;

                return true;
            }
            posicion--;
        }
        return false;
    }

    boolean isAsign() {
        nivel++;
        nodo = new Nodo(++id, nivel, "asign", padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (isID()) {
            if (symbols.get(posicion).getCategoria().equals("asignacion")) {
                nodo = new Nodo(++id, nivel + 1, "=", padre);
                tree.add(nodo);
                posicion++;
                if (isComp()) {
                    padre = temp;
                    if (isEOL()) {
                        tree.add(new Nodo(++id, nivel, "EOL", padre));
                        posicion++;
                        nivel--;
                        return true;
                    }
                    tree.add(new Nodo(++id, nivel, "missing EOL", padre));
                    posicion++;
                    nivel--;
                    return true;
                }
            }
        }
        return false;
    }

    boolean isCalc() {
        nivel++;
        nodo = new Nodo(++id, nivel, "calc", padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (isVar()) {
            padre = temp;
            if (symbols.get(posicion).getCategoria().equals("operador aritmetico")) {
                nodo = new Nodo(++id, nivel + 1, "op_arit", padre, posicion);
                tree.add(nodo);
                posicion++;
                if (isCalc()) {
                    nivel--;
                    return true;
                }
                nivel--;
                return false;
            }
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }

    boolean isComp() {
        nivel++;
        nodo = new Nodo(++id, nivel, "comp", padre);
        tree.add(nodo);
        padre = id;
        int temp = padre;
        if (isCalc()) {
            padre = temp;
            if (symbols.get(posicion).getCategoria().equals("comparacion aritmetica")) {
                nodo = new Nodo(++id, nivel, "comp_arit", padre, posicion);
                tree.add(nodo);
                posicion++;
                nivel--;
                return isCalc();
            }
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }

    boolean isPrint() {
        nivel++;
        nodo = new Nodo(++id, nivel, "PRINT", padre);
        tree.add(nodo);
        padre = id;
        if (symbols.get(posicion).getValor().equals("helloThere")) {
            nivel++;
            posicion++;
            nodo = new Nodo(++id, nivel, "hlloThere", padre);
            tree.add(nodo);
            if (symbols.get(posicion).getValor().equals("(")) {
                nodo = new Nodo(++id, nivel, "(", padre);
                tree.add(nodo);
                posicion++;
                nivel--;
                if (isVar()) {
                    nivel++;
                    if (symbols.get(posicion).getValor().equals(")")) {
                        nodo = new Nodo(++id, nivel, ")", padre);
                        tree.add(nodo);
                        posicion++;
                        if (isEOL()) {
                            tree.add(new Nodo(++id, nivel, "EOL", padre));
                            posicion++;
                            nivel--;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean isVar() {
        nivel++;
        nodo = new Nodo(++id, nivel, "var", padre);
        tree.add(nodo);
        padre = id;
        if (isID() || isConstant()) {
            nivel--;
            return true;
        }
        nivel--;
        return false;
    }

    boolean isConstant() {
        if (symbols.get(posicion).getCategoria().equals("clones") || symbols.get(posicion).getCategoria().equals("lightsaber")
                || symbols.get(posicion).getCategoria().equals("ewok") || symbols.get(posicion).getCategoria().equals("wookie")) {
            nodo = new Nodo(++id, nivel + 1, "Constant", padre, posicion);
            tree.add(nodo);
            posicion++;
            return true;
        }
        return false;
    }

    boolean isID() {
        if (symbols.get(posicion).getCategoria().equals("id")) {
            nodo = new Nodo(++id, nivel + 1, "ID", padre, posicion);
            tree.add(nodo);
            posicion++;
//            
//            nodo = new Nodo(++id, nivel + 2, symbols.get(posicion).getValor(), padre + 1, )
//            tree.add(nodo);

            return true;
        }
        return false;
    }

    void removeNodes(int tempS) {
        while (tempS < tree.size()) {
            id--;
            tree.remove(tree.size() - 1);
        }
    }

    void findNextLine() {
        while (!(isEOL() || isEmpty())) {
            posicion++;
        }
    }
}
