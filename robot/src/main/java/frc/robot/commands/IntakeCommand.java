// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.CargoSubsystem;

public class IntakeCommand extends CommandBase {


 public  static class FeedworksCommand extends CommandBase {
      WPI_TalonSRX feed;

      public FeedworksCommand(WPI_TalonSRX feed) {
        this.feed = feed;
       
      }

      @Override
      public void execute() {
        feed.set(0.5);
      }
      @Override
      public void end(boolean interrupted) {
          feed.neutralOutput();
      }


  }



  private CargoSubsystem cargo;

  /** Creates a new IntakeCommand. */
  public IntakeCommand(CargoSubsystem cargo) {
    this.cargo = cargo;
    addRequirements(cargo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cargo.intake.set(0.5);
  //  cargo.feedworks.set(0.5);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //cargo.feedworks.neutralOutput();
   cargo.intake.neutralOutput();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;//cargo.upperPhotoeye.get();
  }

  
  public static Command autoFeed(CargoSubsystem cargo){
    FeedworksCommand feedworksCommand = new FeedworksCommand(cargo.feedworks);
  //  feedworksCommand.addRequirements(cargo);
   IntakeCommand intakeCommand = new IntakeCommand(cargo);

   Command c = new RunCommand(()->{
    if(cargo.upperPhotoeye.get()){
      feedworksCommand.cancel();
    }
   });

    return intakeCommand.alongWith(c);
  }
}
