/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Exceptions.NotFoundException;
import Expression.Expression;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class SetStatement extends Statement {

    private String ident;
    private Expression expr;

    public SetStatement(String ident, Expression expr) {
        this.ident = ident;
        this.expr = expr;
    }

    public String getIdent() {
        return ident;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "SetStatement{" + "ident=" + ident + ", expr=" + expr + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        try {
            ex.getVars().set(ident, expr.eval(ex));
        } catch (NotFoundException exce) {
            ex.getGlobal().getVars().set(ident, expr.eval(ex));
        }
    }

}
