/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

/**
 *
 * @author tzlat
 */
public abstract class BinaryExpression extends Expression {
    public Expression left;
    public Expression right;
    
    public void setLeft(Expression left){
    this.left=left;
    }

    public void setRight(Expression right){
    this.right=right;
    }

    public Expression getLeft(){
    return left;
    }

    public Expression getRight(){
    return right;
    }
}
