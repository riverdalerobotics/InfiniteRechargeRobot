/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;

public class ShooterSubsystem extends SubsystemBase implements DashboardUpdater{

  CANSparkMax shooterMotor;
  CANEncoder shooterEncoder;
  /**
   * Creates a new ShooterSubsystem.
   */
  public ShooterSubsystem() {
    shooterMotor = new CANSparkMax(RobotMap.SHOOTER_MOTOR, MotorType.kBrushless);
    shooterMotor.setInverted(true);
    
    shooterEncoder = new CANEncoder(shooterMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void setShooterMotor (double speed) {
    shooterMotor.set(speed);
  }

  public void revShooter () {
    setShooterMotor(RobotConstants.SHOOTER_SPEED);
  }

  public void stopShooter () {
    setShooterMotor(0);
  }

  public double getShooterVelocity(){
    
    return shooterEncoder.getVelocity();
    
  }

  @Override
  public void updateSmartdashboard() {
    SmartDashboard.putNumber("Shooter Speed", getShooterVelocity());
  }

}
