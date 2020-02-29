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
  int colourScore; // red is 1, blue is 3, green is 4, yellow is 2
  int target;
  int distance; // distance will be difference between target colour and current observed colour, represented as a speed, either forward or backwards
  public SpinPanelCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.panelSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
     curColor = Robot.panelSubsystem.getColor();
     target = Robot.panelSubsystem.getTargetColour();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (curColor != WheelColor.NONE) {
    switch (curColor) {
      case RED:
        colourScore = 1;
        break;
      case BLUE:
        colourScore = 3;
        break;
      case GREEN:
        colourScore = 4;
        break;
      case YELLOW:
        colourScore = 2;
        break;
      default:
        colourScore = -1;
        break;
    }
    if(Math.abs(target - colourScore) > 2){
      Robot.panelSubsystem.spinClockwise();
    } else {
      Robot.panelSubsystem.spinCounterclockwise();
    }
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
    return colourScore == target || curColor == null;
  }
}
