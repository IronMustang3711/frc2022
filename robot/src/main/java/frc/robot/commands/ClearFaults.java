package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.StickyFaults;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.RobotSubsystem;
import frc.robot.util.TalonUtil;

public class ClearFaults extends RobotCommand {
  public ClearFaults(RobotSubsystem subsystem) {
    super(subsystem);
  }

  @Override
  public void initialize() {
    StickyFaults stickyFaults = new StickyFaults();
    for(var talon: subsystem.talons){
      talon.getStickyFaults(stickyFaults);
      if(stickyFaults.hasAnyFault()){
        DriverStation.reportError(TalonUtil.getName(talon) + "CLEARING STICKY FAULTS:\n"+stickyFaults.toString(),false);
        TalonUtil.requireOK(talon.clearStickyFaults(50));
      }
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
