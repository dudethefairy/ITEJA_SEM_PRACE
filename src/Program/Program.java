/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Block;
import Lexer.Token;
import Lexer.TokenType;
import Parser.Parser;
import java.util.LinkedList;

/**
 *
 * @author tzlat
 */
public class Program {

    LinkedList<Token> list;
    String ident;
    Parser parser;
    Block block;
    ProgramContext pc;
    ExecutionContext ex;

    public Program(LinkedList<Token> tokens) throws Exception {
        pc = new ProgramContext();
        ex = new ExecutionContext(pc, null);
        this.list = tokens;
        if (list.pop().getType() == TokenType.PROGRAM) {
            if (list.peek().getType() == TokenType.IDENT) {
                ident = list.pop().getLexeme();
            } else {
                throw new Exception("Ocekava se identifikator programu");
            }
            if (list.pop().getType() != TokenType.STREDNIK) {
                throw new Exception("Ocekava se strednik na konci radku");
            }
        } else {
            throw new Exception("Ocekava se program");
        }
        parser = new Parser(list);
        block = parser.parse();
        ex.setGlobal(ex);
    }
    
    public void execute() throws Exception {
        block.execute(ex);
    }

    public void printAST() {
        System.out.println("Program: "+ident);
        System.out.println(parser.toString());
    }

}
