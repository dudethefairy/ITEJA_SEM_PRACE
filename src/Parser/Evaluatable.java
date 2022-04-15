/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public abstract class Evaluatable {
    public abstract Object eval(ExecutionContext ex) throws Exception;
}
