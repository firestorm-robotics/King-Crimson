package frc.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.TalonServoSubsystem;

public class Turret extends TalonServoSubsystem {

    public enum ControlMode {
        OPEN_LOOP(), POSITION_CLOSE_LOOP(), ANGULAR_VELOCITY_OPEN_LOOP()
    }


    private static Turret instance;

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret(new TalonSRX(20));
        }
        return instance;
    }

    protected Turret(TalonSRX servoMotor) {
        super(servoMotor);
        mServoMotor.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub
        Shuffleboard.getTab("Turret").add("Angle", mServoAngle);

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub
        mServoAngle = mServoMotor.getSelectedSensorPosition();

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop() {

            @Override
            public void onStop(double timestamp) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoop(double timestamp) {
                //TODO add stuff later
            }
        });

    }

    private class PeriodicIO {
        public double mDesiredAngle = 0;
        public double mDesiredSpeed = 0;
        public double mCurrentSpeed = 0;
    }

}