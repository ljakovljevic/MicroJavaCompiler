// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class CaseList_list extends CaseList {

    private CaseDecl CaseDecl;
    private CaseList CaseList;

    public CaseList_list (CaseDecl CaseDecl, CaseList CaseList) {
        this.CaseDecl=CaseDecl;
        if(CaseDecl!=null) CaseDecl.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
    }

    public CaseDecl getCaseDecl() {
        return CaseDecl;
    }

    public void setCaseDecl(CaseDecl CaseDecl) {
        this.CaseDecl=CaseDecl;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CaseDecl!=null) CaseDecl.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseDecl!=null) CaseDecl.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseDecl!=null) CaseDecl.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseList_list(\n");

        if(CaseDecl!=null)
            buffer.append(CaseDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseList_list]");
        return buffer.toString();
    }
}
