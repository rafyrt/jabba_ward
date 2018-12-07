/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class Semantic {

    boolean end = false;
    String recentVT;
    boolean error = false;
    ArrayList<Symbol> symbols;
    ArrayList<Nodo> tree;
    ArrayList<Var> variables = new ArrayList<>();
    ArrayList<Secciones> seccionesL = new ArrayList<>();
    ArrayList<Var> variablesTemporales = new ArrayList<>();
    Secciones seccionL;
    ArrayList<Tuplas> tuplas = new ArrayList<>();
    ArrayList<Tuplas> tuplasFor = new ArrayList<>();
    ArrayList<Tuplas> tuplasCond = new ArrayList<>();
    String asigValor;
    String asigTipo;
    String vt = null;
    Var variable;
    Tuplas tupla;
    int lCount = 1;
    int tCount = 1;
    int varId = 0;
    int posTemp;
    int indexL = 0;
    String currentVar;
    String[] textos = {"decl", "asign", "ID", "Constant", "FOR", "IF", "PRINT", "WHILE", "}"};
    int pos = 0;
    String currentL;
    String currentText;
    int posSimb;
    int posSimbTemp;
    String op = " ";
    String op1 = " ";
    String op2 = " ";
    String res = " ";
//    Tuplas tupi = new Tuplas(op, op1, op2, res);
//    T/uplas tupi2;

    public Semantic(ArrayList<Symbol> symbols, ArrayList<Nodo> tree) {
        this.symbols = symbols;
        this.tree = tree;

    }

    private String nuevaVT() {
        String newVT = "T" + tCount;
        tCount++;
        return newVT;
    }

    // Crear nueva L
    // La idea es crearla al inicio de cualquier FOR, IF y WHILE
    private String nuevaL() {
        String newL = "L" + lCount;
        lCount++;
        return newL;
    }

    public boolean compareTexto(int p) {
//        if (tree.get(p).equals(t))
        boolean ct = false;
        for (int i = 0; i < textos.length; i++) {
            if (tree.get(p).texto.equals(textos[i])) {
                currentText = tree.get(p).texto;
                ct = true;
                //System.out.println("CURRENT TEXT" + currentText);
            }
        }
        return ct;
    }

    private void findAndCreateLs() {
        int posicionArbol = 0;
        int numDeBrackets = 0;
        while (!(tree.get(posicionArbol).getTexto().equals("{"))) {
            posicionArbol++;
        }
        posicionArbol++;
        for (int i = posicionArbol; i < tree.size(); i++) {
            if (tree.get(i).getTexto().equals("{")) {
                seccionL = new Secciones(nuevaL(), i, tree.get(i).getSimbolo());
                seccionesL.add(seccionL);
                i++;
                numDeBrackets = 0;
                for (int j = i; j < tree.size(); j++) {
                    if (tree.get(j).getTexto().equals("{")) {
                        numDeBrackets++;
                    } else if (tree.get(j).getTexto().equals("}") && numDeBrackets == 0) {
                        seccionL = new Secciones(nuevaL(), j, tree.get(j).getSimbolo());
                        seccionesL.add(seccionL);
                        numDeBrackets--;
                        j = tree.size();
                    } else if (tree.get(j).getTexto().equals("}")) {
                        numDeBrackets--;
                    }
                }
            }
        }
        // Comprobando que se estan creando las Ls, estas.
        // Se crean en orden.
        System.out.println("LAS Ls");
        for (int i = 0; i < seccionesL.size(); i++) {
            System.out.println(seccionesL.get(i).print());
        }
        System.out.println("LAS Ls");

    }

    public ArrayList<Tuplas> start() {
        findAndCreateLs();
        for (pos = 0; pos + 1 < tree.size(); pos++) {
            if (compareTexto(pos)) {
                //System.out.println(tree.get(pos).texto);
                rules();
            }
        }
        System.out.println("VARIABLES");
        for (int i = 0; i < variables.size(); i++) {
            System.out.println(variables.get(i).getVar());
        }
//        System.out.println("TUPLALALALALAALA");
//        for (int i = 0; i < tuplas.size(); i++) {
//            System.out.println(tuplas.get(i).getTuplas());
//        }
//        
        if (end) {
            tuplas.clear();
            tupla = new Tuplas("error::::", "0", "0", "0");
            tuplas.add(tupla);
        }
        // NOTA: ERROR BORRA TODAS LAS TUPLAS EN CASO DE QUE EXISTA UN ERROR
        // EL BOOLEANO SE HACE VERDADERO EN CUANDO APARECE UN ERROR
        // Y SE BORRAN LAS TUPLAS PARA QUE EL DOCUMENTO DE TUPLAS APAREZCA
        // CON UNA SOLA ENTRADA LLAMADA "error:::::"
        // ACTIVAR CUANDO YAAAA ESTE COMPLETO EL PROGRAMA,
        // MIENTRAS ESTARA COMENTADO OUT - DE MODO QUE LAS TUPLAS 
        // NOS DEN INFORMACION SI HAY UN ERROR EN NUESTRO CODIGO
        // --------------------------------------------------------------------

        // --------------------------------------------------------------------
        return tuplas;
    }

    private void findOpenB() {

    }

    private void findClosedB() {

    }

    public void rules() {
        posTemp = pos;
        if (currentText.equals("decl")) {
            decl();
            //DONEEEEEEEEEEEEEEE
            //do this
        } else if (currentText.equals("asign")) {
            asig();
            //DONEEEEEEEEEEEEEEE
            //do this
        } else if (currentText.equals("Constant")) {
            //constant();
            //do this
        } else if (currentText.equals("ID")) {
            isId();
            //do this
        } else if (currentText.equals("FOR")) {
            //DONEEEEEEEEEEEEEEE
            //System.out.println("FOR FOR FOR FOR FOR");
            //System.out.println("CURRENT TEXT> " + currentText);
            isFor();
            //do this
        } else if (currentText.equals("IF")) {
//            System.out.println("IF IF IF IF IF");
            isIf();
            //do this
        } else if (currentText.equals("PRINT")) {
//            System.out.println("PRINT PRINT PRINT");
            isPrint();
            //do this
        } else if (currentText.equals("WHILE")) {
//            System.out.println("WHILE WHILE WHILE");
            isWhile();
            //do this
        } else if (currentText.equals("{")) {
            // openBracket stuff
        } else if (currentText.equals("}")) {
//            System.out.println("WUBBA LUBBA DUB DUB!!!");
//            System.out.println(posTemp);
//            System.out.println(tree.get(posTemp).id);
            for (int i = 0; i < seccionesL.size(); i++) {
//                System.out.println("ENTRA FOR 1");
                if (seccionesL.get(i).getPosNodo() == tree.get(posTemp).id) {
//                    System.out.println(seccionesL.get(0).L);
//                    System.out.println(seccionesL.get(i).getTupis().isEmpty());
//                    System.out.println(seccionesL.get(i).getTupis());
//                    System.out.println("TAMANO DE TUPIS");
//                    System.out.println(seccionesL.get(i).getTupis().size());
//                    System.out.println(seccionesL.get(i).getTupis().get(0).getOp());
                    if (!(seccionesL.get(i).getTupis().isEmpty())) {
                        for (int j = 0; j < seccionesL.get(i).getTupis().size(); j++) {
//                            System.out.println("ENTRA FOR 2");
//                            System.out.println(seccionesL.get(i).getTupis().get(j).getOp());
                            //tupla = new Tuplas(seccionesL.get(i).getTupis().get(j).getOp(), seccionesL.get(i).getTupis().get(j).operando1, seccionesL.get(i).getTupis().get(j).operando2, seccionesL.get(i).getTupis().get(j).resultado);
                            tupla = new Tuplas(seccionesL.get(i).getTupis().get(j).getTuplas());
                            tuplas.add(tupla);
                        }
                    }

                    if (!(seccionesL.get(i).getTupis2().isEmpty())) {
                        for (int j = 0; j < seccionesL.get(i).getTupis2().size(); j++) {
//                            System.out.println("ENTRA FOR 2");
//                            System.out.println(seccionesL.get(i).getTupis2().get(j).getOp());
//                            tupla = new Tuplas(seccionesL.get(i).getTupis2().get(j).getOp(), seccionesL.get(i).getTupis2().get(j).operando1, seccionesL.get(i).getTupis2().get(j).operando2, seccionesL.get(i).getTupis2().get(j).resultado);
                            tupla = new Tuplas(seccionesL.get(i).getTupis2().get(j).getTuplas());
                            tuplas.add(tupla);
                        }
                    }

//                    if (!(seccionesL.get(i).getTupis().isEmpty() && (seccionesL.get(i).getTupis().size() > 2))) {
//                        tupla = new Tuplas(seccionesL.get(i).L);
//                        tuplas.add(tupla);
//
//                    }
                    tupla = new Tuplas(seccionesL.get(i).L);
                    tuplas.add(tupla);
                }
            }

            // closeBracket stuff
        } else {
            //wtf?

        }
    }

    private void getCurrentTextTemporal() {
        currentText = tree.get(posTemp).texto;
    }

    private void decl() {
        posTemp = pos;
        boolean isAsig = false;
        while (!currentText.equals("EOL")) {
            //posTemp++;
            if (!tree.get(posTemp).equals("-1")) {
//                System.out.println("ESTE ES EL TEXTO");
//                System.out.println(tree.get(posTemp).getTexto());
            }
            if (currentText.equals("asign")) {
                isAsig = true;
                pos = posTemp;
            }
            posTemp++;
            getCurrentTextTemporal();
        }

        if (isAsig) {
            declaAsig();
        } else {
            onlyDecl();
        }
    }

    private String getVal() {
        return symbols.get(posSimb).getValor();
    }

    private String getCat() {
        return symbols.get(posSimb).getCategoria();
    }

    private String getValT() {
        return symbols.get(posSimbTemp).getValor();
    }

    private String getCatT() {
        return symbols.get(posSimbTemp).getCategoria();
    }

    private void createTupla() {
//        tupi2 = tupi;
    }

    private boolean yaDeclarada(String vari) {
        boolean noEsta = true;
        for (int i = 0; i < variables.size(); i++) {
            if (vari.equals(variables.get(i).getName())) {
                return false;
            } else {
                noEsta = true;
            }
        }
        return noEsta;
    }

    private boolean yaInicializada(String vari) {
        boolean yaEsta = true;
        for (int i = 0; i < variables.size(); i++) {
            if (vari.equals(variables.get(i).getName())) {
                if (!variables.get(i).hasValue()) {
                    //System.out.println("NOMBRE VAR "+variables.get(i).getName());

                    return false;
                }
            }
        }
        return yaEsta;
    }

    private void declaAsig() {

        posTemp = pos;
        pos++;
        posSimb = tree.get(pos).getSimbolo();
//        System.out.println(symbols.get(posSimb).getValor());
//        System.out.println("el valor es " + getVal());
        asigTipo = symbols.get(posSimb - 1).getValor();
//        System.out.println(symbols.get(posSimb - 1).getValor());
//        System.out.println(symbols.get(posSimb).getValor());
//        System.out.println(symbols.get(posSimb + 2).getValor());
        if (yaDeclarada(getVal())) {
            if (asigTipo.equals("clones")) {
                asigValores("clones", tuplas);
                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), true);
                variables.add(variable);
                varId++;
                tupla = new Tuplas("declasig", asigValor, symbols.get(posSimb).getValor());
                tuplas.add(tupla);
            } else if (asigTipo.equals(symbols.get(posSimb + 2).getCategoria())) {
                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), true);
                variables.add(variable);
                varId++;
                asigValor = symbols.get(posSimb + 2).getValor();
                tupla = new Tuplas("declasig", asigValor, symbols.get(posSimb).getValor());
                tuplas.add(tupla);
            } else {
                System.out.println("---ERROR SEMANTICO---");
                System.out.println("Los valores deben de corresponder al mismo tipo de dato al de la variable que se desa asignar.");
                error();
            }
