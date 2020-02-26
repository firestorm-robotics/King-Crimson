/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// HFOV for logitech webcam 70.42
// VFOV for logitech webacm 41.94
package frc.robot;

import java.util.Arrays;

import frc.subsystems.GoalFlow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import firelib.looper.Looper;
import frc.controls.ControlBoard;
import frc.subsystems.ControlPanel;
import frc.subsystems.Indexer;
import frc.subsystems.Intake;
import frc.subsystems.Turret;
import frc.subsystems.Shooter;
import frc.subsystems.Shooter.ShooterStates;
import frc.subsystems.Goal;

import frc.subsystems.SuperstructureAngle;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
import frc.subsystems.drivetrain.Drivetrain;
import frc.subsystems.drivetrain.Drivetrain.ControlType;
import frc.trajectories.SimpleTrajectory;
import frc.utils.KingMathUtils;
import frc.utils.ToggleBoolean;

public class Robot extends TimedRobot {
  private ControlPanel mControlPanel;
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private ControlBoard mControls = ControlBoard.getInstance();
  private Drivetrain mDrivetrain = Drivetrain.getInstance();
  private Indexer mIndexer = Indexer.getInstance();
  private Shooter mShooter = Shooter.getInstance();
 
  private Turret mTurret = Turret.getInstance();
  private Intake mIntake = Intake.getInstance();
  private SuperstructureAngle mSuperstructureAngle = SuperstructureAngle.getInstance();
  private SimpleTrajectory mSimpleTrajectory = new SimpleTrajectory();
  private final SubsystemManager mSubsystemManager = new SubsystemManager(
      Arrays.asList(mDrivetrain, mTurret, mShooter, mIndexer, mIntake, mSuperstructureAngle));
  private ToggleBoolean mToggleIntake = new ToggleBoolean(false, true);
  private boolean mLooperEnabled = false;

  @Override
  public void robotInit() {
    mSubsystemManager.registerEnabledLoops(mEnabledLooper);
    mSubsystemManager.registerDisabledLoops(mDisabledLooper);
    mSimpleTrajectory.generateTrajectory();

  }

  @Override
  public void disabledPeriodic() {
    mEnabledLooper.stop();
    mDisabledLooper.start();
    mLooperEnabled = false;
  }

  @Override
  public void autonomousInit() {
    if (!mLooperEnabled) {
      mDisabledLooper.stop();
      mEnabledLooper.start();
      mDrivetrain.setTrajectory(mSimpleTrajectory.getTrajectory());
      mTurret.resetEncoder();
      mIntake.resetEncoder();
      mDrivetrain.resetGyro();
      mDrivetrain.resetEncoders();
      mLooperEnabled = true;
    }
  }

  @Override
  public void autonomousPeriodic() {
    // TODO add logic
  }

  @Override
  public void teleopInit() {
    if (!mLooperEnabled) {
      mDisabledLooper.stop();
      mEnabledLooper.start();
      mDrivetrain.setTrajectory(mSimpleTrajectory.getTrajectory());
      mTurret.resetEncoder();
      mIntake.resetEncoder();
      mDrivetrain.resetGyro();
      mDrivetrain.resetEncoders();
      mLooperEnabled = true;
    }

  }
  public void createControlPanel(){
    mControlPanel=new ControlPanel();
  }
  public void controlPanelIntRotations(){
    mControlPanel.setGoal(Goal.INTROTATIONS);
  }
  public void controlPanelToColor(){
    mControlPanel.setGoal(Goal.TOCOLOR);
  }
  public void controlPanelRun(){
    mControlPanel.runGoal();
  }

  @Override
  public void teleopPeriodic() {
    controlPanelRun(); //TODO: (To Andrew Cua): Change the position of this if necessary. It won't break anything, the control panel is self contained.
    double throttle = KingMathUtils.clampD(mControls.getYThrottle(), 0.075);
    double rot = KingMathUtils.clampD(mControls.getXThrottle(), 0.075);

    boolean wantsShot = mControls.getShoot();

    boolean wanstToTurnTurretLeft = mControls.getTurnTurretLeft();
    boolean wantsToTurnTurretRight = mControls.getTurnTurretRight();

    boolean wantsToTrackTarget = mControls.enableVisionTracking();
    boolean runIntake = mControls.runIntake();
    boolean raiseSuperstructure = mControls.raiseAngle();
    boolean lowerSuperstructure = mControls.lowerAngle();
    mToggleIntake.update(mControls.toggleIntake());
    SmartDashboard.putNumber("X", mControls.getBoardX());

    if (wantsShot) {
      mShooter.setIO(-1.2 * throttle, /*13750 * -throttle*/ 2500*3);
      mShooter.setState(Shooter.ShooterStates.OPEN_LOOP);
      if (mShooter.atSpeed()) {
        mIndexer.setIO(0.75);
      } else {
        mIndexer.setIO(0);
      }
    } else {
      mShooter.setIO(0, 0);
      mShooter.setState(Shooter.ShooterStates.IDLE);
      mIndexer.setIO(-mControls.getRightY());
    }

    if (wanstToTurnTurretLeft) {
      mTurret.setOpenloopPower(-1);
      mTurret.setControlType(Turret.ControlType.OPEN_LOOP);
    } else if (wantsToTurnTurretRight) {
      mTurret.setOpenloopPower(1);
      mTurret.setControlType(Turret.ControlType.OPEN_LOOP);
    } else {
      mTurret.setOpenloopPower(0);
    }

    if (mToggleIntake.getCurrentState()) {
      mIntake.lowerIntake();
    } else {
      mIntake.stowIntake();
    }

    if (runIntake) {
      mIntake.runIntake();
      mIndexer.setIO(1);
    } else {
      mIntake.stopIntake();
    }

    if (wantsToTrackTarget) {
      mTurret.setControlType(Turret.ControlType.VISION_CLOSED_LOOP);
    }

    if (raiseSuperstructure) {
      mSuperstructureAngle.setIO(0.5);
    } else if (lowerSuperstructure) {
      mSuperstructureAngle.setIO(-0.5);
    } else {
      mSuperstructureAngle.setIO(0);
    }

    if (!wantsShot) {
      mDrivetrain.setIO(KingMathUtils.logit(-throttle * 0.7), -KingMathUtils.turnExp(rot * 0.5));

    } else {
      mDrivetrain.setIO(0, 0);
    }

    SmartDashboard.putBoolean("Toggle", mToggleIntake.getCurrentState());
    SmartDashboard.putBoolean("LooperRunning",isEnabled());
  }

  @Override
  public void testInit() {
    // TODO add logic
  }

  @Override
  public void testPeriodic() {
    // TODO add logic
  }

}
