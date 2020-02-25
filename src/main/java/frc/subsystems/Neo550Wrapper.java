package frc.subsystems;

import frc.robot.RobotMap;
import frc.subsystems.interfaces.MotorInterface;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Neo550Wrapper implements MotorInterface{
    private CANSparkMax neo;
    private double currentSpeed=0;
    public Neo550Wrapper(int deviceId){
        neo=new CANSparkMax(deviceId, MotorType.kBrushless);
    }
    @Override
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    public void setSpeed(double speed) {
        currentSpeed=speed;
        neo.set(currentSpeed);
    }

}