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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;

public class HopperSubsystem extends SubsystemBase implements DashboardUpdater {

  WPI_VictorSPX hopperMotor;
  DigitalInput topProxSensor;

  boolean override;

  /**
   * Creates a new HopperSubsystem.
   */
  public HopperSubsystem() {
    hopperMotor = new WPI_VictorSPX(RobotMap.HOPPER_MOTOR);
    hopperMotor.setInverted(true);

    topProxSensor = new DigitalInput(RobotMap.TOP_PROXIMITY_SENSOR);

    override = false;
    SmartDashboard.putBoolean("! Override Shooter !", false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  private void setHopperMotor(double speed) {
    hopperMotor.set(ControlMode.PercentOutput, speed);
  }

  public void moveForward() {
    if (getTop() && Robot.shooterSubsystem.getShooterVelocity() < RobotConstants.MIN_SHOOT_VELOCITY) {
      setHopperMotor(0);
    } else {
      setHopperMotor(RobotConstants.HOPPER_SPEED);
    }
  }

  public void moveBackwards() {
    setHopperMotor(-RobotConstants.HOPPER_SPEED);
  }

  public void stop() {
    setHopperMotor(0);
  }

  public boolean getTop() {
    return !topProxSensor.get();
  }

  @Override
  public void updateSmartdashboard() {
    SmartDashboard.putBoolean("Hopper Top Sensor", getTop());
    this.override = SmartDashboard.getBoolean("! Override Shooter !", false);
  }

  @Override
  public void initSubsystem() {
  }

}
