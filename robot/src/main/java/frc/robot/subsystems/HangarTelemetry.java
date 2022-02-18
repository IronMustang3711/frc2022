// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/** Add your docs here. */
public class HangarTelemetry {



    ShuffleboardTab tab;
    WPI_TalonSRX arm;
    WPI_TalonSRX winch;
    WPI_TalonSRX hook;

    
    public HangarTelemetry(HangarSubsystem hangar){
        arm = hangar.arm;
        winch = hangar.winch;
        hook = hangar.hook;

        tab = Shuffleboard.getTab("Hangar");


        for(var talon : List.of(arm,winch,hook)){
            String name = SendableRegistry.getName(talon); //talon.getDescription()
           var container = tab.getLayout(name, BuiltInLayouts.kList);
            container.addNumber("output",()->talon.get());
           
            container.addNumber("position", ()-> talon.getSensorCollection().getQuadraturePosition());
            container.addNumber("velocity", ()->talon.getSensorCollection().getQuadratureVelocity());
            container.addNumber("current", ()->talon.getStatorCurrent());
           // .addNumber("position",()->talon.gry)
        }
    }
}
