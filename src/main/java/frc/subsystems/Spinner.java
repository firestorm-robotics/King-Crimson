package frc.subsystems;

import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;

public class Spinner implements ISubsystem {

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop(){
        
            @Override
            public void onStop(double timestamp) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void onLoop(double timestamp) {
                // TODO Auto-generated method stub
                
            }
        });

    }
    
}