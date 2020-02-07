package frc.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ControlBoard implements IButtonControlBoard, IJoystickControlBoard {
    private XboxController mDriveController;
    private static ControlBoard mInstance = new ControlBoard();

    private ControlBoard() {
        mDriveController = new XboxController(0);
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

    public int getPOV(){
        return mDriveController.getPOV();
    }

    @Override
    public boolean initalizeTrajectory() {
        // TODO Auto-generated method stub
        return mDriveController.getBumperPressed(Hand.kRight);
    }

    @Override
    public boolean getEnableTrajectory() {
        // TODO Auto-generated method stub
        return mDriveController.getBumper(Hand.kRight);
    }

    public boolean getMusic() {
        return mDriveController.getAButtonPressed();
    }
}
