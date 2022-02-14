// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.stream.Stream;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {
  WPI_TalonSRX leftFront = new WPI_TalonSRX(3);
  WPI_TalonSRX leftRear = new WPI_TalonSRX(1);
  WPI_TalonSRX rightFront = new WPI_TalonSRX(5);
  WPI_TalonSRX rightRear = new WPI_TalonSRX(12);


  public ChassisSubsystem() {

    addChild("leftFront", leftFront);
    addChild("leftRear", leftRear);
    addChild("rightFront", rightFront);
    addChild("rightRear", rightRear);

    Stream.of(leftFront, leftRear, rightFront, rightRear).forEach(WPI_TalonSRX::configFactoryDefault);
    
    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    rightFront.setInverted(true);
    rightRear.setInverted(false);

    leftRear.setInverted(InvertType.FollowMaster);
    rightRear.setInverted(InvertType.FollowMaster);

    rightFront.setSensorPhase(true);
    rightRear.setSensorPhase(true);

  }

  @Override
  public void periodic() {
   
  }

  @Override
  public void simulationPeriodic() {
   
  }
}
