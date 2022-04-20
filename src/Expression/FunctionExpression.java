/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

import Exceptions.NotFoundException;
import Program.ExecutionContext;
import Statement.CallFunctionStatement;
import Statement.Statement;

/**
 *
 * @author tzlat
 */
public class FunctionExpression extends Expression {

    private String ident;
    private CallFunctionStatement state;

    public FunctionExpression(String ident,CallFunctionStatement state) {
        this.ident = ident;
        this.state = state;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    @Override
    public Object eval(ExecutionContext ex) throws Exception {
        try {
            state.execute(ex);
            return ex.getVars().getF(ident);
        } catch (NotFoundException ex1) {
            ExecutionContext exG = ex.getGlobal();
            return exG.getVars().getF(ident);
        }
    }

    @Override
    public String toString() {
        return "FunctionExpression{" + "ident=" + ident + '}';
    }
    
}
