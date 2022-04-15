/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

import Block.Datatype.DataType;

/**
 *
 * @author tzlat
 */
public class Var extends Data {

    public Var(String ident, DataType dataType) {
        this.ident = ident;
        this.dataType=dataType;
    }

    public void setValue(DataType dataType) {
        this.dataType= dataType;
    }

    @Override
    public String toString() {
        return "Var{" + "ident=" + ident + ", datatype=" + dataType + '}';
    }
    
}
