/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.TimedRobot;
import firelib.looper.Looper;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private final SubsystemManager mSubsystemManager = new SubsystemManager(Arrays.asList());
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
 }

 @Override
 public void autonomousPeriodic() {
 }

 @Override
 public void teleopInit() {
   mEnabledLooper.start();
 }

 @Override
 public void teleopPeriodic() {
 }

 @Override
 public void testInit() {
 }

 @Override
 public void testPeriodic() {
 }

}
