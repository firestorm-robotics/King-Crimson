package frc.subsystems.drivetrain;

public class DriveSignal {
    private double mLeftSpeed;
    private double mRightSpeed;

    public DriveSignal(double leftSpeed, double rightSpeed) {
        mLeftSpeed = leftSpeed;
        mRightSpeed = rightSpeed;
    }

    public double getLeftSpeed() {
        return mLeftSpeed;
    }

    public double getRightSpeed() {
        return mRightSpeed;
    }
}