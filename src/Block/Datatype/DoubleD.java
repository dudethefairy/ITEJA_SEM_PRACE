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
public class DoubleD extends DataType{
    private double value;

    public DoubleD(double value) {
        this.value = value;
    }

    public DoubleD() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DoubleD{" + "value=" + value + '}';
    }
    
}
