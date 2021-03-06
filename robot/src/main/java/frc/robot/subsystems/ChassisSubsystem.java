// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.stream.Stream;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ChassisSubsystem extends RobotSubsystem {
  final WPI_TalonSRX leftFront = new WPI_TalonSRX(3);
  final WPI_TalonSRX leftRear = new WPI_TalonSRX(1);
  final WPI_TalonSRX rightFront = new WPI_TalonSRX(5);
  final WPI_TalonSRX rightRear = new WPI_TalonSRX(12);

  final DifferentialDrive drive;
  double rotate = 0.0;
  double fwd = 0.0;

  public ChassisSubsystem() {

    addTalon("leftFront", leftFront);
   // addTalon("leftRear", leftRear);
    addTalon("rightFront", rightFront);
   // addTalon("rightRear", rightRear);

    Stream.of(leftFront, leftRear, rightFront, rightRear).forEach(WPI_TalonSRX::configFactoryDefault);

    SupplyCurrentLimitConfiguration currLimit = new SupplyCurrentLimitConfiguration(true, 25, 25, 0);
    rightFront.configSupplyCurrentLimit(currLimit);
    rightRear.configSupplyCurrentLimit(currLimit);
    leftFront.configSupplyCurrentLimit(currLimit);
    leftRear.configSupplyCurrentLimit(currLimit);
    
    rightFront.setInverted(false);
    leftFront.setInverted(true);

    leftRear.setInverted(InvertType.FollowMaster);
    rightRear.setInverted(InvertType.FollowMaster);
    
    rightFront.setSensorPhase(false);
    rightRear.setSensorPhase(true);

    drive = new DifferentialDrive(leftFront,rightFront);
    addChild("drivetrain", drive);
    tab.add(drive);
    drive.setMaxOutput(1.0);

    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    tab.addNumber("rotate",()->this.rotate);
    tab.addNumber("fwd", ()->this.fwd);
  }

  public void drive(double fwd,double rotate){
    this.rotate = rotate;
    this.fwd = fwd;
    drive.arcadeDrive(fwd, rotate);
  }
  @Override
  public void periodic() {
   
  }

  @Override
  public void simulationPeriodic() {
   
  }
}
