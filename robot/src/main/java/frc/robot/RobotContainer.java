// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CargoCommands;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ManualHangarControl;
import frc.robot.commands.ShuffleboardCommandsTab;
import frc.robot.commands.SimpleAutoDrive;
import frc.robot.commands.StopMotors;
import frc.robot.subsystems.CargoSubsystem;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.HangarSubsystem;
import frc.robot.subsystems.RobotSubsystem;
import frc.robot.util.TalonFaultsReporter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ChassisSubsystem chassis = new ChassisSubsystem();
  CargoSubsystem cargo = new CargoSubsystem();
  HangarSubsystem hangar = new HangarSubsystem();

  public final List<RobotSubsystem> subsystems = List.of(chassis, cargo, hangar);

  final Joystick stick = new Joystick(0);
  final XboxController xbox = new XboxController(1);

  DriveWithJoystick drive_cmd = new DriveWithJoystick(stick, chassis);
  ManualHangarControl manual_hangar_control = new ManualHangarControl(hangar, xbox);

  PowerDistribution pdp = new PowerDistribution();

  // HangarTelemetry hangarTelemetry = new HangarTelemetry(hangar);
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    chassis.setDefaultCommand(drive_cmd);
    hangar.setDefaultCommand(new StopMotors(hangar));
    // hangar.setDefaultCommand(manual_hangar_control);

    //SmartDashboard.putData("drive command", drive_cmd);
    // SmartDashboard.putData("hangar command",hangar_cmd);
    //SmartDashboard.putData("auto drive", new SimpleAutoDrive(chassis, -0.5, 2500));

    SmartDashboard.putData(pdp);

    for (var sys : subsystems) {
      for (var talon : sys.talons) {
        TalonFaultsReporter.instrument(talon);
      }
    }

    ShuffleboardCommandsTab.create(this);
    setupCamera();
  }

  void setupCamera() {
    try {
      CameraServer.startAutomaticCapture();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(stick, 1).toggleWhenPressed(CargoCommands.shoot(cargo));
    new JoystickButton(stick, 2).toggleWhenPressed(CargoCommands.autofeed(cargo));

    var hangarCommands = hangar.commands;

    new JoystickButton(xbox, XboxController.Button.kRightBumper.value).whenPressed(hangarCommands.hooksOut());
    new JoystickButton(xbox, XboxController.Button.kLeftBumper.value).whenPressed(hangarCommands.hooksIn());
    new JoystickButton(xbox, XboxController.Button.kY.value).whenPressed(hangarCommands.armOut());

    CommandBase winchToggle = new CommandBase() {
      boolean cancelWinch = false;

      @Override
      public void initialize() {
        if (cancelWinch) {
          hangarCommands.winchLift().cancel();
        } else {
          hangarCommands.winchLift().schedule();
        }
        cancelWinch = !cancelWinch;
      }

      @Override
      public boolean isFinished() {
        return true;
      }

    };

    new JoystickButton(xbox, XboxController.Button.kA.value).whenPressed(winchToggle);

    new JoystickButton(xbox, XboxController.Button.kB.value).whenPressed(hangarCommands.armToNextRung2());
    new JoystickButton(xbox, XboxController.Button.kStart.value).whenPressed(hangarCommands.toHome());
    new JoystickButton(xbox, XboxController.Button.kBack.value).toggleWhenPressed(manual_hangar_control);
  }

  public Command getAutonomousCommand() {
    return CargoCommands.shoot(cargo).andThen(new SimpleAutoDrive(chassis, 0.5, 3500));
  }
}
