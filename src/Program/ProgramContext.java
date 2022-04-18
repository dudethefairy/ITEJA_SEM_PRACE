/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Function;
import Block.Procedure;
import Expression.Expression;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class ProgramContext {

    ArrayList<Procedure> procs;
    ArrayList<Function> funcs;

    public ProgramContext() {
        procs = new ArrayList<>();
        funcs = new ArrayList<>();
    }

    public ArrayList<Procedure> getProcs() {
        return procs;
    }
    public void setProcs(ArrayList<Procedure> procs) {
        this.procs = procs;
    }

    public ArrayList<Function> getFuncs() {
        return funcs;
    }

    public void setFuncs(ArrayList<Function> funcs) {
        this.funcs = funcs;
    }
    public void callFunction(ArrayList<Expression> argumenty, String ident, ExecutionContext ex) throws Exception {
        if (!funcs.isEmpty()) {
            ArrayList<Function> prochazeni = new ArrayList<>();
            prochazeni.addAll(funcs);
            for (Function func : prochazeni) {
                if (func.getIdent().equals(ident)) {
                    if (argumenty.size() != func.getArguments().size()) {
                        throw new Exception("Počet parametrů při volání funkce neodpovídá počtu parametrů funkce");
                    }
                    ExecutionContext exN = new ExecutionContext(ex.getPc(), ex.getGlobal());
                    exN.getVars().setVars(func.getBlock().getVars());
                    exN.getVars().setConsts(func.getBlock().getConsts());
                    exN.getVars().addVars(func.getArguments());
                    ArrayList<Function> list = new ArrayList<>();
                    list.add((Function)ex.getVars().getF(ident));
                    exN.getVars().addFuncs(list);
                    for (int i = 0; i < func.getArguments().size(); i++) {
                        Object eval = argumenty.get(i).eval(ex);
                        exN.getVars().set(func.getArguments().get(i).getIdent(), eval);
                    }
                    funcs.addAll(func.getBlock().getFunctions());
                    func.getBlock().getStatement().execute(exN);
                }
            }
        }
    }

    public void callProcedure(ArrayList<Expression> argumenty, String ident, ExecutionContext ex) throws Exception {
        if (!procs.isEmpty()) {
            ArrayList<Procedure> prochazeni = new ArrayList<>();
            prochazeni.addAll(procs);
            for (Procedure proc : prochazeni) {
                if (proc.getIdent().equals(ident)) {
                    if (argumenty.size() != proc.getArguments().size()) {
                        throw new Exception("Počet parametrů při volání funkce neodpovídá počtu parametrů funkce");
                    }
                    ExecutionContext exN = new ExecutionContext(ex.getPc(), ex.getGlobal());
                    exN.getVars().setVars(proc.getBlock().getVars());
                    exN.getVars().setConsts(proc.getBlock().getConsts());
                    exN.getVars().addVars(proc.getArguments());
                    for (int i = 0; i < proc.getArguments().size(); i++) {
                        Object eval = argumenty.get(i).eval(ex);
                        exN.getVars().set(proc.getArguments().get(i).getIdent(), eval);
                    }
                    procs.addAll(proc.getBlock().getProcedures());
                    proc.getBlock().getStatement().execute(exN);
                }
            }
        }
    }
}
