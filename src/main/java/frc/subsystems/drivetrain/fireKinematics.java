package frc.subsystems.drivetrain;

public class FireKinematics {
    private final double mTrackWidth;
    private final double mMaxOmega;
    private final double mMaxVel;

    /**
     * 
     * @param trackWidth the center-to-center distance between wheels 
     * @param maxVel
     */
    public FireKinematics(double trackWidth, double maxVel) {
        mTrackWidth = trackWidth;
        mMaxVel = maxVel;
        mMaxOmega = (mMaxVel * 2) / mTrackWidth;

    }

    /**
     * turns demanded velocity and angular velocity into left and right wheel speeds
     * @param vel translation velocity of the robot
     * @param omega rotational velocity of the robot
     */
    public DriveSignal toWheelSpeeds(double vel, double omega) {
        vel = vel * mMaxVel;
        omega = omega * mMaxOmega;
        DriveSignal signal = new DriveSignal((vel - ((mTrackWidth / 2) * omega)) / mMaxVel,
                ((vel + ((mTrackWidth / 2) * omega)) / mMaxVel));

        return signal;
    }

    /**
     * broken dont use
     * @param vel
     * @param curv
     * @return
     */
    public DriveSignal toCurveWheelSpeeds(double vel, double curv) {

        double omega = vel * mMaxOmega;
        double radius;
        if (vel == 0 || curv == 0) {
            return toWheelSpeeds(vel, curv);
        } else {
            radius = Math.log(curv + 1);
            radius *= (curv / Math.abs(curv));
        }

        double leftSpeed = omega * (radius + (mTrackWidth / 2));
        double rightSpeed = omega * (radius - (mTrackWidth / 2));
        return new DriveSignal(leftSpeed / mMaxVel, rightSpeed / mMaxVel);
    }
}