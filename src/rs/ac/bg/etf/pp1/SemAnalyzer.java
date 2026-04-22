package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.*;
import java.util.stream.Collectors;

public class SemAnalyzer extends VisitorAdaptor {

    private boolean errorDetected = false;
    Logger log = Logger.getLogger(getClass());
    private Obj currentProgram;
    private Struct currentType;
    private int constant;
    private Struct constantType;
    private Struct boolType = Tab.find("bool").getType();
    private Obj currentMethod;
    private Obj mainMethod;
    private Set<Integer> eConst;
    private Obj currentEnumObj;
    private int enumNextValue;
    private boolean returnHappened;
    private int loopCnt;
    private int switchCnt;
    private Stack<Set<Integer>> switchStack = new Stack<>();
    private Stack<List<Struct>> actParStack = new Stack<>();
    private Map<Obj, Struct> enumConstOwner = new HashMap<>();
    int nVars;


    /* LOG MESSAGES */
    public void report_error(String message, SyntaxNode info) {
        errorDetected  = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.info(msg.toString());
    }

    public boolean passed() {
        return !errorDetected;
    }

    /* SEMANTIC PASS CODE */

    @Override
    public void visit(PrgramName prgramName) {
        currentProgram = Tab.insert(Obj.Prog, prgramName.getI1(), Tab.noType);
        Tab.openScope();

        Obj ordObj = Tab.find("ord");
        Obj chrObj = Tab.find("chr");
        Obj lenObj = Tab.find("len");


        if (ordObj == Tab.noObj) report_error("Nisam pronasao ord metodu", prgramName);
        else {
            List<Obj> list = new ArrayList<>(ordObj.getLocalSymbols());
            list.get(0).setFpPos(1);
        }

        if (chrObj == Tab.noObj) report_error("Nisam pronasao chr metodu", prgramName);
        else {
            List<Obj> list = new ArrayList<>(chrObj.getLocalSymbols());
            list.get(0).setFpPos(1);
        }

        if (lenObj == Tab.noObj) report_error("Nisam pronasao len metodu", prgramName);
        else {
            List<Obj> list = new ArrayList<>(lenObj.getLocalSymbols());
            list.get(0).setFpPos(1);
        }
    }

    @Override
    public void visit(Program program) {
        nVars = Tab.currentScope().getnVars();
        Tab.chainLocalSymbols(currentProgram);
        Tab.closeScope();
        currentProgram = null;

        if (mainMethod == null || mainMethod.getLevel() > 0) {
            report_error("Program nema adekvatnu main metodu", program);
        }
    }

    /* CONST DECLARATION */

    @Override
    public void visit(Type type) {
        Obj typeObj = Tab.find(type.getI1());
        if (typeObj == Tab.noObj) {
            report_error("Nepostojeci tip podataka: " + type.getI1(), type);
            type.struct = currentType = Tab.noType;
        } else if (typeObj.getKind() != Obj.Type) {
            report_error("Neadekvatan tip podataka: " + type.getI1(), type);
        } else {
            type.struct = currentType = typeObj.getType();
        }
    }

    @Override
    public void visit(Constant_n constant_n) {
        constant = constant_n.getN1();
        constantType = Tab.intType;
    }

    @Override
    public void visit(Constant_c constant_c) {
        constant = constant_c.getC1();
        constantType = Tab.charType;
    }

    @Override
    public void visit(Constant_b constant_b) {
        constant = constant_b.getB1();
        constantType = boolType;
    }

    @Override
    public void visit(ConstDecl constDecl) {
        Obj conObj = Tab.find(constDecl.getI1());
        if (conObj != Tab.noObj) {
            report_error("Dvostruka definicija konstante: " + constDecl.getI1(), constDecl);
        } else {
            if (constantType.assignableTo(currentType)) {
                conObj = Tab.insert(Obj.Con, constDecl.getI1(), currentType);
                conObj.setAdr(constant);
            } else {
                report_error("Neadekvatna dodela konstanti: " + constDecl.getI1(), constDecl);
            }
        }
    }

    /* VAR DECLARATION */

