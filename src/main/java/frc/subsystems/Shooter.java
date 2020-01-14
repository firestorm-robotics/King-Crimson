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
    private ShooterStates mDesiredState;

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

        mLeftPID  = mShooterLeft.getPIDController();
        mRightPID = mShooterRight.getPIDController();

        mLeftEncoder  = mShooterLeft.getEncoder();
        mRightEncoder = mShooterRight.getEncoder();

        mShooterRight.setInverted(true);
    }

    public void set(double percent) {
        mLeftPID.setReference(percent, ControlType.kDutyCycle);
        mRightPID.setReference(percent, ControlType.kDutyCycle);
    }

    public void setIO(double demandedPercent, double demandedRPM) {
        mPeriodicIO.mDemandedPercent = demandedPercent;
        mPeriodicIO.mDemandedRPM     = demandedRPM;
    }
    

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub
        SmartDashboard.putNumber("Shooter Left RPM", mPeriodicIO.mCurrentLeftSpd);
        SmartDashboard.putNumber("Shooter Right RPM", mPeriodicIO.mCurrentRightSpd);

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
                set(mPeriodicIO.mDemandedPercent);
                
            }
        
            @Override
            public void onLoop(double timestamp) {
                // TODO Auto-generated method stub
                
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