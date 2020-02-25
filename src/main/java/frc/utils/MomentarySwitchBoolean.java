package frc.utils;

public class MomentarySwitchBoolean extends UtilBoolean {

    public MomentarySwitchBoolean(boolean startState, boolean polarity) {
        super(startState, polarity);
    }

    @Override
    public void update(boolean update) {
        if (update != mLastState) {
            if (update == mPolarity) {
                mCurrentState = !mCurrentState;
            } else {
                mCurrentState = false;
            }
        }
        if (update == mLastState) {
            mCurrentState = false;
        }
        mLastState = update;
    }
}