    @Override
    public void visit(VarDecl_var varDecl_var) {
        Obj varObj = null;
        if (currentMethod == null) {
            varObj = Tab.find(varDecl_var.getI1());
        } else {
            varObj = Tab.currentScope().findSymbol(varDecl_var.getI1());
        }
        if (varObj == Tab.noObj || varObj == null) {
            varObj = Tab.insert(Obj.Var, varDecl_var.getI1(), currentType);
        } else {
            report_error("Koriscenje lokalne promenljive: " + varDecl_var.getI1(), varDecl_var);
        }
    }

    @Override
    public void visit(VarDecl_array varDecl_array) {
        Obj varObj = null;
        if (currentMethod == null) {
            varObj = Tab.find(varDecl_array.getI1());
        } else {
            varObj = Tab.currentScope().findSymbol(varDecl_array.getI1());
        }
        if (varObj == Tab.noObj || varObj == null) {
            varObj = Tab.insert(Obj.Var, varDecl_array.getI1(), new Struct(Struct.Array, currentType));
        } else {
            report_error("Dvostruka definicija promenljive: " + varDecl_array.getI1(), varDecl_array);
        }
    }

    /* METHOD DECLARATION */

    @Override
    public void visit(MethodRetAndName_void methodRetAndName_void) {
        methodRetAndName_void.obj = currentMethod = Tab.insert(Obj.Meth, methodRetAndName_void.getI1(), Tab.noType);
        Tab.openScope();
        
        if (methodRetAndName_void.getI1().equalsIgnoreCase("main")) {
            mainMethod = currentMethod;
        }
    }

    @Override
    public void visit(MethodRetAndName_type methodRetAndName_type) {
        methodRetAndName_type.obj = currentMethod = Tab.insert(Obj.Meth, methodRetAndName_type.getI2(), currentType);
        Tab.openScope();
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        if (currentMethod.getType() != Tab.noType && !returnHappened) {
            report_error("Metoda nema return", methodDecl);
        }
        currentMethod = null;
        returnHappened = false;
    }

    /* FORMAL PARAMETERS DECLARATION */

    @Override
    public void visit(FormPar_var formPar_var) {
        Obj parObj = null;
        if (currentMethod == null) {
            report_error("Semanticka greska! [FormPar_var]", formPar_var);
        } else {
            parObj = Tab.currentScope().findSymbol(formPar_var.getI2());
        }

        if (parObj == null) {
            parObj = Tab.insert(Obj.Var, formPar_var.getI2(), currentType);
            parObj.setFpPos(1);
            currentMethod.setLevel(currentMethod.getLevel() + 1);
        } else {
            report_error("Dvostruka definicija formalnog parametra: " + formPar_var.getI2(), formPar_var);
        }
    }

    @Override
    public void visit(FormPar_array formPar_array) {
        Obj parObj = null;
        if (currentMethod == null) {
            report_error("Semanticka greska! [FormPar_var]", formPar_array);
        } else {
            parObj = Tab.currentScope().findSymbol(formPar_array.getI2());
        }

        if (parObj == null) {
            parObj = Tab.insert(Obj.Var, formPar_array.getI2(), new Struct(Struct.Array, currentType));
            parObj.setFpPos(1);
            currentMethod.setLevel(currentMethod.getLevel() + 1);
        } else {
            report_error("Dvostruka definicija formalnog parametra: " + formPar_array.getI2(), formPar_array);
        }
    }

    /* ENUM DECLARATION */

    @Override
    public void visit(EnumDeclName enumDeclName) {
        Obj enumObj = Tab.find(enumDeclName.getI1());
        eConst = new HashSet<>();

        if (enumObj != Tab.noObj) {
            report_error("Dvostruka definicija enuma: " + enumDeclName.getI1(), enumDeclName);
            currentEnumObj = null;
        } else {
            currentEnumObj = Tab.insert(Obj.Type, enumDeclName.getI1(), new Struct(Struct.Enum, Tab.intType));
            enumNextValue = 0;
            Tab.openScope();
        }
    }

