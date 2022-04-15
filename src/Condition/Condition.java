/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Condition;

import Parser.Evaluatable;
import Program.ExecutionContext;

/**
 *
 * @author tzlat
 */
public abstract class Condition extends Evaluatable {

    @Override
    public abstract Object eval(ExecutionContext ex) throws Exception;
   
}
