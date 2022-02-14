// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangarSubsystem extends SubsystemBase {
  WPI_TalonSRX arm = new WPI_TalonSRX(4);
  WPI_TalonSRX hook = new WPI_TalonSRX(15);
  WPI_TalonSRX winch = new WPI_TalonSRX(10);

  public HangarSubsystem() {
    addChild("arm", arm);
    addChild("hook", hook);
    addChild("winch", winch);
  }

  @Override
  public void periodic() {

  }
}
