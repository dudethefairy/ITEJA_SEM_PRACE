/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Block.Const;
import Block.Data;
import Block.Datatype.DataType;
import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Exceptions.NotFoundException;
import Block.Var;
import java.util.ArrayList;

/**
 *
 * @author tzlat
 */
public class Datas {

    private ArrayList<Var> vars;
    private ArrayList<Const> consts;

    public Datas() {
        vars = new ArrayList<>();
        consts = new ArrayList<>();
    }

    public void setVars(ArrayList<Var> vars) {
        this.vars = vars;
    }

    public void addVars(ArrayList<Var> vars) {
        this.vars.addAll(vars);
    }

    public ArrayList<Var> getVars() {
        return vars;
    }

    public ArrayList<Const> getConsts() {
        return consts;
    }

    public void setConsts(ArrayList<Const> consts) {
        this.consts = consts;
    }

    public void addConsts(ArrayList<Const> consts) {
        this.consts.addAll(consts);
    }

    public Object get(String ident) throws Exception {
        for (Const con : consts) {
            if (con.getIdent().equals(ident)) {
                if (con.getDataType().getClass() == DoubleD.class) {
                    DoubleD doubl = (DoubleD) con.getDataType();
                    return doubl;
                } else if (con.getDataType().getClass() == IntegerD.class) {
                    IntegerD intgr = (IntegerD) con.getDataType();
                    return intgr;
                } else if (con.getDataType().getClass() == StringD.class) {
                    StringD str = (StringD) con.getDataType();
                    return str;
                } else {
                    throw new Exception("Neznámý datový typ");
                }
            }
        }
        for (Var var : vars) {
            if (var.getIdent().equals(ident)) {
                if (var.getDataType().getClass() == DoubleD.class) {
                    DoubleD doubl = (DoubleD) var.getDataType();
                    return doubl;
                } else if (var.getDataType().getClass() == IntegerD.class) {
                    IntegerD intgr = (IntegerD) var.getDataType();
                    return intgr;
                } else if (var.getDataType().getClass() == StringD.class) {
                    StringD str = (StringD) var.getDataType();
                    return str;
                } else {
                    throw new Exception("Neznámý datový typ");
                }
            }
        }
        throw new NotFoundException("Konstanta, nebo proměnná " + ident + " nenalezena");
    }

    public void set(String ident, Object value) throws Exception {
        for (Var var : vars) {
            if (var.getIdent().equals(ident)) {
                if (var.getDataType().getClass() == DoubleD.class) {
                    if (value.getClass() == DoubleD.class) {
                        var.setValue((DoubleD) value);
                    } else if (value.getClass() == IntegerD.class) {
                        IntegerD dou = (IntegerD) value;
                        var.setValue(new DoubleD(dou.getValue()));
                    } else {
                        throw new Exception("Nelze přiřadit hodnotu " + value + " do proměnné \"" + ident + "\" typu " + var.getDataType().getClass().getSimpleName());
                    }
                } else if (var.getDataType().getClass() == IntegerD.class) {
                    if (value.getClass() == IntegerD.class) {
                        var.setValue((IntegerD) value);
                    } else if (value.getClass() == DoubleD.class) {
                        DoubleD dou = (DoubleD) value;
                        var.setValue(new IntegerD((int) dou.getValue()));
                    } else {
                        throw new Exception("Nelze přiřadit hodnotu " + value + " do proměnné \"" + ident + "\" typu " + var.getDataType().getClass().getSimpleName());
                    }
                } else if (var.getDataType().getClass() == StringD.class) {
                    if (value.getClass() == StringD.class) {
                        var.setValue((StringD) value);
                    } else {
                        throw new Exception("Nelze přiřadit hodnotu " + value + " do proměnné \"" + ident + "\" typu " + var.getDataType().getClass().getSimpleName());
                    }
                    
                } else {
                    throw new Exception("Chyba přiřazení proměné");
                }
                return;
            }
        }
        throw new NotFoundException("Proměnná " + ident + " nebyla deklarována");
    }
}
