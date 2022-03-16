// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.Timer;  
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.PhotonCamera;

public class DriveToBall extends CommandBase {

  PhotonCamera camera = new PhotonCamera("rpiUSB");  // %r3
  private double driveSpeed;

final ChassisSubsystem chassis;
final int m_pipeline;

  public DriveToBall(ChassisSubsystem chassis, int pipelineIndex) {

  this.chassis = chassis;
    addRequirements(chassis);
  m_pipeline = pipelineIndex;
}



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSpeed = 0;
    camera.setPipelineIndex(m_pipeline);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var result = camera.getLatestResult();
    SmartDashboard.putBoolean("targets",result.hasTargets());
    double turnSpeed = 0;

    if (result.hasTargets() && (result.getBestTarget().getArea() > 1) )
    {
      double pitch = result.getBestTarget().getPitch();
      double yaw = result.getBestTarget().getYaw();
      SmartDashboard.putNumber("yaw",yaw);
      SmartDashboard.putNumber("pitch",pitch);
      driveSpeed = (pitch + 17) / 34; // range from 0 to 1

      driveSpeed *= 0.7;
      if (driveSpeed  < 0.5)
        driveSpeed = 0.5;
      turnSpeed = yaw * 0.1;  // yaw of 3.3 is full turn
      turnSpeed += -0.1;  // bunny turn correction
      double maxTurn = 0.4;
      if (turnSpeed > maxTurn)
        turnSpeed = maxTurn;
      else if  (turnSpeed < -maxTurn)
        turnSpeed = -maxTurn;
    }
   
    chassis.drive(-driveSpeed, -turnSpeed);  // normal speed
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
