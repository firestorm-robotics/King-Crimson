package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.TalonServoSubsystem;

public class Turret extends TalonServoSubsystem {

    public enum ControlType {
        OPEN_LOOP(), POSITION_CLOSED_LOOP(), VELOCITY_CLOSED_LOOP()
    }

    private ControlType mControlType = ControlType.OPEN_LOOP;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private static Turret instance;

    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret(new TalonSRX(15));
        }
        return instance;
    }

    protected Turret(TalonSRX servoMotor) {
        super(servoMotor);
        mServoMotor.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition);
        mServoMotor.config_kF(0, 3.069);
        mServoMotor.configMotionCruiseVelocity(220);
        mServoMotor.configMotionAcceleration(175);
        // TODO Auto-generated constructor stub
    }

    /**
     * pretty self explanitory
     */
    public void resetEncoder() {
        mServoMotor.setSelectedSensorPosition(0);
    }

    /**
     * sets the angle of the turret for closed loop control
     * @param angle angle of the turret
     */
    public synchronized void setDesiredAngle(int angle) {
        //TODO figure out conversion rate between angle and encoder ticks
        mPeriodicIO.mDesiredAngle = angle;
    }

    /**
     *  sets the speed of the turret for open loop control
     * @param power percent of power from -1 to 1
     */
    public synchronized void setOpenloopPower(double power) {
        mPeriodicIO.mDesiredSpeed = power;
    }

    /**
     * stops the turret
     */
    public synchronized void stop() {
        mPeriodicIO.mDesiredSpeed = 0;
    }

    /**
     * commands the TalonSRX to got to a demanded amount of ticks on the encoder
     */
    private void handleClosedLoop() {
        // Right now we just have position control
        // TODO Maybe add velocity control
        setPos(mPeriodicIO.mDesiredAngle);
    }

    /**
     * sets the TalonSRX to move the motor at the demanded amount of power
     */
    private void handleOpenLoop() {
        mServoMotor.set(ControlMode.PercentOutput, mPeriodicIO.mDesiredSpeed);
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Turret Angle", mServoAngle);
        SmartDashboard.putNumber("Turret Speed", mPeriodicIO.mCurrentSpeed);

    }

    @Override
    public void pollTelemetry() {
        mServoAngle = mServoMotor.getSelectedSensorPosition();
        mPeriodicIO.mCurrentSpeed = mServoMotor.getSelectedSensorVelocity();

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
                // TODO add stuff later
                synchronized (Turret.this) {
                    if (mControlType != ControlType.POSITION_CLOSED_LOOP) {
                        handleOpenLoop();
                    } else {
                        handleClosedLoop();
                    }
                }
            }
        });

    }

    private class PeriodicIO {
        public int mDesiredAngle = 0;
        public double mDesiredSpeed = 0;
        public double mCurrentSpeed = 0;
    }

}