package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RobotSubsystem;

public class RobotCommand extends CommandBase {
  protected final RobotSubsystem subsystem;

  public RobotCommand(RobotSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }
}
