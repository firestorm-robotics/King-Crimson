package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// DEPRACATED BUT STILL MAYBE USABLE
/*
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
*/
import frc.robot.RobotMap;

class ControlPanel{
    // DEPRECATED BUT STIlL MAYBE USABLE: private CANSparkMax lift = new CANSparkMax(RobotMap.LIFT, MotorType.kBrushless);
    private TalonSRX rotatorforthecolorwheel=new TalonSRX(RobotMap.ROTATOR);
    ControlPanel(){
        rotatorforthecolorwheel.set(ControlMode.Position,0.5);
    }
}