    @Override
    public void visit(EnumDecl_ident enumDecl_ident) {
        if (currentEnumObj == null) return;

        Obj enumObj = Tab.currentScope().findSymbol(enumDecl_ident.getI1());
        if (enumObj != null) {
            report_error("Dvostruka definicija enumeratora: " + enumDecl_ident.getI1(), enumDecl_ident);
        } else if (eConst.contains(enumNextValue)) {
            while (eConst.contains(enumNextValue)) {
                enumNextValue++;
            }
            enumObj = Tab.insert(Obj.Con, enumDecl_ident.getI1(), Tab.intType);
            eConst.add(enumNextValue);
            enumObj.setAdr(enumNextValue);
            enumNextValue++;

        } else {
            enumObj = Tab.insert(Obj.Con, enumDecl_ident.getI1(), Tab.intType);
            eConst.add(enumNextValue);
            enumObj.setAdr(enumNextValue);
            enumNextValue++;
        }
    }

    @Override
    public void visit(EnumDecl_assign enumDecl_assign) {
        if (currentEnumObj == null) return;

        Obj enumObj = Tab.currentScope().findSymbol(enumDecl_assign.getI1());
        if (enumObj != null) {
            report_error("Dvostruka definicija enumeratora: " + enumDecl_assign.getI1(), enumDecl_assign);
        } else {
            enumObj = Tab.insert(Obj.Con, enumDecl_assign.getI1(), Tab.intType);
            if (eConst.contains(enumDecl_assign.getN2())) {
                report_error("Duplikat konstanta nabrajanja", enumDecl_assign);
            } else {
                eConst.add(enumDecl_assign.getN2());
                enumObj.setAdr(enumDecl_assign.getN2());
                enumNextValue = enumDecl_assign.getN2() + 1;
            }
        }
    }

    @Override
    public void visit(EnumDeclList enumDeclList) {
        Tab.chainLocalSymbols(currentEnumObj);
        Tab.closeScope();

        currentEnumObj = null;
        eConst = null;
    }

    /* CONTEXT CONDITIONS */
    // DESIGNATOR

    @Override
    public void visit(DesignatorName designatorName) {
        Obj varObj = Tab.find(designatorName.getI1());
        if (varObj == Tab.noObj) {
            report_error("Pristup nedefinisanoj promenljivoj: " + designatorName.getI1(), designatorName);
            designatorName.obj = Tab.noObj;
        } else if (varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth && varObj.getKind() != Obj.Type) {
            report_error("Neadekvatna promenljiva: " + designatorName.getI1(), designatorName);
            designatorName.obj = Tab.noObj;
        } else {
            designatorName.obj = varObj;
            // Detektovanje upotrebe
            if (designatorName.obj.getKind() == Obj.Con) {
                report_info("Koriscenje simbolicke konstante " + designatorName.obj.getName() + " Kind: " + designatorName.obj.getKind() + " Type: " + designatorName.obj.getType().getKind() + " Level: " + designatorName.obj.getLevel(), designatorName);
            } else if (designatorName.obj.getKind() == Obj.Var) {
                if (designatorName.obj.getLevel() == 0) {
                    report_info("Koriscenje globalne promenljive " + designatorName.obj.getName() + " Kind: " + designatorName.obj.getKind() + " Type: " + designatorName.obj.getType().getKind() + " Level: " + designatorName.obj.getLevel(), designatorName);
                } else if (designatorName.obj.getLevel() > 0) {
                    if (designatorName.obj.getFpPos() == 0) {
                        report_info("Koriscenje lokalne promenljive " + designatorName.obj.getName() + " Kind: " + designatorName.obj.getKind() + " Type: " + designatorName.obj.getType().getKind() + " Level: " + designatorName.obj.getLevel(), designatorName);
                    } else {
                        report_info("Koriscenje formalnog parametra " + designatorName.obj.getName() + " Kind: " + designatorName.obj.getKind() + " Type: " + designatorName.obj.getType().getKind() + " Level: " + designatorName.obj.getLevel() +
                                " FpPos: " + designatorName.obj.getFpPos(), designatorName);
                    }
                }
            }
        }
    }

