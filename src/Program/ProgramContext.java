/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Procedure;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class ProgramContext {

    ArrayList<Procedure> procs;

    public ProgramContext() {
        procs = new ArrayList<>();
    }

    public ArrayList<Procedure> getProcs() {
        return procs;
    }

    public void setProcs(ArrayList<Procedure> procs) {
        this.procs = procs;
    }

    public void call(String ident, ExecutionContext ex) throws Exception {
        if (!procs.isEmpty()) {
            ArrayList<Procedure> prochazeni =new ArrayList<>();
            prochazeni.addAll(procs);
            for (Procedure proc : prochazeni) {
                if (proc.getIdent().equals(ident)) {
                    ExecutionContext exN = new ExecutionContext(ex.getPc(), ex.getGlobal());
                    exN.getVars().setVars(proc.getBlock().getVars());
                    exN.getVars().setConsts(proc.getBlock().getConsts());
                    procs.addAll(proc.getBlock().getProcedures());
                    proc.getBlock().getStatement().execute(exN);
                }
            }
        }
    }
}
