/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author tzlat
 */
import java.util.HashMap;
import java.util.Map;

import static Lexer.TokenType.*;
import java.util.LinkedList;
import java.util.Stack;

class Scanner {

    private final String source;
    private final LinkedList<Token> tokens = new LinkedList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("program",PROGRAM);
        keywords.put("const", CONST);
        keywords.put("var", VAR);
        keywords.put("procedure", PROCEDURE);
        keywords.put("function", FUNCTION);
        keywords.put("set", SET);
        keywords.put("fcall", FCALL);
        keywords.put("pcall", PCALL);
        keywords.put("begin", BEGIN);
        keywords.put("end", END);
        keywords.put("if", IF);
        keywords.put("then", THEN);
        keywords.put("else", ELSE);
        keywords.put("repeat", REPEAT);
        keywords.put("until", UNTIL);
        keywords.put("for", FOR);
        keywords.put("to", TO);
        keywords.put("do", DO);
        keywords.put("readln", READ);
        keywords.put("writeln", WRITE);
        keywords.put("string", DATATYPE_STRING);
        keywords.put("double", DATATYPE_DOUBLE);
        keywords.put("int", DATATYPE_INTEGER);

    }

    Scanner(String source) {
        this.source = source;
    }

    LinkedList<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case ',':
                addToken(CARKA);
                break;
            case ';':
                addToken(STREDNIK);
                break;
            case '=':
                addToken(ROVNO);
                break;
            case ':':
                addToken(match('=') ? PRIRAZENI : DVOJTECKA);
                break;
            case '#':
                addToken(NEROVNO);
                break;
            case '<':
                addToken(match('=') ? MENSI_NEBO_ROVNO : MENSI);
                break;
            case '>':
                addToken(match('=') ? VETSI_NEBO_ROVNO : VETSI);
                break;
            case '+':
                addToken(PLUS);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '*':
                addToken(HVEZDA);
                break;
            case '/':
                addToken(LOMENO);
                break;
            case '(':
                addToken(LEVA_ZAVORKA);
                break;
            case ')':
                addToken(PRAVA_ZAVORKA);
                break;
            case '.':
                addToken(TECKA);
                break;

//            case '#':
//                // A comment goes until the end of the line.
//                while (peek() != '\n' && !isAtEnd()) {
//                    advance();
//                }
//                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;
            case '\'':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lexer.error(line, "Neocekavany znak.");
                }
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = IDENT;
        }
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) {
                advance();
            }
        }else{
            addToken(INTEGER, Integer.parseInt(source.substring(start, current)));
            return;
        }
        addToken(DOUBLE, Double.parseDouble(source.substring(start, current)));
        
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }

    private void string() {
        while (peek() != '\'' && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
            }
            advance();
        }

        if (isAtEnd()) {
            Lexer.error(line, "Neukonceny string");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }
    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private boolean match(char expected) {
        if (isAtEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

}
