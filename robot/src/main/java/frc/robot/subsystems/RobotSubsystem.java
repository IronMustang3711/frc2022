// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotSubsystem extends SubsystemBase {

  public RobotSubsystem() {}

 protected  void addTalon(String name, WPI_TalonSRX talon){
  addChild(name + '('+talon.getDeviceID()+')', talon);
} 
}