    @Override
    public void visit(Designator designator) {
        Obj baseObj = designator.getDesignatorName().obj;
        if (baseObj == Tab.noObj || baseObj == null) {
            designator.obj = Tab.noObj;
            return;
        }

        Struct type = baseObj.getType();
        if (baseObj.getKind() != Obj.Meth && (type == Tab.noType || type == null)) {
            designator.obj = Tab.noObj;
            return;
        }

        if (designator.getDesignatorDecl() instanceof DesignatorDecl_i) {
            if (baseObj.getKind() != Obj.Type || type.getKind() != Struct.Enum) {
                report_error("Pristupanje dozvoljeno samo za enum!", designator);
                designator.obj = Tab.noObj;
            } else {
                String memberName = ((DesignatorDecl_i) designator.getDesignatorDecl()).getI1();
                Obj found = null;
                for (Obj o : baseObj.getLocalSymbols()) {
                    if (o != null && memberName.equals(o.getName())) {
                        found = o;
                        break;
                    }
                }

                if (found == null || found.getKind() != Obj.Con) {
                    report_error("Pristup nepostojecoj enum konstanti", designator);
                    designator.obj = Tab.noObj;
                } else  {
                    designator.obj = found;
                    enumConstOwner.put(found, baseObj.getType());
                    report_info("Koriscenje simbolicke konstante " + designator.obj.getName() + " Kind: " + designator.obj.getKind() + " Type: " + designator.obj.getType().getKind() + " Level: " + designator.obj.getLevel(), designator);
                }
            }
        } else if (designator.getDesignatorDecl() instanceof DesignatorDecl_l) {
            if (type.getKind() != Struct.Array) {
                report_error("Koriscenje length nad ne nizom!", designator);
                designator.obj = Tab.noObj;
            } else {
                designator.obj = new Obj(Obj.Con, baseObj.getName() + ".length", Tab.intType);
            }
        } else if (designator.getDesignatorDecl() instanceof DesignatorDecl_expr) {
            if (type.getKind() != Struct.Array) {
                report_error("Indeksiranje nad ne-nizom!", designator);
                designator.obj = Tab.noObj;
            } else if (!Tab.intType.equals(((DesignatorDecl_expr) designator.getDesignatorDecl()).getExpr().struct) && ((DesignatorDecl_expr) designator.getDesignatorDecl()).getExpr().struct.getKind() != Struct.Enum) {
                report_error("Indeksiranje sa ne int elementom!", designator);
                designator.obj = Tab.noObj;
            } else {
                designator.obj = new Obj(Obj.Elem, baseObj.getName() + "[$]", baseObj.getType().getElemType());
                report_info("Pristup elementu niza " + baseObj.getName() + " Kind: " + designator.obj.getKind() + " Type: " + designator.obj.getType().getKind() + " Level: " + baseObj.getLevel(), designator);
            }
        } else {
            designator.obj = baseObj;
        }
    }

    //FactorySub

    @Override
    public void visit(FactorSub_var factorSub_var) {
        factorSub_var.struct = factorSub_var.getDesignator().obj.getType();
    }

    @Override
    public void visit(FactorSub_method factorSub_method) {
        if (factorSub_method.getDesignator().obj.getKind() != Obj.Meth){
            report_error("Poziv neadekvatne metode: " + factorSub_method.getDesignator().obj.getName(), factorSub_method);
            factorSub_method.struct = Tab.noType;
        } else {
            report_info("Poziv metode " + factorSub_method.getDesignator().obj.getName() + " Kind: " + factorSub_method.getDesignator().obj.getKind() +
                    " Type: " + factorSub_method.getDesignator().obj.getType().getKind() + " fParams: " + factorSub_method.getDesignator().obj.getLevel(), factorSub_method);

            factorSub_method.struct = factorSub_method.getDesignator().obj.getType();

            List<Struct> fpList = new ArrayList<>();
            List<Struct> apList;

            apList = actParStack.pop();
            for (Obj local : factorSub_method.getDesignator().obj.getLocalSymbols()) {
                if (local.getKind() == Obj.Var && local.getFpPos() == 1) {
                    fpList.add(local.getType());
                }
            }
            if (fpList.size() != apList.size()) {
                report_error("Metoda ima razlicit broj formalnih i stvarnih parametara ", factorSub_method);
            } else {
                for (int i = 0 ; i<fpList.size(); i++) {
                    if (!apList.get(i).assignableTo(fpList.get(i))) {
                        report_error("Tipovi parametara metode nisu kompatibilni", factorSub_method);
                    }
                }
            }
        }
    }

