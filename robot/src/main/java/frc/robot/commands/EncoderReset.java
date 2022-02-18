package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RobotSubsystem;

import static frc.robot.util.TalonUtil.check;

public class EncoderReset extends CommandBase {
  final RobotSubsystem subsystem;

  public EncoderReset(RobotSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
    setName("reset encoders(" + subsystem.getName() + ")");
  }

  @Override
  public void initialize() {
    for(var talon : subsystem.talons){
      check(talon.setSelectedSensorPosition(0,0,50));
      check(talon.getSensorCollection().setQuadraturePosition(0,50));
    }
  }
}
