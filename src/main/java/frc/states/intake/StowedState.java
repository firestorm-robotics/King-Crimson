package frc.states.intake;

public class StowedState extends IntakeState {

    @Override
    public double intakeAngle() {
        return 0;
    }

    @Override
    public double intakeSpeed() {
        return 0;
    }

    @Override
    public String name() {
        return "Defense";
    }

}