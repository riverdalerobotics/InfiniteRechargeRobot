/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CloseClimbCommand;
import frc.robot.commands.OpenClimbCommand;
import frc.robot.commands.SpinPanelCommand;
import frc.robot.commands.ToggleIntakeLiftPistonCommand;
import frc.robot.commands.TogglePanelPistonCommand;

/**
 * Add your docs here.
 */
public class OI implements DashboardUpdater {

    XboxController operator = new XboxController(RobotMap.XBOXCONTROLLER_OPERATING);
    XboxController driver = new XboxController(RobotMap.XBOXCONTROLLER_MOVING);

    /** Button: A   Number: 1 */
    JoystickButton spinPanelButton;
    /** Button B   Number: 2 */
    JoystickButton openPanelWheel;
    /** D-Pad up */
    Button openClimb;
    /** D-Pad down */
    Button closeClimb;

    JoystickButton toggleIntakeLift;
    public OI () {
        
        spinPanelButton = new JoystickButton(operator, RobotConstants.A_BUTTON);
        spinPanelButton.whenPressed(new SpinPanelCommand());
        
        openPanelWheel = new JoystickButton(operator, RobotConstants.B_BUTTON);
        openPanelWheel.whenPressed(new TogglePanelPistonCommand());
        
        openClimb = new Button (){
            @Override
            public boolean get() {
                return operator.getPOV() == RobotConstants.D_PAD_UP;
            }
        };
        openClimb.whenPressed(new OpenClimbCommand());
        
        closeClimb = new Button (){
            @Override
            public boolean get (){
                return operator.getPOV() == RobotConstants.D_PAD_DOWN;
            }
        };
        closeClimb.whenPressed(new CloseClimbCommand());
        
        toggleIntakeLift = new JoystickButton(operator, RobotConstants.SELECT_BUTTON); 
        toggleIntakeLift.whenPressed(new ToggleIntakeLiftPistonCommand());

    }

    public double getSpeed(){
        return -driver.getY(Hand.kLeft);
    }

    public double getTurn(){
        return driver.getX(Hand.kRight);
    }

    public double getWinchSpeed() {
        return Robot.deadband(driver.getTriggerAxis(Hand.kRight), RobotConstants.TRIGGER_DEADBAND);
    }

    public boolean hopperForward(){
        return operator.getTriggerAxis(Hand.kRight) > RobotConstants.TRIGGER_DEADBAND;
    }
    
    public boolean reverseHopper(){
        return operator.getTriggerAxis(Hand.kLeft) > RobotConstants.TRIGGER_DEADBAND;
    }

    public boolean intake(){
        return operator.getBumper(Hand.kLeft);
    }

    public boolean outtake(){
        return operator.getBumper(Hand.kRight);
    }

    public boolean revShooter(){
        return operator.getBButton();
    }
    public boolean spinPanel(){
        return operator.getAButton();
    }
    public double getPanelSpeed(){
        return operator.getX(Hand.kRight);
    }
    public int getClimb(){
        return operator.getPOV();
    }

    @Override
    public void updateSmartdashboard() {

    }

}

