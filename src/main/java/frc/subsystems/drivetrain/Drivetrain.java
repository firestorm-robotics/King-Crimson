package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;

public class Drivetrain implements ISubsystem {

    private class PeriodicIO {
        public double mDemandedFwd  = 0;
        public double mDemandedRot  = 0;
        public double mDemandedSpd  = 0; 
        public double mDemandedCurv = 0;
        public int    mLeftVel = 0;
        public int    mRightVel = 0;
    }
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private MotorBase mMotorBase;
    
    /**
     * ctor  -- DO NOT USE -- 
     * expect for unit testing
     */
    public Drivetrain(TalonSRX masterLeft, TalonSRX masterRight, VictorSPX slaveLeft, VictorSPX slaveRight) {
        mMotorBase = new MotorBase(masterLeft, masterRight, slaveLeft, slaveRight);
        
    }

    public void cartersianDrive() {
        double left = mPeriodicIO.mDemandedFwd + mPeriodicIO.mDemandedRot;
        double right = mPeriodicIO.mDemandedFwd - mPeriodicIO.mDemandedRot;
        mMotorBase.setVelocity(left, right);
    }

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub
        mPeriodicIO.mLeftVel  = mMotorBase.getLeftVelocity();
        mPeriodicIO.mRightVel = mMotorBase.getRightVelocity();

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop(){
        
            @Override
            public void onStop(double timestamp) {
                mMotorBase.setVelocity(0, 0);
                
            }
        
            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void onLoop(double timestamp) {
                // TODO Auto-generated method stub
                cartersianDrive();

                
            }
        });

    }
    
}