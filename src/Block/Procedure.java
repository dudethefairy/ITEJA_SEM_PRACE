/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class Procedure {

    private Block block;
    private String ident;
    private ArrayList<Var> parameters;

    public Procedure(String ident, Block block, ArrayList<Var> parameters) {
        this.ident = ident;
        this.block = block;
        this.parameters = parameters;
    }

    public String getIdent() {
        return ident;
    }

    public Block getBlock() {
        return block;
    }

    public ArrayList<Var> getParameters() {
        return parameters;
    }
    

    @Override
    public String toString() {
        String par = "";
        for (int i=0;i<parameters.size();i++) {
            if(i>0 && i<parameters.size()-1)
            par+=", ";
            par+=parameters.get(i).toString();
            
        }
        return "Procedure{" + "ident=" + ident + "} Arguments("+par+")\n" + block;
    }

}
