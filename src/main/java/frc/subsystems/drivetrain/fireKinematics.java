package frc.subsystems.drivetrain;

public class fireKinematics {
    private final double mTrackWidth;
    private final double mMaxOmega;
    private final double mMaxVel;

    public fireKinematics(double trackWidth, double maxVel) {
        mTrackWidth = trackWidth;
        mMaxVel = maxVel;
        mMaxOmega = (mMaxVel * 2) / mTrackWidth;

    }

    public DriveSignal toWheelSpeeds(double vel, double omega) {
        vel = vel * mMaxVel;
        omega = omega * mMaxOmega;
        DriveSignal signal = new DriveSignal((vel - ((mTrackWidth / 2) * omega)) / mMaxVel,
                ((vel + ((mTrackWidth / 2) * omega)) / mMaxVel));

        return signal;
    }

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