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
import frc.robot.commands.CloseClimbCommand;
import frc.robot.commands.IntakeSequenceCommandGroup;
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

    /** Operator Button: A */
    Button spinPanelButton;
    /** Operator Button B */
    Button openPanelWheel;
    /** Operator Button X */
    Button intakeSequence;
    /** Operator D-Pad up */
    Button openClimb;
    /** Operator D-Pad down */
    Button closeClimb;
    /** Operator Select Button */
    Button toggleIntakeLift;
    
    public OI () {
        
        spinPanelButton = new Button(() -> operator.getAButton());
        spinPanelButton.whenPressed(new SpinPanelCommand());
        
        openPanelWheel = new Button(() -> operator.getBButton());
        openPanelWheel.whenPressed(new TogglePanelPistonCommand());
        
        intakeSequence = new Button(() -> operator.getXButton());
        intakeSequence.whenPressed(new IntakeSequenceCommandGroup());

        openClimb = new Button (() ->  operator.getPOV() == RobotConstants.D_PAD_UP);
        openClimb.whenPressed(new OpenClimbCommand());
        
        closeClimb = new Button (() ->operator.getPOV() == RobotConstants.D_PAD_DOWN);
        closeClimb.whenPressed(new CloseClimbCommand());
        
        toggleIntakeLift = new Button(() -> operator.getStartButtonPressed()); 
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

    public double getPanelSpeed(){
        return operator.getX(Hand.kRight);
    }

    @Override
    public void updateSmartdashboard() {

    }

}

