// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.util.TalonUtil;

public class FancyPosition extends CommandBase {
double setpoint;
long startTime;
double initialPosition;
WPI_TalonSRX talon;

  public FancyPosition(WPI_TalonSRX talon,double setpoint) {
    this.talon = talon;
  }

  public void setSetpoint(double setpoint){
    this.setpoint = setpoint;
  }
  public double getSetpoint(){ return setpoint;}

  /**
   * 
   * @return the time that this command has been running in seconds
   */
  public double getElapsedTime(){
    long now = System.currentTimeMillis();
    return (now - startTime) / 1000.0;
  }

  public double getMotionProgress(){
    double totalDistance = setpoint - initialPosition;
    double trajPos = talon.getActiveTrajectoryPosition();
    return (trajPos - initialPosition) / totalDistance;
  }
  // public boolean isMotionFinished(){
  //   return Math.abs(setpoint - talon.getActiveTrajectoryPosition()) < 1.0;
  // }
  public boolean isMotionFinished(){
    double closedLoopTarget = talon.getClosedLoopTarget();
    double trajPos = talon.getActiveTrajectoryPosition();
    return Math.abs(closedLoopTarget - trajPos) < 1.0;
  }
  @Override
  public
   void initialize() {
    TalonUtil.setupControlMode(talon, ControlMode.MotionMagic);
    initialPosition = talon.getSelectedSensorPosition();
    startTime = System.currentTimeMillis();
    if(Robot.debug)
      Shuffleboard.addEventMarker("MM_"+getName() + "_Init", EventImportance.kNormal);

  }

  @Override
  public void execute() {
    talon.set(ControlMode.MotionMagic, setpoint);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(Robot.debug)
    Shuffleboard.addEventMarker("MM_"+getName() + "_End", EventImportance.kNormal);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return  isMotionFinished();

  }
}
