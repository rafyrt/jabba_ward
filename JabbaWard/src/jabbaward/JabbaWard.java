/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbaward;

import java.io.IOException;
import java.io.PrintStream;
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
    public static void main(String[] args) {
        // TODO code application logic here
        //String filePath = ".\\test.jarjar";
        String filePath = ".\\t1.jarjar";
        String completeFile = fileReader( filePath );
        String[] list = listCreator(completeFile);
        ArrayList<Symbol> tablaSimbolos = new ArrayList<>();
        tablaSimbolos = LexicalAnalyzer.Analyse(list);
        System.out.println(tablaSimbolos.size());
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
        String[] tokenList = completeFile.split("\\s+"); 
        return tokenList;
    }
}
