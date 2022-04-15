/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Program.ExecutionContext;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class BeginEndStatement extends Statement {

    ArrayList<Statement> statements;

    public BeginEndStatement(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        String string = "BEGIN\n";
        for (Statement stat : statements) {
            string += stat.toString() + "\n";
        }
        string += "END\n";
        return string;
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        for (Statement st : statements) {
            st.execute(ex);
        }
    }

}
