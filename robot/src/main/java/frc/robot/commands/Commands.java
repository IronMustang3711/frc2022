// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/** Add your docs here. */
public class Commands {

    // public static Command constantCurrent(WPI_TalonSRX talon,double current){
    //     return new CommandBase() {
    //         @Override
    //         public void initialize() {
    //             TalonUtil.setupControlMode(talon, ControlMode.Current);
    //         }
    //         @Override
    //         public void execute() {
    //             talon.set(ControlMode.Current,current);
    //         }

    //     };
    // }

    public static Command stopMotor(WPI_TalonSRX talon){
        return new InstantCommand(talon::neutralOutput); 
    }

}