//            variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), true);
//            variables.add(variable);
//            varId++;
//            tupla = new Tuplas("asig", asigValor, symbols.get(posSimb).getValor());
//            tuplas.add(tupla);
        } else {
            System.out.println("---ERROR SEMANTICO---");
            System.out.println("El nombre de la variable \"" + symbols.get(posSimb).getValor() + "\" debe ser unica.");
            error();
        }

    }

    private void asigValores(String tipo, ArrayList<Tuplas> tupi) {
        ArrayList<Symbol> valAsig = new ArrayList<Symbol>();
        ArrayList<Symbol> simbAsig = new ArrayList<Symbol>();
        posSimbTemp = posSimb;
        posSimbTemp++;

        if (getValT().equals("=")) {
            posSimbTemp++;
            while (!(getCatT().equals("EOL"))) {
                simbAsig.add(symbols.get(posSimbTemp));
                if (!getCatT().equals("operador aritmetico")) {
                    valAsig.add(symbols.get(posSimbTemp));
                }
                posSimbTemp++;
            }
        }
        if (isSameType(valAsig, tipo)) {
            for (int i = 0; i < valAsig.size(); i++) {
                if ("id".equals(valAsig.get(i).categoria)) {

                    if (!yaInicializada(valAsig.get(i).valor)) {
                        System.out.println("---ERROR SEMANTICO---");
                        System.out.println("Variable " + valAsig.get(i).valor + " no esta inicializada y no puede utilizarse en operaciones.");
                        error();
                        error = true;
                    } else if (yaDeclarada(valAsig.get(i).valor)) {
                        System.out.println("---ERROR SEMANTICO---");
                        System.out.println("Variable " + valAsig.get(i).valor + " no esta declarada y no puede utilizarse en operaciones.");
                        error();
                        error = true;
                    }
                }
            }
            //System.out.println("YES YES YES");
            pemdas(simbAsig, tupi);

        } else {
            System.out.println("---ERROR SEMANTICO---");
            System.out.println("Los valores deben de corresponder al mismo tipo de dato al que se desa asignar, ademas de estar inicializadas.");
            error();
            error = true;
        }
    }

    private void asigValoresPar(String tipo, ArrayList<Tuplas> tupi) {
        ArrayList<Symbol> valAsig = new ArrayList<Symbol>();
        ArrayList<Symbol> simbAsig = new ArrayList<Symbol>();
        posSimbTemp = posSimb;
        posSimbTemp++;

        // if (getValT().equals("=")) {
        //posSimbTemp++;
        while (!(getCatT().equals("agrupador"))) {
            simbAsig.add(symbols.get(posSimbTemp));
            if (!getCatT().equals("operador aritmetico")) {
                valAsig.add(symbols.get(posSimbTemp));
            }
            posSimbTemp++;
        }
        //}
        if (isSameType(valAsig, tipo)) {
            for (int i = 0; i < valAsig.size(); i++) {
                if ("id".equals(valAsig.get(i).categoria)) {

                    if (!yaInicializada(valAsig.get(i).valor)) {
                        System.out.println("---ERROR SEMANTICO---");
                        System.out.println("Variable " + valAsig.get(i).valor + " no esta inicializada y no puede utilizarse en operaciones.");
                        error();
                        error = true;
                    }
                }
            }
            //System.out.println("YES YES YES");
            pemdas(simbAsig, tupi);

        } else {
            System.out.println("---ERROR SEMANTICO---");
            System.out.println("L123os valores deben de corresponder al mismo tipo de dato al que se desa asignar, ademas de estar inicializadas.");
            error();
            error = true;
        }
    }
