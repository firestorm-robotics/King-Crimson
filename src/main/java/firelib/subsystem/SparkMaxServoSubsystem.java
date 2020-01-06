
package firelib.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;

/**
 * Add your docs here.
 */
public abstract class SparkMaxServoSubsystem implements ISubsystem {
    protected CANSparkMax mServoMotor;
    protected Encoder mServoEncoder;

    protected SparkMaxServoSubsystem(int servoMotorID, int servoEncoderChannelA, int servoEncoderChannelB) {
        mServoMotor = new CANSparkMax(servoMotorID, MotorType.kBrushless);
        mServoEncoder = new Encoder(servoEncoderChannelA, servoEncoderChannelB);
    }

    protected synchronized void setPos(int ticks) {
        // TODO add implementation for this class
    }

}
