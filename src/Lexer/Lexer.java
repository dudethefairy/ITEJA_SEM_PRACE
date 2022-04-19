/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Lexer {

    static boolean hadError = false;
    private static LinkedList<Token> tokens;

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public void runFileP(String path) throws IOException {
        runFile(path);
    }

//    public void runPromptP() throws IOException {
//        runPrompt();
//    }
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) {
            System.exit(65);
        }
    }

//    private static void runPrompt() throws IOException {
//        InputStreamReader input = new InputStreamReader(System.in);
//        BufferedReader reader = new BufferedReader(input);
//
//        for (;;) {
//            System.out.print("> ");
//            String line = reader.readLine();
//            if (line == null) {
//                break;
//            }
//            run(line);
//            hadError = false;
//        }
//    }
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        tokens = scanner.scanTokens();
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " na konci", message);
        } else {
            report(token.line, "", message);
        }
    }

    private static void report(int line, String where,
            String message) {
        System.err.println(
                "[radek " + line + "] Chyba" + where + ": " + message);
        hadError = true;
    }

}
