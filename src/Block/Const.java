/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block;

import Block.Datatype.DataType;
import Lox.TokenType;

/**
 *
 * @author tzlat
 */
public class Const extends Data {

    public Const(String ident, DataType dataType) {
        this.ident = ident;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Const{" + "ident=" + ident + ", datatype=" + dataType + '}';
    }

}
