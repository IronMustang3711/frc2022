package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.RobotSubsystem;
import frc.robot.util.TalonUtil;

public class FaultCheck extends RobotCommand {
  public FaultCheck(RobotSubsystem subsystem) {
    super(subsystem);
  }

  @Override
  public void initialize() {
    StickyFaults stickyFaults = new StickyFaults();
    Faults faults = new Faults();
    for(var talon: subsystem.talons){
      talon.getStickyFaults(stickyFaults);
      if(stickyFaults.hasAnyFault()){
        DriverStation.reportError(TalonUtil.getName(talon) + "STICKY FAULTS:\n"+stickyFaults.toString(),false);
      }
      talon.getFaults(faults);
      if(faults.hasAnyFault()){
        DriverStation.reportError(TalonUtil.getName(talon) + "FAULTS:\n"+stickyFaults.toString(),false);
      }
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
