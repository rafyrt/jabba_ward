/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.*;
/**
 *
 * @author Felipe
 */
public class RegexJUnitTest {
    boolean expResult = true;
    public RegexJUnitTest() {
    }
    
    @Test
    public void asignacion() {
        Pattern p = Pattern.compile("=" );
        Matcher m = p.matcher("=");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
    
    @Test
        public void EOL() {
        Pattern p = Pattern.compile("~endor" );
        Matcher m = p.matcher("~endor");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void wookie() {
        Pattern p = Pattern.compile("\".*\"");
        Matcher m = p.matcher("\"wawa\"");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void ewok() {
        Pattern p = Pattern.compile("'.'");
        Matcher m = p.matcher("'z'");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void comparacionAritmetica() {
        Pattern p = Pattern.compile("(>|<|YouWereLikeMyBrother|IHateYou)");
        Matcher m = p.matcher("IHateYou");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }

        @Test
        public void aritmetico() {
        Pattern p = Pattern.compile("(\\+|\\-|\\*|\\^|\\/)");
        Matcher m = p.matcher("+");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void agrupador() {
        Pattern p = Pattern.compile("(\\(|\\)|\\{|\\})");
        Matcher m = p.matcher("(");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void id() {
        Pattern p = Pattern.compile("_\\w+");
        Matcher m = p.matcher("_pito");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }

//        @Test
//        public void drones() {
//        Pattern p = Pattern.compile("\\d+");
//        Matcher m = p.matcher("69");
//        boolean result = m.matches();
//        assertEquals(expResult, result);
//    }
        
        @Test
        public void clones() {
        Pattern p = Pattern.compile("(((\\d*|\\d*\\.\\d*)E(\\+|\\-)\\d+)|\\d*\\.\\d+|\\d+)");
        Matcher m = p.matcher("42");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }

        @Test
        public void comparacionLogica() {
        Pattern p = Pattern.compile("(AND|OR)");
        Matcher m = p.matcher("OR");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }
        
        @Test
        public void comentario() {
        Pattern p = Pattern.compile("<\\(-_-\\)>.*<\\(-_-\\)>");
        Matcher m = p.matcher("<(-_-)>COMENTARIO<(-_-)>");
        boolean result = m.matches();
        assertEquals(expResult, result);
    }

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
