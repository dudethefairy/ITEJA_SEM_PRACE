/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Condition.Condition;
import Expression.Expression;
import Expression.IdentExpression;
import Expression.LiteralExpression;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class ForStatement extends Statement {

    String ident;
    Expression expr;
    Expression expr2;
    private Statement statement;

    public ForStatement(String ident, Expression expr, Expression expr2, Statement statement) {
        this.ident = ident;
        this.expr = expr;
        this.expr2 = expr2;
        this.statement = statement;
    }

    public String getIdent() {
        return ident;
    }

    public Expression getExpr() {
        return expr;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "ForStatement{" + "ident=" + ident + ", exprStart=" + expr + ", exprTo=" + expr2 + ", statement=" + statement + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        SetStatement setS = new SetStatement(ident, expr);
        setS.execute(ex);
        Object expreEvalO = expr.eval(ex);
        Object expre2EvalO = expr2.eval(ex);
        if (expreEvalO.getClass() == IntegerD.class && expre2EvalO.getClass() == IntegerD.class) {
            IntegerD expreEvalI = (IntegerD) expreEvalO;
            IntegerD expre2EvalI = (IntegerD) expre2EvalO;
            if (expreEvalI.getValue() < expre2EvalI.getValue()) {
                for (int i = expreEvalI.getValue(); i <= expre2EvalI.getValue(); i++) {
                    SetStatement setState = new SetStatement(ident, new LiteralExpression(i));
                    setState.execute(ex);
                    statement.execute(ex);
                }
            } else {
                for (int i = expreEvalI.getValue(); i >= expre2EvalI.getValue(); i--) {
                    SetStatement setState = new SetStatement(ident, new LiteralExpression(i));
                    setState.execute(ex);
                    statement.execute(ex);
                }
            }
        } else {
            throw new Exception("For cyclus musi obsahovat cela cisla");
        }
    }

}
