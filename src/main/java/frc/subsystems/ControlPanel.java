package frc.subsystems;
/*DEPRECATED EVERYTHING (We might need import stuff)


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
// DEPRACATED BUT STILL MAYBE USABLE

//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.util.Color;

class ControlPanel{
    // DEPRECATED BUT STIlL MAYBE USABLE: private CANSparkMax lift = new CANSparkMax(RobotMap.LIFT, MotorType.kBrushless);
    private TalonSRX rotatorforthecolorwheel=new TalonSRX(RobotMap.CONTROLPANEL_ROTATOR);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    // Use Color color = m_colorSensor.getColor() for color output,
    // and use double IRvalue = m_colorSensor.getIR() for IR output.
    // This seems fun!
    // Also, you probably have the URL of an awesome github example ingrained in the computers hard drive. (in a text file.)
    // Just check the file in "/" that you can't mess with.
    ControlPanel(){
        rotatorforthecolorwheel.set(ControlMode.Position,0.5);
    }
}*/

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;
import frc.subsystems.GoalFlow;

public class ControlPanel extends GoalFlow{
    private Neo550Wrapper controlPanel;
    private Color currColor;
    private int passes=0;
    private boolean gotColor=false;
    private double neo550Speed=0.3; //TODO: Find out the actual speed required. 0.3 probably works, but we might want to go higher.
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    public ControlPanel(){
        super();
        controlPanel=new Neo550Wrapper(RobotMap.CONTROLPANEL_ROTATOR);
    }
    public void endGoal(){
        this.goalAlert("Detach the control panel goal, until another match");
        this.controlPanel.setSpeed(0);
    }
    public void runGoal(Object... args) {
        super.runGoal(args);;
        Color color=m_colorSensor.getColor();
        if (this.currentGoal==Goal.INTROTATIONS){
            this.controlPanel.setSpeed(this.neo550Speed);
            if (this.tick==0){
                this.currColor=color;
            }
            else{
                if (color==this.currColor){
                    this.passes+=1;
                }
                if (this.passes>=6){
                    this.endGoal();
                }
            }
        }
        else if (this.currentGoal==Goal.TOCOLOR){
            if (this.gotColor){
                this.controlPanel.setSpeed(this.neo550Speed);
                if (color==this.currColor){
                    this.endGoal();
                }
            }
            else{
                ColorMatch colorMatcher = new ColorMatch();
                String message=DriverStation.getInstance().getGameSpecificMessage();
                HashMap<String,Color> pairs=new HashMap();
                pairs.put("G", Color.kLime);
                pairs.put("B", Color.kBlue);
                pairs.put("R", Color.kRed);
                pairs.put("Y", Color.kYellow);
                if (message.length()>0){
                    this.goalAlert("Got color message, parsing");
                    this.gotColor=true;
                    currColor=pairs.get(message);
                }
            }
        }
    }
}