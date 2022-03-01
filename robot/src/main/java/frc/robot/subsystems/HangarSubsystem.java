// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.EncoderReset;
import frc.robot.commands.FancyPosition;
import frc.robot.commands.HangarCommands;
import frc.robot.util.TalonUtil;

public class HangarSubsystem extends RobotSubsystem {
  public WPI_TalonSRX arm = new WPI_TalonSRX(4);
  public WPI_TalonSRX hook = new WPI_TalonSRX(15);
  public WPI_TalonSRX winch = new WPI_TalonSRX(10);

  NetworkTableEntry armSetpoint;
  NetworkTableEntry winchSetpoint;
  NetworkTableEntry hookSetpoint;
  NetworkTableEntry winchCurrentSetpoint;

  public HangarCommands commands;

  List<Runnable> telems = new ArrayList<>();

  class PositionThing extends FancyPosition {
    DoubleSupplier setpointFN;

    public PositionThing(WPI_TalonSRX talon, DoubleSupplier setpoint) {
      super(talon, setpoint.getAsDouble());
      this.setpointFN = setpoint;
      addRequirements(HangarSubsystem.this);
    }

    @Override
    public void initialize() {
      HangarSubsystem.setupControlMode(talon, ControlMode.MotionMagic);
    }

    @Override
    public double getSetpoint() {
      return setpointFN.getAsDouble();
      // return winchSetpoint.getDouble(0.0);
    }

  }

  public HangarSubsystem() {
    addTalon("arm", arm);
    addTalon("hook", hook);
    addTalon("winch", winch);

    commands = new HangarCommands(this);

    setupTalonTelemWidget(winch);
    setupTalonTelemWidget(arm);
    setupTalonTelemWidget(hook);

    TalonConfigs.configureHangarTalons(this);

    tab.add("Zero encoders", new EncoderReset(this));

    armSetpoint = tab.add("arm setpoint", 0.0).getEntry();
    armSetpoint.addListener((EntryNotification notification) -> {
      this.armSetpointChanged(notification.value.getDouble());
    }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew);

    winchSetpoint = tab.add("winch setpoint", 0.0).getEntry();
    winchSetpoint.addListener((notification) -> {
      this.winchSetpointChanged(notification.value.getDouble());
    }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew);

    hookSetpoint = tab.add("hook setpoint", 0.0).getEntry();
    hookSetpoint.addListener((notification) -> {
      this.hookSetpointChanged(notification.value.getDouble());
    }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew);

    winchCurrentSetpoint = tab.add("winch current setpoint", 0.0).getEntry();


    
    tab.add("winch control", new PositionThing(winch, () -> winchSetpoint.getValue().getDouble()));
    tab.add("arm control", new PositionThing(arm, () -> armSetpoint.getValue().getDouble()));
    tab.add("hook control", new PositionThing(hook, () -> hookSetpoint.getValue().getDouble()));
    tab.add("winch cc", new CommandBase() {
      @Override
      public void initialize() {
        HangarSubsystem.setupControlMode(winch, ControlMode.Current);
      }

      @Override
      public void execute() {
        hook.set(ControlMode.Current, winchCurrentSetpoint.getValue().getDouble());
      }

    });

    var seqs = tab.getLayout("command sequences", BuiltInLayouts.kList);

    seqs.add(commands.armOut());
    seqs.add(commands.toHome());
    seqs.add(commands.winchLift());
    seqs.add(commands.hooksOut());
    seqs.add(commands.hooksIn());
    seqs.add(commands.armToNextRung());


  }

 

  private void hookSetpointChanged(double double1) {
  }

  private void winchSetpointChanged(double double1) {
  }

  private void armSetpointChanged(double double1) {
  }

  void setupTalonTelemWidget(WPI_TalonSRX talon) {
    var container = tab.getLayout(TalonUtil.getName(talon), BuiltInLayouts.kList);
    telems.add(new TalonUtil.BasicTalonTelemetry(talon, container));
    telems.add(new TalonUtil.EncoderTelemetry(talon, container));
    telems.add(new TalonUtil.ClosedLoopTelemetry(talon, container));
    telems.add(new TalonUtil.MotionMagicTelemetry(talon, container));

    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
  }

  @Override
  public void periodic() {
    telems.forEach(Runnable::run);
  }

  public static void setupControlMode(WPI_TalonSRX talon, ControlMode mode) {
    if (TalonUtil.MOTION_PROFILE_MODES.contains(mode))
      talon.selectProfileSlot(0, 0);

    else if (ControlMode.Current.equals(mode))
      talon.selectProfileSlot(1, 0);
    else
      throw new RuntimeException("control mode not set up!! ");
  }
}
