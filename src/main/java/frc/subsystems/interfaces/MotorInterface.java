
package frc.subsystems.interfaces;

public interface MotorInterface {

    // Constant Declarations
    public double mDesiredAngle = 0;
    public double mDesiredSpeed = 0;
    public double mCurrentSpeed = 0;

    // Method Signatures

    // DEPRECATED: It doesn't make any sense to use timed alarms. Just pass a fail value to the main system: public void alarmSet();

    // DEPRECATED: See reason above: public int alarmAction();

    // DEPRECATED: The motor doesn't need to know this, and we should avoid redundencies: public void onStart(double timestamp);

    // DEPRECATED: See reason above: public void onStop(double timestamp);

    public double getCurrentSpeed();
    
    public void setSpeed(double speed);
    
    // DEPRECATED: We should just use pass a value to the system using this subsystem: public void updateSmartDashboard();

    // DEPRECATED: You can't run methods in the top level of a class, also we don't know what it is: pollTelemetry()
}