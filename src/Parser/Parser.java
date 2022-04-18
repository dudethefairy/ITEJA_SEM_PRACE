/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import Block.Block;
import Block.Const;
import Block.Data;
import Block.Datatype.DataType;
import Block.Datatype.DoubleD;
import Block.Datatype.IntegerD;
import Block.Datatype.StringD;
import Block.Function;
import Block.Procedure;
import Block.Var;
import Condition.BinaryRelCondition;
import Condition.Condition;
import Condition.OddCondition;
import Expression.BinaryExpression;
import Expression.Expression;
import Expression.IdentExpression;
import Expression.LiteralExpression;
import Expression.Binary.Divide;
import Expression.Binary.Minus;
import Expression.Unary.MinusUnary;
import Expression.Binary.Multiply;
import Expression.Binary.Plus;
import Expression.Unary.PlusUnary;
import Lox.Lox;
import Lox.Token;
import Lox.TokenType;
import Statement.BeginEndStatement;
import Statement.CallFunctionStatement;
import Statement.CallProcedureStatement;
import Statement.IfStatement;
import Statement.ReadStatement;
import Statement.SetStatement;
import Statement.Statement;
import Statement.WhileStatement;
import Statement.WriteStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tzlat
 */
public class Parser {

    private static class ParseError extends RuntimeException {
    }
    LinkedList<Token> list;
    Block block;

    @Override
    public String toString() {
        return block.toString();
    }

