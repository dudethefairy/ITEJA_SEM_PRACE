/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Procedure;
import Block.Var;
import Expression.Expression;
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

    public void call(ArrayList<Expression> argumenty, String ident, ExecutionContext ex) throws Exception {
        if (!procs.isEmpty()) {
            ArrayList<Procedure> prochazeni = new ArrayList<>();
            prochazeni.addAll(procs);
            for (Procedure proc : prochazeni) {
                if (proc.getIdent().equals(ident)) {
                    if (argumenty.size() != proc.getParameters().size()) {
                        throw new Exception("Počet parametrů při volání funkce neodpovídá počtu parametrů funkce");
                    }
                    ExecutionContext exN = new ExecutionContext(ex.getPc(), ex.getGlobal());
                    exN.getVars().setVars(proc.getBlock().getVars());
                    exN.getVars().setConsts(proc.getBlock().getConsts());
                    exN.getVars().addVars(proc.getParameters());
                    for (int i = 0; i < proc.getParameters().size(); i++) {
                        exN.getVars().set(proc.getParameters().get(i).getIdent(), argumenty.get(i));
                    }
                    procs.addAll(proc.getBlock().getProcedures());
                    proc.getBlock().getStatement().execute(exN);
                }
            }
        }
    }
}
