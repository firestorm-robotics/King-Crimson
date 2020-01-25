package frc.states.intake;
import firelib.statecontrol.*;
public abstract class IntakeState extends State {

    public abstract double intakeAngle();

    public abstract double intakeSpeed();
}