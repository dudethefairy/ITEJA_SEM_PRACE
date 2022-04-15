/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

/**
 *
 * @author tzlat
 */
public class Procedure {
    private Block block;
    private String ident;

    public Procedure(String ident, Block block) {
        this.ident = ident;
        this.block = block;
    }
    
    public String getIdent() {
        return ident;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Procedure{" + "ident=" + ident + "}\n" + block;
    }
    
}
