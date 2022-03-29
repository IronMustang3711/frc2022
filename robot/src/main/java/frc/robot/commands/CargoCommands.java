// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoSubsystem;

/** Add your docs here. */
public class CargoCommands {
    static final double INTAKE_AUTOFEED_SPEED = 1.0;
    static final double INTAKE_SHOOT_SPEED = INTAKE_AUTOFEED_SPEED;

    static final double FEEDWORKS_AUTOFEED_SPEED = 0.5;
    static final double FEEDWORKS_SHOOT_SPEED = FEEDWORKS_AUTOFEED_SPEED;

    static class Autofeed extends CommandBase {
        CargoSubsystem cargo;

        Autofeed(CargoSubsystem cargo) {
            this.cargo = cargo;
            addRequirements(cargo);
        }

        @Override
        public void execute() {
            boolean lowerBlocked = cargo.isLowerPhotoeyeBlocked();
            boolean upperBlocked = cargo.isUpperPhotoeyeBlocked();

            if (lowerBlocked & upperBlocked) {
                cargo.feedworks.neutralOutput();
                cargo.intake.neutralOutput();
            } else if (lowerBlocked & !upperBlocked) {
                cargo.intake.set(INTAKE_AUTOFEED_SPEED);
                cargo.feedworks.set(FEEDWORKS_AUTOFEED_SPEED);
            } else if (!lowerBlocked & upperBlocked) {
                cargo.feedworks.neutralOutput();
                cargo.intake.set(INTAKE_AUTOFEED_SPEED);
            } else if (!lowerBlocked & !upperBlocked) {
                cargo.intake.set(INTAKE_AUTOFEED_SPEED);
                cargo.feedworks.set(INTAKE_AUTOFEED_SPEED);
            }

        }

        @Override
        public boolean isFinished() {
            return cargo.isLowerPhotoeyeBlocked() && cargo.isUpperPhotoeyeBlocked();
        }

        @Override
        public void end(boolean interrupted) {
            for (var talon : cargo.talons) {
                talon.neutralOutput();
            }
        }
    }

    public static Command autofeed(CargoSubsystem cargo) {
        return new Autofeed(cargo);
    }

    public static Command shoot(CargoSubsystem cargo) {
        return new CommandBase() {
            {
                addRequirements(cargo);
            }
            long startTime = 0;
            long elapsed = 0;

            @Override
            public void initialize() {
                startTime = System.currentTimeMillis();
            }

            @Override
            public void execute() {
                cargo.shooter.set(1.0);

                elapsed = System.currentTimeMillis() - startTime;

                if (elapsed > 1500)
                    cargo.feedworks.set(FEEDWORKS_SHOOT_SPEED);

                if (elapsed > 2000)
                    cargo.intake.set(INTAKE_SHOOT_SPEED);

            }

            @Override
            public boolean isFinished() {
                return elapsed > 5500;
            }

            @Override
            public void end(boolean interrupted) {
                for (var talon : cargo.talons) {
                    talon.neutralOutput();
                }
            }
        };
    }
}
