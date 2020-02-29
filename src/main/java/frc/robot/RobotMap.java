/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class RobotMap {

    public final static int LEFT_MOTOR_LEAD = 10;
    public final static int LEFT_MOTOR_FOLLOW_ONE = 11;
    public final static int LEFT_MOTOR_FOLLOW_TWO = 12;

    public final static int LEFT_MOTOR_ENCODER_A = 2;
    public final static int LEFT_MOTOR_ENCODER_B = 3;
        
    public final static int RIGHT_MOTOR_LEAD = 20;
    public final static int RIGHT_MOTOR_FOLLOW_ONE = 21;
    public final static int RIGHT_MOTOR_FOLLOW_TWO = 22;
    
    public final static int RIGHT_MOTOR_ENCODER_A = 5;
    public final static int RIGHT_MOTOR_ENCODER_B = 6;

    public final static int INTAKE_MOTOR = 30;
    public final static int HOPPER_MOTOR = 40;
    public final static int SHOOTER_MOTOR = 50;
    
    public final static int WINCH_MOTOR = 9;

    public final static int XBOXCONTROLLER_MOVING = 0;
    public final static int XBOXCONTROLLER_OPERATING = 1;

    public static final I2C.Port COLOUR_SENSOR_PORT = I2C.Port.kOnboard;
    public static final int PANEL_MOTOR = 60;

    public static final int CONTROL_PANEL_PISTON_FORWARD = 4;
    public static final int CONTROL_PANEL_PISTON_REVERSE = 5;

    public static final int CLIMB_PISTON_FORWARD = 2;
    public static final int CLIMB_PISTON_REVERSE = 3;

    public static final int INTAKE_LIFT_PISTON_FORWARD = 0;
    public static final int INTAKE_LIFT_PISTON_REVERSE = 1;

    public static final int TOP_PROXIMITY_SENSOR = 7;
    public static final int INTAKE_PROXIMITY_SENSOR = 9;
    
}
