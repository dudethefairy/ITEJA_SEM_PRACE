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

public enum TokenType {
 IDENT, DOUBLE,INTEGER, CONST, VAR, PROCEDURE, FUNCTION, PCALL, FCALL, BEGIN, END,
 IF, THEN, REPEAT, UNTIL, CARKA, STREDNIK, SET, READ, WRITE, ELSE,FOR, TO, DO,
 ROVNO, PRIRAZENI, NEROVNO, MENSI_NEBO_ROVNO, VETSI_NEBO_ROVNO, MENSI, VETSI,
 PLUS, MINUS, HVEZDA, LOMENO, LEVA_ZAVORKA, PRAVA_ZAVORKA, TECKA, STRING, 
 DATATYPE_STRING,DATATYPE_DOUBLE,DATATYPE_INTEGER,DVOJTECKA, PROGRAM,EOF
}
