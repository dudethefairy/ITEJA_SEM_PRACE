/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Expression.Expression;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class WriteStatement extends Statement {

    private Expression expr;

    public WriteStatement(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "WriteStatement{" + "expr=" + expr + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        Object printO = expr.eval(ex);
        
        if (printO.getClass() == DoubleD.class) {
            DoubleD printN = (DoubleD) printO;
            System.out.println(printN.getValue());
        } else if (printO.getClass() == IntegerD.class) {
            IntegerD printI = (IntegerD) printO;
            System.out.println(printI.getValue());
        } else if (printO.getClass() == StringD.class) {
            StringD printS = (StringD) printO;
            System.out.println(printS.getValue());
        } else{
            throw new Exception("Nelze vypsat neznamy typ");
        }
    }

}
