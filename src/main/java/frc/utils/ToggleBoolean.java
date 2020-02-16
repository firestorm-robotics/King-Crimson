package frc.utils;

public class ToggleBoolean {
    private boolean mCurrentState;
    private boolean mLastState;
    private boolean mPolarity;

    public ToggleBoolean(boolean startState,boolean polarity) {
        mCurrentState = startState;
        mPolarity = polarity;
        mLastState = polarity;
    }

    public void update(boolean update) {
        if(update !=mLastState && update == mPolarity) {
            mCurrentState = !mCurrentState;
            System.out.println("Toggling");
        }
        System.out.println("update" + update);
        mLastState = update;
    }

    public boolean getCurrentState() {
        return mCurrentState;
    }


}