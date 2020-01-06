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
}