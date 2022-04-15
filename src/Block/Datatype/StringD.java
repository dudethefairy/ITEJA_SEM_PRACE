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
public class StringD extends DataType {

    private String value;

    public StringD(String value) {
        this.value = value;
    }

    public StringD() {
    }
    

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "String{" + "value=" + value + '}';
    }
    
}
