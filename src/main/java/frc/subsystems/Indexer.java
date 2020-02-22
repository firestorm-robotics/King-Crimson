package frc.subsystems;

import firelib.looper.ILooper;

import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Indexer implements ISubsystem {
    public enum ControlType {
        OPEN_LOOP,
        POSITION_CLOSED_LOOP,
        VELOCITY_CLOSED_LOOP
    }
    public enum Mode {
        INTAKEING,
        SHOOTING;
    }
    private TalonSRX mRightBelt;
    private TalonSRX mLeftBelt;
    private VictorSPX mPreBelt;
    private ControlType mControlType = ControlType.OPEN_LOOP;
    private Mode mMode = Mode.INTAKEING;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private static Indexer instance;


    /**
     * singleton method for use throughout robot
     * @return Indexer instance
     */
    public static Indexer getInstance() {
        if(instance == null) {
            instance = new Indexer(new TalonSRX(RobotMap.INDEX_LEFT), new VictorSPX(RobotMap.PREBELT));
        }

        return instance;
    }

    /**
     * ctor - not for use unless for static builders or unit testing
     * @param rightBelt the motor controlling the right belt
     * @param leftBelt the motor controlling the left belt
     */
    public Indexer(TalonSRX leftBelt, VictorSPX preBelt) {
        mPreBelt = preBelt;
        mLeftBelt = leftBelt;

        mLeftBelt.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    }

    public synchronized void setPos(int ticks) {
        mLeftBelt.set(ControlMode.MotionMagic,ticks);
    }    
    
    public synchronized void setIO(double power) {
        mPeriodicIO.power = power;
    }

    public synchronized void incrementPos() {
        //TODO finish logic for belt incrementation 
        mPeriodicIO.currentPosition += 0; //TODO figure out how many ticks in diameter of ball
        setPos(mPeriodicIO.currentPosition);
    }
    /**
     * moves motors on the belts to a certain position or velocity
     */
    public synchronized void handleClosedLoop() {
        //TODO add closed loop control
    }

    /**
     * sets the motors to a percentage of voltage
     */
    public synchronized void handleOpenLoop() {
        mLeftBelt.set(ControlMode.PercentOutput,mPeriodicIO.power);
        mPreBelt.set(ControlMode.PercentOutput,mPeriodicIO.power);

    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Indexer/LeftSpeed",mLeftBelt.getSelectedSensorVelocity());

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
               synchronized(Indexer.this) {
                   handleOpenLoop();
               }
           }
       });

    }


    private class PeriodicIO {
        public double power = 0;
        public int currentPosition = 0;

    }

}