/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

import Program.ExecutionContext;
import Statement.Statement;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class Block {

    private ArrayList<Const> consts;
    private ArrayList<Var> vars;
    private ArrayList<Procedure> procedures;
    private ArrayList<Function> functions;
    private Statement statement;

    public Block() {
        consts = new ArrayList<>();
        vars = new ArrayList<>();
        procedures = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public void addConsts(ArrayList<Const> list) {
        consts.addAll(list);
    }

    public void addVars(ArrayList<Var> list) {
        vars.addAll(list);
    }

    public void addProcedures(ArrayList<Procedure> list) {
        procedures.addAll(list);
    }
    public void addFunctions(ArrayList<Function> list) {
        functions.addAll(list);
    }

    public ArrayList<Const> getConsts() {
        return consts;
    }

    public ArrayList<Var> getVars() {
        return vars;
    }

    public ArrayList<Procedure> getProcedures() {
        return procedures;
    }
    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    public void execute(ExecutionContext ex) throws Exception{
        ex.getVars().setVars(getVars());
        ex.getVars().setConsts(getConsts());
        ex.getPc().setProcs(getProcedures());
        ex.getPc().setFuncs(getFunctions());
        ex.getVars().setFuncs(getFunctions());
        getStatement().execute(ex);
    }

    @Override
    public String toString() {
        String string = "Consts(" + consts.size() + "):\n";
        for (Const con : consts) {
            string += con.toString() + "\n";
        }
        string += "Vars(" + vars.size() + "):\n";
        for (Var var : vars) {
            string += var.toString() + "\n";
        }
        string += "Procedures(" + procedures.size() + "):\n";
        for (Procedure proc : procedures) {
            string += "\n" + proc.toString() + "\n";
        }
        string += "Functions(" + functions.size() + "):\n";
        for (Function func : functions) {
            string += "\n" + func.toString() + "\n";
        }
        string += statement.toString();
        return string;
    }

}