    private ParseError printError(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    public Parser(LinkedList<Token> list) {
        this.list = list;
        block = new Block();
    }

    public Block parse() throws Exception {
        block = readBlock();
        if (list.peek().getType() == TokenType.TECKA) {
            return block;
        } else {
            throw printError(list.getFirst(), "Ocekavam .");
        }

    }

    public Block readBlock() throws Exception {
        Block localBlock = new Block();
        if (list.peek().getType() == TokenType.CONST) {
            localBlock.addConsts(readConsts());
        }
        if (list.peek().getType() == TokenType.VAR) {
            localBlock.addVars(readVars());
        }
        ArrayList<Procedure> procedury = new ArrayList<>();
        ArrayList<Function> funkce = new ArrayList<>();
        loop:
        while (true) {
            switch (list.peek().getType()) {
                case PROCEDURE:
                    Procedure proc = readProcedure();
                    if (proc == null) {
                        throw printError(list.getFirst(), "Chyba pri nacitani procedury");
                    }
                    for (Procedure dat : procedury) {
                        if (dat.getIdent().equals(proc.getIdent())) {
                            throw printError(list.getFirst(), "Identifikator procedury je jiz deklarovany");
                        }
                    }
                    procedury.add(proc);
                    break;
                case FUNCTION:
                    Function func = readFunction();
                    if (func == null) {
                        throw printError(list.getFirst(), "Chyba pri nacitani funkce");
                    }
                    for (Function dat : funkce) {
                        if (dat.getIdent().equals(func.getIdent())) {
                            throw printError(list.getFirst(), "Identifikator funkce je jiz deklarovany");
                        }
                    }
                    funkce.add(func);
                    break;
                default:
                    localBlock.addProcedures(procedury);
                    localBlock.addFunctions(funkce);
                    break loop;

            }
        }
        localBlock.setStatement(readStatement());
        return localBlock;
    }

    public ArrayList<Const> readConsts() throws Exception {
        ArrayList<Const> consts = new ArrayList<>();
        if (list.pop().getType() == TokenType.CONST) {
            while (true) {
                if (list.peek().getType() == TokenType.IDENT) {
                    String ident = list.pop().getLexeme();
                    ArrayList<Const> test = new ArrayList<>();
                    test.addAll(consts);
                    for (Const dat : test) {
                        if (dat.getIdent().equals(ident)) {
                            throw printError(list.getFirst(), "Identifikator konstanty je jiz deklarovany");
                        }
                    }
                    TokenType typ;
                    if (list.pop().getType() == TokenType.DVOJTECKA) {
                        if (list.peek().getType() == TokenType.DATATYPE_DOUBLE || list.peek().getType() == TokenType.DATATYPE_INTEGER || list.peek().getType() == TokenType.DATATYPE_STRING) {
                            typ = list.pop().getType();
                        } else {
                            throw printError(list.getFirst(), "Ocekavam datový typ");
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam :");
                    }
                    if (list.pop().getType() == TokenType.ROVNO) {
                        if (typ == TokenType.DATATYPE_DOUBLE) {
                            double value;
                            if (list.peek().getType() == TokenType.INTEGER) {
                                value = Double.valueOf((int) list.pop().getLiteral());
                                consts.add(new Const(ident, new DoubleD(value)));
                            } else if (list.peek().getType() == TokenType.DOUBLE) {
                                value = (double) list.pop().getLiteral();
                                consts.add(new Const(ident, new DoubleD(value)));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam NUMBER nebo INTEGER");
                            }
                        } else if (typ == TokenType.DATATYPE_INTEGER) {
                            if (list.peek().getType() == TokenType.INTEGER) {
                                int value = (int) list.pop().getLiteral();
                                consts.add(new Const(ident, new IntegerD(value)));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam INTEGER");
                            }
                        } else {
                            if (list.peek().getType() == TokenType.STRING) {
                                String value = (String) list.pop().getLiteral();
                                consts.add(new Const(ident, new StringD(value)));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam STRING");
                            }
                        }
                        if (list.peek().getType() == TokenType.CARKA) {
                            list.pop();
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam =");
                    }
                } else {
                    if (list.pop().getType() == TokenType.STREDNIK) {
                        return consts;
                    } else {
                        throw printError(list.getFirst(), "Ocekavam ;");
                    }
                }
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam CONST");
        }
    }

    public ArrayList<Var> readVars() throws Exception {
        ArrayList<Var> vars = new ArrayList<>();
        if (list.pop().getType() == TokenType.VAR) {
            while (true) {
                if (list.peek().getType() == TokenType.IDENT) {
                    String ident = list.pop().getLexeme();
                    ArrayList<Var> test = new ArrayList<>();
                    test.addAll(vars);
                    for (Var dat : test) {
                        if (dat.getIdent().equals(ident)) {
                            throw printError(list.getFirst(), "Identifikator promenne je jiz deklarovany");
                        }
                    }
                    TokenType typ;
                    if (list.pop().getType() == TokenType.DVOJTECKA) {
                        if (list.peek().getType() == TokenType.DATATYPE_DOUBLE || list.peek().getType() == TokenType.DATATYPE_INTEGER || list.peek().getType() == TokenType.DATATYPE_STRING) {
                            typ = list.pop().getType();
                        } else {
                            throw printError(list.getFirst(), "Ocekavam datový typ");
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam :");
                    }
                    if (typ == TokenType.DATATYPE_DOUBLE) {
                        vars.add(new Var(ident, new DoubleD()));
                    } else if (typ == TokenType.DATATYPE_INTEGER) {
                        vars.add(new Var(ident, new IntegerD()));
                    } else {
                        vars.add(new Var(ident, new StringD()));
                    }
                    if (list.peek().getType() == TokenType.CARKA) {
                        list.pop();
                    }
                } else {
                    if (list.pop().getType() == TokenType.STREDNIK) {
                        return vars;
                    } else {
                        throw printError(list.getFirst(), "Ocekavam ;");
                    }
                }
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam VAR");
        }
    }

    public Procedure readProcedure() throws Exception {
        Procedure proc = null;
        if (list.peek().getType() == TokenType.PROCEDURE) {
            list.pop();
            if (list.peek().getType() == TokenType.IDENT) {
                String ident = list.pop().getLexeme();
                if (list.pop().getType() == TokenType.LEVA_ZAVORKA) {
                    ArrayList<Var> parametry = new ArrayList<>();
                    while (list.peek().getType() == TokenType.IDENT) {
                        String identPar = list.pop().getLexeme();
                        if (list.pop().getType() == TokenType.DVOJTECKA) {
                            if (list.peek().getType() == TokenType.DATATYPE_STRING) {
                                list.pop();
                                parametry.add(new Var(identPar, new StringD()));
                            } else if (list.peek().getType() == TokenType.DATATYPE_INTEGER) {
                                list.pop();
                                parametry.add(new Var(identPar, new IntegerD()));
                            } else if (list.peek().getType() == TokenType.DATATYPE_DOUBLE) {
                                list.pop();
                                parametry.add(new Var(identPar, new DoubleD()));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam datovy typ");
                            }
                            if (list.peek().getType() != TokenType.CARKA) {
                                break;
                            } else {
                                list.pop();
                            }
                        } else {
                            throw printError(list.getFirst(), "Ocekavam :");
                        }
                    }
                    if (list.pop().getType() == TokenType.PRAVA_ZAVORKA) {
                        if (list.pop().getType() == TokenType.STREDNIK) {
                            Block block = readBlock();
                            if (list.pop().getType() == TokenType.STREDNIK) {
                                proc = new Procedure(ident, block, parametry);
                            } else {
                                throw printError(list.getFirst(), "Ocekavam ;");
                            }
                        } else {
                            throw printError(list.getFirst(), "Ocekavam ;");
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam )");
                    }
                } else {
                    throw printError(list.getFirst(), "Ocekavam (");
                }
            } else {
                throw printError(list.getFirst(), "Ocekavam Identifikator");
            }
        }
        return proc;
    }

    public Function readFunction() throws Exception {
        Function func = null;
        if (list.peek().getType() == TokenType.FUNCTION) {
            list.pop();
            if (list.peek().getType() == TokenType.IDENT) {
                String ident = list.pop().getLexeme();
                if (list.pop().getType() == TokenType.LEVA_ZAVORKA) {
                    ArrayList<Var> parametry = new ArrayList<>();
                    while (list.peek().getType() == TokenType.IDENT) {
                        String identPar = list.pop().getLexeme();
                        if (list.pop().getType() == TokenType.DVOJTECKA) {
                            if (list.peek().getType() == TokenType.DATATYPE_STRING) {
                                list.pop();
                                parametry.add(new Var(identPar, new StringD()));
                            } else if (list.peek().getType() == TokenType.DATATYPE_INTEGER) {
                                list.pop();
                                parametry.add(new Var(identPar, new IntegerD()));
                            } else if (list.peek().getType() == TokenType.DATATYPE_DOUBLE) {
                                list.pop();
                                parametry.add(new Var(identPar, new DoubleD()));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam datovy typ");
                            }
                            if (list.peek().getType() != TokenType.CARKA) {
                                break;
                            } else {
                                list.pop();
                            }
                        } else {
                            throw printError(list.getFirst(), "Ocekavam :");
                        }
                    }
                    if (list.pop().getType() == TokenType.PRAVA_ZAVORKA) {
                        if (list.pop().getType() == TokenType.DVOJTECKA) {
                            DataType typ;
                            switch (list.pop().getType()) {
                                case DATATYPE_DOUBLE:
                                    typ = new DoubleD();
                                    break;
                                case DATATYPE_INTEGER:
                                    typ = new IntegerD();
                                    break;
                                case DATATYPE_STRING:
                                    typ = new StringD();
                                    break;
                                default:
                                    throw printError(list.getFirst(), "Neznamy datovy typ");
                            }
                            if (list.pop().getType() == TokenType.STREDNIK) {
                                Block block = readBlock();
                                if (list.pop().getType() == TokenType.STREDNIK) {
                                    func = new Function(ident, block, typ, parametry);
                                } else {
                                    throw printError(list.getFirst(), "Ocekavam ;");
                                }
                            } else {
                                throw printError(list.getFirst(), "Ocekavam ;");
                            }
                        } else {
                            throw printError(list.getFirst(), "Ocekavam :");
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam )");
                    }
                } else {
                    throw printError(list.getFirst(), "Ocekavam (");
                }
            } else {
                throw printError(list.getFirst(), "Ocekavam Identifikator");
            }
        }
        return func;
    }

    public Statement readStatement() throws Exception {
//        System.out.println(list.peek().getType());
        switch (list.peek().getType()) {
            case IDENT:
                return readReturnStatement();
            case SET:
                return readSetStatement();
            case PCALL:
                return readCallProcedureStatement();
            case FCALL:
                return readCallFunctionStatement();
            case OTAZNIK:
                return readReadStatement();
            case VYKRICNIK:
                return readWriteStatement();
            case BEGIN:
                return readBeginEndStatement();
            case IF:
                return readIfStatement();
            case WHILE:
                return readWhileStatement();
        }
        throw printError(list.getFirst(), "Neplatny statement token");
    }

    public SetStatement readSetStatement() throws Exception {
        if (list.pop().getType() != TokenType.SET) {
            throw printError(list.getFirst(), "Ocekavam set");
        }
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        if (list.pop().getType() != TokenType.PRIRAZENI) {
//            throw printError(list.getFirst(), "Ocekavam :=");
            throw printError(list.getFirst(), "Ocekavam :=");
        }
        Expression expression = readExpression(false);
        return new SetStatement(ident, expression);
    }

    public SetStatement readReturnStatement() throws Exception {
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        if (list.pop().getType() != TokenType.PRIRAZENI) {
//            throw printError(list.getFirst(), "Ocekavam :=");
            throw printError(list.getFirst(), "Ocekavam :=");
        }
        Expression expression = readExpression(false);
        return new SetStatement(ident, expression);
    }

    public CallProcedureStatement readCallProcedureStatement() throws Exception {
        if (list.peek().getType() != TokenType.PCALL) {
            throw printError(list.getFirst(), "Ocekavam pcall");
        }
        list.pop();
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        ArrayList<Expression> parametry = new ArrayList<>();
        if (list.pop().getType() == TokenType.LEVA_ZAVORKA) {
            if (list.peek().getType() != TokenType.PRAVA_ZAVORKA) {
                while (true) {
                    Expression expr = readExpression(false);
                    parametry.add(expr);
                    if (list.peek().getType() == TokenType.CARKA) {
                        list.pop();
                    } else {
                        break;
                    }
                }
            }
            if (list.pop().getType() != TokenType.PRAVA_ZAVORKA) {
                throw printError(list.getFirst(), "Ocekavam )");
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam (");
        }
        return new CallProcedureStatement(ident, parametry);
    }

    public CallFunctionStatement readCallFunctionStatement() throws Exception {
        if (list.peek().getType() != TokenType.FCALL) {
            throw printError(list.getFirst(), "Ocekavam fcall");
        }
        list.pop();
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        ArrayList<Expression> parametry = new ArrayList<>();
        if (list.pop().getType() == TokenType.LEVA_ZAVORKA) {
            if (list.peek().getType() != TokenType.PRAVA_ZAVORKA) {
                while (true) {
                    Expression expr = readExpression(false);
                    parametry.add(expr);
                    if (list.peek().getType() == TokenType.CARKA) {
                        list.pop();
                    } else {
                        break;
                    }
                }
            }
            if (list.pop().getType() != TokenType.PRAVA_ZAVORKA) {
                throw printError(list.getFirst(), "Ocekavam )");
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam (");
        }
        return new CallFunctionStatement(ident, parametry);
    }

    public ReadStatement readReadStatement() throws Exception {
        if (list.peek().getType() != TokenType.OTAZNIK) {
            throw printError(list.getFirst(), "Ocekavam ?");
        }
        list.pop();
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        return new ReadStatement(ident);
    }

    public WriteStatement readWriteStatement() throws Exception {
        if (list.peek().getType() != TokenType.VYKRICNIK) {
            throw printError(list.getFirst(), "Ocekavam !");
        }
        list.pop();

        Expression expression = readExpression(false);
        return new WriteStatement(expression);
    }

    public BeginEndStatement readBeginEndStatement() throws Exception {
        if (list.peek().getType() != TokenType.BEGIN) {
            throw printError(list.getFirst(), "Ocekavam BEGIN");
        }
        list.pop();
        ArrayList<Statement> aList = new ArrayList<>();
        while (true) {
            if (list.peek().getType() != TokenType.EOF) {
                if (list.peek().getType() != TokenType.END) {
                    aList.add(readStatement());
                    if (list.peek().getType() == TokenType.STREDNIK) {
                        list.pop();
                    }
                } else {
                    list.pop();
                    break;
                }
            } else {
                throw printError(list.getFirst(), "Ocekavam END");
            }
        }
        return new BeginEndStatement(aList);
    }

    public IfStatement readIfStatement() throws Exception {
        if (list.peek().getType() != TokenType.IF) {
            throw printError(list.getFirst(), "Ocekavam IF");
        }
        list.pop();
        Condition condition = readCondition();
        if (list.peek().getType() != TokenType.THEN) {
            throw printError(list.getFirst(), "Ocekavam THEN");
        }
        list.pop();
        Statement statement = readStatement();

        return new IfStatement(statement, condition);
    }

    public WhileStatement readWhileStatement() throws Exception {
        if (list.peek().getType() != TokenType.WHILE) {
            throw printError(list.getFirst(), "Ocekavam WHILE");
        }
        list.pop();
        Condition condition = readCondition();
        if (list.peek().getType() != TokenType.DO) {
            throw printError(list.getFirst(), "Ocekavam DO");
        }
        list.pop();
        Statement statement = readStatement();

        return new WhileStatement(statement, condition);
    }

    public Condition readCondition() throws Exception {
        if (list.peek().getType() == TokenType.ODD) {
            return readOddCondition();
        }
        return readBinaryRelCondition();
    }

    public OddCondition readOddCondition() throws Exception {
        list.pop();
        Expression expression = readExpression(false);
        return new OddCondition(expression);
    }

    public BinaryRelCondition readBinaryRelCondition() throws Exception {
        Expression left = readExpression(false);
        TokenType rel;
        switch (list.peek().getType()) {
            case ROVNO:
                rel = TokenType.ROVNO;
                break;
            case NEROVNO:
                rel = TokenType.NEROVNO;
                break;
            case VETSI:
                rel = TokenType.VETSI;
                break;
            case VETSI_NEBO_ROVNO:
                rel = TokenType.VETSI_NEBO_ROVNO;
                break;
            case MENSI:
                rel = TokenType.MENSI;
                break;
            case MENSI_NEBO_ROVNO:
                rel = TokenType.MENSI_NEBO_ROVNO;
                break;
            default:
                throw printError(list.getFirst(), "Ocekavam relaci");
        }
        list.pop();
        Expression right = readExpression(false);
        return new BinaryRelCondition(left, rel, right);
    }

    public Expression readExpression(boolean zavorka) throws Exception {
        Expression exp = readTerm(zavorka);
        if (exp != null) {
            while (true) {
                BinaryExpression bEx = readPlusMinus(zavorka);
                if (bEx == null) {
                    return exp;
                }
                bEx.setLeft(exp);
                bEx.setRight(readTerm(zavorka));
                exp = bEx;
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam vyraz");
        }
    }

    public Expression readTerm(boolean zavorka) throws Exception {
        Expression exp = null;
        if (list.peek().getType() == TokenType.MINUS) {
            list.pop();
            MinusUnary mUnEx = new MinusUnary();
            mUnEx.setExpression(readFactor(zavorka));
            exp = mUnEx;
        } else if (list.peek().getType() == TokenType.PLUS) {
            list.pop();
            PlusUnary pUnEx = new PlusUnary();
            pUnEx.setExpression(readFactor(zavorka));
            exp = pUnEx;
        } else {
            exp = readFactor(zavorka);
        }
        if (exp != null) {
            while (true) {
                BinaryExpression bEx = nactiKratDeleno(zavorka);
                if (bEx == null) {
                    return exp;
                }
                bEx.setLeft(exp);
                bEx.setRight(readFactor(zavorka));
                exp = bEx;
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam Vyraz");
        }
    }

    public Expression readFactor(boolean zavorka) throws Exception {
        if (list.peek().getType() == TokenType.IDENT) {
            String ident = list.pop().getLexeme();
            IdentExpression ie = new IdentExpression(ident);
            return ie;
        } else if (list.peek().getType() == TokenType.LEVA_ZAVORKA) {
            list.pop();
            return readExpression(true);
        } else {
            return readLiteral();
        }
    }

    public Expression readLiteral() {
        if (list.peek().getType() == TokenType.DOUBLE) {
            double number = (double) list.pop().getLiteral();
            LiteralExpression lit = new LiteralExpression(number);
            return lit;
        } else if (list.peek().getType() == TokenType.INTEGER) {
            int number = (int) list.pop().getLiteral();
            LiteralExpression lit = new LiteralExpression(number);
            return lit;
        } else if (list.peek().getType() == TokenType.STRING) {
            String string = (String) list.pop().getLiteral();
            LiteralExpression lit = new LiteralExpression(string);
            return lit;
        } else {
            return null;
        }

    }

    public BinaryExpression readPlusMinus(boolean zavorka) {
        if (list.peek().getType() == TokenType.PRAVA_ZAVORKA && zavorka) {
            list.pop();
            return null;
        }
        if (list.peek().getType() == TokenType.PLUS) {
            list.pop();
            return new Plus();
        } else {
            if (list.peek().getType() == TokenType.MINUS) {
                list.pop();
                return new Minus();
            } else {
                return null;
            }
        }
    }

    public BinaryExpression nactiKratDeleno(boolean zavorka) {
        if (list.peek().getType() == TokenType.PRAVA_ZAVORKA && zavorka) {
            list.pop();
            return null;
        }
        if (list.peek().getType() == TokenType.HVEZDA) {
            list.pop();
            return new Multiply();
        } else {
            if (list.peek().getType() == TokenType.LOMENO) {
                list.pop();
                return new Divide();
            } else {
                return null;
            }
        }
    }

}
