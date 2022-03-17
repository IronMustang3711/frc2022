// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveToBall extends CommandBase {

  PhotonCamera camera;

  private boolean oppositeTeam;
  public DriveToBall(PhotonCamera camera, ChassisSubsystem chassis, boolean oppositeTeam) {
    this.camera = camera;
    this.chassis = chassis;
    this.oppositeTeam = oppositeTeam;
  
    addRequirements(chassis);
  }

  private double driveSpeed;

final ChassisSubsystem chassis;

 



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(oppositeTeam){
      var idx = camera.getPipelineIndex(); 
      if(idx == 0 ) idx =1;
      else idx = 0;
      camera.setPipelineIndex(idx);
    }

    driveSpeed = 0;


    
    //camera.setPipelineIndex(m_pipeline);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var result = camera.getLatestResult();
    double turnSpeed = 0;

    if (result.hasTargets() && (result.getBestTarget().getArea() > 1) )
    {
      double pitch = result.getBestTarget().getPitch();
      double yaw = result.getBestTarget().getYaw();
      SmartDashboard.putNumber("yaw",yaw);
      SmartDashboard.putNumber("pitch",pitch);
      driveSpeed = (pitch + 17) / 34; // range from 0 to 1

      driveSpeed *= 0.7;
      double maxFwd = 0.6;
      if (driveSpeed  < maxFwd)
        driveSpeed = maxFwd;

      turnSpeed = yaw * 0.05;  // yaw of 3.3 is full turn
      turnSpeed += -0.1;  // bunny turn correction
      double maxTurn = 0.7;
      if (turnSpeed > maxTurn)
        turnSpeed = maxTurn;
      else if  (turnSpeed < -maxTurn)
        turnSpeed = -maxTurn;
    }
   
    chassis.drive(-driveSpeed, -turnSpeed);  // normal speed
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(oppositeTeam){
      var idx = camera.getPipelineIndex(); 
      if(idx == 0 ) idx =1;
      else idx = 0;
      camera.setPipelineIndex(idx);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
