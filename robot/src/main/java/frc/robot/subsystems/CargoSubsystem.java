// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CargoSubsystem extends RobotSubsystem {
  WPI_TalonSRX feedworks = new WPI_TalonSRX(6);
  WPI_TalonSRX intake = new WPI_TalonSRX(2);
  WPI_TalonSRX shooter = new WPI_TalonSRX(27);

  public CargoSubsystem() {
    addTalon("feedworks", feedworks);
    addTalon("intake", intake);
    addTalon("shooter", shooter);

    shooter.setInverted(true);
    intake.setInverted(true);
    feedworks.setInverted(false);

    shooter.setSensorPhase(false);
    intake.setSensorPhase(true);
    feedworks.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    
  }
}
