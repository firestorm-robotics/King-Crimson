package frc.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.util.Units;

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

        mMasterLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        mMasterRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);


        mMasterLeft.config_kP(0, 0.13);
        mMasterRight.config_kP(0,0.13);
        
        mMasterLeft.config_kD(0, 0.09*10);
        mMasterRight.config_kD(0,0.09*10);

        mMasterLeft.config_kF(0, 0.044);
        mMasterRight.config_kF(0,0.044);
    }

    public void setPower(double leftVel, double rightVel) {
        mMasterLeft.set(ControlMode.PercentOutput, leftVel);
        mMasterRight.set(ControlMode.PercentOutput, rightVel);
    }

    public void setVelocity(double leftVel, double rightVel) {
        double rawLeftRotations = leftVel/(Math.PI*2*Units.inchesToMeters(3));
        double rawRightRotations = rightVel/(Math.PI*2*Units.inchesToMeters(3));
        double leftMotorTicks = (rawLeftRotations*6.54545788*2048)/10;
        double rightMotorTicks = (rawRightRotations*6.54545788*2048)/10;
        mMasterLeft.set(ControlMode.Velocity,leftMotorTicks);
        mMasterRight.set(ControlMode.Velocity,rightMotorTicks);
    }

    public int getRightVeloicty() {
        return mMasterRight.getSelectedSensorVelocity();
    }

    public int getLeftVelocity() {
        return mMasterLeft.getSelectedSensorVelocity();
    }

    public int getRightPos() {
        return mMasterRight.getSelectedSensorPosition();
    }
    public int getLeftPos() {
        return mMasterLeft.getSelectedSensorPosition();
    }
}