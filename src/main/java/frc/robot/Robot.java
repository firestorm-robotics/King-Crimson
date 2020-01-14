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
import frc.subsystems.Shooter;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
import frc.subsystems.drivetrain.Drivetrain;
import frc.utils.KingMathUtils;

public class Robot extends TimedRobot {
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private ControlBoard mControls = ControlBoard.getInstance();
  private Drivetrain mDrivetrain = Drivetrain.getInstance();
  private Shooter mShooter = Shooter.getInstance();
  private final SubsystemManager mSubsystemManager = new SubsystemManager(Arrays.asList(mDrivetrain,mShooter));

  @Override
  public void robotInit() {
    mSubsystemManager.registerEnabledLoops(mEnabledLooper);
    mSubsystemManager.registerDisabledLoops(mDisabledLooper);
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
    mEnabledLooper.start();
  }

  @Override
  public void teleopPeriodic() {
    double throttle = KingMathUtils.clampD(mControls.getYThrottle(),0.075);
    double rot = KingMathUtils.clampD(mControls.getXThrottle(),0.075);
    boolean wantsShoot = mControls.getShoot();

    if(wantsShoot) {
      mShooter.set(0.90);
    } else {
      mShooter.set(0);
    }


    mDrivetrain.setPeriodicIO(-KingMathUtils.logit(-throttle), -KingMathUtils.turnExp(-rot));
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
