// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangarSubsystem;

public class ManualHangarControl extends CommandBase {
  private HangarSubsystem hangar;
  private XboxController xbox;

  /** Creates a new HangarCommand. */
  public ManualHangarControl(HangarSubsystem hangar, XboxController xbox) {
    this.hangar = hangar;
    this.xbox = xbox;
    addRequirements(hangar);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double arm_amt = -0.5 * xbox.getLeftY();
    double winch = xbox.getRightY();

    double left_trigger = xbox.getLeftTriggerAxis();
    double right_trigger = xbox.getRightTriggerAxis();
    double hook_amt = left_trigger > 0.01 ? -left_trigger : right_trigger;

    hook_amt *= 0.5;
    hangar.arm.set(arm_amt);
    hangar.winch.set(winch);
    hangar.hook.set(hook_amt);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hangar.arm.disable();
    hangar.winch.disable();
    hangar.hook.disable();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
