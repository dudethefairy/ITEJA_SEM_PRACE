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
public class Function {
    private Block block;
    private String ident;
    private DataType dataType;
    private ArrayList<Var> parameters;

    public Function(String ident, Block block,DataType dataType, ArrayList<Var> parameters) {
        this.ident = ident;
        this.block = block;
        this.dataType = dataType;
        this.parameters = parameters;
    }
    
    public String getIdent() {
        return ident;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Function{ ident=" + ident + ", dataType=" + dataType + "}\n"+block;
    }
}
