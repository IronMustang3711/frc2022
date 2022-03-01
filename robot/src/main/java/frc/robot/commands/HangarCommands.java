// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
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

    static class HangarSetpointCommand2 extends HangarSetpointCommand {

        public HangarSetpointCommand2(WPI_TalonSRX talon, double setpoint) {
            super(talon, setpoint);
        }

        @Override
        public boolean isFinished() {
            return isMotionFinished();
        }

    }

    HangarSubsystem hangar;

    public HangarCommands(HangarSubsystem subsystem) {
        this.hangar = subsystem;
    }

    CommandBase _toHome;

    public CommandBase toHome() {
        if (_toHome != null)
            return _toHome;
        var winchToZero = new HangarSetpointCommand(hangar.winch, 0);
        var armToZero = new HangarSetpointCommand(hangar.arm, 0);
        var hookToZero = new HangarSetpointCommand(hangar.hook, 0);
        var cmd = winchToZero.alongWith(armToZero, hookToZero);
        cmd.setName("initial position");
        cmd.addRequirements(hangar);
        return _toHome = cmd;
    }

    CommandBase _armOut;

    public CommandBase armOut() {
        if (_armOut != null)
            return _armOut;
        var unspoolWinch = new HangarSetpointCommand(hangar.winch, -52000);
        var armUp = new HangarSetpointCommand(hangar.arm, 1200);
        var delayedArmUp = new WaitUntilCommand(() -> unspoolWinch.getMotionProgress() > 0.3).andThen(armUp);
        var delayedHooksIn = new WaitUntilCommand(() -> hangar.winch.getSelectedSensorPosition() > -25000)
                .andThen(hooksIn());
        var cmd = unspoolWinch.alongWith(delayedArmUp).alongWith(delayedHooksIn);
        cmd.setName("reach up");
        cmd.addRequirements(hangar);
        return _armOut = cmd;
    }

    CommandBase _armOutNoHooks;

    private CommandBase armOutNoHooks() {
        if (_armOutNoHooks != null)
            return _armOutNoHooks;
        var unspoolWinch = new HangarSetpointCommand(hangar.winch, -52000);
        var armUp = new HangarSetpointCommand(hangar.arm, 1200);
        var delayedArmUp = new WaitUntilCommand(() -> unspoolWinch.getMotionProgress() > 0.3).andThen(armUp);
        var cmd = unspoolWinch.alongWith(delayedArmUp);
        cmd.setName("reach up(no_hooks)");
        cmd.addRequirements(hangar);
        return _armOutNoHooks = cmd;
    }

    private CommandBase _winchLift;

    public CommandBase winchLift() {
        if (_winchLift != null)
            return _winchLift;
        var winchToZero = new HangarSetpointCommand(hangar.winch, 600);

        var neutralArm = new WaitUntilCommand(() -> winchToZero.getMotionProgress() > 0.2)
                .andThen(() -> hangar.arm.neutralOutput());

        var hooks = new InstantCommand(() -> {
            _hooksOut.cancel();
            hangar.hook.neutralOutput();
        })
                .andThen(new WaitUntilCommand(() -> winchToZero.getMotionProgress() > 0.3))
                .andThen(hooksIn());
        var cmd = winchToZero.alongWith(neutralArm).alongWith(hooks);
        cmd.setName("winch up robot");
        cmd.addRequirements(hangar);
        return (_winchLift = cmd);
    }

    private CommandBase _hooksOut = null;

    public CommandBase hooksOut() {
        if (_hooksOut != null)
            return _hooksOut;

        var engageHook = new HangarSetpointCommand(hangar.hook, -2800) {
            public void initialize() {
                super.initialize();
                if (_hooksIn != null && (_hooksIn.isScheduled() || !_hooksIn.isFinished())) {
                    _hooksIn.cancel();
                }
            };
        };

        engageHook.setName("hooks out");
        return (_hooksOut = engageHook);
    }

    private CommandBase _hooksIn = null;

    public CommandBase hooksIn() {
        var cmd = new HangarSetpointCommand(hangar.hook, 0) {
            public void initialize() {
                super.initialize();
                if (_hooksOut != null && (_hooksOut.isScheduled())) {
                    _hooksOut.cancel();
                }
            }
        };
        cmd.setName("hooks in");
        return (_hooksIn = cmd);
    }

    private CommandBase _armToNextRung;

    public CommandBase armToNextRung() {
        if (_armToNextRung != null)
            return _armToNextRung;
        var unspoolWinch = new HangarSetpointCommand2(hangar.winch, -11000);
        var armUp = new HangarSetpointCommand2(hangar.arm, 1100);
        var delayedArmUp = new WaitUntilCommand(() -> unspoolWinch.getMotionProgress() > 0.1).andThen(armUp);
        var tmp0 = unspoolWinch.alongWith(delayedArmUp);
        var tmp1 = tmp0.andThen(() -> hangar.arm.neutralOutput()).andThen(new WaitCommand(1));

        var cmd = tmp1.andThen(armOutNoHooks());

        cmd.setName("arm to next rung");
        cmd.addRequirements(hangar);
        return _armToNextRung = cmd;

    }

}
