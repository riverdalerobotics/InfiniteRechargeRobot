/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbSubsystem extends SubsystemBase implements DashboardUpdater {

  Victor winchMotor;
  DoubleSolenoid hookPiston = new DoubleSolenoid(RobotMap.CLIMB_PISTON_FORWARD, RobotMap.CLIMB_PISTON_REVERSE);

  public ClimbSubsystem() {

    winchMotor = new Victor(RobotMap.WINCH_MOTOR);
    hookPiston.set(Value.kOff);

  }

  public void setWinchMotor(double speed) {
    winchMotor.set(speed);
  }

  public void openClimb() {
    hookPiston.set(Value.kForward);
  }

  public void closeClimb() {
    hookPiston.set(Value.kReverse);
  }

  public void neutralClimb() {
    hookPiston.set(Value.kOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void updateSmartdashboard() {
    // Robot.shuffleBoardtab.addBoolean("Climb Piston Engaged", () -> {
    //   return hookPiston.get() == Value.kForward;
    // });
  }
}
