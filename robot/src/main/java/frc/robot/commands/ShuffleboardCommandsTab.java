// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.RobotContainer;

/** Add your docs here. */
public class ShuffleboardCommandsTab {

    public static void create(RobotContainer robot){
        var tab = Shuffleboard.getTab("Commands");
        tab.add(new JoystickErrorMessageSilencer());

        for(var sys : robot.subsystems){
            var lst = tab.getLayout(sys.getName(), BuiltInLayouts.kList);
            lst.add(new ClearFaults(sys));
            lst.add(new StopMotors(sys));
            lst.add(new EncoderReset(sys));

        }

    }
}
