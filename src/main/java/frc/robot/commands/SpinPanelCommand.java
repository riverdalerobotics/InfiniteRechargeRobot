/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.WheelColor;

public class SpinPanelCommand extends CommandBase {
  /**
   * Creates a new SpinPanelCommand.
   */
  WheelColor curColor;
  WheelColor target;

  public SpinPanelCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.panelSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    curColor = WheelColor.NONE; //Robot.panelSubsystem.getColor();
    target = Robot.panelSubsystem.getTargetColour();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(target.getNum() - curColor.getNum()) > 2) {
      Robot.panelSubsystem.spinClockwise();
    } else {
      Robot.panelSubsystem.spinCounterclockwise();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.panelSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return curColor == target || curColor == WheelColor.NONE;
  }
}
