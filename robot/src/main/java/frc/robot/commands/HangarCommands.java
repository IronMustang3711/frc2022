// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
public class HangarCommands {


    public static Command armOut(){
        //bring winch(-52000) out & arm
        //winch needs to come out first
        return null;
    }


    public static Command winchLift(){
        //neutral arm, run winch
        return null;
    }


    public static Command unHookAndExtendArm(){
        //requires: hooks in
        //winch out a little, rotate arm, winch out more;
        return null;
    }

    
}
