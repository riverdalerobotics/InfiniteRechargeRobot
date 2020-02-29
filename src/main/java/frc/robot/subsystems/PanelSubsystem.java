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
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardUpdater;
import frc.robot.Robot;
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

  String[] colorLookup = {
    "---", "Red", "Yellow", "Blue", "Green"
  };
  
  public PanelSubsystem() {

    panelMotor = new WPI_TalonSRX(RobotMap.PANEL_MOTOR);
    panelMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    colorSensor = new ColorSensorV3(RobotMap.COLOUR_SENSOR_PORT);

    pistonState = Value.kReverse;

    upPiston = new DoubleSolenoid(RobotMap.CONTROL_PANEL_PISTON_FORWARD, RobotMap.CONTROL_PANEL_PISTON_REVERSE);
    upPiston.set(pistonState);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public int getTargetColour() {
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      switch (gameData.charAt(0))
      {
        case 'B' :
          return 3;
        case 'G' :
          return 4;
        case 'R' :
          return 1;
        case 'Y' :
          return 2;
        default :
          break;
      }
    }
    return 0;
  }

  public WheelColor getColor () {
    
    Color c = colorSensor.getColor();
      if (c.blue  > 200 && c.green > 200 && c.red < 20) {
        return WheelColor.BLUE;
      } else if(c.blue < 20 && c.green > 200 && c.red < 20){
        return WheelColor.GREEN;
      } else if(c.blue < 20 && c.green < 20 && c.red > 200){
        return WheelColor.RED;
      } else if(c.blue < 20 && c.green > 200 && c.red > 200){
        return WheelColor.YELLOW;
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
    Robot.colorLayout.addString("Target Color", () -> colorLookup[getTargetColour()]);
    Robot.colorLayout.addString("Current Color", () -> getColor().toString());
  }
}
