// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class CaseStatementList_list extends CaseStatementList {

    private CaseStatement CaseStatement;
    private CaseStatementList CaseStatementList;

    public CaseStatementList_list (CaseStatement CaseStatement, CaseStatementList CaseStatementList) {
        this.CaseStatement=CaseStatement;
        if(CaseStatement!=null) CaseStatement.setParent(this);
        this.CaseStatementList=CaseStatementList;
        if(CaseStatementList!=null) CaseStatementList.setParent(this);
    }

    public CaseStatement getCaseStatement() {
        return CaseStatement;
    }

    public void setCaseStatement(CaseStatement CaseStatement) {
        this.CaseStatement=CaseStatement;
    }

    public CaseStatementList getCaseStatementList() {
        return CaseStatementList;
    }

    public void setCaseStatementList(CaseStatementList CaseStatementList) {
        this.CaseStatementList=CaseStatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CaseStatement!=null) CaseStatement.accept(visitor);
        if(CaseStatementList!=null) CaseStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseStatement!=null) CaseStatement.traverseTopDown(visitor);
        if(CaseStatementList!=null) CaseStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseStatement!=null) CaseStatement.traverseBottomUp(visitor);
        if(CaseStatementList!=null) CaseStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseStatementList_list(\n");

        if(CaseStatement!=null)
            buffer.append(CaseStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseStatementList!=null)
            buffer.append(CaseStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseStatementList_list]");
        return buffer.toString();
    }
}
