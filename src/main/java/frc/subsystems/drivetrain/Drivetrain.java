package frc.subsystems.drivetrain;

import java.util.ArrayList;
import java.util.Arrays;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.util.Units;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;
import frc.robot.RobotMap;

public class Drivetrain implements ISubsystem {

    public enum ControlType {
        OPEN_LOOP, POSITION_CLOSED_LOOP, TRAJECTORY_FOLLOWING, LONG_SQUAD;
    }

    private static Drivetrain instance;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private MotorBase mMotorBase;
    private fireKinematics kinematics = new fireKinematics(0.6096, 7.8);
    /**
     * variables for trajectory following
     */
    private DifferentialDriveKinematics mTrajKinematics = new DifferentialDriveKinematics(0.6096);
    private AHRS mGyro = new AHRS(Port.kMXP);
    private DifferentialDriveOdometry mOdometry = new DifferentialDriveOdometry(
            Rotation2d.fromDegrees(-mGyro.getYaw()));
    private RamseteController mController = new RamseteController();
    private Trajectory mTrajectory;
    private Timer mTimer = new Timer();
    private double mTimeStamp = 0;
    private double mTrajectoryTime;
    /**
     * variables for music playing
     */
    private Orchestra mOrchestra;
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
        ArrayList<TalonFX> list = new ArrayList<TalonFX>();
        mOrchestra = new Orchestra(Arrays.asList(masterLeft, masterRight, slaveLeft, slaveRight));
        mOrchestra.loadMusic("beeg11.chrp");

    }

    public synchronized void setIO(double demandedThrottle, double demandedRot) {
        mPeriodicIO.mDemandedThrottle = demandedThrottle;
        mPeriodicIO.mDemandedRot = demandedRot;
    }

    public synchronized void setTrajectory(Trajectory traj) {
        mTrajectory = traj;
    }

    public synchronized void setTimestamp(double time) {
        mTimeStamp = time;
    }

    public synchronized void setControlType(ControlType type) {
        mControlType = type;
    }

    /**
     * basic drive code for normal use
     */
    private synchronized void cartersianDrive() {
        DriveSignal signal = kinematics.toWheelSpeeds(mPeriodicIO.mDemandedThrottle, mPeriodicIO.mDemandedRot);
        mMotorBase.setVelocity(signal.getLeftSpeed() * Units.feetToMeters(25.6),
                signal.getRightSpeed() * Units.feetToMeters(25.6));
    }

    /**
     * drives the robot in openloop mode
     */
    private synchronized void handleOpenLoop() {
        cartersianDrive();
    }

    /**
     * drives the robot in some fashion of closed loop mode depending on if its just
     * positon or trajectory
     */
    private synchronized void handleClosedLoop() {
        if (mControlType == ControlType.TRAJECTORY_FOLLOWING) {
            mTrajectoryTime = Timer.getFPGATimestamp() - mTimeStamp;
            mTimer.start();
            Trajectory.State goal = mTrajectory.sample(mTrajectoryTime);
            ChassisSpeeds speeds = mController.calculate(mOdometry.getPoseMeters(), goal);
            DifferentialDriveWheelSpeeds diffSpeeds = mTrajKinematics.toWheelSpeeds(speeds);
            mMotorBase.setVelocity(diffSpeeds.leftMetersPerSecond, diffSpeeds.rightMetersPerSecond);
            if (mTrajectoryTime + mTimeStamp >= mTrajectory.getTotalTimeSeconds() + mTimeStamp) {
                mControlType = ControlType.OPEN_LOOP;
            }
        }
        if (mControlType == ControlType.LONG_SQUAD) {
            if (!mOrchestra.isPlaying()) {
                mOrchestra.play();
            }
        }
    }

    public void updateOdometry(double leftPos, double rightPos, double angle) {
        var yaw = Rotation2d.fromDegrees(angle);
        var rawLeftRotations = (leftPos / 2048) / 6.54545788;
        var rawRightRotations = rightPos / 2048 / 6.54545788;

        var leftDistanceMeters = rawLeftRotations * (Math.PI * 2 * (Units.inchesToMeters(3)));
        var rightDistanceMeters = rawRightRotations * (Math.PI * 2 * (Units.inchesToMeters(3)));
        mOdometry.update(yaw, leftDistanceMeters, rightDistanceMeters);

    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("Drivetrain State", mControlType == ControlType.LONG_SQUAD);
        SmartDashboard.putNumber("Left Drivetrain Speed", mPeriodicIO.mLeftVel);
        SmartDashboard.putNumber("Right Drivetrain Speed", mPeriodicIO.mRightVel);
    }

    @Override
    public void pollTelemetry() {
        mPeriodicIO.mLeftVel = mMotorBase.getLeftVelocity();
        mPeriodicIO.mRightVel = mMotorBase.getRightVeloicty();

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
                synchronized (Drivetrain.this) {
                    if (mControlType != ControlType.POSITION_CLOSED_LOOP
                            && mControlType != ControlType.TRAJECTORY_FOLLOWING
                            && mControlType != ControlType.LONG_SQUAD) {
                        handleOpenLoop();
                    } else {
                        handleClosedLoop();
                    }

                    updateOdometry(mMotorBase.getLeftPos(), mMotorBase.getRightPos(), -mGyro.getYaw());
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