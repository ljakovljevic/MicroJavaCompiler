// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class Expr_cond extends Expr {

    private TernaryCondition TernaryCondition;
    private Expr Expr;
    private TernaryColon TernaryColon;
    private Expr Expr1;
    private TernaryEnd TernaryEnd;

    public Expr_cond (TernaryCondition TernaryCondition, Expr Expr, TernaryColon TernaryColon, Expr Expr1, TernaryEnd TernaryEnd) {
        this.TernaryCondition=TernaryCondition;
        if(TernaryCondition!=null) TernaryCondition.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.TernaryColon=TernaryColon;
        if(TernaryColon!=null) TernaryColon.setParent(this);
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
        this.TernaryEnd=TernaryEnd;
        if(TernaryEnd!=null) TernaryEnd.setParent(this);
    }

    public TernaryCondition getTernaryCondition() {
        return TernaryCondition;
    }

    public void setTernaryCondition(TernaryCondition TernaryCondition) {
        this.TernaryCondition=TernaryCondition;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public TernaryColon getTernaryColon() {
        return TernaryColon;
    }

    public void setTernaryColon(TernaryColon TernaryColon) {
        this.TernaryColon=TernaryColon;
    }

    public Expr getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr Expr1) {
        this.Expr1=Expr1;
    }

    public TernaryEnd getTernaryEnd() {
        return TernaryEnd;
    }

    public void setTernaryEnd(TernaryEnd TernaryEnd) {
        this.TernaryEnd=TernaryEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TernaryCondition!=null) TernaryCondition.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(TernaryColon!=null) TernaryColon.accept(visitor);
        if(Expr1!=null) Expr1.accept(visitor);
        if(TernaryEnd!=null) TernaryEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TernaryCondition!=null) TernaryCondition.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(TernaryColon!=null) TernaryColon.traverseTopDown(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
        if(TernaryEnd!=null) TernaryEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TernaryCondition!=null) TernaryCondition.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(TernaryColon!=null) TernaryColon.traverseBottomUp(visitor);
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        if(TernaryEnd!=null) TernaryEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_cond(\n");

        if(TernaryCondition!=null)
            buffer.append(TernaryCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryColon!=null)
            buffer.append(TernaryColon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryEnd!=null)
            buffer.append(TernaryEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_cond]");
        return buffer.toString();
    }
}
