// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.stream.Stream;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ChassisSubsystem extends RobotSubsystem {
  final WPI_TalonSRX leftFront = new WPI_TalonSRX(3);
  final WPI_TalonSRX leftRear = new WPI_TalonSRX(1);
  final WPI_TalonSRX rightFront = new WPI_TalonSRX(5);
  final WPI_TalonSRX rightRear = new WPI_TalonSRX(12);

  final DifferentialDrive drive;

  public ChassisSubsystem() {

    addTalon("leftFront", leftFront);
   // addTalon("leftRear", leftRear);
    addTalon("rightFront", rightFront);
   // addTalon("rightRear", rightRear);

    Stream.of(leftFront, leftRear, rightFront, rightRear).forEach(WPI_TalonSRX::configFactoryDefault);
    
    rightFront.setInverted(false);
    leftFront.setInverted(true);

    leftRear.setInverted(InvertType.FollowMaster);
    rightRear.setInverted(InvertType.FollowMaster);
    
    rightFront.setSensorPhase(false);
    rightRear.setSensorPhase(true);

    drive = new DifferentialDrive(leftFront,rightFront);
    drive.setMaxOutput(0.5);

    leftRear.follow(leftFront);
    rightRear.follow(rightFront);
  }

  public void drive(double fwd,double rotate){

    drive.arcadeDrive(fwd, rotate);

  }
  @Override
  public void periodic() {
   
  }

  @Override
  public void simulationPeriodic() {
   
  }
}
