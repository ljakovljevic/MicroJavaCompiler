// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class Statement_for extends Statement {

    private ForNonTerm ForNonTerm;
    private DesignatorStatement DesignatorStatement;
    private CondSemi CondSemi;
    private ForCondition ForCondition;
    private UpdateSemi UpdateSemi;
    private DesignatorStatement DesignatorStatement1;
    private BodyParen BodyParen;
    private Statement Statement;

    public Statement_for (ForNonTerm ForNonTerm, DesignatorStatement DesignatorStatement, CondSemi CondSemi, ForCondition ForCondition, UpdateSemi UpdateSemi, DesignatorStatement DesignatorStatement1, BodyParen BodyParen, Statement Statement) {
        this.ForNonTerm=ForNonTerm;
        if(ForNonTerm!=null) ForNonTerm.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
        this.CondSemi=CondSemi;
        if(CondSemi!=null) CondSemi.setParent(this);
        this.ForCondition=ForCondition;
        if(ForCondition!=null) ForCondition.setParent(this);
        this.UpdateSemi=UpdateSemi;
        if(UpdateSemi!=null) UpdateSemi.setParent(this);
        this.DesignatorStatement1=DesignatorStatement1;
        if(DesignatorStatement1!=null) DesignatorStatement1.setParent(this);
        this.BodyParen=BodyParen;
        if(BodyParen!=null) BodyParen.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonTerm getForNonTerm() {
        return ForNonTerm;
    }

    public void setForNonTerm(ForNonTerm ForNonTerm) {
        this.ForNonTerm=ForNonTerm;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public CondSemi getCondSemi() {
        return CondSemi;
    }

    public void setCondSemi(CondSemi CondSemi) {
        this.CondSemi=CondSemi;
    }

    public ForCondition getForCondition() {
        return ForCondition;
    }

    public void setForCondition(ForCondition ForCondition) {
        this.ForCondition=ForCondition;
    }

    public UpdateSemi getUpdateSemi() {
        return UpdateSemi;
    }

    public void setUpdateSemi(UpdateSemi UpdateSemi) {
        this.UpdateSemi=UpdateSemi;
    }

    public DesignatorStatement getDesignatorStatement1() {
        return DesignatorStatement1;
    }

    public void setDesignatorStatement1(DesignatorStatement DesignatorStatement1) {
        this.DesignatorStatement1=DesignatorStatement1;
    }

    public BodyParen getBodyParen() {
        return BodyParen;
    }

    public void setBodyParen(BodyParen BodyParen) {
        this.BodyParen=BodyParen;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
        if(CondSemi!=null) CondSemi.accept(visitor);
        if(ForCondition!=null) ForCondition.accept(visitor);
        if(UpdateSemi!=null) UpdateSemi.accept(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.accept(visitor);
        if(BodyParen!=null) BodyParen.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonTerm!=null) ForNonTerm.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
        if(CondSemi!=null) CondSemi.traverseTopDown(visitor);
        if(ForCondition!=null) ForCondition.traverseTopDown(visitor);
        if(UpdateSemi!=null) UpdateSemi.traverseTopDown(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.traverseTopDown(visitor);
        if(BodyParen!=null) BodyParen.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        if(CondSemi!=null) CondSemi.traverseBottomUp(visitor);
        if(ForCondition!=null) ForCondition.traverseBottomUp(visitor);
        if(UpdateSemi!=null) UpdateSemi.traverseBottomUp(visitor);
        if(DesignatorStatement1!=null) DesignatorStatement1.traverseBottomUp(visitor);
        if(BodyParen!=null) BodyParen.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_for(\n");

        if(ForNonTerm!=null)
            buffer.append(ForNonTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondSemi!=null)
            buffer.append(CondSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondition!=null)
            buffer.append(ForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UpdateSemi!=null)
            buffer.append(UpdateSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement1!=null)
            buffer.append(DesignatorStatement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BodyParen!=null)
            buffer.append(BodyParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_for]");
        return buffer.toString();
    }
}
