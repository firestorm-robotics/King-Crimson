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

}
