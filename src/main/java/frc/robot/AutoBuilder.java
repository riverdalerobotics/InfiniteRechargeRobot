/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Add your docs here.
 */
public class AutoBuilder {

    private final static int autoBalls = 3;

    public static CommandBase getAutoSequence(String choice) {
        if (choice.equals("shoot")) {
            return new SequentialCommandGroup(driveTo(
                    () -> Robot.chassisSubsystem.getAvgEncoder() >= RobotConstants.INITIATION_DISTANCE, 6 /** Timeout */
            ), shootSequenceRepeat(autoBalls));
        } else if (choice.equals("drive")) {
            return driveTo(() -> Robot.chassisSubsystem.getAvgEncoder() >= 5000, 1.5);
        } else {
            return new WaitCommand(5);
        }
    }

    @SuppressWarnings("unused")
    private static SequentialCommandGroup intakeAndShootSequence(int numBalls) {

        Command[] sequence = new Command[numBalls * 2];

        for (int i = 0; i < numBalls; i++) {
            sequence[i] = driveAndIntakeGroup();
        }

        for (int i = numBalls; i < numBalls * 2; i++) {
            sequence[i] = ShootSequence();
        }

        return new SequentialCommandGroup(sequence);

    }

    private static ParallelCommandGroup driveAndIntakeGroup() {
        return new ParallelCommandGroup(driveTo(Robot.intakeSubsystem::getIndex), intakeSequence());
    }

    private static SequentialCommandGroup shootSequenceRepeat(int num) {
        Command[] s = new Command[num];
        for (int i = 0; i < num; i++) {
            s[i] = ShootSequence();
        }
        return new SequentialCommandGroup(s);
    }

    private static SequentialCommandGroup ShootSequence() {
        return new SequentialCommandGroup(maxHopper(), shootBall());
    }

    private static CommandBase driveTo(BooleanSupplier bool) {
        return new FunctionalCommand(Robot.chassisSubsystem::resetEncoders,
                () -> Robot.chassisSubsystem.move(RobotConstants.DRIVE_SPEED, 0),
                (a) -> Robot.chassisSubsystem.move(0, 0), bool, Robot.chassisSubsystem);
    }

    private static CommandBase driveTo(BooleanSupplier bool, double timeout) {
        return driveTo(bool).withTimeout(timeout);
    }

    private static SequentialCommandGroup intakeSequence() {
        return new SequentialCommandGroup(intake(), indexRelease());
    }

    private static CommandBase intake() {
        return new StartEndCommand(Robot.intakeSubsystem::intake, Robot.intakeSubsystem::stop, Robot.intakeSubsystem)
                .withInterrupt(Robot.intakeSubsystem::getIndex);
    }

    private static CommandBase indexRelease() {
        return new FunctionalCommand(Robot.intakeSubsystem::intake, Robot.hopperSubsystem::moveForward, (a) -> {
            Robot.intakeSubsystem.stop();
            Robot.hopperSubsystem.stop();
        }, () -> !Robot.intakeSubsystem.getIndex(), Robot.intakeSubsystem, Robot.hopperSubsystem);
    }

    private static CommandBase maxHopper() {
        return new FunctionalCommand(Robot.hopperSubsystem::moveForward, Robot.hopperSubsystem::moveForward,
                (a) -> Robot.hopperSubsystem.stop(), Robot.hopperSubsystem::getTop, Robot.hopperSubsystem);
    }

    private static CommandBase shootBall() {
        return new FunctionalCommand(Robot.shooterSubsystem::revShooter, Robot.hopperSubsystem::moveForward, (a) -> {
            Robot.shooterSubsystem.stopShooter();
            Robot.hopperSubsystem.stop();
        }, () -> !Robot.hopperSubsystem.getTop(), Robot.shooterSubsystem, Robot.hopperSubsystem);
    }

}