    @Override
    public void visit(FactorSub_n factorSub_n) {
        factorSub_n.struct = Tab.intType;
    }

    @Override
    public void visit(FactorSub_c factorSub_c) {
        factorSub_c.struct = Tab.charType;
    }

    @Override
    public void visit(FactorSub_b factorSub_b) {
        factorSub_b.struct = boolType;
    }

    @Override
    public void visit(FactorSub_array factorSub_array) {
        if (!factorSub_array.getExpr().struct.equals(Tab.intType)) {
            report_error("Velicina niza nije int tipa", factorSub_array);
            factorSub_array.struct = Tab.noType;
        } else {
            factorSub_array.struct = new Struct(Struct.Array, currentType);
        }
    }

    @Override
    public void visit(FactorSub_expr factorSub_expr) {
        factorSub_expr.struct = factorSub_expr.getExpr().struct;
    }

    // Factor

    @Override
    public void visit(Factor factor) {
        if (factor.getUnary() instanceof Unary_m){
            if (factor.getFactorSub().struct.getKind() == Struct.Int) {
                factor.struct = Tab.intType;
            } else {
                report_error("Negacija ne int vrednosti", factor);
                factor.struct = Tab.noType;
            }
        } else {
            factor.struct = factor.getFactorSub().struct;
        }
    }

    //Expression

    @Override
    public void visit(MulopFactorList_factor mulopFactorList_factor) {
        mulopFactorList_factor.struct = mulopFactorList_factor.getFactor().struct;
    }

    @Override
    public  void visit(MulopFactorList_mul mulopFactorList_mul) {
        if ((mulopFactorList_mul.getFactor().struct.equals(Tab.intType) || mulopFactorList_mul.getFactor().struct.getKind() == Struct.Enum )
                && (mulopFactorList_mul.getMulopFactorList().struct.equals(Tab.intType) || mulopFactorList_mul.getMulopFactorList().struct.getKind() == Struct.Enum)) {
            mulopFactorList_mul.struct = Tab.intType;
        } else {
            report_error("Mulop operacija ne int vrednosti", mulopFactorList_mul);
            mulopFactorList_mul.struct = Tab.noType;
        }
    }

    @Override
    public void visit(Term term) {
        term.struct = term.getMulopFactorList().struct;
    }

    @Override
    public void visit(AddopTermList_term addopTermList_term) {
        addopTermList_term.struct = addopTermList_term.getTerm().struct;
    }

    @Override
    public void visit(AddopTermList_add addopTermList_add) {
        if ((addopTermList_add.getTerm().struct.equals(Tab.intType) || addopTermList_add.getTerm().struct.getKind() == Struct.Enum)
                && (addopTermList_add.getAddopTermList().struct.equals(Tab.intType) || addopTermList_add.getAddopTermList().struct.getKind() == Struct.Enum)) {
            addopTermList_add.struct = Tab.intType;
        } else {
            report_error("Addop operacija ne int vrednosti", addopTermList_add);
            addopTermList_add.struct = Tab.noType;
        }
    }

    @Override
    public void visit(ExprBase exprBase) {
        exprBase.struct = exprBase.getAddopTermList().struct;
    }

    @Override
    public void visit(Expr_add expr_add) {
        expr_add.struct = expr_add.getExprBase().struct;
    }

    @Override
    public void visit(Expr_cond expr_cond) {
        if (!expr_cond.getExpr().struct.equals(expr_cond.getExpr1().struct)) {
            report_error("Nekompatibilni tipovi ternarnog operatora", expr_cond);
            expr_cond.struct = Tab.noType;
        } else if (!expr_cond.getTernaryCondition().struct.equals(boolType)) {
            report_error("Uslov ternarnog operatora nije bool tipa", expr_cond);
            expr_cond.struct = Tab.noType;
        } else {
            expr_cond.struct = expr_cond.getExpr().struct;
        }
    }

