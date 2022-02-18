// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class HangarSubsystem extends RobotSubsystem {
  public WPI_TalonSRX arm = new WPI_TalonSRX(4);
  public WPI_TalonSRX hook = new WPI_TalonSRX(15);
  public WPI_TalonSRX winch = new WPI_TalonSRX(10);

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

    arm.setNeutralMode(NeutralMode.Brake);
    winch.setNeutralMode(NeutralMode.Brake);
    hook.setNeutralMode(NeutralMode.Brake);

    winch.configContinuousCurrentLimit(10);
    winch.configPeakCurrentLimit(0);
   // winch.configPeakCurrentDuration(100);
   // SupplyCurrentLimitConfiguration limitConfiguration = new SupplyCurrentLimitConfiguration(true, 15, 20, 100);
  //  winch.configSupplyCurrentLimit(limitConfiguration);
    winch.enableCurrentLimit(true);

    winch.configOpenloopRamp(1.0);



  }

  @Override
  public void periodic() {

  }
}
