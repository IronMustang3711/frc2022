// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveWithJoystick extends CommandBase {

final Joystick stick;
final ChassisSubsystem chassis;

  public DriveWithJoystick(Joystick stick, ChassisSubsystem chassis) {
  this.stick = stick;
  this.chassis = chassis;
    addRequirements(chassis);
}



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double fwd = stick.getY();
    //fwd *= -1;
  //  fwd = fwd*fwd*fwd;



    double rotate = -0.75*stick.getTwist();
   // rotate = rotate*rotate*rotate;

    chassis.drive(fwd, rotate);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
