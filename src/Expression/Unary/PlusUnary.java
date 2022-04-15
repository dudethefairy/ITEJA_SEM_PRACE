/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression.Unary;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Expression.UnaryExpression;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class PlusUnary extends UnaryExpression {

    @Override
    public Object eval(ExecutionContext ex) throws Exception {
        Object eval = expr.eval(ex);
        if (eval.getClass() == DoubleD.class) {
            DoubleD doubl = (DoubleD) eval;
            return new DoubleD(doubl.getValue());
        } else if (eval.getClass() == IntegerD.class) {
            IntegerD intgr = (IntegerD) eval;
            return new IntegerD(intgr.getValue());
        } else if (eval.getClass() == StringD.class) {
            StringD str = (StringD) eval;
            return new DoubleD(Double.parseDouble(str.getValue()));
        } else {
            throw new Exception("Neznámý datový typ pro unární +");
        }
    }

    @Override
    public String toString() {
        return "PlusUnary{" + expr + '}';
    }

}
