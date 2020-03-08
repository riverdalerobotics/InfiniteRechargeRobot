/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;
import frc.robot.WheelColor;

public class PanelSubsystem extends SubsystemBase implements DashboardUpdater{
  /**
   * Creates a new PanelSubsystem.
   */
  WPI_TalonSRX panelMotor;

  ColorSensorV3 colorSensor;

  DoubleSolenoid upPiston;
  Value pistonState;
  
  public PanelSubsystem() {

    panelMotor = new WPI_TalonSRX(RobotMap.PANEL_MOTOR);
    panelMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    colorSensor = new ColorSensorV3(RobotMap.COLOUR_SENSOR_PORT);

    pistonState = Value.kForward;

    upPiston = new DoubleSolenoid(RobotMap.CONTROL_PANEL_PISTON_FORWARD, RobotMap.CONTROL_PANEL_PISTON_REVERSE);
    upPiston.set(pistonState);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public WheelColor getTargetColour() {
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      return WheelColor.getColorForChar(gameData.charAt(0));
    }
    return WheelColor.NONE;
  }

  public WheelColor getColor () {
    
      Color c = colorSensor.getColor();
      
      if (c.red * 255 > 100) {
        return WheelColor.RED;
      } else if(c.blue * 255 > 100){
        return WheelColor.BLUE;
      } else if(c.red * 255 > 70){
        return WheelColor.YELLOW;
      } else if(c.green * 255 > 130){
        return WheelColor.GREEN;
      }

      return WheelColor.NONE;
  }
  
  public void spinClockwise () {
    panelMotor.set(RobotConstants.PANEL_SPEED);
  }

  public void spinCounterclockwise () {
    panelMotor.set(-RobotConstants.PANEL_SPEED);
  }

  public void stop () {
    panelMotor.set(0);
  }

  public void invertPiston(){
    pistonState = (pistonState == Value.kForward) ? Value.kReverse : Value.kForward;
    upPiston.set(pistonState);
  }
  
  public void spin(double speed){
    panelMotor.set(speed);
  }

  @Override
  public void updateSmartdashboard() {
    SmartDashboard.putString("Target Color", getTargetColour().toString());
    SmartDashboard.putString("Current Color", getColor().toString());
    SmartDashboard.putNumber("R", colorSensor.getColor().red * 255);
    SmartDashboard.putNumber("G", colorSensor.getColor().green * 255);
    SmartDashboard.putNumber("B", colorSensor.getColor().blue * 255);
  }

  @Override
  public void initSubsystem() {
    pistonState = Value.kForward;
    upPiston.set(pistonState);
  }
}
