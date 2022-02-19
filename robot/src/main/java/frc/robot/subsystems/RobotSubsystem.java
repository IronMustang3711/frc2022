// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.TalonUtil;

import java.util.ArrayList;
import java.util.List;

public class RobotSubsystem extends SubsystemBase {

  final ShuffleboardTab tab;
  public final List<WPI_TalonSRX> talons = new ArrayList<>();

  public RobotSubsystem() {
    tab = Shuffleboard.getTab(getName());
    tab.add(this);
  }


  protected void addTalon(String name, WPI_TalonSRX talon) {
    addChild(name + '(' + talon.getDeviceID() + ')', talon);
    talons.add(talon);
  }
}
