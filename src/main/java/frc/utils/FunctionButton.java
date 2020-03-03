package frc.utils;

public class FunctionButton {
    protected boolean swit;
    public FunctionButton(){
        this.swit=false;
    }
    public void stateIsTrue(){
        if (this.swit==false){
            this.swit=true;
            trigger();
        }
    }
    public void stateIsFalse(){
        if (this.swit==true){
            this.swit=false;
        }
    }
    public void trigger(){};
}
// Extend this class for calling functions once, and only once, upon condition equals true.