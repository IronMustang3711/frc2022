// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.IntakeCommand;

public class CargoSubsystem extends RobotSubsystem {
 public WPI_TalonSRX feedworks = new WPI_TalonSRX(6);
  public WPI_TalonSRX intake = new WPI_TalonSRX(2);
  public WPI_TalonSRX shooter = new WPI_TalonSRX(27);

  public DigitalInput upperPhotoeye = new DigitalInput(0);
  DigitalInput lowerPhotoeye = new DigitalInput(1);

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

    intake.configOpenloopRamp(0.0);

  
    addChild("upperPhotoeye", upperPhotoeye);
    addChild("lowerPhotoeye", lowerPhotoeye);
 
    

     SmartDashboard.putData( upperPhotoeye);
     SmartDashboard.putData(lowerPhotoeye);

   // tab.add(upperPhotoeye);//.withWidget(BuiltInWidgets.kBooleanBox);
   // tab.add(lowerPhotoeye);//.withWidget(BuiltInWidgets.kBooleanBox);
    tab.addBoolean("lowerPhotoeye", ()->lowerPhotoeye.get());
    tab.addBoolean("upperPhotoeye",()->upperPhotoeye.get());

    tab.addString("current command",
    ()->  getCurrentCommand() == null ? "None" : getCurrentCommand().toString()
    );


    var feedworksCommand = new IntakeCommand.FeedworksCommand(feedworks);
    //  feedworksCommand.addRequirements(cargo);
     IntakeCommand intakeCommand = new IntakeCommand(this);
  
     Command c = new RunCommand(()->{
      if(upperPhotoeye.get()){
        feedworksCommand.cancel();
      }
     });

     Command intakeStopper = new RunCommand(()->{
      if(lowerPhotoeye.get() && upperPhotoeye.get()){
        intakeCommand.cancel();
      
      }
     });

     var cc = c.alongWith(intakeStopper,intakeCommand.asProxy(),feedworksCommand.asProxy());
     tab.add("autofeed",cc);
     tab.add("feedworksCmd",feedworksCommand);
     tab.add("intakeCmd",intakeCommand);




    Command shootCommand = new StartEndCommand(
      ()->shooter.set(0.9),()->shooter.set(0.0) ) ;

      
     //TODO: add to button, make sure it can be canceled before shooting starts
     var shootSequence =  shootCommand.alongWith(new WaitCommand(2).andThen(
        intakeCommand.asProxy().alongWith(feedworksCommand.asProxy())
      ));
     tab.add("shoot seq",shootSequence);
    
    }

  public boolean isLowerPhotoeyeBlocked(){
    return lowerPhotoeye.get();
  }

  @Override
  public void periodic() {
    
  }
}
