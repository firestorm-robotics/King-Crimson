package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
public class MotorBase {
    private TalonSRX  mMasterLeft;
    private TalonSRX  mMasterRight;
    private VictorSPX mSlaveLeft;
    private VictorSPX mSlaveRight;

    public MotorBase(TalonSRX masterLeft, TalonSRX masterRight, VictorSPX slaveLeft, VictorSPX slaveRight) {
        mMasterLeft  = masterLeft;
        mMasterRight = masterRight;
        mSlaveLeft   = slaveLeft;
        mSlaveRight  = slaveRight;
        
        mMasterLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        mMasterRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        mMasterLeft.setInverted(true);
        mSlaveLeft.setInverted(true);

        mSlaveLeft.follow(mMasterLeft);
        mSlaveRight.follow(mMasterRight);
    }

    public void setVelocity(double leftVel, double rightVel) {
        mMasterLeft.set(ControlMode.PercentOutput,leftVel);
        mMasterRight.set(ControlMode.PercentOutput,rightVel);
    }

    public int getLeftVelocity() {
        return mMasterLeft.getSelectedSensorVelocity();
    }

    public int getRightVelocity() {
        return mMasterRight.getSelectedSensorVelocity();
    }
}