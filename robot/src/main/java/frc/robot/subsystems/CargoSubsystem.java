// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    shooter.configVoltageCompSaturation(10);
    shooter.enableVoltageCompensation(true);

  
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

     tab.addNumber("shooter velocity", shooter::getSelectedSensorVelocity);
     tab.addNumber("bus voltage", shooter::getBusVoltage);
    
    }

  public boolean isLowerPhotoeyeBlocked(){
    return lowerPhotoeye.get();
  }

  @Override
  public void periodic() {
    
  }

public boolean isUpperPhotoeyeBlocked() {
	return upperPhotoeye.get();
}
}
