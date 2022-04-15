/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lox;

/**
 *
 * @author tzlat
 */

public enum TokenType {
 IDENT, DOUBLE,INTEGER, CONST, VAR, PROCEDURE, CALL, BEGIN, END,
 IF, THEN, WHILE, DO, ODD, OTAZNIK, VYKRICNIK, CARKA, STREDNIK, 
 ROVNO, PRIRAZENI, NEROVNO, MENSI_NEBO_ROVNO, VETSI_NEBO_ROVNO, MENSI, VETSI,
 PLUS, MINUS, HVEZDA, LOMENO, LEVA_ZAVORKA, PRAVA_ZAVORKA, TECKA, STRING, DATATYPE_STRING,DATATYPE_DOUBLE,DATATYPE_INTEGER,DVOJTECKA, EOF
}
