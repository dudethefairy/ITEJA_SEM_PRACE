/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Block.Datatype.DataType;
import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Exceptions.NotFoundException;
import Program.ExecutionContext;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author tzlat
 */
public class ReadStatement extends Statement {

    private String ident;

    public ReadStatement(String ident) {
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        return "ReadStatement{" + "ident=" + ident + '}';
    }

    @Override
    public void execute(ExecutionContext ex) throws Exception {
        Scanner vst = new Scanner(System.in).useLocale(Locale.US);;
        Object vstupO = null;
        DataType typ;
        boolean local;
        try {
            vstupO = ex.getVars().get(ident);
            local = true;
        } catch (NotFoundException exc) {
            vstupO = ex.getGlobal().getVars().get(ident);
            local = false;
        }
        if (vstupO == null) {
            throw new Exception("Ident" + ident + "nebyl nalezen");
        } else if (vstupO.getClass() == DoubleD.class) {
            double nova = vst.nextDouble();
            typ = new DoubleD(nova);
        } else if (vstupO.getClass() == IntegerD.class) {
            int nova = vst.nextInt();
            typ = new IntegerD(nova);
        } else if (vstupO.getClass() == StringD.class) {
            String nova = vst.nextLine();
            typ = new StringD(nova);
        } else{
            throw new Exception("Neznámý typ pro vstup z klávesnice");
        }
        if (local) {
            ex.getVars().set(ident, typ);
        } else {
            ex.getGlobal().getVars().set(ident, typ);
        }
    }

}
