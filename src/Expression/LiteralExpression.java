/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

import Block.Datatype.DataType;
import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class LiteralExpression extends Expression {

    private DataType typ;

    public LiteralExpression(double number) {
        this.typ = new DoubleD(number);
    }
    public LiteralExpression(int number) {
        this.typ = new IntegerD(number);
    }
    public LiteralExpression(String string) {
        this.typ = new StringD(string);
    }

    public DataType getTyp() {
        return typ;
    }

    public void setTyp(DataType typ) {
        this.typ = typ;
    }
    
    @Override
    public Object eval(ExecutionContext ex) {
        return typ;
    }

    @Override
    public String toString() {
        return "LiteralExpression{" + "datatype=" + typ + '}';
    }

}
