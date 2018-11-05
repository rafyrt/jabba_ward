/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 *
 * @author Rafael  
 */
public class JabbaWard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
          //String filePath = ".\\prueba.jarjar";
          String filePath = ".\\noExam.jarjar";
//String filePath = ".\\z.jarjar";
//        String filePath = ".\\test4.jarjar";
//        String filePath = ".\\test3.jarjar";
//        String filePath = ".\\test2.jarjar";
        //String filePath = ".\\t1.jarjar";
        String completeFile = fileReader( filePath );
        String[] list = listCreator(completeFile);
        ArrayList<Symbol> tablaSimbolos = new ArrayList<>();
        tablaSimbolos = LexicalAnalyzer.Analyse(list);
//        System.out.println("Lista de Simbolos");
        PrintWriter writer = new PrintWriter(new FileWriter(".\\TablaDeSimbolos.txt"));
        writer.println("Tabla de Simbolos");
        String format = "%-10s %-25s %-5s %n";
        writer.printf(format, "ENTRADA", "CATEGORIA", "VALOR");
        writer.printf(format, "-------", "---------", "-----");
        for (int i = 0; i < tablaSimbolos.size(); i++){
            writer.printf(format, tablaSimbolos.get(i).getEntrada(), tablaSimbolos.get(i).getCategoria(), tablaSimbolos.get(i).getValor());
        }
        writer.close();
//        ParserRafa pr = new ParserRafa(tablaSimbolos);
//         System.out.println(pr.isS());
        
        ParserRafa pr = new ParserRafa(tablaSimbolos);
        if (pr.isS()) {System.out.println("EL CODIGO ES CORRECTO!");}
        else {System.out.println("AVISO: Existen errores en el codigo.");}
        
        SA p = new SA(tablaSimbolos);
        ArrayList<Nodo> tree = new ArrayList<Nodo>();
        
        tree = p.compare();
        
        PrintWriter writer2 = new PrintWriter(new FileWriter(".\\tree.txt"));
          
       String format2 = "%-20s";
        
       writer2.println("----------------------------TREE----------------------------");
       writer2.println("NIVEL-------------------------------------------------------");
       //System.out.println("NuMERO DE NODOS EN tree" + tree.size());
       int nivel = 0;
        for (int i = 0; i < tree.size(); i++) {
            if (nivel<tree.get(i).getNivel()) {
                nivel = tree.get(i).getNivel();
            }
            
        }
        //System.out.println("EL NIVEL ES: "+nivel);
         
        //String format2 = "%-10s";
        for (int nivelActual = 0; nivelActual <= nivel; nivelActual++) {
            writer2.print(nivelActual + "\t");   
            for (int i = 0; i < tree.size(); i++) {
                if (nivelActual == tree.get(i).getNivel()){
                    writer2.printf(format2, tree.get(i).getTexto()+ "," + tree.get(i).getId());
                }

            }
            writer2.println();
        }
//                System.out.println("El tercer id es " + tree.get(7).getTexto());
//                System.out.println("El nodo padre es " + tree.get(tree.get(7).getPadre()).getTexto());
        writer2.close();
        
        PrintWriter writer3 = new PrintWriter(new FileWriter(".\\nodos.txt"));
        
        for (int i = 0; i < tree.size(); i++) {
//            writer3.println(tree.get(i).getId() + "\t" + tree.get(i).getTexto() + "\t\t" + tree.get(i).getNivel());
            writer3.printf(format, tree.get(i).getId(), tree.get(i).getTexto(), tree.get(i).getNivel());
            //writer3.println();
        }
        
        writer3.close();
//        
//        for (int i = 0; i < tree.size(); i++) {
//            System.out.println();
//            System.out.println("Papi es " + tree.get(tree.get(i).getPadre()).getTexto());
//            System.out.println("El id es " + tree.get(i).getTexto());
//            System.out.println();
//        
    }

    private static String fileReader(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
    
    private static String[] listCreator(String completeFile){
        String[] tokenList = completeFile.trim().split("\\s+"); 
        return tokenList;
    }
}