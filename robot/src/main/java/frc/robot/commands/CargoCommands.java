// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoSubsystem;

/** Add your docs here. */
public class CargoCommands {

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

            if (!upperBlocked)
                cargo.feedworks.set(0.5);
            else
                cargo.feedworks.neutralOutput();

            if (!lowerBlocked && !upperBlocked)
                cargo.intake.set(0.5);
            else if (lowerBlocked && upperBlocked)
                cargo.intake.neutralOutput();

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
            long startTime = System.currentTimeMillis();
            long elapsed = 0;

            @Override
            public void initialize() {
                startTime = System.currentTimeMillis();
            }

            @Override
            public void execute() {
                cargo.shooter.set(1.0);
                elapsed = System.currentTimeMillis() - startTime;

                if (elapsed > 2500)
                    cargo.feedworks.set(0.5);

                if (elapsed > 4000)
                    cargo.intake.set(0.5);

            }

            @Override
            public boolean isFinished() {
                return elapsed > 10000;
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
