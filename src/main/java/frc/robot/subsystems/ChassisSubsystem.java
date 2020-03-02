/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.RobotMap;

public class ChassisSubsystem extends SubsystemBase implements DashboardUpdater{

  WPI_VictorSPX leftMotorLead;
  WPI_VictorSPX leftMotorFollowOne;
  WPI_VictorSPX leftMotorFollowTwo;

  Encoder leftEncoder;
  
  WPI_VictorSPX rightMotorLead;
  WPI_VictorSPX rightMotorFollowOne;
  WPI_VictorSPX rightMotorFollowTwo;

  Encoder rightEncoder;

  DifferentialDrive drive;

  /**
   * Creates a new ChassisSubsystem.
   */
  public ChassisSubsystem() {

    leftMotorLead = new WPI_VictorSPX(RobotMap.LEFT_MOTOR_LEAD);
    leftMotorFollowOne = new WPI_VictorSPX(RobotMap.LEFT_MOTOR_FOLLOW_ONE);
    leftMotorFollowTwo = new WPI_VictorSPX(RobotMap.LEFT_MOTOR_FOLLOW_TWO);

    leftMotorFollowOne.follow(leftMotorLead);
    leftMotorFollowTwo.follow(leftMotorLead);

    leftMotorLead.configPeakOutputForward(1);

    leftEncoder = new Encoder(RobotMap.LEFT_MOTOR_ENCODER_A, RobotMap.LEFT_MOTOR_ENCODER_B);
    
    rightMotorLead = new WPI_VictorSPX(RobotMap.RIGHT_MOTOR_LEAD);
    rightMotorFollowOne = new WPI_VictorSPX(RobotMap.RIGHT_MOTOR_FOLLOW_ONE);
    rightMotorFollowTwo = new WPI_VictorSPX(RobotMap.RIGHT_MOTOR_FOLLOW_TWO);

    rightMotorLead.configPeakOutputForward(1);

    rightEncoder = new Encoder(RobotMap.RIGHT_MOTOR_ENCODER_A, RobotMap.RIGHT_MOTOR_ENCODER_B);

    rightMotorFollowOne.follow(rightMotorLead);
    rightMotorFollowTwo.follow(rightMotorLead);

    drive = new DifferentialDrive(leftMotorLead, rightMotorLead);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void move (double speed, double turn) {
    drive.arcadeDrive(speed, turn);
  }

  public double getRightEncoder () {
    return rightEncoder.getDistance();
  }

  public double getLeftEncoder () {
    return leftEncoder.getDistance();
  }

  @Override
  public void updateSmartdashboard() {
    SmartDashboard.putData(drive);
    SmartDashboard.putNumber("Right Encoder", getRightEncoder());
    SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
  }

}
