package frc.robot.commands;

import frc.robot.subsystems.RobotSubsystem;

public class StopMotors extends RobotCommand {

  public StopMotors(RobotSubsystem subsystem) {
    super(subsystem);
  }

  @Override
  public void initialize() {
    for(var talon : subsystem.talons){
      talon.neutralOutput();
    }
  }

  @Override
  public void execute() {
    for(var talon : subsystem.talons){
      talon.neutralOutput();
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
