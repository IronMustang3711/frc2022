// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class SimpleAutoDrive extends CommandBase {
  ChassisSubsystem chassis;
  double amt;
  long time;
  public SimpleAutoDrive(ChassisSubsystem chassis,double amt, long time_millis) {
    this.amt = amt;
    this.time = time_millis;
    this.chassis = chassis;
    addRequirements(chassis);
  }


 long startTime = System.currentTimeMillis();

 long elapsed(){ return System.currentTimeMillis() - startTime;}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    chassis.drive(amt, 0);
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chassis.talons.forEach(WPI_TalonSRX::neutralOutput);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   return elapsed() > time;
  }
}
