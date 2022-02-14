// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CargoSubsystem extends SubsystemBase {
  WPI_TalonSRX feedworks = new WPI_TalonSRX(6);
  WPI_TalonSRX intake = new WPI_TalonSRX(2);
  WPI_TalonSRX shooter = new WPI_TalonSRX(27);

  public CargoSubsystem() {
    addChild("feedworks", feedworks);
    addChild("intake", intake);
    addChild("shooter", shooter);
  }

  @Override
  public void periodic() {
    
  }
}
