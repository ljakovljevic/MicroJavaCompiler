package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

import java.util.*;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPC;
    private Obj currentDesignator;
    private int tempAdr;
    private Obj switchExprTemp;

    public int getmainPc() {
        return this.mainPC;
    }

    private void initializePredeclaredMethods() {
        // 'ord' and 'chr' are the same code.
        Obj ordMethod = Tab.find("ord");
        Obj chrMethod = Tab.find("chr");
        ordMethod.setAdr(Code.pc);
        chrMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);

        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);

    }

    CodeGenerator() {
        this.initializePredeclaredMethods();
    }

    /* METHOD DECLARATION */

    @Override
    public void visit(MethodRetAndName_type methodRetAndName_type) {
        methodRetAndName_type.obj.setAdr(Code.pc);
        if (methodRetAndName_type.getI2().equalsIgnoreCase("main")) {
            this.mainPC = Code.pc;
        }

        Code.put(Code.enter);
        Code.put(methodRetAndName_type.obj.getLevel());
        Code.put(methodRetAndName_type.obj.getLocalSymbols().size() + 1);

        tempAdr = methodRetAndName_type.obj.getLocalSymbols().size();
    }

    @Override
    public void visit(MethodRetAndName_void methodRetAndName_void) {
        methodRetAndName_void.obj.setAdr(Code.pc);
        if (methodRetAndName_void.getI1().equalsIgnoreCase("main")) {
            this.mainPC = Code.pc;
        }

        Code.put(Code.enter);
        Code.put(methodRetAndName_void.obj.getLevel());
        Code.put(methodRetAndName_void.obj.getLocalSymbols().size() + 1);

        tempAdr = methodRetAndName_void.obj.getLocalSymbols().size();
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    /* DESIGNATOR STATEMENTS */

    @Override
    public void visit(DesignatorStatement_assign designatorStatement_assign) {
        Code.store(designatorStatement_assign.getDesignator().obj);
    }

    @Override
    public void visit(DesignatorStatement_method designatorStatement_method) {
        int offset = designatorStatement_method.getDesignator().obj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);

        if (designatorStatement_method.getDesignator().obj.getType() != Tab.noType) {
            Code.put(Code.pop);
        }
    }

    @Override
    public void visit(DesignatorStatement_inc designatorStatement_inc) {
        if (designatorStatement_inc.getDesignator().obj.getKind() == Obj.Elem) {
            Code.put(Code.dup2);
        }

        Code.load(designatorStatement_inc.getDesignator().obj);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(designatorStatement_inc.getDesignator().obj);
    }

    @Override
    public void visit(DesignatorStatement_dec designatorStatement_dec) {
        if (designatorStatement_dec.getDesignator().obj.getKind() == Obj.Elem) {
            Code.put(Code.dup2);
        }

        Code.load(designatorStatement_dec.getDesignator().obj);
        Code.loadConst(1);
        Code.put(Code.sub);
        Code.store(designatorStatement_dec.getDesignator().obj);
    }

    /* STATEMENTS */

    @Override
    public void visit(Statement_return1 statement_return1) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(Statement_return2 statement_return2) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override
    public void visit(Statement_read statement_read) {
        if (statement_read.getDesignator().obj.getType().equals(Tab.charType)) {
            Code.put(Code.bread);
        } else {
            Code.put(Code.read);
        }
        Code.store(statement_read.getDesignator().obj);
    }

    @Override
    public void visit(Statement_print1 statement_print1) {
        Code.loadConst(0);
        if (statement_print1.getExpr().struct.equals(Tab.charType)) {
            Code.put(Code.bprint);
        } else {
            Code.put(Code.print);
        }
    }

    @Override
    public void visit(Statement_print2 statement_print2) {
        Code.loadConst(statement_print2.getN2());
        if (statement_print2.getExpr().struct.equals(Tab.charType)) {
            Code.put(Code.bprint);
        } else {
            Code.put(Code.print);
        }
    }

    /* EXPR */

    @Override
    public void visit(AddopTermList_add addopTermList_add) {
        if (addopTermList_add.getAddop() instanceof Addop_plus) {
            Code.put(Code.add);
        } else if (addopTermList_add.getAddop() instanceof Addop_minus) {
            Code.put(Code.sub);
        }
    }

    @Override
    public void visit(MulopFactorList_mul mulopFactorList_mul) {
        if (mulopFactorList_mul.getMulop() instanceof Mulop_mul) {
            Code.put(Code.mul);
        } else if (mulopFactorList_mul.getMulop() instanceof Mulop_div) {
            Code.put(Code.div);
        } else if (mulopFactorList_mul.getMulop() instanceof Mulop_rem) {
            Code.put(Code.rem);
        }
    }

    @Override
    public void visit(Factor factor) {
        if (factor.getUnary() instanceof Unary_m) {
            Code.put(Code.neg);
        }
    }

    /* FACTOR */

    @Override
    public void visit(FactorSub_var factorSub_var) {
        if (factorSub_var.getDesignator().getDesignatorDecl() instanceof DesignatorDecl_l) {
            Code.load(currentDesignator);
            Code.put(Code.arraylength);
        } else {
            Code.load(factorSub_var.getDesignator().obj);
        }
    }

    @Override
    public void visit(FactorSub_n factorSub_n) {
        Code.loadConst(factorSub_n.getN1());
    }

    @Override
    public void visit(FactorSub_c factorSub_c) {
        Code.loadConst(factorSub_c.getC1());
    }

    @Override
    public void visit(FactorSub_b factorSub_b) {
        Code.loadConst(factorSub_b.getB1());
    }

    @Override
    public void visit(FactorSub_array factorSub_array) {
        Code.put(Code.newarray);
        if (factorSub_array.getType().struct.equals(Tab.charType)) {
            Code.put(0);
        } else {
            Code.put(1);
        }
    }

    @Override
    public void visit(FactorSub_method factorSub_method) {
        int offset = factorSub_method.getDesignator().obj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);
    }

    /* DESIGNATOR */
    
    @Override
    public void visit(DesignatorName designatorName) {
        currentDesignator = designatorName.obj;
    }

    @Override
    public void visit(ArrayDesignator_e arrayDesignator_e) {
        Code.load(currentDesignator);
    }



    /* CONDITION */

    Stack<Integer> skipCondFact = new Stack<>();
    Stack<Integer> skipCondition = new Stack<>();
    Stack<Integer> skipThen = new Stack<>();
    Stack<Integer> skipElse = new Stack<>();

    private int returnRelop(Relop relop) {
        if (relop instanceof Relop_eq) {
            return Code.eq;
        } else if(relop instanceof Relop_ne) {
            return Code.ne;
        } else if(relop instanceof Relop_gt) {
            return Code.gt;
        } else if (relop instanceof Relop_ge) {
            return Code.ge;
        } else if (relop instanceof Relop_lt) {
            return Code.lt;
        } else if (relop instanceof Relop_le) {
            return Code.le;
        } else {
            return 0;
        }
    }

    @Override
    public void visit (CondFact_expr condFact_expr) {
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0); //bacio netacne
        skipCondFact.push(Code.pc - 2);
    }

    @Override
    public void visit(CondFact_r_expr condFact_r_expr) {
        Code.putFalseJump(returnRelop(condFact_r_expr.getRelop()), 0); //bacio netacne
        skipCondFact.push(Code.pc-2);
    }

    @Override
    public void visit(CondTerm condTerm) {
        Code.putJump(0);
        skipCondition.push(Code.pc-2); //tacne koje preskacu uslov
        while (!skipCondFact.empty()) {
            Code.fixup(skipCondFact.pop()); //vrati netacne
        }
    }

    @Override
    public void visit(Condition_list condition_list) {
        Code.putJump(0);
        skipThen.push(Code.pc-2); // baci netacne na ELSE
        //THEN
        while(!skipCondition.empty()) {
            Code.fixup(skipCondition.pop()); // vrati tacne
        }
    }

    @Override
    public void visit (ElseStatement_e elseStatement_e) {
        while (!skipThen.empty()) {
            Code.fixup(skipThen.pop()); // skupili tacne i netacne
        }
    }

    @Override
    public void visit (Else else_) {
        Code.putJump(0); // bacamo tacne
        skipElse.push(Code.pc - 2);
        while (!skipThen.empty()) {
            Code.fixup(skipThen.pop()); // dovlacimo netacne
        }
    }

    @Override
    public void visit (ElseStatement_yes elseStatement_yes) {
        while (!skipElse.empty()) {
            Code.fixup(skipElse.pop()); // vracamo tacne
        }
        //tacne i netacne
    }

    // TERNARY
    // x == 1 ? 2 : 1

    Stack<Integer> tSkipThen = new Stack<>();
    Stack<Integer> tSkipElse = new Stack<>();


    @Override
    public void visit(TernaryCondition ternaryCondition) {
        Code.putJump(0);
        tSkipThen.push(Code.pc - 2); //baci netacne na posle :
        //THEN
        while (!skipCondition.empty()) {
            Code.fixup(skipCondition.pop());
        }
    }

    @Override
    public void visit(TernaryColon ternaryColon) {
        Code.putJump(0);
        tSkipElse.push(Code.pc - 2); // bacio tacne na kraj
        //ELSE - dovuko pogresne
        while (!tSkipThen.empty()) {
            Code.fixup(tSkipThen.pop());
        }
    }

    @Override
    public void visit(TernaryEnd ternaryEnd) {
        // dovukao tacne
        while (!tSkipElse.empty()) {
            Code.fixup(tSkipElse.pop());
        }
        // tacni + netacni
    }

    private Stack<List<Integer>> breakJump = new Stack<>();
    private Stack<List<Integer>> continueJumps = new Stack<>();

    @Override
    public void visit(Statement_break statement_break) {
        Code.putJump(0);
        breakJump.peek().add(Code.pc - 2);
    }

    @Override
    public void visit(Statement_continue statement_continue) {
        Code.putJump(0);
        continueJumps.peek().add(Code.pc - 2);
    }

    // FOR LOOP -> for (int x=0; x<5; x++) print(x)

    Stack<List<Integer>> loopSkip = new Stack<>();

    Stack<Integer> condStart = new Stack<>();
    Stack<Integer> updateStart = new Stack<>();
    Stack<Integer> bodyStart = new Stack<>();
    Stack<Integer> end = new Stack<>();

    Stack<Integer> updateJump = new Stack<>();

    @Override
    public void visit(ForCondition_list forCondition_list) {
        Code.putJump(0);
        loopSkip.peek().add(Code.pc - 2); //baci netacne na posle :
        //THEN
        while (!skipCondition.empty()) {
            Code.fixup(skipCondition.pop());
        }
    }

    @Override
    public void visit(ForNonTerm forNonTerm) {
        breakJump.push(new ArrayList<>());
        continueJumps.push(new ArrayList<>());
        loopSkip.push(new ArrayList<>());
    }

    @Override
    public void visit(CondSemi condSemi) {
        condStart.push(Code.pc);
    }

    @Override
    public void visit(UpdateSemi updateSemi) {
        Code.putJump(0);
        updateJump.push(Code.pc - 2);

        updateStart.push(Code.pc);
    }

    @Override
    public void visit(BodyParen bodyParen) {
        Code.putJump(condStart.pop());

        bodyStart.push(Code.pc);
        while (!updateJump.empty()) {
            Code.fixup(updateJump.pop());
        }

    }

    @Override
    public void visit(Statement_for statement_for) {
        while (!continueJumps.peek().isEmpty()) {
            Code.fixup(continueJumps.peek().remove(0));
        }

        Code.putJump(updateStart.pop());
        end.push(Code.pc);

        while (!breakJump.peek().isEmpty()) {
            Code.fixup(breakJump.peek().remove(0));
        }

        while (!loopSkip.peek().isEmpty()) {
            Code.fixup(loopSkip.peek().remove(0));
        }

        breakJump.pop();
        continueJumps.pop();
        loopSkip.pop();
        bodyStart.pop();
        end.pop();
    }

    // SWITCH STATEMENT

    Stack<Integer> caseSkip = new Stack<>();
    Stack<Integer> endSwitch = new Stack<>();
    List<Integer> caseValues = new ArrayList<>();
    List<Integer> caseAddrs = new ArrayList<>();

    @Override
    public void visit(SwitchNonTerm switchNonTerm) {
        breakJump.push(new ArrayList<>());
    }

    @Override
    public void visit(SwitchStart switchStart) {
        switchExprTemp = new Obj(Obj.Var, "ExprTemp", Tab.intType, tempAdr, 1);

        Code.store(switchExprTemp);

        Code.putJump(0);
        caseSkip.push(Code.pc - 2);
    }

    @Override
    public void visit(CaseStart caseStart) {
        caseAddrs.add(Code.pc);
    }

    @Override
    public void visit(CaseDecl caseDecl) {
        caseValues.add(caseDecl.getN1());
    }

    @Override
    public void visit(Statement_switch statement_switch) {
        Code.putJump(0);
        endSwitch.push(Code.pc - 2);

        while (!caseSkip.empty()) {
            Code.fixup(caseSkip.pop());
        }

        for (int i=0; i<caseValues.size(); i++) {
            Code.load(switchExprTemp);
            Code.loadConst(caseValues.get(i));
            Code.putFalseJump(Code.ne, caseAddrs.get(i));
        }

        while (!breakJump.peek().isEmpty()) {
            Code.fixup(breakJump.peek().remove(0));
        }

        while (!endSwitch.empty()) {
            Code.fixup(endSwitch.pop());
        }

        breakJump.pop();
    }

}