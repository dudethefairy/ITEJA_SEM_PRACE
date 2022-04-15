/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

/**
 *
 * @author tzlat
 */
public class ExecutionContext {

    ProgramContext pc;
    Datas vars;
    ExecutionContext global;

    public ExecutionContext(ProgramContext pc, ExecutionContext global) {
        this.pc = pc;
        vars = new Datas();
        this.global = global;
    }

    public ProgramContext getPc() {
        return pc;
    }

    public Datas getVars() {
        return vars;
    }

    public ExecutionContext getGlobal() {
        return global;
    }
    
    public void setGlobal(ExecutionContext global) {
        this.global = global;
    }

}
