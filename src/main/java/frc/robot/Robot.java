/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.Looper;
import frc.controls.ControlBoard;
import frc.subsystems.Turret;
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

public class Robot extends TimedRobot {
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private ControlBoard mControls = ControlBoard.getInstance();
  private Drivetrain mDrivetrain = Drivetrain.getInstance();
  //private Shooter mShooter = Shooter.getInstance();
  private Turret mTurret = Turret.getInstance();
  private SimpleTrajectory mSimpleTrajectory = new SimpleTrajectory();
  private final SubsystemManager mSubsystemManager = new SubsystemManager(
      Arrays.asList(mDrivetrain,mTurret));

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
  }

  @Override
  public void autonomousInit() {
    // TODO add logic
  }

  @Override
  public void autonomousPeriodic() {
    // TODO add logic
  }

  @Override
  public void teleopInit() {
    mDisabledLooper.stop();
    mEnabledLooper.start();
    mDrivetrain.setTrajectory(mSimpleTrajectory.getTrajectory());
    mTurret.resetEncoder();

  }

  @Override
  public void teleopPeriodic() {
    double throttle = KingMathUtils.clampD(mControls.getYThrottle(), 0.075);
    double rot = KingMathUtils.clampD(mControls.getXThrottle(), 0.075);

    boolean wantsShot = mControls.getShoot();

    boolean wanstToTurnTurretLeft = mControls.getTurnTurretLeft();
    boolean wantsToTurnTurretRight = mControls.getTurnTurretRight();

    boolean wantsToTurnTurret = mControls.getEnableTrajectory();
    /*if (wantsShot) {
      mShooter.setIO(-1*throttle, 11000*-throttle);
      mShooter.setState(ShooterStates.SPINNING_UP);
    } else {
      mShooter.setIO(0, 0);
      mShooter.setState(ShooterStates.IDLE);
    }*/

    if (wanstToTurnTurretLeft) {
      mTurret.setOpenloopPower(-1);
      mTurret.setControlType(Turret.ControlType.OPEN_LOOP);
    } else if (wantsToTurnTurretRight) {
      mTurret.setOpenloopPower(1);
      mTurret.setControlType(Turret.ControlType.OPEN_LOOP);
    } else {
      mTurret.setOpenloopPower(0);
    }



    if(wantsToTurnTurret) {
      //mDrivetrain.setTimestamp(Timer.getFPGATimestamp());
      //mDrivetrain.setControlType(Drivetrain.ControlType.TRAJECTORY_FOLLOWING);
      /*if(mControls.getPOV() != -1) {
      mTurret.setDesiredAngle(mControls.getPOV());
      mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);*/
      mTurret.setDesiredAngle(180);
      mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);
      } else {
        mTurret.setDesiredAngle(0);
    }

    if (!wantsShot) {
     mDrivetrain.setIO(KingMathUtils.logit(-throttle*0.7), -KingMathUtils.turnExp(rot*0.5));

    } else {
      mDrivetrain.setIO(0,0);
    }

    if(mControls.getMusic()) {
      mDrivetrain.setControlType(ControlType.LONG_SQUAD);
    }
    SmartDashboard.putNumber("Pov", mControls.getPOV()*Constants.TURRET_TICK_TO_ANGLE);
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
