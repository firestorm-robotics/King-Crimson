package frc.states.intake;

import firelib.statecontrol.State;

public abstract class IntakeState extends State {

    public abstract double intakeAngle();

    public abstract double intakeSpeed();
}