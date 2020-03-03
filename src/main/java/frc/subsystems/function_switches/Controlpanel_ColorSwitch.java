package frc.subsystems.function_switches;

import frc.subsystems.ControlPanel;
import frc.subsystems.Goal;

public class Controlpanel_ColorSwitch extends frc.utils.FunctionButton{
    public void stateIsTrue(ControlPanel controlPanel){
        if (this.swit==false){
            this.swit=true;
            trigger();
        }
    }
    public void stateIsFalse(ControlPanel controlPanel){
        if (this.swit==true){
            this.swit=false;
        }
    }
    public void trigger(ControlPanel controlPanel){
        if (controlPanel.getGoal()==Goal.INTROTATIONS){
            controlPanel.passes+=1;
        }
    }
}