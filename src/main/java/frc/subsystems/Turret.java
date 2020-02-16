package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.TalonServoSubsystem;
import frc.robot.Constants;

/**
 * implementation to control the turret on the robot
 * multiple control loops depending if it needs to lock position or follow target
 */
public class Turret extends TalonServoSubsystem {

    public enum ControlType {
        OPEN_LOOP(), POSITION_CLOSED_LOOP(), VISION_CLOSED_LOOP(), VELOCITY_OPEN_LOOP;
    }

    private ControlType mControlType = ControlType.OPEN_LOOP;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private PIDConstants mPIDConstants = new PIDConstants();
    private static Turret instance;



    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret(new TalonSRX(15));
        }
        return instance;
    }
    
    protected Turret(TalonSRX servoMotor) {
        super(servoMotor);
        mServoMotor.setInverted(false);
        mServoMotor.config_kF(0,2.9416666666666667);
        mServoMotor.configMotionCruiseVelocity(210);
        mServoMotor.configMotionAcceleration(210);
        mServoMotor.config_kP(0,10);
        mServoMotor.config_kD(0,1);
        mServoMotor.config_kI(0,0.023); //4062 ticks to rev
        mServoMotor.setSensorPhase(true);

        mPIDConstants.kP = 0.037037037037037*0.75; 

        //mTurretAngleLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        //mTurretAngleRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
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
    public synchronized void setDesiredAngle(double angle) {
        mPeriodicIO.mDesiredAngle =  (angle * Constants.TURRET_TICK_TO_ANGLE);
    }

    /**
     *  sets the speed of the turret for open loop control
     * @param power percent of power from -1 to 1
     */
    public synchronized void setOpenloopPower(double power) {
        mPeriodicIO.mDesiredSpeed = power;
        mServoMotor.set(ControlMode.PercentOutput,power);
    }

    public synchronized double getAngle() {
        return mServoAngle;
    }

    /**
     * sets the control type of the turret
     * @param type type of control loop for the turret
     */
    public synchronized void setControlType(ControlType type) {
        mControlType = type;
    }

    /**
     * stops the turret
     */
    private synchronized void stop() {
        mPeriodicIO.mDesiredSpeed = 0;
    }

    /**
     * commands the TalonSRX to got to a demanded amount of ticks on the encoder
     */
    private void handleClosedLoop() {
        // Right now we just have position control
        // TODO Maybe add velocity control
        if(mControlType == ControlType.POSITION_CLOSED_LOOP) {
            setPos(mPeriodicIO.mDesiredAngle);
        } else if(mControlType == ControlType.VISION_CLOSED_LOOP) {
            //TODO maybe add velocity control
            mPIDConstants.currentError = mPeriodicIO.mTargettedAngle;
            double p_Power = -mPIDConstants.currentError*mPIDConstants.kP;
            double d_Power = -mPIDConstants.kD * (mPIDConstants.currentError-mPIDConstants.lastError);
            System.out.println(p_Power);
            setOpenloopPower(p_Power + d_Power);
        }
    }

    /*
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
        mServoAngle = (mServoMotor.getSelectedSensorPosition()/4092) * 360;
        mPeriodicIO.mCurrentSpeed = mServoMotor.getSelectedSensorVelocity();

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {

            @Override
            public void onStop(double timestamp) {
                stop();

            }

            @Override
            public void onStart(double timestamp) {
                mControlType = ControlType.OPEN_LOOP;
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Turret.this) {
                    if (mControlType != ControlType.POSITION_CLOSED_LOOP && mControlType != ControlType.VISION_CLOSED_LOOP) {
                        handleOpenLoop();
                    } else {
                        handleClosedLoop();
                    }

                    mPeriodicIO.mTargettedAngle = SmartDashboard.getNumber("x_offset", 0);
                    //System.out.println(mPeriodicIO.mTargettedAngle);
                }
            }
        });

    }

    private class PeriodicIO {
        public double mDesiredAngle = 0;
        public double mDesiredSpeed = 0;
        public double mCurrentSpeed = 0;
        public double mTargettedAngle = 0;
    }

    private class PIDConstants {
        public double kP = 0;
        public double kI = 0;
        public double kD = 0;
        public double currentError = 0;
        public double lastError = 0;
    }

}