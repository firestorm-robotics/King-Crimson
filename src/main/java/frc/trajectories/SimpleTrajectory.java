package frc.trajectories;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.util.Units;

public class SimpleTrajectory {
    private Trajectory mTraj;
    public void generateTrajectory() {

        var start = new Pose2d(Units.feetToMeters(0),Units.feetToMeters(0),Rotation2d.fromDegrees(0));
        var end   = new Pose2d(Units.feetToMeters(24),Units.feetToMeters(6),Rotation2d.fromDegrees(0));
    
        var interiorWaypoints = new ArrayList<Pose2d>();
        interiorWaypoints.add(start);
        interiorWaypoints.add(new Pose2d(Units.feetToMeters(3),Units.feetToMeters(3),Rotation2d.fromDegrees(90)));
        interiorWaypoints.add(new Pose2d(Units.feetToMeters(6),Units.feetToMeters(6),Rotation2d.fromDegrees(0)));
        interiorWaypoints.add(end);
        CentripetalAccelerationConstraint constraint = new CentripetalAccelerationConstraint(2);
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(10), Units.feetToMeters(10)).addConstraint(constraint);
        config.setReversed(false);
        var trajectory = TrajectoryGenerator.generateTrajectory(
            interiorWaypoints,
            config);

        mTraj = trajectory;
      }
    
      public Trajectory getTrajectory() {
          return mTraj;
      }
    }
