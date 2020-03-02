/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotConstants;

public class PID extends CommandBase {
  /**
   * Creates a new Pid.
   */
  int deadbandThreshold = 100;
  double startDeadBand;
  double integral, setpoint = 0;
  double previous_error;
  boolean completeFlag;

  NetworkTableEntry P = Robot.shuffleBoardtab.add("P", 0.00025).getEntry();
  NetworkTableEntry I = Robot.shuffleBoardtab.add("I", 0.000075).getEntry();
  NetworkTableEntry D = Robot.shuffleBoardtab.add("D", 0.00005).getEntry();

  public PID(double setPoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassisSubsystem);
    this.setpoint = Robot.chassisSubsystem.getRightEncoder() + setPoint;
    completeFlag = false;
  }

  // Called when the command is initially scheduled.

  /**
   * @param setpoint the setpoint to set
   */

  public double runPID() {
    double error = setpoint - Robot.chassisSubsystem.getRightEncoder();
    if (error < 5000) {
      integral += error * 0.02; // Assuming clock is constant and doesn't fluctuate
    }

    double derivative = (error - previous_error);

    double endValue = P.getDouble(RobotConstants.DRIVE_P) * error + I.getDouble(RobotConstants.DRIVE_I) * integral
        + D.getDouble(RobotConstants.DRIVE_D) * derivative;

    previous_error = error;
    return endValue;

  }

  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.chassisSubsystem.move(/* Math.min(Math.max(PID(), -0.4), 0.4) */runPID(), 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double encoderCount = Robot.chassisSubsystem.getRightEncoder();

    if (encoderCount > setpoint - deadbandThreshold && encoderCount < setpoint + deadbandThreshold) {
      if (System.currentTimeMillis() - startDeadBand > 1000) {
        return true;
      }
    } else {
      startDeadBand = System.currentTimeMillis();

    }
    return false;
  }
}