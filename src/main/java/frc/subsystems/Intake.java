package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.StateSubsystem;
import frc.states.intake.IntakeState;
import frc.states.intake.LoweredState;
import frc.states.intake.StowedState;
import frc.utils.KingMathUtils;

public class Intake extends StateSubsystem<IntakeState> {

    private TalonSRX mIntakeAngle;
    private VictorSPX mIntake;

    private double mCurrentAngle = 0;

    public Intake(TalonSRX intakeAngle, VictorSPX intake) {
        mIntakeAngle = intakeAngle;
        mIntake = intake;

        mIntakeAngle.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        addState("Stowed", new StowedState());
        addState("Lowered", new LoweredState());

    }

    /**
     * interface to run intake at desired speed will not run if intake is stowed
     */
    public void runIntake() {
        mIntake.set(ControlMode.PercentOutput, mCurrentState.intakeSpeed());
    }

    /**
     * stops intake regardless of state
     */
    public void stopIntake() {
        mIntake.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void updateSmartDashboard() {
        Shuffleboard.getTab("Intake").add("Current State", mCurrentState.name());
        Shuffleboard.getTab("Intake").add("Desired State", mDesiredState.name());

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub
        mCurrentAngle = mIntakeAngle.getSelectedSensorPosition();

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop() {

            @Override
            public void onStop(double timestamp) {
            }

            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoop(double timestamp) {
                // TODO Auto-generated method stub
                synchronized (this) {
                    update();
                }

            }
        });

    }

    @Override
    protected void update() {
        if (KingMathUtils.applyDeadband(mCurrentAngle, 50, 50, mDesiredState.intakeAngle()) != mDesiredState
                .intakeAngle()) {
            mIntakeAngle.set(ControlMode.MotionMagic, mDesiredState.intakeAngle());
        } else if (KingMathUtils.applyDeadband(mCurrentAngle, 50, 50, mDesiredState.intakeAngle()) == mDesiredState
                .intakeAngle()) {
            mCurrentState = mDesiredState;
        }

    }

}