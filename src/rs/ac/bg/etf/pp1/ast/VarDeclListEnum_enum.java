// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListEnum_enum extends VarDeclListEnum {

    private VarDeclListEnum VarDeclListEnum;
    private VarDeclList VarDeclList;

    public VarDeclListEnum_enum (VarDeclListEnum VarDeclListEnum, VarDeclList VarDeclList) {
        this.VarDeclListEnum=VarDeclListEnum;
        if(VarDeclListEnum!=null) VarDeclListEnum.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public VarDeclListEnum getVarDeclListEnum() {
        return VarDeclListEnum;
    }

    public void setVarDeclListEnum(VarDeclListEnum VarDeclListEnum) {
        this.VarDeclListEnum=VarDeclListEnum;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListEnum!=null) VarDeclListEnum.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListEnum!=null) VarDeclListEnum.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListEnum!=null) VarDeclListEnum.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListEnum_enum(\n");

        if(VarDeclListEnum!=null)
            buffer.append(VarDeclListEnum.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListEnum_enum]");
        return buffer.toString();
    }
}
