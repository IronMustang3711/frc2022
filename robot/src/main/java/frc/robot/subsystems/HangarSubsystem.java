// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import frc.robot.commands.EncoderReset;
import frc.robot.util.TalonUtil;

public class HangarSubsystem extends RobotSubsystem {
  public WPI_TalonSRX arm = new WPI_TalonSRX(4);
  public WPI_TalonSRX hook = new WPI_TalonSRX(15);
  public WPI_TalonSRX winch = new WPI_TalonSRX(10);

  List<Runnable> telems = new ArrayList<>();

  public HangarSubsystem() {
    addTalon("arm", arm);
    addTalon("hook", hook);
    addTalon("winch", winch);

    

    setupTalonTelemWidget(winch);
    setupTalonTelemWidget(arm);
    setupTalonTelemWidget(hook);

    TalonConfigs.configureHangarTalons(this);


    tab.add(new EncoderReset(this));

  }

  void setupTalonTelemWidget(WPI_TalonSRX talon) {
    var container = tab.getLayout(TalonUtil.getName(talon), BuiltInLayouts.kList);
    telems.add(new TalonUtil.BasicTalonTelemetry(talon, container));
    telems.add(new TalonUtil.EncoderTelemetry(talon, container));
    telems.add(new TalonUtil.ClosedLoopTelemetry(talon, container));
  }

  @Override
  public void periodic() {
    telems.forEach(Runnable::run);
  }
}
