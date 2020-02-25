package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import firelib.looper.ILooper;
import firelib.looper.Loop;
import firelib.subsystem.ISubsystem;
import frc.utils.KingMathUtils;

public class Superstructure implements ISubsystem {
    public enum SuperstructureTarget {
        DEFENSE("Defense"), SHOOTING("Shooting"), INTAKING("Intaking");

        private String mName;

        SuperstructureTarget(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }
    }

    public enum TurretHomePosition {
        FRONT(0), BACK(180);

        private int mAngle;

        TurretHomePosition(int angle) {
            mAngle = angle;
        }

        public int getAngle() {
            return mAngle;
        }
    }

    private Turret mTurret = Turret.getInstance();
    private Intake mIntake = Intake.getInstance();
    private Indexer mIndexer = Indexer.getInstance();
    private Shooter mShooter = Shooter.getInstance();

    private SuperstructureTarget mCurrentTarget = SuperstructureTarget.DEFENSE;
    private SuperstructureTarget mDesiredTarget = SuperstructureTarget.DEFENSE;

    private TurretHomePosition mHome = TurretHomePosition.FRONT;

    private UserControlValues mVals = new UserControlValues();

    private static Superstructure instance;

    public static Superstructure getInstance() {
        if (instance == null) {
            instance = new Superstructure();
        }
        return instance;
    }

    public void handleStateChange() {
        /**
         * command the robot initial state settings
         */
        switch (mDesiredTarget) {
        case DEFENSE:
            mShooter.setState(Shooter.ShooterStates.IDLE);
            mTurret.setDesiredAngle(mHome.getAngle());
            mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);
            mIntake.stowIntake();
            mIntake.stopIntake();
            mIndexer.setIO(0);
            break;
        case INTAKING:
            mShooter.setState(Shooter.ShooterStates.IDLE);
            mTurret.setDesiredAngle(mHome.getAngle());
            mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);
            mIndexer.setIO(0);
            break;
        case SHOOTING:
            mShooter.setState(Shooter.ShooterStates.SPINNING_UP);
            mIntake.stowIntake();
            mIntake.stopIntake();
            mTurret.setDesiredAngle(mHome.getAngle());
            mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);
            break;

        }

        /**
         * if condiditons are met set current state to desired state
         */

        if (mDesiredTarget == SuperstructureTarget.DEFENSE
                && KingMathUtils.applyDeadband(mTurret.getAngle(), 50, 50, mHome.getAngle()) == mHome.getAngle()
                && mIntake.getState().equals("Defense")) {

            mCurrentTarget = mDesiredTarget;
        }

        if (mDesiredTarget == SuperstructureTarget.INTAKING
                && KingMathUtils.applyDeadband(mTurret.getAngle(), 50, 50, mHome.getAngle()) == mHome.getAngle()) {

            mIntake.lowerIntake();
            mCurrentTarget = mDesiredTarget;

        }

        if (mDesiredTarget == SuperstructureTarget.SHOOTING
                && KingMathUtils.applyDeadband(mTurret.getAngle(), 50, 50, mHome.getAngle()) == mHome.getAngle()
                && mIntake.getState().equals("Defense")) {

            mCurrentTarget = mDesiredTarget;

        }
    }

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub
        SmartDashboard.putString("Superstructure/currentTarget", mCurrentTarget.getName());
        SmartDashboard.putString("Superstructure/desiredTarget", mDesiredTarget.getName());

    }

    @Override
    public void pollTelemetry() {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        // TODO Auto-generated method stub
        enabledLooper.register(new Loop() {

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
                synchronized (Superstructure.this) {
                    if (mCurrentTarget != mDesiredTarget) {
                        handleStateChange();
                    } else if (mCurrentTarget == mDesiredTarget) {

                        /**
                         * target angnostic logic here
                         */
                            if (mVals.flipHomePosition) {
                                if(mHome == TurretHomePosition.FRONT) {
                                    mHome = TurretHomePosition.BACK;
                                } else if (mHome == TurretHomePosition.BACK)
                                {
                                    mHome = TurretHomePosition.FRONT;
                                }
                                mVals.flipHomePosition = false;
                            }


                        /**
                         * shooting logic
                         */
                        if (mCurrentTarget == SuperstructureTarget.SHOOTING) {
                            mTurret.setDesiredAngle(mVals.turretAngle);
                            mShooter.setIO(0, mVals.shooterRPM);
                            mShooter.setState(Shooter.ShooterStates.SPINNING_UP);
                            // TODO add superstructure angle logic
                        }

                        /**
                         * intake logic
                         */
                        if (mCurrentTarget == SuperstructureTarget.INTAKING) {
                            if (mVals.runIntake) {
                                mIntake.runIntake();
                            } else {
                                mIntake.stopIntake();
                            }

                            //TODO make sure that superstructure angle is 0
                        }

                        /**
                         * defense logic
                         */
                        if(mCurrentTarget == SuperstructureTarget.DEFENSE) {
                            mIntake.stowIntake();
                            mIntake.stopIntake();
                            mTurret.setDesiredAngle(mHome.mAngle);
                            mTurret.setControlType(Turret.ControlType.POSITION_CLOSED_LOOP);
                            mShooter.stop();

                            //TODO make sure that superstructure angle is 0
                        }
                    }

                }

            }
        });

    }

    private class UserControlValues {
        public boolean flipHomePosition = false;
        public boolean runIntake = false;
        public double turretAngle = 0;
        public double superstructureAngle = 0;
        public double shooterRPM = 0;
    }

}