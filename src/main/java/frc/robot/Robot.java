/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.defaultCommands.ChassisDefaultCommand;
import frc.robot.commands.defaultCommands.ClimbDefaultCommand;
import frc.robot.commands.defaultCommands.HopperDefaultCommand;
import frc.robot.commands.defaultCommands.IntakeDefaultCommand;
import frc.robot.commands.defaultCommands.PanelDefaultCommand;
import frc.robot.commands.defaultCommands.ShooterDefaultCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PanelSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  public static final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
  public static final HopperSubsystem hopperSubsystem = new HopperSubsystem();
  public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  public static final PanelSubsystem panelSubsystem = new PanelSubsystem();
  public static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  public static final ShuffleboardTab shuffleBoardtab = Shuffleboard.getTab("Game Tab");
  // public static final ShuffleboardLayout shooterLayout = Robot.shuffleBoardtab.getLayout("Shooter System");
  // public static final ShuffleboardLayout colorLayout = Robot.shuffleBoardtab.getLayout("Color System");

  public static final OI oi = new OI();
  public static UsbCamera camera;

  private static final ArrayList<DashboardUpdater> smartdashboardUpdaters = new ArrayList<DashboardUpdater>();

  public static SendableChooser<String> autoChooser = new SendableChooser<String>();

  @Override
  public void robotInit() {
    CommandScheduler.getInstance().setDefaultCommand(chassisSubsystem, new ChassisDefaultCommand());
    smartdashboardUpdaters.add(chassisSubsystem);

    CommandScheduler.getInstance().setDefaultCommand(hopperSubsystem, new HopperDefaultCommand());
    smartdashboardUpdaters.add(hopperSubsystem);
    
    CommandScheduler.getInstance().setDefaultCommand(intakeSubsystem, new IntakeDefaultCommand());
    smartdashboardUpdaters.add(intakeSubsystem);
    
    CommandScheduler.getInstance().setDefaultCommand(climbSubsystem, new ClimbDefaultCommand());
    smartdashboardUpdaters.add(climbSubsystem);
    
    CommandScheduler.getInstance().setDefaultCommand(panelSubsystem, new PanelDefaultCommand());
    smartdashboardUpdaters.add(panelSubsystem);
    
    CommandScheduler.getInstance().setDefaultCommand(shooterSubsystem, new ShooterDefaultCommand());
    smartdashboardUpdaters.add(shooterSubsystem);  

    smartdashboardUpdaters.add(oi);

    camera = CameraServer.getInstance().startAutomaticCapture();

    autoChooser.setDefaultOption("Shoot", "shoot");
    autoChooser.addOption("Drive", "drive");
    SmartDashboard.putData("Auto", autoChooser);

    for (DashboardUpdater updater : smartdashboardUpdaters) {
      updater.initSubsystem();
    }
    
  }

  @Override
  public void robotPeriodic() {
    for (DashboardUpdater updater : smartdashboardUpdaters) {
      updater.updateSmartdashboard();
    }
    
  }
  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    CommandScheduler.getInstance().schedule(AutoBuilder.getAutoSequence(autoChooser.getSelected()));
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public static double deadband (double mag, double dead) {
      if (mag > dead) {
          return mag;
      }
      return 0;
  }

}
