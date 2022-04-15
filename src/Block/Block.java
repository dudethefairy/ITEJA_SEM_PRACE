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
    private Statement statement;

    public Block() {
        consts = new ArrayList<>();
        vars = new ArrayList<>();
        procedures = new ArrayList<>();
    }

    public void addConsts(ArrayList list) {
        consts.addAll(list);
    }

    public void addVars(ArrayList list) {
        vars.addAll(list);
    }

    public void addProcedures(ArrayList list) {
        procedures.addAll(list);
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
        string += statement.toString();
        return string;
    }

}
