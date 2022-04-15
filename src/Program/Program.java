/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Block;
import Block.Var;
import Lox.Lox;
import Lox.Token;
import Parser.Executable;
import Parser.Parser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class Program extends Executable {

    LinkedList<Token> list;
    Parser parser;
    Block block;

    public Program(LinkedList<Token> tokens) throws Exception {
        this.list = tokens;
        parser = new Parser(list);
        block = parser.parse();
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        block.execute(ex);
    }

    public void printAST() {
        System.out.println(parser.toString());
    }

}
