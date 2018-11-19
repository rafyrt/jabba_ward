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

    public SA(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        tree.add(inicial);
    }

    public ArrayList<Nodo> compare() {
        for (int i = 0; i <= symbols.size(); i++) {

            if (symbols.get(i).getValor().equals("{") || symbols.get(i).getCategoria().equals("EOL")) {
                symbChunk.add(symbols.get(i));
                pos = 0;
                System.out.println("TAMANO DEL CHUNK " + symbChunk.size());
                rules();
                tree.addAll(currentTree);
                erroresTotal.add(errorCurrent);
                errorCurrent.clear();
                symbChunk.clear();
                currentTree.clear();
            } else if (symbols.get(i).getCategoria().equals("EOF")) {
                pos = 0;
                //
                for (int j = 0; j < symbChunk.size(); j++) {
                    System.out.println("ESTE " + symbChunk.size());
                }
                System.out.println("chetos");
                if (symbChunk.size() > 1) {
                    rules();
                    tree.addAll(currentTree);
                    erroresTotal.add(errorCurrent);
                    errorCurrent.clear();
                }

                symbChunk.clear();
                currentTree.clear();
                mostrarErrores();
                break;
            } else {
                symbChunk.add(symbols.get(i));
            }
        
        }
        return tree;
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
        System.out.println("TAMANO DEL CHUNK " + symbChunk.size());
        for (int i = 0; i <= symbChunk.size(); i++) {
            System.out.println(symbChunk.get(i).getValor());
        }
        System.out.println(pos);
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

        if (mainExist == false) {
            id++;
            niv++;

            //nivOrig = niv;
            main();
            //niv = nivOrig;
            //System.out.println("tamanio" + currentTree.size());
        } else {
            down();
            addCurrent(id, niv, "line", dad);
            line();
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
                lr();
                up();
                //mainExist = true;
            }
            // System.out.println(niv);
        } else {
            tokenError(getS(pos));
        }
    }
    boolean is isC(int p, String t){
        if (getC(p).equals(t)){
            return true;
        } else {
            return false;
        }
    }

    boolean is isV(String t){
        if (getV(pod).equals(t)){
            return true;
        } else {
            return false;
        }
    }
    //getC(pos).equals("tipo de dato")
     void line() {
        if (isC("tipo de dato")) {
            declOrAsig();
        } else if (isV("do")) {
            isIf();
        } else if (isV("helloThere")) {
            isPrint();
        }
     }

     void declOrAsig(){
        if (getC(pos+1).equals("id") && getC(pos+2).equals("EOL")){
            addCurrent(id, niv, "decl", dad);
            down();
            decl();

        } else {
            asig();
        }
     }

     void decl(){

        if (isC("tipo de dato")){
            addCurrent(id, niv, "type", dad);
            down();
            addCurrent(id, nivel, getV(pos), dad);
            up(); up(); lr();
            if (isC("id")){
                addCurrent(id, niv, "id", dad);
                down();
                addCurrent(id, nivel, getV(pos), dad);
                up(); up(); lr();   
                if (isC("EOL")){
                    isEOL();
                }   
                else {error();}
            }
            else {error();}
        }
        else {error();}
    }
    