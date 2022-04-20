/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Block.Datatype.IntegerD;
import Condition.Condition;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class IfStatement extends Statement {

    private Statement statement;
    private Condition condition;
    private Statement elseStatement;

    public IfStatement(Statement statement, Condition condition) {
        this.statement = statement;
        this.condition = condition;
        elseStatement = null;
    }

    public IfStatement(Statement statement, Condition condition, Statement elseStatement) {
        this.statement = statement;
        this.condition = condition;
        this.elseStatement = elseStatement;
    }

    public Statement getStatement() {
        return statement;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        if (elseStatement == null) {
            return "IfStatement{" + "condition=" + condition + ", statement=" + condition + '}';
        }
        return "IfStatement{" + "condition=" + condition + ", statement=" + statement + "\n elseStatement=" + elseStatement + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        Object conEvalO = condition.eval(ex);
        if (conEvalO.getClass() == IntegerD.class) {
            IntegerD conEvalI = (IntegerD) conEvalO;
            if (conEvalI.getValue() == 1) {
                statement.execute(ex);
            } else if (elseStatement != null) {
                elseStatement.execute(ex);
            }
        } else {
            throw new Exception("Vysledek podminky v if statement neni spravny");
        }
    }

}
