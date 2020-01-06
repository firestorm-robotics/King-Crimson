package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import firelib.looper.ILooper;
import firelib.subsystem.ISubsystem;

public class Drivetrain implements ISubsystem {

    private class PeriodicIO {
        public double mDemandedFwd  = 0;
        public double mDemandedRot  = 0;
        public double mDemandedSpd  = 0; 
        public double mDemandedCurv = 0;
    }
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    
    /**
     * ctor  -- DO NOT USE -- 
     * expect for unit testing
     */
    public Drivetrain() {
        
    }
    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub

    }
    
}