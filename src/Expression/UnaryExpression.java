/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

import Lexer.TokenType;

/**
 *
 * @author tzlat
 */
public abstract class UnaryExpression extends Expression {

    public Expression expr;

    public Expression getExpression() {
        return expr;
    }

    public void setExpression(Expression expr) {
        this.expr = expr;
    }
}
