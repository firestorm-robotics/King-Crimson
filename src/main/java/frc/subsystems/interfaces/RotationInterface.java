
package frc.subsystems.interfaces;

public interface RotationInterface {

    // Constant Declarations
    public double mDesiredAngle = 0;
    public double mDesiredSpeed = 0;
    public double mCurrentSpeed = 0;

    // Method Signatures

    public void alarmSet();

    public int alarmAction();

    public void onStart(double timestamp);

    public void onStop(double timestamp);

    public double getCurrentSpeed();
    
    public void setSpeed(double speed);
    
    public void updateSmartDashboard();

    //pollTelemetry()

}