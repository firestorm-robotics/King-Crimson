package frc.utils;

public abstract class UtilBoolean {
    protected boolean mCurrentState;
    protected boolean mLastState;
    protected boolean mPolarity;

    protected UtilBoolean(boolean startState, boolean polarity) {
        mCurrentState = startState;
        mPolarity = polarity;
        mLastState = polarity;
    }

    public abstract void update(boolean update);

    public boolean getCurrentState() {
        return mCurrentState;
    }
}