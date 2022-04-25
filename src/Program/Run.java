/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Lexer.Lexer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class Run {

    public static void main(String[] args) throws IOException {
        Lexer lox = new Lexer();
        if (args.length == 1) {
            lox.runFileP(args[0]);
        } else {
            URL url = Run.class.getResource("vstup.txt");
            File file = new File(url.getPath());
            lox.runFileP(file.getAbsolutePath());
        }
        try {
            Program prog = new Program(lox.getTokens());
            prog.execute();
//            prog.printAST();

        } catch (Exception ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
