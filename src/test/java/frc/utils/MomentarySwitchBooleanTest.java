package frc.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MomentarySwitchBooleanTest {
    
    @Test
    public void testToggleTrue() {
        MomentarySwitchBoolean switchBoolean = new MomentarySwitchBoolean(false, true);
        switchBoolean.update(false);
        switchBoolean.update(true);
        assertEquals(true, switchBoolean.mCurrentState);
    }

    @Test
    public void testMomentarySwitch() {
        MomentarySwitchBoolean switchBoolean = new MomentarySwitchBoolean(false, true);
        switchBoolean.update(false);
        switchBoolean.update(true);
        assertEquals(true, switchBoolean.mCurrentState);
        switchBoolean.update(true);
        assertEquals(false, switchBoolean.mCurrentState);
    }

    @Test
    public void testPolarity() {
        MomentarySwitchBoolean switchBoolean = new MomentarySwitchBoolean(false, true);
        switchBoolean.update(false);
        switchBoolean.update(true);
        assertEquals(true, switchBoolean.mCurrentState);
        switchBoolean.update(true);
        assertEquals(false, switchBoolean.mCurrentState);
        switchBoolean.update(false);
        assertEquals(false, switchBoolean.mCurrentState);
    }


}