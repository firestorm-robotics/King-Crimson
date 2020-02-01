package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class MotorBase {
    private TalonFX mMasterLeft;
    private TalonFX mMasterRight;
    private TalonFX mSlaveLeft;
    private TalonFX mSlaveRight;

    public MotorBase(TalonFX masterLeft, TalonFX masterRight, TalonFX slaveLeft, TalonFX slaveRight) {
        mMasterLeft = masterLeft;
        mMasterRight = masterRight;
        mSlaveLeft = slaveLeft;
        mSlaveRight = slaveRight;

        mMasterLeft.setInverted(true);
        mSlaveLeft.setInverted(true);

        mSlaveLeft.follow(mMasterLeft);
        mSlaveRight.follow(mMasterRight);

        mMasterLeft.configVoltageCompSaturation(12);
        mMasterRight.configVoltageCompSaturation(12);

        mMasterLeft.setNeutralMode(NeutralMode.Brake);
        mMasterRight.setNeutralMode(NeutralMode.Brake);
    }

    public void setVelocity(double leftVel, double rightVel) {
        mMasterLeft.set(ControlMode.PercentOutput, leftVel);
        mMasterRight.set(ControlMode.PercentOutput, rightVel);
    }
}