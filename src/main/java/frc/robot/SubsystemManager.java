package frc.robot;

import firelib.subsystem.ISubsystem;

import java.util.ArrayList;
import java.util.List;

import firelib.looper.*;
public class SubsystemManager implements ILooper {

    private final List<ISubsystem> mAllSubsystems;
    private List<Loop> mLoops = new ArrayList<>();

    public SubsystemManager(List<ISubsystem> allSubsystems) {
        mAllSubsystems = allSubsystems;
    }

    public void outputToSmartDashboard() {
        mAllSubsystems.forEach((s) -> s.updateSmartDashboard());
    }

    private class EnabledLoop implements Loop {

        @Override
        public void onStart(double timestamp) {
            for (Loop l : mLoops) {
                l.onStart(timestamp);
            }
        }

        @Override
        public void onLoop(double timestamp) {
            for (ISubsystem s : mAllSubsystems) {
                s.pollTelemetry();
                outputToSmartDashboard();
            }
            for (Loop l : mLoops) {
                l.onLoop(timestamp);
            }
        }

        @Override
        public void onStop(double timestamp) {
            for (Loop l : mLoops) {
                l.onStop(timestamp);
            }
        }
    }

    private class DisabledLoop implements Loop {

        @Override
        public void onStart(double timestamp) {

        }

        @Override
        public void onLoop(double timestamp) {
            for (ISubsystem s : mAllSubsystems) {
                s.pollTelemetry();
                outputToSmartDashboard();
            }
        }

        @Override
        public void onStop(double timestamp) {

        }
    }

    public void registerEnabledLoops(Looper enabledLooper) {
        mAllSubsystems.forEach((s) -> s.registerEnabledLoops(this));
        enabledLooper.register(new EnabledLoop());
    }

    public void registerDisabledLoops(Looper disabledLooper) {
        disabledLooper.register(new DisabledLoop());
    }

    @Override
    public void register(Loop loop) {
        mLoops.add(loop);
    }
}