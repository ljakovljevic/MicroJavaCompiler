// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private EnumDeclName EnumDeclName;
    private EnumDecl EnumDecl;
    private EnumDeclMore EnumDeclMore;

    public EnumDeclList (EnumDeclName EnumDeclName, EnumDecl EnumDecl, EnumDeclMore EnumDeclMore) {
        this.EnumDeclName=EnumDeclName;
        if(EnumDeclName!=null) EnumDeclName.setParent(this);
        this.EnumDecl=EnumDecl;
        if(EnumDecl!=null) EnumDecl.setParent(this);
        this.EnumDeclMore=EnumDeclMore;
        if(EnumDeclMore!=null) EnumDeclMore.setParent(this);
    }

    public EnumDeclName getEnumDeclName() {
        return EnumDeclName;
    }

    public void setEnumDeclName(EnumDeclName EnumDeclName) {
        this.EnumDeclName=EnumDeclName;
    }

    public EnumDecl getEnumDecl() {
        return EnumDecl;
    }

    public void setEnumDecl(EnumDecl EnumDecl) {
        this.EnumDecl=EnumDecl;
    }

    public EnumDeclMore getEnumDeclMore() {
        return EnumDeclMore;
    }

    public void setEnumDeclMore(EnumDeclMore EnumDeclMore) {
        this.EnumDeclMore=EnumDeclMore;
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
        if(EnumDeclName!=null) EnumDeclName.accept(visitor);
        if(EnumDecl!=null) EnumDecl.accept(visitor);
        if(EnumDeclMore!=null) EnumDeclMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumDeclName!=null) EnumDeclName.traverseTopDown(visitor);
        if(EnumDecl!=null) EnumDecl.traverseTopDown(visitor);
        if(EnumDeclMore!=null) EnumDeclMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumDeclName!=null) EnumDeclName.traverseBottomUp(visitor);
        if(EnumDecl!=null) EnumDecl.traverseBottomUp(visitor);
        if(EnumDeclMore!=null) EnumDeclMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclList(\n");

        if(EnumDeclName!=null)
            buffer.append(EnumDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDecl!=null)
            buffer.append(EnumDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDeclMore!=null)
            buffer.append(EnumDeclMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclList]");
        return buffer.toString();
    }
}