//    private boolean isSameType(String tipo) {
//
//    }

    private boolean isSameType(ArrayList<Symbol> valores, String tipo) {

        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i).getCategoria().equals(tipo)) {
                return true;
//
            } else if (valores.get(i).getCategoria().equals("id")) {
//                System.out.println("VALORES");
                for (int j = 0; j < variables.size(); j++) {
//                    System.out.println(valores.get(i).getValor());
                    if (valores.get(i).getValor().equals(variables.get(j).getName())) {
                        if (variables.get(j).getType().equals(tipo)) {
                            if (variables.get(j).getVar().equals(null)) {
                                System.out.println("---ERROR SEMANTICO---");
                                System.out.println("Variable " + valores.get(i).getValor() + " no ha sido inicializada.");
                                error();
                                return false;
                            } else {
                                //do something  

                            }

                        } else {
                            System.out.println("---ERROR SEMANTICO---");
                            System.out.println("Variable " + valores.get(i).getValor() + " no es mismo tipo.");
                            error();
                            return false;
                        }
                    } else if (valores.get(i).getCategoria().equals(tipo)) {
                        System.out.println("---ERROR SEMANTICO---");
                        System.out.println("El valor " + valores.get(i).getValor() + " no es mismo tipo.");
                        error();
                        return false;
                    } else {
//                        System.out.println("---ERROR SEMANTICO---");
//                        System.out.println("Variable " + valores.get(i).getValor() + " no ha sido declarada.");
//                        error();
//                        return false;
                    }
                }
                //System.out.println("EL ID SALE COMO " + valores.get(i).getCategoria());
            } else {
//                System.out.println("---ERROR SEMANTICO---");
//                error();
                return false;
            }

        }

        return true;
    }

    private ArrayList<Symbol> opArit(ArrayList<Symbol> valores, String valor, String nombre, ArrayList<Tuplas> tupi) {
        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i).getValor().equals(valor)) {
                vt = nuevaVT();
                variable = new Var(varId, vt, null, true);
                variablesTemporales.add(variable);
                tupla = new Tuplas(nombre, valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                tupi.add(tupla);
                valores.remove(i + 1);
                valores.remove(i);
                valores.remove(i - 1);
                Symbol temporal = new Symbol(valores.size() + 1, null, vt);
                valores.add(i - 1, temporal);
                i--;
            }
        }
        return valores;
    }

    private ArrayList<Symbol> exponencial(ArrayList<Symbol> valores) {
        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i).getValor().equals("^")) {
                vt = nuevaVT();
                variable = new Var(varId, vt, null, true);
                variablesTemporales.add(variable);
                tupla = new Tuplas("exponencial", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                tuplas.add(tupla);
                valores.remove(i + 1);
                valores.remove(i);
                valores.remove(i - 1);
                Symbol temporal = new Symbol(valores.size() + 1, null, vt);
                valores.add(i - 1, temporal);
            }
        }
        return valores;
    }

    private void pemdas(ArrayList<Symbol> valores, ArrayList<Tuplas> tupi) {
        //int i = 0;
        if (valores.size() > 1) {

            for (int j = 0; j < valores.size(); j++) {
                if (valores.get(j).getValor().equals("(")) {
                    ArrayList<Symbol> parentesis = new ArrayList<Symbol>();
//                if (valores.get(i).getCategoria().equals("operador aritmetico"))
                    while (!valores.get(j).getValor().equals(")")) {
                        parentesis.add(valores.get(j));
                        j++;
                    }
                    pemdas(parentesis, tupi);
                    while (!valores.get(j).getValor().equals(")")) {
                        valores.remove(j);
                        j++;
                    }
                    if (valores.get(j).getValor().equals(")")) {
                        valores.remove(j);
                    }
                }
            }
//            for (int j = 0; j < valores.size(); j++) {
//                System.out.println(valores.get(j).getValor());
//            }
//            System.out.println("SIZE es: " + valores.size());
            //while (i < valores.size()) {
            //int tamano = valores.size();
            while (valores.size() > 1) {
                valores = opArit(valores, "^", "exponencial", tupi);
                valores = opArit(valores, "*", "multiplicacion", tupi);
                valores = opArit(valores, "/", "division", tupi);
                valores = opArit(valores, "+", "suma", tupi);
                valores = opArit(valores, "-", "resta", tupi);
                valores = opArit(valores, "*", "multiplicacion", tupi);
//            valores = division(valores);
//            valores = suma(valores);
//            valores = resta(valores);
//                System.out.println("SIZE es: " + valores.size());
            }
            asigValor = vt;
        } else {
            asigValor = valores.get(0).getValor();
            vt = asigValor;
        }
    }

    private void pemdasFalseGod(ArrayList<Symbol> valores) {
        //int i = 0;
        for (int j = 0; j < valores.size(); j++) {
            if (valores.get(j).getValor().equals("(")) {
                ArrayList<Symbol> parentesis = new ArrayList<Symbol>();
//                if (valores.get(i).getCategoria().equals("operador aritmetico"))
                while (!valores.get(j).getValor().equals(")")) {
                    parentesis.add(valores.get(j));
                    j++;
                }
                pemdas(parentesis, null);
                while (!valores.get(j).getValor().equals(")")) {
                    valores.remove(j);
                    j++;
                }
                if (valores.get(j).getValor().equals(")")) {
                    valores.remove(j);
                }
            }
        }

        //while (i < valores.size()) {
        // int tamano = valores.size();
        for (int i = 0; 1 < valores.size(); i++) {
            // if (!valores.get(i).equals(null)) {
            if ((valores.get(i).getCategoria().equals("operador aritmetico"))) {
                if (valores.get(i).getValor().equals("^")) {
                    vt = nuevaVT();
                    variable = new Var(varId, vt, null, true);
                    variablesTemporales.add(variable);
                    tupla = new Tuplas("exponencial", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                    tuplas.add(tupla);
                    valores.remove(i + 1);
                    valores.remove(i);
                    valores.remove(i - 1);
                    i++;
                    i++;
                    i++;

                } else if (valores.get(i).getValor().equals("*")) {
                    vt = nuevaVT();
                    variable = new Var(varId, vt, null, true);
                    variablesTemporales.add(variable);
                    tupla = new Tuplas("multiplicacion", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                    tuplas.add(tupla);
                    valores.remove(i + 1);
                    valores.remove(i);
                    valores.remove(i - 1);
                    i++;
                    i++;
                } else if (valores.get(i).getValor().equals("/")) {
                    vt = nuevaVT();
                    variable = new Var(varId, vt, null, true);
                    variablesTemporales.add(variable);
                    tupla = new Tuplas("division", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                    tuplas.add(tupla);
                    valores.remove(i + 1);
                    valores.remove(i);
                    valores.remove(i - 1);

                    i++;
                    i++;
                } else if (valores.get(i).getValor().equals("+")) {
                    vt = nuevaVT();
                    variable = new Var(varId, vt, null, true);
                    variablesTemporales.add(variable);
                    tupla = new Tuplas("suma", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                    tuplas.add(tupla);
                    valores.remove(i + 1);
                    valores.remove(i);
                    valores.remove(i - 1);

                    Symbol temporal = new Symbol(valores.size() + 1, null, vt);
//                        valores.add(temporal);
                    valores.add(i - 1, temporal);
                    i++;

                } else if (valores.get(i).getValor().equals("-")) {
                    vt = nuevaVT();
                    variable = new Var(varId, vt, null, true);
                    variablesTemporales.add(variable);
                    tupla = new Tuplas("resta", valores.get(i - 1).getValor(), valores.get(i + 1).getValor(), vt);
                    tuplas.add(tupla);
                    valores.remove(i + 1);
                    valores.remove(i);
                    valores.remove(i - 1);
                    i++;
                    i++;
                } else {
                    //
                }
            }

            valores.trimToSize();

            //tamano = valores.size();
        }

        //  i++;
        //}
        asigValor = vt;
    }

    private void onlyDecl() {

        posTemp = pos;
        pos++;
        getCurrentTextTemporal();
        posSimb = tree.get(pos).getSimbolo();

        for (int i = 0; i < variables.size(); i++) {
            // System.out.println(variables.get(i).getName());
        }
        posSimb++;
        //  System.out.println(symbols.get(posSimb).getValor());
        if (yaDeclarada(symbols.get(posSimb).getValor())) {
            variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), false);
            variables.add(variable);
            tupla = new Tuplas("decl", symbols.get(posSimb).getValor());
            tuplas.add(tupla);
        } else {
            System.out.println("---ERROR SEMANTICO---");
            System.out.println("El nombre de la variable \"" + symbols.get(posSimb).getValor() + "\" debe ser unica.");
            error();
        }

//        System.out.println(tuplas.get(0).getTuplas());
//        System.out.println(variables.get(0).getVar());
        // ESTO SE USARA PARA ASIGNACION, NO DECLARACION.
//        if (tipo.equals(categoria)){
//            variables.add(new Var(varId, symbols.get(posSimb + 1).getValor(), symbols.get(posSimb + 1).getCategoria(), false));
//        }
//        else{
//            System.out.println("ERROR SEMANTICO: El tipo \"" + tipo + "\" no concuerda con el tipo de la variable \"" + categoria + "\".");
//        }
//        Tuplas tup = new Tuplas("decl",  )
    }

    private void error() {
        pos = tree.size();
        error = true;
        end = true;
    }

    private String obtenTipo(String id) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).getName().equals(id)) {
                variables.get(i).setValue(true);
                return variables.get(i).getType();
            }
        }
        return null;
    }

    private void asig() {
        posTemp = pos;
        // System.out.println(pos);
        posSimb = tree.get(pos + 1).getSimbolo();
//        posTemp++;
//        posSimb = tree.get(posTemp).getSimbolo();
//        System.out.println(tree.get(posTemp).getTexto());
//        System.out.println(tree.get(posTemp).getSimbolo());
        //asigTipo = symbols.get(posSimb - 1).getValor();
        if (!yaDeclarada(symbols.get(posSimb).getValor())) {
            asigTipo = obtenTipo(symbols.get(posSimb).getValor());

            if (asigTipo.equals("clones")) {
                asigValores("clones", tuplas);
//                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb + 1).getValor(), false);
//                variables.add(variable);
                //if (error == false) {
                tupla = new Tuplas("asig", asigValor, symbols.get(posSimb).getValor());
                tuplas.add(tupla);
                //}

            } else if (asigTipo.equals(symbols.get(posSimb + 2).getCategoria())) {
//                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), true);
//                variables.add(variable);
//                varId++;
                if (error == false) {
                    asigValor = symbols.get(posSimb + 2).getValor();
                    tupla = new Tuplas("asig", asigValor, symbols.get(posSimb).getValor());
                    tuplas.add(tupla);
                }

            } else {
                System.out.println("---ERROR SEMANTICO---");
                System.out.println("El nombre de la variable \"" + symbols.get(posSimb).getValor() + "\" debe ser unica.");
                error();
            }

//            while (!currentText.equals("=")) {
//                posTemp++;
//                getCurrentTextTemporal();
//            }
        }
    }

    private void asigFor(ArrayList<Tuplas> tupi) {
        posTemp = pos;
        //System.out.println("ASig FORRRR");
        //System.out.println(pos);
        posSimb = tree.get(pos + 1).getSimbolo();
//        posTemp++;
//        posSimb = tree.get(posTemp).getSimbolo();
//        System.out.println(tree.get(posTemp).getTexto());
//        System.out.println(tree.get(posTemp).getSimbolo());
        //asigTipo = symbols.get(posSimb - 1).getValor();
        if (!yaDeclarada(symbols.get(posSimb).getValor())) {
            asigTipo = obtenTipo(symbols.get(posSimb).getValor());

            if (asigTipo.equals("clones")) {
                asigValores("clones", tupi);
//                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb + 1).getValor(), false);
//                variables.add(variable);
                if (error == false) {
                    tupla = new Tuplas("asig", asigValor, symbols.get(posSimb).getValor());
                    tupi.add(tupla);
                }

            } else if (asigTipo.equals(symbols.get(posSimb + 2).getCategoria())) {
//                variable = new Var(varId, symbols.get(posSimb).getValor(), symbols.get(posSimb - 1).getValor(), true);
//                variables.add(variable);
//                varId++;
                if (error == false) {
                    asigValor = symbols.get(posSimb + 2).getValor();
                    tupla = new Tuplas("asig", asigValor, symbols.get(posSimb).getValor());
                    tupi.add(tupla);
                }

            } else {
                System.out.println("---ERROR SEMANTICO---");
                System.out.println("El nombre de la variable \"" + symbols.get(posSimb).getValor() + "\" debe ser unica.");
                error();
            }

//            while (!currentText.equals("=")) {
//                posTemp++;
//                getCurrentTextTemporal();
//            }
        }
    }

    private void constant() {
    }

    private void isId() {
    }

    private void compValores(String tipo, ArrayList<Tuplas> tupi) {
        ArrayList<Symbol> valAsig = new ArrayList<Symbol>();
        ArrayList<Symbol> simbAsig = new ArrayList<Symbol>();
        posSimbTemp = posSimb;
        //posSimbTemp++;

        //if (getValT().equals("=")) {
        //posSimbTemp++;
        while (!(getCatT().equals("EOL") || (getCatT().equals("agrupador")) || (getCatT().equals("comparacion aritmetica")))) {
            simbAsig.add(symbols.get(posSimbTemp));
            if (!getCatT().equals("operador aritmetico")) {
                valAsig.add(symbols.get(posSimbTemp));
            }
            posSimbTemp++;
        }
        //}

        if (isSameType(valAsig, tipo)) {
            for (int i = 0; i < valAsig.size(); i++) {
                if ("id".equals(valAsig.get(i).categoria)) {

                    if (!yaInicializada(valAsig.get(i).valor)) {
                        System.out.println("---ERROR SEMANTICO---");
                        System.out.println("Variable " + valAsig.get(i).valor + " no esta inicializada y no puede utilizarse en operaciones.");
                        error();
                        error = true;
                    }
                }
            }
            //System.out.println("YES YES YES");
            pemdas(simbAsig, tupi);

        } else {
            System.out.println("---ERROR SEMANTICO---");
            System.out.println("L11os valores deben de corresponder al mismo tipo de dato al que se desa asignar, ademas de estar inicializadas.");
            error();
            error = true;
        }
    }

    private void comp(ArrayList<Tuplas> tupi) {

        String primeraVt;
        String segundaVt;
        //System.out.println("1era VT " + getSimboloValor(posSimb));
        System.out.println(asigTipo);
        if (!(getSimboloCategoria(posSimb + 1).equals("comparacion aritmetica"))) {
            compValores(asigTipo, tupi);
            primeraVt = vt;
        } else {
            primeraVt = getSimboloValor(posSimb);
        }

        posSimb++;
        posSimb++;
        //System.out.println("2da VT " + getSimboloValor(posSimb));
        if (!(getSimboloCategoria(posSimb + 1).equals("EOL")) || getSimboloCategoria(posSimb + 1).equals("agrupador")) {
            //System.out.println("2da VT " + getSimboloValor(posSimb));
            compValores(asigTipo, tupi);
            segundaVt = vt;
        } else {
            segundaVt = getSimboloValor(posSimb);
        }
        recentVT = nuevaVT();
        //if (error == false) {
        tupla = new Tuplas("lessThan", primeraVt, segundaVt, recentVT, true);
        tuplasCond.add(tupla);
        tuplas.add(tupla);
    }

    private void isFor() {
        while (!(tree.get(posTemp).texto.equals("asign"))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        pos = posTemp;
        asig();
        while (!(tree.get(posTemp).texto.equals("EOL"))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        posSimb = tree.get(posTemp).simbolo;
        posSimb++;
        //
        //
        // HACER LAS COMPARACIONES ARITMETICAS, VERIFICAR QUE SEAN EL MISMO TIPO Y LAS VARIABLES ESTEN DECLARADAS.
        //
        //

        // }
        asigTipo = "clones";
        comp(tuplas);

        tupla = new Tuplas("if_true", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);
        tuplasCond.add(tupla);
        indexL++;
        tupla = new Tuplas("if_false", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);
        tuplasCond.add(tupla);
//        tupla = new Tuplas("if_true", recentVT, seccionesL.get(indexL).L);
//        tuplas.add(tupla);
//        tupla = new Tuplas("if_false", recentVT, seccionesL.get(indexL).L);
//        tuplas.add(tupla);

        while (!(tree.get(posTemp).texto.equals("asign"))) {
            posTemp++;
            //getCurrentTextTemporal();
        }
        //System.out.println("asignnnnnnn");
        //System.out.println(tree.get(posTemp).texto);
        //posTemp++;
        //System.out.println(posTemp);
        pos = posTemp;
        asigFor(tuplasFor);

        //tupla = new Tuplas("if_true", recentVT, seccionesL.get(indexL).L);
        //tuplasFor.add(tupla);
        //tuplasCond.add(tupla);
        //tupla = new Tuplas("if_false", recentVT, seccionesL.get(indexL).L);
        //tuplasFor.add(tupla);
        // tuplasCond.add(tupla);
//        System.out.println("TUPLAS EN FOR");
//        for (int i = 0; i < tuplasFor.size(); i++) {
//            System.out.println(tuplasFor.get(i).getTuplas());
//        }
        //ArrayList<Tuplas> tupitas = new ArrayList<Tuplas>;
        seccionesL.get(indexL).addTuplas(new ArrayList<Tuplas>(tuplasFor), new ArrayList<Tuplas>(tuplasCond));
        //seccionesL.get(indexL).addTuplas(new ArrayList<Tuplas>(tuplasCond));
        //
        indexL++;

        while (!(getSimboloCategoria(posSimb).equals("EOL"))) {
            posSimb++;
            getCurrentTextTemporal();
        }
        while ((!getSimboloValor(posSimb).equals("{"))) {
            posSimb++;
            getCurrentTextTemporal();
        }

        while ((!tree.get(posTemp).texto.equals("{"))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        pos = posTemp;
        // System.out.println("POSICION GENERAL");
        //System.out.println(pos);
        //pepepepepe;

        for (int i = 0; i < seccionesL.size(); i++) {
            if (seccionesL.get(i).getPosNodo() == tree.get(posTemp).id) {
                tupla = new Tuplas(seccionesL.get(i).L);

//                System.out.println("SECCION");
//                System.out.println(seccionesL.get(i).L);
            }

        }
        tuplas.add(tupla);

        //CREATE THE TWO LS's
//        System.out.println(getSimboloValor(posSimb));
        tuplasFor.clear();
        tuplasCond.clear();
    }

    private void isIf() {
        while (!(tree.get(posTemp).texto.equals("("))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        pos = posTemp;

        posSimb = tree.get(posTemp).simbolo;
//        System.out.println("SIMBOLOOOOOOOO");
//        System.out.println(getSimboloValor(posSimb));
        posSimb++;
//        System.out.println(getSimboloValor(posSimb));
//        System.out.println(getSimboloCategoria(posSimb));
        asigTipo = getSimboloCategoria(posSimb);
        if (asigTipo.equals("id")) {
            for (int i = 0; i < variables.size(); i++) {
                if (getSimboloValor(posSimb).equals(variables.get(i).name)) {
                    asigTipo = variables.get(i).type;
                    System.out.println("TIPO TIPO TIPO");
                    System.out.println(asigTipo);
                    i = variables.size();
                }
            }
        }
        comp(tuplas);

        tupla = new Tuplas("if_true", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);
        //tuplasCond.add(tupla);
        indexL++;
        tupla = new Tuplas("if_false", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);

        while ((!getSimboloValor(posSimb).equals("{"))) {
            posSimb++;
            getCurrentTextTemporal();
        }

        while ((!tree.get(posTemp).texto.equals("{"))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        pos = posTemp;
//        System.out.println("POSICION GENERAL");
//        System.out.println(pos);
        //pepepepepe;

        for (int i = 0; i < seccionesL.size(); i++) {
            if (seccionesL.get(i).getPosNodo() == tree.get(posTemp).id) {
                tupla = new Tuplas(seccionesL.get(i).L);

//                System.out.println("SECCION");
//                System.out.println(seccionesL.get(i).L);
            }

        }
        tuplas.add(tupla);

        //CREATE THE TWO LS's
        //System.out.println(getSimboloValor(posSimb));
        tuplasFor.clear();
        tuplasCond.clear();
    }

    private void isPrint() {
        posTemp = pos;
        posTemp++;
        tupla = new Tuplas("print", tree.get(posTemp).texto, "", "");
        tuplas.add(tupla);
    }

    private void isWhile() {
        while (!(tree.get(posTemp).texto.equals("("))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        pos = posTemp;

        posSimb = tree.get(posTemp).simbolo;
//        System.out.println("SIMBOLOOOOOOOO");
//        System.out.println(getSimboloValor(posSimb));
        posSimb++;
//        System.out.println(getSimboloValor(posSimb));
//        System.out.println(getSimboloCategoria(posSimb));
        asigTipo = getSimboloCategoria(posSimb);
        comp(tuplas);

        tupla = new Tuplas("if_true", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);
        tuplasCond.add(tupla);
        indexL++;
        tupla = new Tuplas("if_false", recentVT, seccionesL.get(indexL).L);
        tuplas.add(tupla);
        tuplasCond.add(tupla);

        while ((!getSimboloValor(posSimb).equals("{"))) {
            posSimb++;
            getCurrentTextTemporal();
        }

        while ((!tree.get(posTemp).texto.equals("{"))) {
            posTemp++;
            getCurrentTextTemporal();
        }
        //pos = posTemp;
        seccionesL.get(indexL).addTuplas(new ArrayList<Tuplas>(tuplasCond));
        //indexL++;
        pos = posTemp;
//        System.out.println("POSICION GENERAL");
//        System.out.println(pos);
        for (int i = 0; i < seccionesL.size(); i++) {
            if (seccionesL.get(i).getPosNodo() == tree.get(posTemp).id) {
                tupla = new Tuplas(seccionesL.get(i).L);

//                System.out.println("SECCION");
//                System.out.println(seccionesL.get(i).L);
            }

        }
        tuplas.add(tupla);
        pos++;
        //CREATE THE TWO LS's

        tuplasFor.clear();
        tuplasCond.clear();
    }

    private String getSimboloValor(int p) {
        return symbols.get(p).getValor();
    }

    private String getSimboloCategoria(int p) {
        return symbols.get(p).getCategoria();
    }
}
