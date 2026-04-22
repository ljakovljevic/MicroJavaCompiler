// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class Expr_add extends Expr {

    private ExprBase ExprBase;

    public Expr_add (ExprBase ExprBase) {
        this.ExprBase=ExprBase;
        if(ExprBase!=null) ExprBase.setParent(this);
    }

    public ExprBase getExprBase() {
        return ExprBase;
    }

    public void setExprBase(ExprBase ExprBase) {
        this.ExprBase=ExprBase;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprBase!=null) ExprBase.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprBase!=null) ExprBase.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprBase!=null) ExprBase.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_add(\n");

        if(ExprBase!=null)
            buffer.append(ExprBase.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_add]");
        return buffer.toString();
    }
}
