package firelib.subsystem;

import firelib.looper.ILooper;

public interface ISubsystem {
    public void updateSmartDashboard();

    public void pollTelemetry();

    public void registerEnabledLoops(ILooper enabledLooper);
}