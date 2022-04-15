/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

import Exceptions.NotFoundException;
import Program.ExecutionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tzlat
 */
public class IdentExpression extends Expression {

    private String ident;

    public IdentExpression(String ident) {
        this.ident = ident;
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
            return ex.getVars().get(ident);
        } catch (NotFoundException ex1) {
            ExecutionContext exG = ex.getGlobal();
            return exG.getVars().get(ident);
        }
    }

    @Override
    public String toString() {
        return "IdentExpression{" + "ident=" + ident + '}';
    }

}
