/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Block.Datatype;

/**
 *
 * @author tzlat
 */
public class IntegerD extends DataType {
    private int value;

    public IntegerD(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public IntegerD() {
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntegerD{" + "value=" + value + '}';
    }
    
}
