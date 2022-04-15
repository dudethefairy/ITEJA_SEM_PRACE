/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression.Binary;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Expression.BinaryExpression;
import Program.ExecutionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class Multiply extends BinaryExpression {

    @Override
    public Object eval(ExecutionContext ex) throws Exception {
        Object leftO = left.eval(ex);
        Object rightO = right.eval(ex);
        if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
            DoubleD leftD = (DoubleD) leftO;
            DoubleD rightD = (DoubleD) rightO;
            return new DoubleD(leftD.getValue() * rightD.getValue());
        } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
            IntegerD leftI = (IntegerD) leftO;
            IntegerD rightI = (IntegerD) rightO;
            return new IntegerD(leftI.getValue() * rightI.getValue());
        } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
            IntegerD leftI = (IntegerD) leftO;
            DoubleD rightD = (DoubleD) rightO;
            return new DoubleD(leftI.getValue() * rightD.getValue());
        } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
            DoubleD leftD = (DoubleD) leftO;
            IntegerD rightI = (IntegerD) rightO;
            return new DoubleD(leftD.getValue() * rightI.getValue());
        } else if (leftO.getClass() == StringD.class && rightO.getClass() == IntegerD.class) {
            StringD leftS = (StringD) leftO;
            IntegerD rightI = (IntegerD) rightO;
            String vystup = "";
            for (int i = 0; i < rightI.getValue(); i++) {
                vystup += leftS.getValue();
            }
            return new StringD(vystup);
        } else {
            System.out.println(leftO.getClass());
            System.out.println(rightO.getClass());
            throw new Exception("Nelze nasobit string a string, string a double, nebo number a string");
        }
    }

    @Override
    public String toString() {
        try {
            return "Multiply{" + left + "*" + right + '}';
        } catch (Exception ex) {
            Logger.getLogger(Divide.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
