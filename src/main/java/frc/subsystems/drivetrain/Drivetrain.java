package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;
import frc.robot.RobotMap;

public class Drivetrain implements ISubsystem {

    public enum ControlType{
        OPEN_LOOP, POSITION_CLOSED_LOOP, TRAJECTORY_FOLLOWING;
    }
    private static Drivetrain instance;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private MotorBase mMotorBase;
    private Kinematics kinematics = new Kinematics(0.6096, 7.8);
    private AHRS mGyro = new AHRS(Port.kMXP);
    private ControlType mControlType = ControlType.OPEN_LOOP;

    /**
     * singleton method for use throughout the robot
     * 
     * @return
     */
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain(new TalonFX(RobotMap.DRIVETRAIN_LEFT_MASTER),
                    new TalonFX(RobotMap.DRIVETRAIN_RIGHT_MASTER), new TalonFX(RobotMap.DRIVETRAIN_LEFT_SLAVE),
                    new TalonFX(RobotMap.DRIVETRAIN_RIGHT_SLAVE));
        }
        return instance;
    }

    /**
     * ctor -- DO NOT USE -- except for unit testing
     */
    public Drivetrain(TalonFX masterLeft, TalonFX masterRight, TalonFX slaveLeft, TalonFX slaveRight) {
        mMotorBase = new MotorBase(masterLeft, masterRight, slaveLeft, slaveRight);

    }


    public synchronized void setIO(double demandedThrottle, double demandedRot) {
        mPeriodicIO.mDemandedThrottle = demandedThrottle;
        mPeriodicIO.mDemandedRot = demandedRot;
    }
    /**
     * basic drive code for normal use
     */
    private synchronized void cartersianDrive() {
        DriveSignal signal = kinematics.toWheelSpeeds(mPeriodicIO.mDemandedThrottle, mPeriodicIO.mDemandedRot);
        mMotorBase.setVelocity(signal.getLeftSpeed(), signal.getRightSpeed());
    }

    /**
     * drives the robot in openloop mode 
     */
    private synchronized void handleOpenLoop() {
        cartersianDrive();
    }

    /**
     * drives the robot in some fashion of closed loop mode
     * depending on if its just positon or trajectory
     */
    private synchronized void handleClosedLoop() {
        //TODO add logic here
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Drivetrain Speed", mPeriodicIO.mDemandedThrottle);
        SmartDashboard.putNumber("Gyro",-mGyro.getYaw());
    }

    @Override
    public void pollTelemetry() {
        mPeriodicIO.mLeftVel = 0;
        mPeriodicIO.mRightVel = 0;

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop() {

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
                synchronized (Drivetrain.this) {
                    if(mControlType != ControlType.POSITION_CLOSED_LOOP || mControlType != ControlType.TRAJECTORY_FOLLOWING) {
                        handleOpenLoop();
                    } else {
                        handleClosedLoop();
                    }
                }

            }
        });

    }

    private class PeriodicIO {
        public double mDemandedThrottle = 0;
        public double mDemandedRot = 0;
        public int mLeftVel = 0;
        public int mRightVel = 0;
    }

}