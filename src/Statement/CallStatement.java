/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public class CallStatement extends Statement {
    private String ident;

    public CallStatement(String ident) {
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        return "CallStatement{" + "ident=" + ident + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        ex.getPc().call(ident, ex);
    }
}
