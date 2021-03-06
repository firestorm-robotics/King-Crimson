package frc.robot;

public class RobotMap {
    public static final int DRIVETRAIN_LEFT_MASTER = 10,
                            DRIVETRAIN_LEFT_SLAVE = 11,
                            DRIVETRAIN_RIGHT_MASTER = 12,
                            DRIVETRAIN_RIGHT_SLAVE = 13;

    public static final int SHOOTER_LEFT = 10,
                            SHOOTER_RIGHT = 11;
    // Todo: find out what to set both numbers too. The lift is the Neo 550 on a spark max, and the rotator is a Talon on the Rio.
    // Todo: find out the device number
    public static final int CONTROLPANEL_LIFT = 0;
    public static final int CONTROLPANEL_ROTATOR = 0;
    public static final int CONTROLPANEL_COLORSENSER = 0;
    public static final int TURRET_ID = 15,
                            SHOOTER_ANGLE_LEFT  = 16,
                            SHOOTER_ANGLE_RIGHT = 17;

    public static final int INDEX_LEFT = 18,
                            INDEX_RIGHT = 19;
    
    public static final int INTAKE_ANGLE = 20,
                            INTAKE_SPEED = 21;

    public static final int PREBELT = 25;

    public static final int CLIMB = 15,
                            CLIMB_SLIDER = 22 ;
}