/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Expression.Expression;
import Program.ExecutionContext;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class CallFunctionStatement extends Statement {
    private String ident;
    private ArrayList<Expression> argumenty;

    public CallFunctionStatement(String ident,ArrayList<Expression> argumenty) {
        this.ident = ident;
        this.argumenty = argumenty;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        String par = "";
        for (int i=0;i<argumenty.size();i++) {
            if(i>0 && i<argumenty.size()-1)
            par+=", ";
            par+=argumenty.get(i).toString(); 
        }
        return "CallFunctionStatement{" + "ident=" + ident + "} Arguments("+par+")\n";
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        
        ex.getPc().callFunction(argumenty,ident, ex);
    }
}
