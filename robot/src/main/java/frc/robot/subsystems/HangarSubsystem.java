// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class HangarSubsystem extends RobotSubsystem {
  final WPI_TalonSRX arm = new WPI_TalonSRX(4);
  final WPI_TalonSRX hook = new WPI_TalonSRX(15);
  final WPI_TalonSRX winch = new WPI_TalonSRX(10);

  public HangarSubsystem() {
    addTalon("arm", arm);
    addTalon("hook", hook);
    addTalon("winch", winch);

    arm.setInverted(true);
    winch.setInverted(true);
    hook.setInverted(false);

    arm.setSensorPhase(false);
    winch.setSensorPhase(false);
    hook.setSensorPhase(true);
  }

  @Override
  public void periodic() {

  }
}
