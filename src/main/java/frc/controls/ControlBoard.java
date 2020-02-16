package frc.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ControlBoard implements IButtonControlBoard, IJoystickControlBoard {
    private XboxController mDriveController;
    private Joystick mBoard;
    private static ControlBoard mInstance = new ControlBoard();

    private ControlBoard() {
        mDriveController = new XboxController(0);
        mBoard = new Joystick(1);
    }

    
   

    @Override
    public double getYThrottle() {
        return mDriveController.getY(Hand.kLeft);

    }

    @Override
    public double getXThrottle() {
        return mDriveController.getX(Hand.kRight);

    }

    @Override
    public boolean getShoot() {
        return mDriveController.getBumper(Hand.kLeft);
    }
 
    public static ControlBoard getInstance() {
        return mInstance;
    }

    @Override
    public boolean getTurnTurretLeft() {
        return (mDriveController.getPOV() == 90);
    }

    @Override
    public boolean getTurnTurretRight() {
        return (mDriveController.getPOV() == 270);
    }

    public boolean raiseAngle() {
        return (mDriveController.getPOV() == 0);
    }

    public boolean lowerAngle() {
        return (mDriveController.getPOV() == 180);
    }

    public int getPOV(){
        return mDriveController.getPOV();
    }


    @Override
    public boolean runIntake() {
        // TODO Auto-generated method stub
        return mDriveController.getBumper(Hand.kRight);
    }

    @Override
    public boolean enableMusic() {
        return mDriveController.getAButtonPressed();
    }

    @Override
    public boolean enableVisionTracking() {
        return mDriveController.getBButton();
    }

    @Override
    public boolean toggleIntake() {
        return mDriveController.getXButton();
    }

    public double getBoardX() {
        return mBoard.getX();
    }
}
