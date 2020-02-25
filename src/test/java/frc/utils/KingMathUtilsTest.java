package frc.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KingMathUtilsTest {

        @Test
        public void testDeadband() {
            assertEquals(25, KingMathUtils.applyDeadband(20, 10, 10, 25),0);
        }

        @Test
        public void testClamp() {
            assertEquals(0, KingMathUtils.clampD(10, 20),0);
        }
}