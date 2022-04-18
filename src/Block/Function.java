/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

import Block.Datatype.DataType;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class Function extends Data {
    private Block block;
    private ArrayList<Var> arguments;

    public Function(String ident, Block block,DataType dataType, ArrayList<Var> arguments) {
        this.ident = ident;
        this.block = block;
        this.dataType = dataType;
        this.arguments = arguments;
    }

    public Block getBlock() {
        return block;
    }

    public ArrayList<Var> getArguments() {
        return arguments;
    }
    
    public void setValue(DataType dataType) {
        this.dataType= dataType;
    }

    @Override
    public String toString() {
        String arg = "";
        for (int i=0;i<arguments.size();i++) {
            if(i>0 && i<arguments.size()-1)
            arg+=", ";
            arg+=arguments.get(i).toString();
        }
        return "Function{ ident=" + ident + ", dataType=" + dataType + "} Arguments("+arg+")\n" + block;
    }
}