    // Condition

    @Override
    public void visit(CondFact_expr condFact_expr) {
        if (!condFact_expr.getExprBase().struct.equals(boolType)) {
            report_error("Logicki operand nije tipa bool", condFact_expr);
            condFact_expr.struct = Tab.noType;
        } else {
            condFact_expr.struct = boolType;
        }
    }

    @Override
    public void visit(CondFact_r_expr condFact_r_expr) {
        Struct left = condFact_r_expr.getExprBase().struct;
        Struct right = condFact_r_expr.getExprBase1().struct;
        if (left.compatibleWith(right) || (left.getKind() == Struct.Int && right.getKind() == Struct.Enum) || (left.getKind() == Struct.Enum && right.getKind() == Struct.Int)) {
            if (left.isRefType() || right.isRefType()) {
                if (condFact_r_expr.getRelop() instanceof Relop_eq || condFact_r_expr.getRelop() instanceof Relop_ne) {
                    condFact_r_expr.struct = boolType;
                } else {
                    report_error("Poredjenje ref tipova sa ne adekvatnim relacionim operatorom", condFact_r_expr);
                    condFact_r_expr.struct = Tab.noType;
                }
            } else {
                condFact_r_expr.struct = boolType;
            }
        } else {
            report_error("Logicki operandi nisu kompatibilni.", condFact_r_expr);
            condFact_r_expr.struct = Tab.noType;
        }
    }

    @Override
    public void visit(CondFactList_fact condFactList_fact) {
        condFactList_fact.struct = condFactList_fact.getCondFact().struct;
    }

    @Override
    public void visit(CondFactList_list condFactList_list) {
        if (condFactList_list.getCondFact().struct.equals(boolType) && condFactList_list.getCondFactList().struct.equals(boolType)) {
            condFactList_list.struct = boolType;
        } else {
            report_error("And operacija ne vraca bool vrednosti", condFactList_list);
            condFactList_list.struct = Tab.noType;
        }
    }

    @Override
    public void visit(CondTerm condTerm) {
        condTerm.struct = condTerm.getCondFactList().struct;
    }

    @Override
    public void visit(CondTermList_term condTermList_term) {
        condTermList_term.struct = condTermList_term.getCondTerm().struct;
    }

    @Override
    public void visit(CondTermList_list condTermList_list) {
        if (condTermList_list.getCondTerm().struct.equals(boolType) && condTermList_list.getCondTermList().struct.equals(boolType)) {
            condTermList_list.struct = boolType;
        } else {
            report_error("Or operacija ne vraca bool vrednost", condTermList_list);
            condTermList_list.struct = Tab.noType;
        }
    }

    @Override
    public void visit(Condition_list condition){
        condition.struct = condition.getCondTermList().struct;
        if (!condition.struct.equals(boolType)) {
            report_error("Uslov nije bool tipa", condition);
        }
    }

    @Override
    public void visit(TernaryCondition ternaryCondition) {
        ternaryCondition.struct = ternaryCondition.getCondTermList().struct;
        if (!ternaryCondition.struct.equals(boolType)) {
            report_error("Uslov nije bool tipa", ternaryCondition);
        }
    }

    @Override
    public void visit(ForCondition_list forCondition_list) {
        forCondition_list.struct = forCondition_list.getCondTermList().struct;
        if (!forCondition_list.struct.equals(boolType)) {
            report_error("Uslov nije bool tipa", forCondition_list);
        }
    }

    @Override
    public void visit(ForCondition_e forCondition_e) {
        forCondition_e.struct = boolType;
    }

    // Designator Statement

