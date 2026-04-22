// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class CondFact_r_expr extends CondFact {

    private ExprBase ExprBase;
    private Relop Relop;
    private ExprBase ExprBase1;

    public CondFact_r_expr (ExprBase ExprBase, Relop Relop, ExprBase ExprBase1) {
        this.ExprBase=ExprBase;
        if(ExprBase!=null) ExprBase.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.ExprBase1=ExprBase1;
        if(ExprBase1!=null) ExprBase1.setParent(this);
    }

    public ExprBase getExprBase() {
        return ExprBase;
    }

    public void setExprBase(ExprBase ExprBase) {
        this.ExprBase=ExprBase;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public ExprBase getExprBase1() {
        return ExprBase1;
    }

    public void setExprBase1(ExprBase ExprBase1) {
        this.ExprBase1=ExprBase1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprBase!=null) ExprBase.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(ExprBase1!=null) ExprBase1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprBase!=null) ExprBase.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(ExprBase1!=null) ExprBase1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprBase!=null) ExprBase.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(ExprBase1!=null) ExprBase1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_r_expr(\n");

        if(ExprBase!=null)
            buffer.append(ExprBase.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprBase1!=null)
            buffer.append(ExprBase1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_r_expr]");
        return buffer.toString();
    }
}
