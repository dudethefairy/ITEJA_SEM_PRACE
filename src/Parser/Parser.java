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
import Expression.BinaryExpression;
import Expression.Expression;
import Expression.IdentExpression;
import Expression.LiteralExpression;
import Expression.Binary.Divide;
import Expression.Binary.Minus;
import Expression.Unary.MinusUnary;
import Expression.Binary.Multiply;
import Expression.Binary.Plus;
import Expression.FunctionExpression;
import Expression.Unary.PlusUnary;
import Lexer.Lexer;
import Lexer.Token;
import Lexer.TokenType;
import Statement.BeginEndStatement;
import Statement.CallFunctionStatement;
import Statement.CallProcedureStatement;
import Statement.ForStatement;
import Statement.IfStatement;
import Statement.ReadStatement;
import Statement.ReturnStatement;
import Statement.SetStatement;
import Statement.Statement;
import Statement.RepeatStatement;
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
    int pocetZavorek;

    @Override
    public String toString() {
        return block.toString();
    }

    private ParseError printError(Token token, String message) {
        Lexer.error(token, message);
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
                                throw printError(list.getFirst(), "Ocekavam double nebo integer");
                            }
                        } else if (typ == TokenType.DATATYPE_INTEGER) {
                            if (list.peek().getType() == TokenType.INTEGER) {
                                int value = (int) list.pop().getLiteral();
                                consts.add(new Const(ident, new IntegerD(value)));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam integer");
                            }
                        } else {
                            if (list.peek().getType() == TokenType.STRING) {
                                String value = (String) list.pop().getLiteral();
                                consts.add(new Const(ident, new StringD(value)));
                            } else {
                                throw printError(list.getFirst(), "Ocekavam string");
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
            throw printError(list.getFirst(), "Ocekavam const");
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

                    if (list.pop().getType() == TokenType.DVOJTECKA) {
                        if (list.peek().getType() == TokenType.DATATYPE_DOUBLE || list.peek().getType() == TokenType.DATATYPE_INTEGER || list.peek().getType() == TokenType.DATATYPE_STRING) {
                            TokenType typ = list.pop().getType();
                            if (list.peek().getType() == TokenType.ROVNO) {
                                list.pop();
                                if (typ == TokenType.DATATYPE_DOUBLE) {
                                    double value;
                                    if (list.peek().getType() == TokenType.INTEGER) {
                                        value = Double.valueOf((int) list.pop().getLiteral());
                                        vars.add(new Var(ident, new DoubleD(value)));
                                    } else if (list.peek().getType() == TokenType.DOUBLE) {
                                        value = (double) list.pop().getLiteral();
                                        vars.add(new Var(ident, new DoubleD(value)));
                                    } else {
                                        throw printError(list.getFirst(), "Ocekavam double nebo integer");
                                    }
                                } else if (typ == TokenType.DATATYPE_INTEGER) {
                                    if (list.peek().getType() == TokenType.INTEGER) {
                                        int value = (int) list.pop().getLiteral();
                                        vars.add(new Var(ident, new IntegerD(value)));
                                    } else {
                                        throw printError(list.getFirst(), "Ocekavam integer");
                                    }
                                } else {
                                    if (list.peek().getType() == TokenType.STRING) {
                                        String value = (String) list.pop().getLiteral();
                                        vars.add(new Var(ident, new StringD(value)));
                                    } else {
                                        throw printError(list.getFirst(), "Ocekavam string");
                                    }
                                }
                            } else {
                                if (typ == TokenType.DATATYPE_DOUBLE) {
                                    vars.add(new Var(ident, new DoubleD()));
                                } else if (typ == TokenType.DATATYPE_INTEGER) {
                                    vars.add(new Var(ident, new IntegerD()));
                                } else {
                                    vars.add(new Var(ident, new StringD()));
                                }
                            }
                        } else {
                            throw printError(list.getFirst(), "Ocekavam datový typ");
                        }
                    } else {
                        throw printError(list.getFirst(), "Ocekavam :");
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
            throw printError(list.getFirst(), "Ocekavam var");
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
                list.pop();
                switch (list.peek().getType()) {
                    case WRITE:
                        return readWriteStatement();
                    case READ:
                        return readReadStatement();
                    default:
                        return readCallProcedureStatement();
                }
            case FCALL:
                return readCallFunctionStatement();
            case BEGIN:
                return readBeginEndStatement();
            case IF:
                return readIfStatement();
            case REPEAT:
                return readRepeatStatement();
            case FOR:
                return readForStatement();
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
        Expression expression = readExpression();
        return new SetStatement(ident, expression);
    }

    public ReturnStatement readReturnStatement() throws Exception {
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        if (list.pop().getType() != TokenType.PRIRAZENI) {
//            throw printError(list.getFirst(), "Ocekavam :=");
            throw printError(list.getFirst(), "Ocekavam :=");
        }
        Expression expression = readExpression();
        if (list.peek().getType() != TokenType.END) {
            throw printError(list.getFirst(), "Ocekavam end");
        }
        return new ReturnStatement(ident, expression);
    }

    public CallProcedureStatement readCallProcedureStatement() throws Exception {
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();
        ArrayList<Expression> parametry = new ArrayList<>();
        if (list.pop().getType() == TokenType.LEVA_ZAVORKA) {
            if (list.peek().getType() != TokenType.PRAVA_ZAVORKA) {
                while (true) {
                    Expression expr = readExpression();
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
                    Expression expr = readExpression();
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
        if (list.pop().getType() != TokenType.READ) {
            throw printError(list.getFirst(), "Ocekavam readln");
        }
        if (list.pop().getType() != TokenType.LEVA_ZAVORKA) {
            throw printError(list.getFirst(), "Ocekavam (");
        }
        if (list.peek().getType() != TokenType.IDENT) {
            throw printError(list.getFirst(), "Ocekavam IDENT");
        }
        String ident = list.pop().getLexeme();

        if (list.pop().getType() != TokenType.PRAVA_ZAVORKA) {
            throw printError(list.getFirst(), "Ocekavam )");
        }
        return new ReadStatement(ident);
    }

    public WriteStatement readWriteStatement() throws Exception {
        if (list.pop().getType() != TokenType.WRITE) {
            throw printError(list.getFirst(), "Ocekavam writeln");
        }

        if (list.pop().getType() != TokenType.LEVA_ZAVORKA) {
            throw printError(list.getFirst(), "Ocekavam (");
        }

        Expression expression = readExpression();

        if (list.pop().getType() != TokenType.PRAVA_ZAVORKA) {
            throw printError(list.getFirst(), "Ocekavam )");
        }
        return new WriteStatement(expression);
    }

    public BeginEndStatement readBeginEndStatement() throws Exception {
        if (list.peek().getType() != TokenType.BEGIN) {
            throw printError(list.getFirst(), "Ocekavam begin");
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
                throw printError(list.getFirst(), "Ocekavam end");
            }
        }
        return new BeginEndStatement(aList);
    }

    public IfStatement readIfStatement() throws Exception {
        if (list.peek().getType() != TokenType.IF) {
            throw printError(list.getFirst(), "Ocekavam if");
        }
        list.pop();
        Condition condition = readCondition();
        if (list.peek().getType() != TokenType.THEN) {
            throw printError(list.getFirst(), "Ocekavam then");
        }
        list.pop();
        Statement statement = readStatement();
        if (list.peek().getType() == TokenType.ELSE) {
            list.pop();

            Statement statementElse = readStatement();
            return new IfStatement(statement, condition, statementElse);
        } else {
            return new IfStatement(statement, condition);
        }
    }

    public RepeatStatement readRepeatStatement() throws Exception {
        if (list.pop().getType() != TokenType.REPEAT) {
            throw printError(list.getFirst(), "Ocekavam repeat");
        }
        Statement statement = readStatement();
        if (list.pop().getType() != TokenType.UNTIL) {
            throw printError(list.getFirst(), "Ocekavam until");
        }

        Condition condition = readCondition();

        return new RepeatStatement(statement, condition);
    }

    public ForStatement readForStatement() throws Exception {
        if (list.pop().getType() != TokenType.FOR) {
            throw printError(list.getFirst(), "Ocekavam for");
        }
        String ident;
        if (list.peek().getType() == TokenType.IDENT) {
            ident = list.pop().getLexeme();
        } else {
            throw printError(list.getFirst(), "Ocekavam ident");
        }
        if (list.pop().getType() != TokenType.PRIRAZENI) {
            throw printError(list.getFirst(), "Ocekavam :=");
        }
        Expression expr = readExpression();
        if (list.pop().getType() != TokenType.TO) {
            throw printError(list.getFirst(), "Ocekavam to");
        }
        Expression expr2 = readExpression();
        if (list.pop().getType() != TokenType.DO) {
            throw printError(list.getFirst(), "Ocekavam to");
        }
        Statement statement = readStatement();

        return new ForStatement(ident, expr, expr2, statement);
    }

    public Condition readCondition() throws Exception {
        return readBinaryRelCondition();
    }

    public BinaryRelCondition readBinaryRelCondition() throws Exception {
        Expression left = readExpression();
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
        Expression right = readExpression();
        return new BinaryRelCondition(left, rel, right);
    }

    public Expression readExpression() throws Exception {
        Expression exp = readTerm();
        if (exp != null) {
            while (true) {
                BinaryExpression bEx = readPlusMinus();
                if (bEx == null) {
                    return exp;
                }
                bEx.setLeft(exp);
                bEx.setRight(readTerm());
                exp = bEx;
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam vyraz");
        }
    }

    public Expression readTerm() throws Exception {
        Expression exp = null;
        if (list.peek().getType() == TokenType.MINUS) {
            list.pop();
            MinusUnary mUnEx = new MinusUnary();
            mUnEx.setExpression(readFactor());
            exp = mUnEx;
        } else if (list.peek().getType() == TokenType.PLUS) {
            list.pop();
            PlusUnary pUnEx = new PlusUnary();
            pUnEx.setExpression(readFactor());
            exp = pUnEx;
        } else {
            exp = readFactor();
        }
        if (exp != null) {
            while (true) {
                BinaryExpression bEx = nactiKratDeleno();
                if (bEx == null) {
                    return exp;
                }
                bEx.setLeft(exp);
                bEx.setRight(readFactor());
                exp = bEx;
            }
        } else {
            throw printError(list.getFirst(), "Ocekavam Vyraz");
        }
    }

    public Expression readFactor() throws Exception {
        if (list.peek().getType() == TokenType.IDENT) {
            String ident = list.pop().getLexeme();
            IdentExpression ie = new IdentExpression(ident);
            return ie;
        } else if (list.peek().getType() == TokenType.FCALL) {
            CallFunctionStatement stat = readCallFunctionStatement();
            FunctionExpression func = new FunctionExpression(stat.getIdent(), stat);
            return func;
        } else if (list.peek().getType() == TokenType.LEVA_ZAVORKA) {
            list.pop();
            pocetZavorek++;
            return readExpression();
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

    public BinaryExpression readPlusMinus() {
        if (list.peek().getType() == TokenType.PRAVA_ZAVORKA && pocetZavorek > 0) {
            list.pop();
            pocetZavorek--;
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

    public BinaryExpression nactiKratDeleno() {
        if (list.peek().getType() == TokenType.PRAVA_ZAVORKA && pocetZavorek > 0) {
            list.pop();
            pocetZavorek--;
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
