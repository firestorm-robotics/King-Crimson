package frc.subsystems.drivetrain;

public class Kinematics {
    public final double mTrackWidth;
    public Kinematics(double trackWidth ) {
        mTrackWidth = trackWidth;
    }


    public DriveSignal toWheelSpeeds(double vel, double omega) {
        DriveSignal signal = new DriveSignal(vel - mTrackWidth / 2 * omega, vel + mTrackWidth / 2 * omega);
        
        return signal;
    }

    public DriveSignal toCurveWheelSpeeds(double vel, double curv) {

        double theta  = vel;
        double radius;
        if(curv == 0) {
            return toWheelSpeeds(vel, curv);
        } else {
            radius = 1/curv;
        }

        double leftSpeed  = theta*(radius+(mTrackWidth/2));
        double rightSpeed = theta*(radius-(mTrackWidth/2));
        return new DriveSignal(leftSpeed, rightSpeed);
    }
}