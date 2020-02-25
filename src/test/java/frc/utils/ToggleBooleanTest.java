package frc.utils;


import static org.junit.Assert.*;

import org.junit.Test;
public class ToggleBooleanTest {

    @Test
    public void togglesTrue() {
        ToggleBoolean toggle = new ToggleBoolean(false, true);
        toggle.update(false);
        toggle.update(true);

        assertEquals("toggle is not true",true,toggle.getCurrentState());
    }

    @Test
    public void togglesFalse() {
        ToggleBoolean toggle = new ToggleBoolean(false,true);
        //first toggle
        toggle.update(false);
        toggle.update(true);
        assertEquals("did not toggle to true", true, toggle.getCurrentState());
        toggle.update(false);
        toggle.update(true);
        //second toggle should toggle to false
        assertEquals("did not toggle to false",false, toggle.getCurrentState());
    }
}