    @Override
    public void visit(DesignatorStatement_assign designatorStatement_assign) {
        int kind = designatorStatement_assign.getDesignator().obj.getKind();
        Obj isEnum = Tab.noObj;
        if (kind != Obj.Var && kind != Obj.Elem) {
            report_error("Dodela u neadekvatnu promenljivu", designatorStatement_assign);
        } else if (designatorStatement_assign.getDesignator().obj.getType().getKind() == Struct.Enum) {
            if (designatorStatement_assign.getExpr() instanceof Expr_add) {
                if (((Expr_add) designatorStatement_assign.getExpr()).getExprBase().getAddopTermList() instanceof AddopTermList_term) {
                    if (((AddopTermList_term) ((Expr_add) designatorStatement_assign.getExpr()).getExprBase().getAddopTermList()).getTerm().getMulopFactorList() instanceof MulopFactorList_factor) {
                        if (((MulopFactorList_factor) ((AddopTermList_term) ((Expr_add) designatorStatement_assign.getExpr()).getExprBase().getAddopTermList()).getTerm().getMulopFactorList()).getFactor().getUnary() instanceof Unary_e) {
                            if (((MulopFactorList_factor) ((AddopTermList_term) ((Expr_add) designatorStatement_assign.getExpr()).getExprBase().getAddopTermList()).getTerm().getMulopFactorList()).getFactor().getFactorSub() instanceof FactorSub_var){
                                isEnum = ((FactorSub_var) ((MulopFactorList_factor) ((AddopTermList_term) ((Expr_add) designatorStatement_assign.getExpr())
                                        .getExprBase().getAddopTermList()).getTerm().getMulopFactorList()).getFactor().getFactorSub()).getDesignator().obj;
                            }
                        }
                    }
                }
            }
            if (isEnum.getKind() == Obj.Con) {
                Struct owner = enumConstOwner.get(isEnum);
                if (owner == null) {
                    report_error("Neadekvatna dodela vrednosti ENUM konstante", designatorStatement_assign);
                } else if (owner != designatorStatement_assign.getDesignator().obj.getType()) {
                    report_error("Neadekvatna dodela vrednosti ENUM konstante", designatorStatement_assign);
                }
            } else if (isEnum.getKind() == Obj.Var || isEnum.getKind() == Obj.Elem) {
                if (!isEnum.getType().equals(designatorStatement_assign.getDesignator().obj.getType())) {
                    report_error("Enum promenljive nisu istog tipa", designatorStatement_assign);
                }
            }
        } else if (!designatorStatement_assign.getExpr().struct.assignableTo(designatorStatement_assign.getDesignator().obj.getType())) {
            report_error("Neadekvatna dodela vrednosti u promenljivu", designatorStatement_assign);
        }
    }

    @Override
    public void visit(DesignatorStatement_method designatorStatement_method) {
        if (designatorStatement_method.getDesignator().obj.getKind() != Obj.Meth) {
            report_error("Poziv neadekvatne metode", designatorStatement_method);
        } else {
            report_info("Poziv metode " + designatorStatement_method.getDesignator().obj.getName() + " Kind: " + designatorStatement_method.getDesignator().obj.getKind() +
                    " Type: " + designatorStatement_method.getDesignator().obj.getType().getKind() + " fParams: " + designatorStatement_method.getDesignator().obj.getLevel(), designatorStatement_method);

            List<Struct> fpList = new ArrayList<>();
            List<Struct> apList;

            apList = actParStack.pop();
            for (Obj local : designatorStatement_method.getDesignator().obj.getLocalSymbols()) {
                if (local.getKind() == Obj.Var && local.getFpPos() == 1) {
                    fpList.add(local.getType());
                }
            }
            if (fpList.size() != apList.size()) {
                report_error("Metoda ima razlicit broj formalnih i stvarnih parametara", designatorStatement_method);
            } else {
                for (int i=0; i<fpList.size(); i++) {
                    if(!apList.get(i).assignableTo(fpList.get(i))) {
                        report_error("Razliciti tipovi parametara metode", designatorStatement_method);
                    }
                }
            }
        }
    }

    @Override
    public void visit(DesignatorStatement_inc designatorStatement_inc) {
        int kind = designatorStatement_inc.getDesignator().obj.getKind();
        if (kind != Obj.Var && kind != Obj.Elem) {
            report_error("Inkrementiranje neadekvatne promenljive", designatorStatement_inc);
        } else if (!designatorStatement_inc.getDesignator().obj.getType().equals(Tab.intType)) {
            report_error("Inkrementiranje ne int promenljive", designatorStatement_inc);
        }
    }

