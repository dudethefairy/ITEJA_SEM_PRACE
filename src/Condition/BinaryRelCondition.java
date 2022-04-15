/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Condition;

import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Expression.Expression;
import Lox.TokenType;
import Program.ExecutionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class BinaryRelCondition extends Condition {

    private Expression left;
    private TokenType rel;
    private Expression right;

    public BinaryRelCondition(Expression left, TokenType rel, Expression right) {
        this.left = left;
        this.rel = rel;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public TokenType getRel() {
        return rel;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        try {
            return "BinaryRelCondition{" + "left=" + left + ", rel=" + rel + ", right=" + right + '}';
        } catch (Exception ex) {
            Logger.getLogger(BinaryRelCondition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Object eval(ExecutionContext ex) throws Exception {
        Object leftO = left.eval(ex);
        Object rightO = right.eval(ex);
        switch (rel) {
            case ROVNO:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() == rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() == rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() == rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() == rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    StringD leftS = (StringD) leftO;
                    StringD rightS = (StringD) rightO;
                    if (leftS.getValue() == rightS.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
            case NEROVNO:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() != rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() != rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() != rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() != rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    StringD leftS = (StringD) leftO;
                    StringD rightS = (StringD) rightO;
                    if (leftS.getValue() != rightS.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
            case VETSI:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() > rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() > rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() > rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() > rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
            case VETSI_NEBO_ROVNO:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() >= rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() >= rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() >= rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() >= rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    StringD leftS = (StringD) leftO;
                    StringD rightS = (StringD) rightO;
                    if (leftS.getValue().contains(rightS.getValue())) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
            case MENSI:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() < rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() < rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() < rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() < rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
            case MENSI_NEBO_ROVNO:
                if (leftO.getClass() == DoubleD.class && rightO.getClass() == DoubleD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftD.getValue() <= rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == IntegerD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftI.getValue() <= rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == IntegerD.class && rightO.getClass() == DoubleD.class) {
                    IntegerD leftI = (IntegerD) leftO;
                    DoubleD rightD = (DoubleD) rightO;
                    if (leftI.getValue() <= rightD.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == DoubleD.class && rightO.getClass() == IntegerD.class) {
                    DoubleD leftD = (DoubleD) leftO;
                    IntegerD rightI = (IntegerD) rightO;
                    if (leftD.getValue() <= rightI.getValue()) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else if (leftO.getClass() == StringD.class && rightO.getClass() == StringD.class) {
                    StringD leftS = (StringD) leftO;
                    StringD rightS = (StringD) rightO;
                    if (rightS.getValue().contains(leftS.getValue())) {
                        return new IntegerD(1);
                    }
                    return new IntegerD(0);
                } else {
                    throw new Exception("Leva a Prava strana vyrazu nejsou srovnatelneho typu");
                }
        }
        throw new Exception("Binary Condition Error");
    }

}
