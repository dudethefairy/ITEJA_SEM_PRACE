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
    private ArrayList<Var> arguments;

    public Procedure(String ident, Block block, ArrayList<Var> arguments) {
        this.ident = ident;
        this.block = block;
        this.arguments = arguments;
    }

    public String getIdent() {
        return ident;
    }

    public Block getBlock() {
        return block;
    }

    public ArrayList<Var> getArguments() {
        return arguments;
    }
    

    @Override
    public String toString() {
        String arg = "";
        for (int i=0;i<arguments.size();i++) {
            if(i>0 && i<arguments.size()-1)
            arg+=", ";
            arg+=arguments.get(i).toString();
        }
        return "Procedure{" + "ident=" + ident + "} Arguments("+arg+")\n" + block;
    }

}
