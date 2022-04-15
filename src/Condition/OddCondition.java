/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Condition;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Expression.Expression;
import Program.ExecutionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class OddCondition extends Condition {

    private Expression expression;

    public OddCondition(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Object eval(ExecutionContext ex) throws Exception {
        Object expr = expression.eval(ex);
        if (expr.getClass() == DoubleD.class) {
            DoubleD doubl = (DoubleD) expr;
            if (doubl.getValue() % 2 == 0) {
                return new IntegerD(0);
            } else {
                return new IntegerD(1);
            }
        } else if (expr.getClass() == IntegerD.class) {
            IntegerD intgr = (IntegerD) expr;
            if (intgr.getValue() % 2 == 0) {
                return new IntegerD(0);
            } else {
                return new IntegerD(1);
            }
        } else {
            System.out.println(expr.getClass());
            throw new Exception("ODD vyžaduje číslo");
        }
    }

    @Override
    public String toString() {
        try {
            return "OddCondition{" + "expression=" + expression + '}';
        } catch (Exception ex) {
            Logger.getLogger(OddCondition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
