package frc.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;
import frc.robot.RobotMap;


public class Shooter implements ISubsystem {
    public enum ShooterStates {
        IDLE,
        SPINNING_UP,
        MAINTAIN_SPEED,
    }
    private PeriodicIO mPeriodicIO = new PeriodicIO();


    private ShooterStates mCurrentState = ShooterStates.IDLE;
    private ShooterStates mDesiredState = ShooterStates.IDLE;

    private CANSparkMax mShooterLeft;
    private CANSparkMax mShooterRight;
    private CANPIDController mLeftPID;
    private CANPIDController mRightPID;
    private CANEncoder mLeftEncoder;
    private CANEncoder mRightEncoder;

    private static Shooter instance;
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter(new CANSparkMax(RobotMap.SHOOTER_LEFT, MotorType.kBrushless), new CANSparkMax(RobotMap.SHOOTER_RIGHT, MotorType.kBrushless));
        }
        return instance;
    }
    public Shooter(CANSparkMax shooterLeft, CANSparkMax shooterRight) {
        mShooterLeft  = shooterLeft;
        mShooterRight = shooterRight;

        mShooterLeft.enableVoltageCompensation(12);
        mShooterRight.enableVoltageCompensation(12);

        mShooterLeft.setSmartCurrentLimit(40);
        mShooterRight.setSmartCurrentLimit(40);

        mLeftPID  = mShooterLeft.getPIDController();
        mRightPID = mShooterRight.getPIDController();

        mLeftEncoder  = mShooterLeft.getEncoder();
        mRightEncoder = mShooterRight.getEncoder();

        mLeftPID.setP(0.001);
        mRightPID.setP(0.001);
        mLeftPID.setFF(0.00020000D);
        mRightPID.setFF(0.0002000D);

        mShooterRight.setInverted(true);
    }

    public void setState(ShooterStates state) {
        mDesiredState = state;
    }

    /**
     * interface to set the motors to a certain percent
     * @param percent percent of voltage to set the motors to (-1,1)
     */
    private void set(double percent) {
        mLeftPID.setReference(percent, ControlType.kDutyCycle);
        mRightPID.setReference(percent, ControlType.kDutyCycle);
    }

    /**
     * interface to set the motors to a certain rpm
     * @param rpm rotations per minute
     */
    private void setRPM(double rpm) {
        mLeftPID.setReference(rpm, ControlType.kVelocity);
        mRightPID.setReference(rpm, ControlType.kVelocity);
    }

    public void stop() {
        mLeftPID.setReference(0, ControlType.kDutyCycle);
        mRightPID.setReference(0, ControlType.kDutyCycle);
    }

    /**
     * set the demanded inputs of the shooter
     * @param demandedPercent voltage percent to set to
     * @param demandedRPM Rotations Per Minute to set to
     */
    public void setIO(double demandedPercent, double demandedRPM) {
        mPeriodicIO.mDemandedPercent = demandedPercent;
        mPeriodicIO.mDemandedRPM     = demandedRPM;
    }
    

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub
        SmartDashboard.putNumber("Shooter Left RPM", mPeriodicIO.mCurrentLeftSpd);
        SmartDashboard.putNumber("Shooter Right RPM", mPeriodicIO.mCurrentRightSpd);
        SmartDashboard.putNumber("Shooter Demanded RPM", mPeriodicIO.mDemandedRPM);

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub
        mPeriodicIO.mCurrentLeftSpd  = mLeftEncoder.getVelocity();
        mPeriodicIO.mCurrentRightSpd = mRightEncoder.getVelocity();

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop(){
        
            @Override
            public void onStop(double timestamp) {
                // TODO Auto-generated method stub
                set(0);
                
            }
        
            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub
                
                
            }
        
            @Override
            public void onLoop(double timestamp) {
                
                switch(mDesiredState) {
                    case IDLE:
                        stop();
                        break;
                    case SPINNING_UP:
                        setRPM(mPeriodicIO.mDemandedRPM);
                        break;
                    case MAINTAIN_SPEED:
                        setRPM(mPeriodicIO.mDemandedRPM);
                        break;
                }

                if(mPeriodicIO.mCurrentLeftSpd == 0 && mPeriodicIO.mCurrentRightSpd == 0) {
                    mCurrentState = ShooterStates.IDLE;
                }

            }
        });

    }


    private class PeriodicIO {
        public double mDemandedPercent = 0;
        public double mDemandedRPM = 0;

        public double mCurrentLeftSpd = 0;
        public double mCurrentRightSpd = 0;
    }
    
}