/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;

public class IntakeSubsystem extends SubsystemBase implements DashboardUpdater{

  WPI_VictorSPX intakeMotor;

  DigitalInput indexSensor;
  
  DoubleSolenoid intakeLiftPiston;
  Value pistonState;

  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {
    intakeMotor = new WPI_VictorSPX(RobotMap.INTAKE_MOTOR);
    intakeMotor.setInverted(true);

    indexSensor = new DigitalInput(RobotMap.INTAKE_PROXIMITY_SENSOR);

    intakeLiftPiston = new DoubleSolenoid(RobotMap.INTAKE_LIFT_PISTON_FORWARD, RobotMap.INTAKE_LIFT_PISTON_REVERSE);
    pistonState = Value.kForward;
    intakeLiftPiston.set(pistonState);
  }

  @Override
  public void periodic() {
    
  }

  private void setIntakeMotor(double speed) {
    intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public void intake () {
    setIntakeMotor(RobotConstants.INTAKE_SPEED);
  }

  public void outtake () {
    setIntakeMotor(-RobotConstants.INTAKE_SPEED);
  }

  public void stop() {
    setIntakeMotor(0);
  }

  public void invertPiston(){
    pistonState = (pistonState == Value.kForward) ? Value.kReverse : Value.kForward;
    intakeLiftPiston.set(pistonState);
  }

  public boolean getIndex () {
    return !indexSensor.get();
  }

  @Override
  public void updateSmartdashboard() {
  }

  @Override
  public void initSubsystem() {
    pistonState = Value.kForward;
    intakeLiftPiston.set(pistonState);
  }

}
