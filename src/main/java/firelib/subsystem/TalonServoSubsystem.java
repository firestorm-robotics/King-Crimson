package firelib.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * 
 */
public abstract class TalonServoSubsystem implements ISubsystem {
    protected TalonSRX mServoMotor;
    protected double mServoAngle;

    protected TalonServoSubsystem(TalonSRX servoMotor) {
        mServoMotor = servoMotor;
        mServoMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    /**
     * private interface to set the position of the "servo"
     * 
     * @param tickPos the tick position for the encoder
     */
    protected synchronized void setPos(int tickPos) {
        mServoMotor.set(ControlMode.MotionMagic, tickPos);
    }

}