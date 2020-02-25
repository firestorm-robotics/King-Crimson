package frc.utils;

public class ToggleBoolean extends UtilBoolean {

    public ToggleBoolean(boolean startState,boolean polarity) {
        super(startState,polarity);
    }

    @Override
    public void update(boolean update) {
        if(update !=mLastState && update == mPolarity) {
            mCurrentState = !mCurrentState;
        }
        mLastState = update;
    }



}