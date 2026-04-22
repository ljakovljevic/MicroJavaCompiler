// generated with ast extension for cup
// version 0.8
// 11/2/2026 12:14:30


package rs.ac.bg.etf.pp1.ast;

public class FactorSub_method extends FactorSub {

    private Designator Designator;
    private CallStart CallStart;
    private ActParList ActParList;

    public FactorSub_method (Designator Designator, CallStart CallStart, ActParList ActParList) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.CallStart=CallStart;
        if(CallStart!=null) CallStart.setParent(this);
        this.ActParList=ActParList;
        if(ActParList!=null) ActParList.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public CallStart getCallStart() {
        return CallStart;
    }

    public void setCallStart(CallStart CallStart) {
        this.CallStart=CallStart;
    }

    public ActParList getActParList() {
        return ActParList;
    }

    public void setActParList(ActParList ActParList) {
        this.ActParList=ActParList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(CallStart!=null) CallStart.accept(visitor);
        if(ActParList!=null) ActParList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(CallStart!=null) CallStart.traverseTopDown(visitor);
        if(ActParList!=null) ActParList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(CallStart!=null) CallStart.traverseBottomUp(visitor);
        if(ActParList!=null) ActParList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorSub_method(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CallStart!=null)
            buffer.append(CallStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParList!=null)
            buffer.append(ActParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorSub_method]");
        return buffer.toString();
    }
}