    @Override
    public void visit(DesignatorStatement_dec designatorStatement_dec) {
        int kind = designatorStatement_dec.getDesignator().obj.getKind();
        if (kind != Obj.Var && kind != Obj.Elem) {
            report_error("Dekrementiranje neadekvatne promenljive", designatorStatement_dec);
        } else if (!designatorStatement_dec.getDesignator().obj.getType().equals(Tab.intType)) {
            report_error("Dekrementiranje ne int promenljive", designatorStatement_dec);
        }
    }

    //Statements

    @Override
    public void visit(Statement_if statement_if) {
        if (!statement_if.getCondition().struct.equals(boolType)) {
            report_error("Uslov za IF nije tipa bool", statement_if);
        }
    }

    @Override
    public void visit(SwitchNonTerm switchNonTerm) {
        switchCnt++;
        Set<Integer> set = new HashSet<>();
        switchStack.push(set);
    }

    @Override
    public void visit(ForNonTerm forNonTerm) {
        loopCnt++;
    }

    @Override
    public void visit(Statement_break statement_break) {
        if (switchCnt == 0 && loopCnt == 0) {
            report_error("Koriscenje break-a van petlje i switch-a", statement_break);
        }
    }

    @Override
    public void visit(Statement_continue statement_continue) {
        if (loopCnt == 0) {
            report_error("Koriscenje continue van petlje", statement_continue);
        }
    }

    @Override
    public void visit(Statement_return1 statement_return1) {
        returnHappened = true;
    }

    @Override
    public void visit(Statement_return2 statement_return2) {
        returnHappened = true;
        if (currentMethod.getType() == Tab.noType) {
            report_error("Return se koristi u void metodi", statement_return2);
        } else if (!statement_return2.getExpr().struct.equals(currentMethod.getType())) {
            report_error("Return vrednost nije istog tipa kao povratna vrednost metode", statement_return2);
        }
    }

    @Override
    public void visit(Statement_read statement_read) {
        int kind = statement_read.getDesignator().obj.getKind();
        Struct type = statement_read.getDesignator().obj.getType();
        if (kind != Obj.Var && kind != Obj.Elem) {
          report_error("Read operacija neadekvatne promenljive", statement_read);
        } else if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
            report_error("Read operacija ne int/char/bool promenljive", statement_read);
        }
    }

    @Override
    public void visit(Statement_print1 statement_print1) {
        Struct type = statement_print1.getExpr().struct;
        if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
            report_error("Print operacija ne int/char/bool promenljive", statement_print1);
        }
    }

    @Override
    public void visit(Statement_print2 statement_print2) {
        Struct type = statement_print2.getExpr().struct;
        if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
            report_error("Print operacija ne int/char/bool promenljive", statement_print2);
        }
    }

    @Override
    public void visit(Statement_switch statement_switch) {
        if (!statement_switch.getExpr().struct.equals(Tab.intType)) {
            report_error("Switch Expression nije celobrojnog tipa", statement_switch);
        }
        switchStack.pop();
        switchCnt--;
    }

    @Override
    public void visit(CaseDecl caseDecl) {
        Set<Integer> set = switchStack.peek();
        if (set.contains(caseDecl.getN1())) {
            report_error("Duplikat Case u Switch-u", caseDecl);
        } else {
            set.add(caseDecl.getN1());
        }
    }

    @Override
    public void visit(Statement_for statement_for) {
        if (!statement_for.getForCondition().struct.equals(boolType)) {
            report_error("For uslov nije bool tipa", statement_for);
        }

        loopCnt--;
    }

    // Act Parameters

    @Override
    public void visit(CallStart_e callStart_e) {
        List<Struct> list = new ArrayList<>();
        actParStack.push(list);
    }

    @Override
    public void visit(ActPar actPar) {
        actParStack.peek().add(actPar.getExpr().struct);
    }
}