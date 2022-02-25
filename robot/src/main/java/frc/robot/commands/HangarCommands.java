// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.HangarSubsystem;

/** Add your docs here. */
public class HangarCommands {

    static class HangarSetpointCommand extends FancyPosition {

        public HangarSetpointCommand(WPI_TalonSRX talon, double setpoint) {
            super(talon, setpoint);
        }

        @Override
        public void initialize() {
            super.initialize();
            talon.selectProfileSlot(0, 0);
        }

    }

    HangarSubsystem hangar;

    public HangarCommands(HangarSubsystem subsystem) {
        this.hangar = subsystem;
    }

    public CommandBase toHome() {
        var winchToZero = new HangarSetpointCommand(hangar.winch, 0);
        var armToZero = new HangarSetpointCommand(hangar.arm, 0);
        var hookToZero = new HangarSetpointCommand(hangar.hook, 0);
        var cmd = winchToZero.alongWith(armToZero, hookToZero);
        cmd.setName("initial position");
        cmd.addRequirements(hangar);
        return cmd;
    }

    public CommandBase armOut() {
        var unspoolWinch = new HangarSetpointCommand(hangar.winch, -52000);
        var armUp = new HangarSetpointCommand(hangar.arm, 900);
        var delayedArmUp = new WaitCommand(1.5).andThen(armUp);
        // bring winch(-52000) out & arm
        // winch needs to come out first
        var cmd = unspoolWinch.alongWith(delayedArmUp);
        cmd.setName("reach up");
        cmd.addRequirements(hangar);
        return cmd;
    }

    public CommandBase winchLift() {
        var winchToZero = new HangarSetpointCommand(hangar.winch, 600);
        var neutralArm = new WaitCommand(1.0).andThen(new InstantCommand(() -> {
            hangar.arm.neutralOutput();
        }));

        var engageHook = new HangarSetpointCommand(hangar.hook, -2300);
        var delayedHook = new WaitCommand(7).andThen(engageHook);
        var cmd = winchToZero.alongWith(neutralArm, delayedHook);
        cmd.setName("winch up robot");
        cmd.addRequirements(hangar);
        return cmd;
    }

    public static Command unHookAndExtendArm() {
        // requires: hooks in
        // winch out a little, rotate arm, winch out more;
        return null;
    }

}
