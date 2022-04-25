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
public class RepeatStatement extends Statement {

    private Statement statement;
    private Condition condition;

    public RepeatStatement(Statement statement, Condition condition) {
        this.statement = statement;
        this.condition = condition;
    }

    public Statement getStatement() {
        return statement;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "RepeatStatement{" + "statement=" + statement + ", condition=" + condition + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        Object conEvalO = condition.eval(ex);
        if (conEvalO.getClass()==IntegerD.class) {
            IntegerD conEvalI = (IntegerD) conEvalO;
            do{
                statement.execute(ex);
                conEvalO = condition.eval(ex);
                conEvalI = (IntegerD) conEvalO;
            }
            while (conEvalI.getValue() != 1);
        }
    }

}
