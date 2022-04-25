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
public abstract class Data {

    protected String ident;
    protected DataType dataType;
    

    public String getIdent() {
        return ident;
    }

    public DataType getDataType() {
        return dataType;
    }


}
