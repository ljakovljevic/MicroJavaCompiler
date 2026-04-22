// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodRetAndName MethodRetAndName;
    private FormParList FormParList;
    private VarDeclListEnum VarDeclListEnum;
    private StatementList StatementList;

    public MethodDecl (MethodRetAndName MethodRetAndName, FormParList FormParList, VarDeclListEnum VarDeclListEnum, StatementList StatementList) {
        this.MethodRetAndName=MethodRetAndName;
        if(MethodRetAndName!=null) MethodRetAndName.setParent(this);
        this.FormParList=FormParList;
        if(FormParList!=null) FormParList.setParent(this);
        this.VarDeclListEnum=VarDeclListEnum;
        if(VarDeclListEnum!=null) VarDeclListEnum.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodRetAndName getMethodRetAndName() {
        return MethodRetAndName;
    }

    public void setMethodRetAndName(MethodRetAndName MethodRetAndName) {
        this.MethodRetAndName=MethodRetAndName;
    }

    public FormParList getFormParList() {
        return FormParList;
    }

    public void setFormParList(FormParList FormParList) {
        this.FormParList=FormParList;
    }

    public VarDeclListEnum getVarDeclListEnum() {
        return VarDeclListEnum;
    }

    public void setVarDeclListEnum(VarDeclListEnum VarDeclListEnum) {
        this.VarDeclListEnum=VarDeclListEnum;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodRetAndName!=null) MethodRetAndName.accept(visitor);
        if(FormParList!=null) FormParList.accept(visitor);
        if(VarDeclListEnum!=null) VarDeclListEnum.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodRetAndName!=null) MethodRetAndName.traverseTopDown(visitor);
        if(FormParList!=null) FormParList.traverseTopDown(visitor);
        if(VarDeclListEnum!=null) VarDeclListEnum.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodRetAndName!=null) MethodRetAndName.traverseBottomUp(visitor);
        if(FormParList!=null) FormParList.traverseBottomUp(visitor);
        if(VarDeclListEnum!=null) VarDeclListEnum.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodRetAndName!=null)
            buffer.append(MethodRetAndName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParList!=null)
            buffer.append(FormParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListEnum!=null)
            buffer.append(VarDeclListEnum.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
