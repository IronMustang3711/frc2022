// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.HangarCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RunFeedworks;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.subsystems.CargoSubsystem;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.HangarSubsystem;
import frc.robot.subsystems.HangarTelemetry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ChassisSubsystem chassis = new ChassisSubsystem();
  CargoSubsystem cargo = new CargoSubsystem();
  HangarSubsystem hangar = new HangarSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(chassis);

  final Joystick stick = new Joystick(0);
  final XboxController xbox = new XboxController(1);


  DriveWithJoystick drive_cmd = new DriveWithJoystick(stick, chassis);
  HangarCommand hangar_cmd = new HangarCommand(hangar, xbox);


  PowerDistribution pdp = new PowerDistribution();
HangarTelemetry hangarTelemetry = new HangarTelemetry(hangar);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    chassis.setDefaultCommand(drive_cmd);
    hangar.setDefaultCommand(hangar_cmd);

    SmartDashboard.putData("drive command",drive_cmd);
    SmartDashboard.putData("hangar command",hangar_cmd);


    SmartDashboard.putData(pdp);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(stick, 1).whileHeld(new RunShooter(cargo));
    new JoystickButton(stick, 3).toggleWhenPressed(new RunIntake(cargo));
    new JoystickButton(stick, 2).whileHeld(new RunFeedworks(cargo));

    //FIXME:
    //new JoystickButton(stick, 4).whenPressed(new IntakeCommand(cargo));
   // new JoystickButton(stick, 4).whenPressed(IntakeCommand.autoFeed(cargo));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
